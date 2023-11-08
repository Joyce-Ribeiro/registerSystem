package com.example.registersystem.conexao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.registersystem.model.Empresa;

public class BDContrato {
    private SQLiteDatabase conexao;

    public BDContrato(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserirContrato(String cnpj, String cpf) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("CNPJ", cnpj);
        contentValues.put("CPF", cpf);

        conexao.insertOrThrow("CONTRATO", null, contentValues);
    }
    public void excluirContrato(String cnpj, String cpf) {
        String whereClause = "CNPJ = ? AND CPF = ?";
        String[] whereArgs = {cnpj, cpf};

        conexao.delete("CONTRATO", whereClause, whereArgs);
    }


}
