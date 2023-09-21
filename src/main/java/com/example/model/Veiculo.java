package com.example.model;

import java.math.BigDecimal;

public class Veiculo { 
    private Integer id;
    private String marca;
    private String modelo;
    private Integer ano;
    private BigDecimal valor;
    private Cliente cliente;
    
    public Veiculo(String marca, String modelo, Integer ano, BigDecimal valor, Cliente cliente) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.cliente = cliente;
    }

    public Veiculo(Integer id, String marca, String modelo, Integer ano, BigDecimal valor, Cliente cliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.valor = valor;
        this.cliente = cliente;
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Veiculo [marca=" + marca + ", modelo=" + modelo + ", ano=" + ano + ", valor=" + valor + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Veiculo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public Veiculo modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public Veiculo ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public Veiculo valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
}
