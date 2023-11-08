package com.example.registersystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.registersystem.conexao.BDcliente;
import com.example.registersystem.conexao.BDempresa;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class EntrarConta extends AppCompatActivity {
    private static final int RADIO_CNPJ = R.id.radio_cnpj;
    private static final int RADIO_CPF = R.id.radio_cpf;

    private BDcliente bDcliente;
    private BDempresa bDempresa;

    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_conta);

        AppCompatButton loginConta = findViewById(R.id.loginConta);

        RadioButton radioCPF = findViewById(R.id.radio_cpf);
        RadioButton radioCNPJ = findViewById(R.id.radio_cnpj);

        loginConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioCPF.isChecked()) {

                    EditText editTextCpf = findViewById(R.id.editTextCpfCnpj);
                    EditText editTextSenha = findViewById(R.id.editTextSenha);

                    String cpf = editTextCpf.getText().toString();
                    String senha = editTextSenha.getText().toString();

                    criarConexao();

                    Cliente cliente = bDcliente.buscarCliente(cpf, senha);
                    if(cliente==null){
                        showErrorMessage("Credenciais Incorretas.");
                    }

                    if (cliente != null) {
                        ArrayList<Empresa> empresas = bDcliente.buscarEmpresas(cliente);
                        if (empresas == null) {
                            empresas = new ArrayList<>();
                        }
                        Intent intent = new Intent(EntrarConta.this, RecyclerViewEmpresas.class);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("empresasList", empresas);
                        startActivity(intent);
                    }


                } else if (radioCNPJ.isChecked()) {
                    EditText editTextCnpj = findViewById(R.id.editTextCpfCnpj);
                    EditText editTextSenha = findViewById(R.id.editTextSenha);

                    String cnpj = editTextCnpj.getText().toString();
                    String senha = editTextSenha.getText().toString();

                    criarConexao();

                    Empresa empresa = bDempresa.buscarEmpresa(cnpj, senha);

                    if(empresa==null){
                        showErrorMessage("Credenciais Incorretas.");
                    }

                    if (empresa != null) {
                        ArrayList<Cliente> clientes = bDempresa.buscarClientes(empresa);
                        if (clientes == null) {
                            clientes = new ArrayList<>();
                        }
                        Intent intent = new Intent(EntrarConta.this, RecyclerViewClientes.class);
                        intent.putExtra("empresa", empresa);
                        intent.putExtra("clientesList", clientes);
                        startActivity(intent);
                    }

                }
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId()==RADIO_CNPJ && checked) {
            changeLabelToCNPJ();
        } else if (view.getId()==RADIO_CPF && checked) {
            changeLabelToCPF();
        }
    }

    private void criarConexao(){
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();

            bDcliente = new BDcliente(conexao);
            bDempresa = new BDempresa(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }
    }
    private void changeLabelToCNPJ() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CNPJ:");
    }

    private void changeLabelToCPF() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CPF:");
    }
    private void showErrorMessage(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.title_erro);
        dlg.setMessage(message);
        dlg.setNeutralButton(R.string.action_ok, null);
        dlg.show();
    }
}

