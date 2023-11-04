package com.example.registersystem.model;

import java.io.Serializable;

public class Empresa extends Usuario implements Serializable {
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
