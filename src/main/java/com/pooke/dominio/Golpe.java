package com.pooke.dominio;

public class Golpe {
    private String nome;
    private int poderBase;
    private String tipo;

    public Golpe(String nome, int poderBase, String tipo) {
        this.nome = nome;
        this.poderBase = poderBase;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public int getPoderBase() {
        return poderBase;
    }

    public String getTipo() {
        return tipo;
    }
}
