package com.example.registersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registersystem.databinding.EmpresaItemBinding;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder> {

    private ArrayList<Empresa> empresaList;
    private Context context;

    public EmpresaAdapter(ArrayList<Empresa> empresaList, Context context) {
        this.empresaList = empresaList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmpresaAdapter.EmpresaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmpresaItemBinding listItem;
        listItem = EmpresaItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new EmpresaAdapter.EmpresaViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaAdapter.EmpresaViewHolder holder, int position) {
        holder.binding.txtEmpresaName.setText(empresaList.get(position).getNome());
        holder.binding.txtEmpresaTel.setText(empresaList.get(position).getTelefone());
        holder.binding.txtEmpresaEmail.setText(empresaList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return empresaList.size();
    }

    public static class EmpresaViewHolder extends RecyclerView.ViewHolder{
        EmpresaItemBinding binding;
        public EmpresaViewHolder(EmpresaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
