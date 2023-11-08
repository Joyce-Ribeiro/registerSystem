package com.example.registersystem;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.registersystem.conexao.BDcliente;
import com.example.registersystem.conexao.BDcomprovante;
import com.example.registersystem.conexao.BDempresa;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Comprovante;
import com.example.registersystem.model.Empresa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CriarComprovante extends AppCompatActivity {
    private BDcomprovante bDcomprovante;
    private Cliente cliente;
    private Empresa empresa;
    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_comprovante);

        if (getIntent().hasExtra("empresa") && getIntent().hasExtra("cliente")) {
            empresa = (Empresa) getIntent().getSerializableExtra("empresa");
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        }

    }
    private void showErrorMessage(String message) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        dlg.setTitle(R.string.title_erro);
        dlg.setMessage(message);
        dlg.setNeutralButton(R.string.action_ok, null);
        dlg.show();
    }
    public void addArquivo(View view) {
            EditText editTextNomeArquivo = findViewById(R.id.editNomeArquivo);
            EditText editTextData = findViewById(R.id.editTextData);
            EditText editTextCodigo = findViewById(R.id.editTextCodigo);
            String nomeArquivo = editTextNomeArquivo.getText().toString();
            String codigo = editTextCodigo.getText().toString();
            String dataText = editTextData.getText().toString().trim();

            if (nomeArquivo.isEmpty() || codigo.isEmpty() || dataText.isEmpty()) {
                showErrorMessage("Por Favor Preencha todas as Lacunas.");
                return;
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataArquivo;

            try {
                dataArquivo = formato.parse(editTextData.getText().toString());
            } catch (ParseException e) {
                showErrorMessage("Formato da Data inválida.");
                return;
            }

            criarConexao();
            Comprovante comprovante = new Comprovante(nomeArquivo, dataArquivo, codigo, empresa, cliente);

            try {
                bDcomprovante.inserirComprovante(comprovante);
                finish();

            } catch (SQLException ex) {
                showErrorMessage(ex.getMessage());
            }

    }


    private void criarConexao(){
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();

            bDcomprovante = new BDcomprovante(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }
    }
}