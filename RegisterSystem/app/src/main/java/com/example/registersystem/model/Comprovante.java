package com.example.registersystem.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class Comprovante implements Serializable {
    private String nomeArquivo;
    private Date data;
    private String codigo;
    private Empresa empresa;
    private Cliente cliente;


    public Comprovante(String nomeArquivo, Date data, String codigo, Empresa empresa, Cliente cliente) {
        this.nomeArquivo = nomeArquivo;
        this.data = data;
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String arquivo) {
        this.codigo = codigo;
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
