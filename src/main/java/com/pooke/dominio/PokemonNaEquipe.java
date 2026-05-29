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

    public PokemonNaEquipe(EspeciePokemon especie, int nivel, int hpAtual, PokemonStats statsAtual, List<Golpe> golpesConhecidos, List<Golpe> golpesEquipados) {
        this.especie = especie;
        this.nivel = 1;
        this.statsAtual = statsAtual;
        this.golpesConhecidos = new ArrayList<>();
        this.golpesEquipados = new ArrayList<>();

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
}
