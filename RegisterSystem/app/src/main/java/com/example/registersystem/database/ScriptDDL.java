package com.example.registersystem.database;

public class ScriptDDL {
    public static String getCreateTableCliente(){

        StringBuilder sql = new StringBuilder();


        sql.append("    CREATE TABLE IF NOT EXISTS CLIENTE (    ");
        sql.append("        CPF VARCHAR (11) PRIMARY KEY NOT NULL,   ");
        sql.append("        NOME TEXT NOT NULL,  ");
        sql.append("        SENHA TEXT NOT NULL,  ");
        sql.append("        EMAIL TEXT NOT NULL, ");
        sql.append("        TELEFONE VARCHAR (9) NOT NULL   ");
        sql.append("    );");

        return sql.toString();
    }

    public static String getCreateTableEmpresa(){

        StringBuilder sql = new StringBuilder();


        sql.append("    CREATE TABLE IF NOT EXISTS EMPRESA (    ");
        sql.append("        CNPJ VARCHAR (14) PRIMARY KEY NOT NULL,   ");
        sql.append("        NOME TEXT NOT NULL,  ");
        sql.append("        SENHA TEXT NOT NULL,  ");
        sql.append("        EMAIL TEXT NOT NULL, ");
        sql.append("        TELEFONE VARCHAR (9) NOT NULL   ");
        sql.append("    );");

        return sql.toString();
    }


    public static String getCreateTableContrato(){

        StringBuilder sql = new StringBuilder();


        sql.append("    CREATE TABLE  IF NOT EXISTS CONTRATO (    ");
        sql.append("        CNPJ VARCHAR (14) NOT NULL,   ");
        sql.append("        CPF VARCHAR (11) NOT NULL,   ");
        sql.append("        PRIMARY KEY (cpf, cnpj),   ");
        sql.append("        FOREIGN KEY (CNPJ) REFERENCES EMPRESA(CNPJ),   ");
        sql.append("        FOREIGN KEY (CPF) REFERENCES CLIENTE(CPF)   ");
        sql.append("    );");

        return sql.toString();
    }

    public static String getCreateTableComprovante(){

        StringBuilder sql = new StringBuilder();


        sql.append("    CREATE TABLE  IF NOT EXISTS COMPROVANTE (    ");
        sql.append("        ID INTEGER PRIMARY KEY AUTOINCREMENT,    ");
        sql.append("        CNPJ VARCHAR (14) NOT NULL,   ");
        sql.append("        CPF VARCHAR (11) NOT NULL,   ");
        sql.append("        NOME TEXT NOT NULL,  ");
        sql.append("        DATA VARCHAR (10) NOT NULL, ");
        sql.append("        ARQUIVO BLOB,   ");
        sql.append("        FOREIGN KEY (CNPJ) REFERENCES CONTRATO(CNPJ),   ");
        sql.append("        FOREIGN KEY (CPF) REFERENCES CONTRATO(CPF)   ");
        sql.append("    );");

        return sql.toString();
    }

}
