package com.example.registersystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.registersystem.adapter.ComprovanteAdapter;
import com.example.registersystem.conexao.BDcomprovante;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.databinding.ActivityRecyclerViewComprovanteBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Comprovante;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class RecyclerViewComprovante extends AppCompatActivity {
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
                comprovantesList = bDcomprovante.buscarComprovantes(cliente, empresa);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RecyclerView recyclerViewComprovantes = binding.RecyclerViewComprovante;
        recyclerViewComprovantes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComprovantes.setHasFixedSize(true);
        comprovanteAdapter = new ComprovanteAdapter(comprovantesList, this);
        recyclerViewComprovantes.setAdapter(comprovanteAdapter);

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