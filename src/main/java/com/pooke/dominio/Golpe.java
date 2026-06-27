package com.pooke.dominio;

public class Golpe {
    private String nome;
    private int poderBase;
    private Tipo tipo;

    public Golpe(String nome, int poderBase, Tipo tipo) {
        this.nome = nome;
        this.poderBase = poderBase;
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s - %s]", nome, tipo));
        return sb.toString();
    }

    public String getNome() {
        return nome;
    }

    public int getPoderBase() {
        return poderBase;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
