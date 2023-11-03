package com.example.registersystem.model;

import java.io.File;
import java.util.Date;

public class Comprovante {
    private String nomeArquivo;
    private Date data;
    private File arquivo;
    private Empresa empresa;
    private Cliente cliente;


    public Comprovante(String nomeArquivo, Date data, File arquivo, Empresa empresa, Cliente cliente) {
        this.nomeArquivo = nomeArquivo;
        this.data = data;
        this.arquivo = arquivo;
        this.empresa = empresa;
        this.cliente = cliente;
    }


    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
