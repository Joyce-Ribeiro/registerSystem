package com.example.registersystem.conexao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Comprovante;
import com.example.registersystem.model.Empresa;

import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;

import java.io.File;
import java.io.FileOutputStream;
public class BDcomprovante {
    private SQLiteDatabase conexao;

    public BDcomprovante(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }


    public void inserirComprovante(Comprovante comprovante) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME", comprovante.getNomeArquivo());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(comprovante.getData());
        contentValues.put("DATA", dataFormatada);

        byte[] arquivoBytes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(comprovante.getArquivo());
            arquivoBytes = new byte[(int) comprovante.getArquivo().length()];
            fileInputStream.read(arquivoBytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contentValues.put("ARQUIVO", arquivoBytes);
        contentValues.put("CNPJ", comprovante.getEmpresa().getCnpj());
        contentValues.put("CPF", comprovante.getCliente().getCpf());

        conexao.insertOrThrow("COMPROVANTE", null, contentValues);
    }


    public ArrayList<Comprovante> buscarComprovantes(Cliente cliente, Empresa empresa) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT COMPROVANTE.*  ");
        sql.append("        FROM COMPROVANTE  ");
        sql.append("        WHERE COMPROVANTE.CPF = ? AND COMPROVANTE.CNPJ = ?  ");

        String[] parametros = new String[2];
        parametros[0] = cliente.getCpf();
        parametros[1] = empresa.getCnpj();

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            ArrayList<Comprovante> comprovantes = new ArrayList<>();
            while (resultado.moveToNext()) {
                String nome_comp = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                Date data_string_to_date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    data_string_to_date = sdf.parse(resultado.getString(resultado.getColumnIndexOrThrow("DATA")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date data_comp = data_string_to_date;

                byte[] arquivoBytes = (resultado.getBlob(resultado.getColumnIndexOrThrow("ARQUIVO")));
                File arquivoFile = new File(nome_comp);
                if (arquivoBytes != null) {
                    try {
                        FileOutputStream outputStream = new FileOutputStream(arquivoFile);
                        outputStream.write(arquivoBytes);
                        outputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                File arq_comp = (arquivoFile);
                BDempresa bdempresa = new BDempresa(conexao);
                com.example.registersystem.conexao.BDcliente bdcliente = new com.example.registersystem.conexao.BDcliente(conexao);

                Empresa emp_comp = bdempresa.buscarEmpresa(resultado.getString(resultado.getColumnIndexOrThrow("CNPJ")));
                Cliente cli_comp = bdcliente.buscarCliente(resultado.getString(resultado.getColumnIndexOrThrow("CPF")));

                Comprovante comprovante = new Comprovante(nome_comp, data_comp, arquivoFile, emp_comp, cli_comp);

                comprovantes.add(comprovante);
            }
            return comprovantes;
        }
        return null;
    }

}
