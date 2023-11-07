package com.example.registersystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registersystem.RecyclerViewClientes;
import com.example.registersystem.RecyclerViewComprovante;
import com.example.registersystem.RecyclerViewEmpresas;
import com.example.registersystem.databinding.ClienteItemBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private ArrayList<Cliente> clienteList;
    private Context context;

    public ClientAdapter(ArrayList<Cliente> clienteList, Context context) {
        this.clienteList = clienteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClientAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClienteItemBinding listItem;
        listItem = ClienteItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ClientViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
        holder.binding.txtClientName.setText(clienteList.get(position).getNome());
        holder.binding.txtClientTel.setText(clienteList.get(position).getTelefone());
        holder.binding.txtClientEmail.setText(clienteList.get(position).getEmail());

        holder.binding.btShowComprovante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cliente cliente = clienteList.get(position);

                RecyclerViewClientes recyclerViewClientes = (RecyclerViewClientes) context;
                Empresa empresa = recyclerViewClientes.getEmpresa();

                Intent intent = new Intent(v.getContext(), RecyclerViewComprovante.class);

                intent.putExtra("cliente", cliente);
                intent.putExtra("empresa", empresa);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder{
        ClienteItemBinding binding;
        public ClientViewHolder(ClienteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
