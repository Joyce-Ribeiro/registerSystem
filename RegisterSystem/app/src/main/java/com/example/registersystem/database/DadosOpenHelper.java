package com.example.registersystem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public DadosOpenHelper(Context context) {
        super(context, "DADOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( ScriptDDL.getCreateTableCliente());
        db.execSQL( ScriptDDL.getCreateTableEmpresa());
        db.execSQL( ScriptDDL.getCreateTableComprovante());
        db.execSQL( ScriptDDL.getCreateTableContrato());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
