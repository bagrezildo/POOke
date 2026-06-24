package com.pooke.dominio;

import java.util.ArrayList;
import java.util.List;

public class EspeciePokemon {
    private final int id;
    private final String nome;
    private final Tipo tipoPrimario;
    private final Tipo tipoSecundario;
    private final PokemonStats statsBase;
    private final List<GolpeAprendivel> golpesPossiveis;

    public EspeciePokemon(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.tipoPrimario = builder.tipoPrimario;
        this.tipoSecundario = builder.tipoSecundario;
        this.statsBase = builder.statsBase;
        this.golpesPossiveis = builder.golpesPossiveis != null ?  builder.golpesPossiveis : new ArrayList<>();
    }

    //Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public Tipo getTipoPrimario() { return tipoPrimario; }
    public Tipo getTipoSecundario() { return tipoSecundario; }
    public PokemonStats getStatsBase() { return statsBase; }
    public List<GolpeAprendivel> getGolpesPossiveis() { return golpesPossiveis; }

    public static class Builder {
        private int id;
        private String nome;
        private Tipo tipoPrimario;
        private Tipo tipoSecundario;
        private PokemonStats statsBase;
        private List<GolpeAprendivel> golpesPossiveis;

        public Builder id(int id) { this.id = id; return this; }

        public Builder nome(String nome) { this.nome = nome.substring(0,1).toUpperCase() + nome.substring(1); return this; }

        public Builder tipoPrimario(Tipo tipo) { this.tipoPrimario = tipo; return this; }
        public Builder tipoSecundario(Tipo tipo) { this.tipoSecundario = tipo; return this; }
        public Builder statsBase(PokemonStats stats) { this.statsBase = stats; return this; }
        public Builder adicionarGolpe(GolpeAprendivel golpe) { this.golpesPossiveis.add(golpe); return this; }

        public EspeciePokemon build() {
            return new EspeciePokemon(this);
        }
    }
}
