package com.example.registersystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.registersystem.adapter.ComprovanteAdapter;
import com.example.registersystem.databinding.ActivityRecylerViewComprovanteBinding;
import com.example.registersystem.model.Comprovante;

import java.util.ArrayList;

public class RecylerViewComprovante extends AppCompatActivity {
    private ActivityRecylerViewComprovanteBinding binding;
    private ComprovanteAdapter comprovanteAdapter;
    private ArrayList<Comprovante> comprovantesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecylerViewComprovanteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent().hasExtra("comprovantesList")) {
            try {
                comprovantesList = (ArrayList<Comprovante>) getIntent().getSerializableExtra("comprovantesList");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RecyclerView recyclerViewComprovantes = binding.RecyclerViewComprovante;
        recyclerViewComprovantes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComprovantes.setHasFixedSize(true);
        comprovanteAdapter = new ComprovanteAdapter(comprovantesList,this);
        recyclerViewComprovantes.setAdapter(comprovanteAdapter);
    }
}