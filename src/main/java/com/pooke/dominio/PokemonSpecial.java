package com.pooke.dominio;

public class PokemonSpecial extends Pokemon {

    public PokemonSpecial(String nome, Tipo tipoPrimario, Tipo tipoSecundario, int hpMax, int ataque, int defesa, int velocidade) {
        super(nome, tipoPrimario, tipoSecundario, hpMax, ataque, defesa, velocidade);
    }

    @Override
    public void subirDeNivel(){
        this.nivel += 1;
        this.ataque += 4;
        this.defesa += 7;
        this.velocidade += 5;
        this.hpMax += 15;
        this.hpAtual = this.hpMax;
    }
}
