package com.example.registersystem.adapter;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registersystem.R;
import com.example.registersystem.RecyclerViewClientes;
import com.example.registersystem.RecyclerViewEmpresas;
import com.example.registersystem.conexao.BDContrato;
import com.example.registersystem.conexao.BDcomprovante;
import com.example.registersystem.database.DadosOpenHelper;
import com.example.registersystem.databinding.ComprovanteItemBinding;
import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Comprovante;
import com.example.registersystem.model.Contrato;
import com.example.registersystem.model.Empresa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ComprovanteAdapter extends RecyclerView.Adapter<ComprovanteAdapter.ComprovanteViewHolder>{
    private ArrayList<Comprovante> comprovanteList;
    private Context context;
    private int visibilidade;
    private SQLiteDatabase conexao;

    private DadosOpenHelper dadosOpenHelper;
    private BDcomprovante bdComprovante;
    public ComprovanteAdapter(ArrayList<Comprovante> comprovanteList, Context context, int visibilidade) {
        this.comprovanteList = comprovanteList;
        this.context = context;
        this.visibilidade = visibilidade;
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
        holder.binding.txtArquivoCodigo.setText(comprovanteList.get(position).getCodigo());

        if (visibilidade == 1) {
            holder.binding.btExcluirComprovante.setVisibility(View.GONE);
        } else {
            holder.binding.btExcluirComprovante.setVisibility(View.VISIBLE);
        }
        holder.binding.btExcluirComprovante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarConexao();
                Comprovante comprovante = comprovanteList.get(position);

                try {
                    bdComprovante.excluirComprovante(comprovante);

                }catch (SQLException ex){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    dlg.setTitle(R.string.title_erro);
                    dlg.setMessage(ex.getMessage());
                    dlg.setNeutralButton(R.string.action_ok, null);
                    dlg.show();
                }

            }

        });

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
    private void criarConexao(){
        try {
            dadosOpenHelper = new DadosOpenHelper(context);
            conexao = dadosOpenHelper.getWritableDatabase();

            bdComprovante = new BDcomprovante(conexao);

        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle(R.string.title_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.action_ok, null);
            dlg.show();
        }
    }
}