package com.example.registersystem.conexao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.registersystem.model.Cliente;
import com.example.registersystem.model.Empresa;

import java.util.ArrayList;

public class BDempresa {
    private SQLiteDatabase conexao;

    public BDempresa(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserirEmpresa(Empresa empresa) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME", empresa.getNome());
        contentValues.put("TELEFONE", empresa.getTelefone());
        contentValues.put("EMAIL", empresa.getEmail());
        contentValues.put("CNPJ", empresa.getCnpj());
        contentValues.put("SENHA", empresa.getSenha());

        conexao.insertOrThrow("EMPRESA", null, contentValues);
    }

    public Empresa buscarEmpresa(String cnpj, String senha) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT CNPJ, NOME, SENHA, EMAIL, TELEFONE  ");
        sql.append("        FROM EMPRESA  ");
        sql.append("        WHERE CNPJ = ? AND SENHA = ?  ");

        String[] parametros = new String[2];
        parametros[0] = cnpj;
        parametros[1] = senha;

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            resultado.moveToFirst();

            String cnpj_emp = (resultado.getString(resultado.getColumnIndexOrThrow("CNPJ")));
            String nome_emp = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
            String senha_emp = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
            String email_emp = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
            String tel_emp = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

            Empresa empresa = new Empresa(nome_emp,tel_emp,email_emp,cnpj_emp,senha_emp);
            return empresa;
        }
        return null;
    }

    public Empresa buscarEmpresa(String cnpj) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT CNPJ, NOME, SENHA, EMAIL, TELEFONE  ");
        sql.append("        FROM EMPRESA  ");
        sql.append("        WHERE CNPJ = ?  ");

        String[] parametros = new String[1];
        parametros[0] = cnpj;

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            resultado.moveToFirst();

            String cnpj_emp = (resultado.getString(resultado.getColumnIndexOrThrow("CNPJ")));
            String nome_emp = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
            String senha_emp = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
            String email_emp = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
            String tel_emp = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

            Empresa empresa = new Empresa(nome_emp,tel_emp,email_emp,cnpj_emp,senha_emp);
            return empresa;
        }
        return null;
    }

    public ArrayList<Cliente> buscarClientes(Empresa empresa) {

        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT CLIENTE.*  ");
        sql.append("        FROM CLIENTE  ");
        sql.append("        INNER JOIN CONTRATO ON CLIENTE.CPF = CONTRATO.CPF  ");
        sql.append("        WHERE CONTRATO.CNPJ = ?  ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(empresa.getCnpj());

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if (resultado.getCount()>0){
            ArrayList<Cliente> clientes = new ArrayList<>();
            while (resultado.moveToNext()) {
                String cpf_cli = (resultado.getString(resultado.getColumnIndexOrThrow("CPF")));
                String nome_cli = (resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                String senha_cli = (resultado.getString(resultado.getColumnIndexOrThrow("SENHA")));
                String email_cli = (resultado.getString(resultado.getColumnIndexOrThrow("EMAIL")));
                String tel_cli = (resultado.getString(resultado.getColumnIndexOrThrow("TELEFONE")));

                Cliente cliente = new Cliente(nome_cli,tel_cli,email_cli,cpf_cli,senha_cli);
                clientes.add(cliente);
            }
            return clientes;
        }
        return null;
    }
}
