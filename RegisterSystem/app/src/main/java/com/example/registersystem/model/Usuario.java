package com.example.registersystem.model;

public abstract class Usuario{
    protected String nome;
    protected String telefone;
    protected String email;

    protected String senha;

    protected Usuario(String nome, String telefone, String email, String senha){
        this.nome=nome;
        this.telefone=telefone;
        this.email=email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
