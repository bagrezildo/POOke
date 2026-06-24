package com.pooke.dominio;

import java.util.ArrayList;
import java.util.List;

public class PokemonNaEquipe {
    private final EspeciePokemon especie;
    private int nivel;
    private int hpAtual;
    private PokemonStats statsAtual;
    private List<Golpe> golpesConhecidos;
    private List<Golpe> golpesEquipados;

    public PokemonNaEquipe(Builder builder) {
        this.especie = builder.especie;
        this.nivel = builder.nivel;
        this.statsAtual = builder.statsAtual;
        this.golpesConhecidos = builder.golpesConhecidos;
        this.golpesEquipados = builder.golpesEquipados;

        recalcularStats();
        this.hpAtual = this.statsAtual.hp();
    }

    private void recalcularStats(){
        PokemonStats base = especie.getStatsBase();

        this.statsAtual =  new PokemonStats(
                ((base.hp() * nivel) / 50) + 10 + nivel,
                ((base.ataque() * nivel) / 50) + 5,
                ((base.defesa() * nivel) / 50) + 5,
                ((base.ataqueEspecial() * nivel) / 50) + 5,
                ((base.defesaEspecial() * nivel) / 50) + 5,
                ((base.velocidade() * nivel) / 50) + 5
        );
    }

    public void subirDeNivel() {
        this.nivel++;
        recalcularStats();
    }

    //Getters
    public EspeciePokemon getEspecie() { return especie; }
    public int getNivel() { return nivel; }
    public int getHpAtual() { return hpAtual; }
    public PokemonStats getStatsAtual() { return statsAtual; }
    public List<Golpe> getGolpesConhecidos() { return new ArrayList<>(golpesConhecidos); }
    public List<Golpe> getGolpesEquipados() { return new ArrayList<>(golpesEquipados); }

    public static class Builder {
        private EspeciePokemon especie;
        private int nivel = 1;
        private int hpAtual;
        private PokemonStats statsAtual;
        private List<Golpe> golpesConhecidos = new ArrayList<>();
        private List<Golpe> golpesEquipados = new ArrayList<>();

        public Builder especie(EspeciePokemon especie) { this.especie = especie; return this; }
        public Builder nivel(int nivel) { this.nivel = nivel; return this; }
        public Builder hpAtual(int hpAtual) { this.hpAtual = hpAtual; return this; }
        public Builder statsAtual(PokemonStats stats) { this.statsAtual = stats; return this; }
        public Builder golpesConhecidos(List<Golpe> golpes) { this.golpesConhecidos = golpes; return this; }
        public Builder golpesEquipados(List<Golpe> golpes) { this.golpesEquipados = golpes; return this; }

        public PokemonNaEquipe build() {
            return new PokemonNaEquipe(this);
        }
    }
}
