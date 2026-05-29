package com.pooke.dominio;

import java.util.List;

public class EspeciePokemon {
    private final int id;
    private final String nome;
    private final Tipo tipoPrimario;
    private final Tipo tipoSecundario;
    private final PokemonStats statsBase;
    private final List<GolpeAprendivel> golpesPossiveis;

    public EspeciePokemon(int id, String nome, PokemonStats statsBase, Tipo tipoPrimario, Tipo tipoSecundario, List<GolpeAprendivel> golpesPossiveis) {
        this.id = id;
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.statsBase = statsBase;
        this.golpesPossiveis = golpesPossiveis;
    }

    //Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public Tipo getTipoPrimario() { return tipoPrimario; }
    public Tipo getTipoSecundario() { return tipoSecundario; }
    public PokemonStats getStatsBase() { return statsBase; }
    public List<GolpeAprendivel> getGolpesPossiveis() { return golpesPossiveis; }
}
