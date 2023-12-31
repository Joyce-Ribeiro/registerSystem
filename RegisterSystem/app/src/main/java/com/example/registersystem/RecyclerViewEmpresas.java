package com.example.registersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.registersystem.adapter.EmpresaAdapter;
import com.example.registersystem.databinding.ActivityRecyclerViewEmpresasBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class RecyclerViewEmpresas extends AppCompatActivity {

    private ActivityRecyclerViewEmpresasBinding binding;
    private EmpresaAdapter empresaAdapter;
    private Cliente cliente;
    private ArrayList<Empresa> empresasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewEmpresasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().hasExtra("empresasList")) {
            try {
                cliente = (Cliente) getIntent().getSerializableExtra("cliente");
                empresasList = (ArrayList<Empresa>) getIntent().getSerializableExtra("empresasList");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        RecyclerView recyclerViewEmpresas = binding.RecyclerViewEmpresa;
        recyclerViewEmpresas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmpresas.setHasFixedSize(true);
        empresaAdapter = new EmpresaAdapter(empresasList,this);
        recyclerViewEmpresas.setAdapter(empresaAdapter);
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void showContratoAdd(View view){
        Intent intent = new Intent(this, CriarContrato.class);
        startActivity(intent);
    }
}