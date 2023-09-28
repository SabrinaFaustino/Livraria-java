package com.example.model;

import java.math.BigDecimal;

public class Livro { 
    private Integer id;
    private String editora;
    private String titulo;
    private Integer ano;
    private BigDecimal valor;
    private Autor autor;
    
    public Livro(String editora, String titulo, Integer ano, BigDecimal valor, Autor autor) {
        this.editora = editora;
        this.titulo = titulo;
        this.ano = ano;
        this.valor = valor;
        this.autor = autor;
    }

    public Livro(Integer id, String editora, String titulo, Integer ano, BigDecimal valor, Autor autor) {
        this.id = id;
        this.editora = editora;
        this.titulo = titulo;
        this.ano = ano;
        this.valor = valor;
        this.autor = autor;
    }


    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        return "Livro [editora=" + editora + ", titulo=" + titulo + ", ano=" + ano + ", valor=" + valor + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livro editora(String editora) {
        this.editora = editora;
        return this;
    }

    public Livro titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Livro ano(Integer ano) {
        this.ano = ano;
        return this;
    }

    public Livro valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    
}
