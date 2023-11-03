package com.example.registersystem.model;

public class Contrato{
    private Cliente cliente;
    private Empresa empresa;

    public Contrato(Cliente cliente, Empresa empresa){
        this.cliente=cliente;
        this.empresa=empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
