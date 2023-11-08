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

import java.util.ArrayList;

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

                    if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                        showErrorMessage("Por Favor preencha todas as lacunas.");
                        return;
                    }
                    if (!isValidCPF(cpf)) {
                        showErrorMessage("CPF Invalido.");
                        return;
                    }

                    criarConexao();

                    Cliente cliente = new Cliente(nome, telefone, email, cpf, senha);

                    try {
                        bDcliente.inserirCliente(cliente);
                        finish();

                    } catch (SQLException ex) {
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

                    if (nome.isEmpty() || cnpj.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                        showErrorMessage("Por Favor preencha todas as lacunas.");
                        return;
                    }
                    if (!isValidCNPJ(cnpj)) {
                        showErrorMessage("CNPJ Invalido.");
                        return;
                    }

                    criarConexao();

                    Empresa empresa = new Empresa(nome, telefone, email, cnpj, senha);

                    try {
                        bDempresa.inserirEmpresa(empresa);
                        finish();

                    } catch (SQLException ex) {
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

    private boolean isValidCPF(String cpf) {
        int primeiroDigito = 0;
        int segundoDigito = 0;

        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }
        int soma = 0;
        String digitos = cpf.substring(0, 9);

        for (int i = 0; i < 9; i++) {
            soma += Integer.parseInt(String.valueOf(digitos.charAt(i))) * (10 - i);
        }
        if (soma % 11 < 2) {
            primeiroDigito = 0;
        } else {
            primeiroDigito = 11 - (soma % 11);
        }
        soma = 0;
        digitos = cpf.substring(0, 9) + primeiroDigito;

        for (int i = 0; i < 10; i++) {
            soma += Integer.parseInt(String.valueOf(digitos.charAt(i))) * (11 - i);
        }
        if (soma % 11 < 2) {
            segundoDigito = 0;
        } else {
            segundoDigito = 11 - (soma % 11);
        }
        int digitoVerificador1 = Integer.parseInt(cpf.substring(9, 10));
        int digitoVerificador2 = Integer.parseInt(cpf.substring(10, 11));

        return primeiroDigito == digitoVerificador1 && segundoDigito == digitoVerificador2;
    }


    private boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.length() != 14 || !cnpj.matches("\\d{14}")) {
            return false;
        }

        int[] sequencia1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] sequencia2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 12; i++) {
            soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * sequencia1[i];
        }

        int primeiroDigito;
        if (soma % 11 < 2) {
            primeiroDigito = 0;
        } else {
            primeiroDigito = 11 - (soma % 11);
        }

        if (Integer.parseInt(String.valueOf(cnpj.charAt(12))) != primeiroDigito) {
            return false;
        }

        soma = 0;

        for (int i = 0; i < 13; i++) {
            soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * sequencia2[i];
        }

        int segundoDigito;
        if (soma % 11 < 2) {
            segundoDigito = 0;
        } else {
            segundoDigito = 11 - (soma % 11);
        }

        return Integer.parseInt(String.valueOf(cnpj.charAt(13))) == segundoDigito;
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

    private void changeLabelToCPF() {
        TextView labelCpfCnpj = findViewById(R.id.labelCpfCnpj);
        labelCpfCnpj.setText("CPF:");
    }
}
