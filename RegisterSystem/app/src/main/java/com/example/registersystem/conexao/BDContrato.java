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
}
