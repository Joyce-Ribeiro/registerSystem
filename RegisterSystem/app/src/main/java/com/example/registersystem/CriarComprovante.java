package com.example.registersystem;

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
    private BDcliente bDcliente;
    private BDempresa bDempresa;

    private File file;
    private BDcomprovante bDcomprovante;
    private String cpf;
    private String cnpj;

    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;

    final Context context = this;
    private static final int PICK_FILE_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_comprovante);
    }
    public void selecionarArquivo(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
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


            Empresa empresa = bDempresa.buscarEmpresa(cnpj);
            Cliente cliente = bDcliente.buscarCliente(cpf);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFile = data.getData();
                String filePath = getPathFromURI(selectedFile);

                if (filePath != null) {
                    file = new File(filePath);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }
}