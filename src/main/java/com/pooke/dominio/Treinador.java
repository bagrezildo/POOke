package com.pooke.dominio;

public class Treinador {
    private String nome;
    protected Equipe equipe;

    public Treinador(String nome) {
        this.nome = nome;
        this.equipe = new Equipe();
    }
}
