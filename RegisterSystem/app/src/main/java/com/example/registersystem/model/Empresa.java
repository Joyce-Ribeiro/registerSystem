package com.example.registersystem.model;

public class Empresa extends Usuario {
    private String cnpj;


    public Empresa(String nome, String telefone, String email, String cnpj, String senha){
        super(nome, telefone, email, senha);
        this.cnpj=cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cpf) {
        this.cnpj = cnpj;
    }

}
