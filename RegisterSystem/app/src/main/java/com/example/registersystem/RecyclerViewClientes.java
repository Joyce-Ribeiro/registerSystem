package com.example.registersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.registersystem.adapter.ClientAdapter;
import com.example.registersystem.databinding.ActivityRecyclerViewClientesBinding;
import com.example.registersystem.model.Cliente;

import java.util.ArrayList;

public class RecyclerViewClientes extends AppCompatActivity {
    private ActivityRecyclerViewClientesBinding binding;
    private ClientAdapter clientAdapter;
    private ArrayList<Cliente> clientesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerViewClientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clientesList = (ArrayList<Cliente>) getIntent().getSerializableExtra("clientesList");

        RecyclerView recyclerViewClientes = binding.RecyclerViewCliente;
        recyclerViewClientes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewClientes.setHasFixedSize(true);
        clientAdapter = new ClientAdapter(clientesList,this);
        recyclerViewClientes.setAdapter(clientAdapter);

    }

}