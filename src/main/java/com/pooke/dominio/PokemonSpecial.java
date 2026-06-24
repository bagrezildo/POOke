package com.pooke.dominio;

public class PokemonSpecial extends Pokemon {
    public PokemonSpecial(String nome, int nivel, int hpMax, int hpAtual, int ataque, int defesa, int velocidade) {
        super(nome, nivel, hpMax, hpAtual, ataque, defesa, velocidade);
    }

    public void subirDeNivel(){
        this.nivel += 1;
        this.ataque += 3;
        this.defesa += 1;
        this.velocidade += 5;
        this.hpMax +=10;
        this.hpAtual = this.hpMax;
    }
}
