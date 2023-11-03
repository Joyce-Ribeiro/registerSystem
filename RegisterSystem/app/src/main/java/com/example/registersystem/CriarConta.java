package com.example.registersystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.registersystem.conexao.BDcliente;
import com.example.registersystem.conexao.BDempresa;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

public class CriarConta extends AppCompatActivity {
    private static final int RADIO_CNPJ = R.id.radio_cnpj;
    private static final int RADIO_CPF = R.id.radio_cpf;

    final Context context = this;
    private BDcliente bDcliente;
    private BDempresa bDempresa;
    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        AppCompatButton addContaButton = findViewById(R.id.addConta);
        RadioButton radioCPF = findViewById(R.id.radio_cpf);
        RadioButton radioCNPJ = findViewById(R.id.radio_cnpj);

        addContaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioCPF.isChecked()) {
                    EditText editTextNome = findViewById(R.id.editTextNome);
                    EditText editTextCpf = findViewById(R.id.editTextCpfCnpj);
                    EditText editTextTelefone = findViewById(R.id.editTextTelefone);
                    EditText editTextEmail = findViewById(R.id.editTextEmail);
                    EditText editTextSenha = findViewById(R.id.editTextSenha);

                    String nome = editTextNome.getText().toString();
                    String cpf = editTextCpf.getText().toString();
                    String telefone = editTextTelefone.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String senha = editTextSenha.getText().toString();

                    criarConexao();

                    Cliente cliente =new Cliente(nome,telefone, email,cpf,senha);

                    try {
                        bDcliente.inserirCliente(cliente);
                        finish();

                    }catch (SQLException ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setTitle(R.string.title_erro);
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.action_ok, null);
                        dlg.show();
                    }


                } else if (radioCNPJ.isChecked()) {
                    EditText editTextNome = findViewById(R.id.editTextNome);
                    EditText editTextCnpj = findViewById(R.id.editTextCpfCnpj);
                    EditText editTextTelefone = findViewById(R.id.editTextTelefone);
                    EditText editTextEmail = findViewById(R.id.editTextEmail);
                    EditText editTextSenha = findViewById(R.id.editTextSenha);

                    String nome = editTextNome.getText().toString();
                    String cnpj = editTextCnpj.getText().toString();
                    String telefone = editTextTelefone.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String senha = editTextSenha.getText().toString();

                    criarConexao();

                    Empresa empresa =new Empresa(nome, telefone, email, cnpj, senha);

                    try {
                        bDempresa.inserirEmpresa(empresa);
                        finish();

                    }catch (SQLException ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setTitle(R.string.title_erro);
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.action_ok, null);
                        dlg.show();
                    }
                }
            }
        });
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
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if (view.getId()==RADIO_CNPJ && checked) {
            changeLabelToCNPJ();
        } else if (view.getId()==RADIO_CPF && checked) {
            changeLabelToCPF();
        }
    }

    private void changeLabelToCNPJ() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CNPJ:");
    }

    // Método para alterar o texto para "CPF"
    private void changeLabelToCPF() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CPF:");
    }
}
