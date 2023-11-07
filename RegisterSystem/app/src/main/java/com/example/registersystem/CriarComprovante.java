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

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CriarComprovante extends AppCompatActivity {
    private File file;
    private BDcomprovante bDcomprovante;
    private Cliente cliente;
    private Empresa empresa;

    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;
    private Uri selectedPdfUri;
    private ActivityResultLauncher<String> getContent;

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_comprovante);

        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                selectedPdfUri = uri;
                file = new File(selectedPdfUri.getPath());
            }
        });

        if (getIntent().hasExtra("empresa") && getIntent().hasExtra("cliente")) {
            empresa = (Empresa) getIntent().getSerializableExtra("empresa");
            cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        }

    }
    public void selecionarArquivo(View view) {
        getContent.launch("*/*");
    }
    public void addArquivo(View view) {
        if (file!=null) {
            EditText editTextNomeArquivo = findViewById(R.id.editNomeArquivo);
            EditText editTextData = findViewById(R.id.editTextData);
            String nomeArquivo = editTextNomeArquivo.getText().toString();

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataArquivo = new Date();
            try {
                dataArquivo = formato.parse(editTextData.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            criarConexao();
            Comprovante comprovante = new Comprovante(nomeArquivo, dataArquivo, file, empresa, cliente);

            try {
                bDcomprovante.inserirComprovante(comprovante);
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