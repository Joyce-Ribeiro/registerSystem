package com.example.registersystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.registersystem.adapter.ComprovanteAdapter;
import com.example.registersystem.conexao.BDcomprovante;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.databinding.ActivityRecyclerViewComprovanteBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Comprovante;
import com.example.registersystem.model.Empresa;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewComprovante extends AppCompatActivity implements ComprovanteAdapter.OnItemClickListener{
    private ActivityRecyclerViewComprovanteBinding binding;
    private ComprovanteAdapter comprovanteAdapter;
    private ArrayList<Comprovante> comprovantesList = new ArrayList<>();
    private BDcomprovante bDcomprovante;

    private DadosOpenHelper dadosOpenHelper;

    private SQLiteDatabase conexao;

    private Empresa empresa;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewComprovanteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        criarConexao();

        if (getIntent().hasExtra("empresa") && getIntent().hasExtra("cliente")) {
            try {
                empresa = (Empresa) getIntent().getSerializableExtra("empresa");
                cliente = (Cliente) getIntent().getSerializableExtra("cliente");
                comprovantesList = bDcomprovante.buscarComprovantes(cliente, empresa, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int visibilidade = getIntent().getIntExtra("visibilidade", 0);

        RecyclerView recyclerViewComprovantes = binding.RecyclerViewComprovante;
        recyclerViewComprovantes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComprovantes.setHasFixedSize(true);
        comprovanteAdapter = new ComprovanteAdapter(comprovantesList, this, this);
        recyclerViewComprovantes.setAdapter(comprovanteAdapter);

        if (visibilidade == 1) {
            binding.addComprovante.setVisibility(View.GONE);
        } else {
            binding.addComprovante.setVisibility(View.VISIBLE);
        }

        binding.addComprovante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewComprovante.this, CriarComprovante.class);

                intent.putExtra("cliente", cliente);
                intent.putExtra("empresa", empresa);

                startActivity(intent);
            }
        });

    }
    @Override
    public void onItemClick(Comprovante comprovante) {
        File arquivo = comprovante.getArquivo();
        Log.d("MEUDEUSEUNAOAGUENTOMAIS:", arquivo.toString());

        if (arquivo != null) {
            Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", arquivo);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("TAG", "Nenhum aplicativo pode abrir este arquivo");
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