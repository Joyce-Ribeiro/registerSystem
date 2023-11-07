package com.example.registersystem.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registersystem.databinding.ComprovanteItemBinding;
import com.example.registersystem.model.Comprovante;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ComprovanteAdapter extends RecyclerView.Adapter<ComprovanteAdapter.ComprovanteViewHolder>{
    private ArrayList<Comprovante> comprovanteList;
    private Context context;

    public ComprovanteAdapter(ArrayList<Comprovante> comprovanteList, Context context) {
        this.comprovanteList = comprovanteList;
        this.context = context;
    }
    @NonNull
    @Override
    public ComprovanteAdapter.ComprovanteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComprovanteItemBinding listItem;
        listItem = ComprovanteItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ComprovanteAdapter.ComprovanteViewHolder(listItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ComprovanteAdapter.ComprovanteViewHolder holder, int position) {
        holder.binding.txtArquivoName.setText(comprovanteList.get(position).getNomeArquivo());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataFormatada = sdf.format(comprovanteList.get(position).getData());
        holder.binding.txtArquivoDate.setText(dataFormatada);
        holder.binding.txtArquivoCnpj.setText(comprovanteList.get(position).getEmpresa().getCnpj());
        holder.binding.txtArquivoCpf.setText(comprovanteList.get(position).getCliente().getCpf());

    }
    @Override
    public int getItemCount() {
        if (comprovanteList != null) {
            return comprovanteList.size();
        } else {
            return 0;
        }
    }

    public static class ComprovanteViewHolder extends RecyclerView.ViewHolder {
        ComprovanteItemBinding binding;

        public ComprovanteViewHolder(ComprovanteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}