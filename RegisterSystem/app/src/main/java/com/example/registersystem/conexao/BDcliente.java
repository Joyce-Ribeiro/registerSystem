package com.example.registersystem.conexao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class BDcliente{
    private SQLiteDatabase conexao;

    public BDcliente(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }


    public void inserirCliente(Cliente cliente) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME", cliente.getNome());
        contentValues.put("TELEFONE", cliente.getTelefone());
        contentValues.put("EMAIL", cliente.getEmail());
        contentValues.put("CPF", cliente.getCpf());
        contentValues.put("SENHA", cliente.getSenha());

        conexao.insertOrThrow("CLIENTE", null, contentValues);
    }

    public Cliente buscarCliente(String cpf, String senha) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT CPF, NOME, SENHA, EMAIL, TELEFONE  ");
        sql.append("        FROM CLIENTE  ");
        sql.append("        WHERE CPF = ? AND SENHA = ?  ");

        String[] parametros = new String[2];
        parametros[0] = cpf;
        parametros[1] = senha;

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            resultado.moveToFirst();

            String cpf_cli = (resultado.getString(resultado.getColumnIndexOrThrow("CPF")));
            String nome_cli = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
            String senha_cli = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
            String email_cli = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
            String tel_cli = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

            Cliente cliente = new Cliente(nome_cli,tel_cli,email_cli,cpf_cli,senha_cli);
            return cliente;
        }
        return null;
    }
    public Cliente buscarCliente(String cpf) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT CPF, NOME, SENHA, EMAIL, TELEFONE  ");
        sql.append("        FROM CLIENTE  ");
        sql.append("        WHERE CPF = ?  ");

        String[] parametros = new String[1];
        parametros[0] = cpf;

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            resultado.moveToFirst();

            String cpf_cli = (resultado.getString(resultado.getColumnIndexOrThrow("CPF")));
            String nome_cli = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
            String senha_cli = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
            String email_cli = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
            String tel_cli = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

            Cliente cliente = new Cliente(nome_cli,tel_cli,email_cli,cpf_cli,senha_cli);
            return cliente;
        }
        return null;
    }

    public ArrayList<Empresa> buscarEmpresas(Cliente cliente) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT EMPRESA.*  ");
        sql.append("        FROM EMPRESA  ");
        sql.append("        INNER JOIN CONTRATO ON EMPRESA.CNPJ = CONTRATO.CNPJ  ");
        sql.append("        WHERE CONTRATO.CPF = ?  ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(cliente.getCpf());

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            ArrayList<Empresa> empresas = new ArrayList<>();
            while (resultado.moveToNext()) {
                String cnpj_emp = (resultado.getString(resultado.getColumnIndexOrThrow("CNPJ")));
                String nome_emp = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                String senha_emp = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
                String email_emp = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
                String tel_emp = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

                Empresa empresa = new Empresa(nome_emp,tel_emp,email_emp,cnpj_emp,senha_emp);

                empresas.add(empresa);
            }
            return empresas;
        }
        return null;
    }


}

