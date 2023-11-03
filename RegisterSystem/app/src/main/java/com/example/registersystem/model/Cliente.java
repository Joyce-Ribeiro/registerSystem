package com.example.registersystem.model;

public class Cliente extends Usuario{
    private String cpf;


    public Cliente(String nome, String telefone, String email, String cpf, String senha){
        super(nome, telefone, email, senha);
        this.cpf=cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }



}
