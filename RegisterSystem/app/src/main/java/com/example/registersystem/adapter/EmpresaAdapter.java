package com.example.registersystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registersystem.R;
import com.example.registersystem.RecyclerViewComprovante;
import com.example.registersystem.RecyclerViewEmpresas;
import com.example.registersystem.conexao.BDContrato;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.databinding.EmpresaItemBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder> {

    private ArrayList<Empresa> empresaList;
    private Context context;
    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;
    private BDContrato bdContrato;

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

        holder.binding.btShowComprovante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Empresa empresa = empresaList.get(position);

                if (context instanceof RecyclerViewEmpresas) {
                    RecyclerViewEmpresas recyclerViewEmpresas = (RecyclerViewEmpresas) context;
                    Cliente cliente = recyclerViewEmpresas.getCliente();
                    Intent intent = new Intent(v.getContext(), RecyclerViewComprovante.class);

                    intent.putExtra("cliente", cliente);
                    intent.putExtra("empresa", empresa);
                    v.getContext().startActivity(intent);
                }
            }

        });
        holder.binding.btExcluirContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarConexao();
                Empresa empresa = empresaList.get(position);

                if (context instanceof RecyclerViewEmpresas) {
                    RecyclerViewEmpresas recyclerViewEmpresas = (RecyclerViewEmpresas) context;
                    Cliente cliente = recyclerViewEmpresas.getCliente();

                    try {
                        bdContrato.excluirContrato(empresa.getCnpj(),cliente.getCpf());

                    }catch (SQLException ex){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                        dlg.setTitle(R.string.title_erro);
                        dlg.setMessage(ex.getMessage());
                        dlg.setNeutralButton(R.string.action_ok, null);
                        dlg.show();
                    }
                }
            }

        });
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
    private void criarConexao(){
        try {
            dadosOpenHelper = new DadosOpenHelper(context);
            conexao = dadosOpenHelper.getWritableDatabase();

            bdContrato = new BDContrato(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }
    }

}