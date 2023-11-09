package com.example.registersystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.registersystem.conexao.BDContrato;
import com.example.registersystem.conexao.BDcliente;
import com.example.registersystem.conexao.BDempresa;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Contrato;
import com.example.registersystem.model.Empresa;

public class CriarContrato extends AppCompatActivity {
    final Context context = this;
    private BDcliente bDcliente;
    private BDempresa bDempresa;
    private BDContrato bdContrato;
    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_contrato);

        AppCompatButton addContaButton = findViewById(R.id.criarContrato);
        addContaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EditText editTextCnpj = findViewById(R.id.editTextCnpj);
                    String cnpj = editTextCnpj.getText().toString();
                    EditText editTextCpf = findViewById(R.id.editTextCpf);
                    String cpf = editTextCpf.getText().toString();

                    criarConexao();
                    Cliente cliente = bDcliente.buscarCliente(cpf);
                    Empresa empresa = bDempresa.buscarEmpresa(cnpj);
                    if(cliente==null || empresa==null){
                        showErrorMessage("Credenciais Incorretas");
                        return;
                    }
                    Contrato contrato =new Contrato(cliente, empresa);

                    try {
                        bdContrato.inserirContrato(contrato.getEmpresa().getCnpj(),contrato.getCliente().getCpf());
                        finish();

                    }catch (SQLException ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setTitle(R.string.title_erro);
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.action_ok, null);
                        dlg.show();
                    }



            }
        });
    }
    private void showErrorMessage(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.title_erro);
        dlg.setMessage(message);
        dlg.setNeutralButton(R.string.action_ok, null);
        dlg.show();
    }
    private void criarConexao(){
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();

            bDcliente = new BDcliente(conexao);
            bDempresa = new BDempresa(conexao);
            bdContrato = new BDContrato(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }
    }

}
