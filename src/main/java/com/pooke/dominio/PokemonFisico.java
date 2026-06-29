package com.pooke.dominio;

public class PokemonFisico extends Pokemon{

    public PokemonFisico(String nome, Tipo tipoPrimario, Tipo tipoSecundario, int hpMax, int ataque, int defesa, int velocidade) {
        super(nome, tipoPrimario, tipoSecundario, hpMax, ataque, defesa, velocidade);
    }

    @Override
    public void subirDeNivel(){
        this.nivel += 1;
        this.ataque += 7;
        this.defesa += 6;
        this.velocidade += 4;
        this.hpMax += 15;
        this.hpAtual = this.hpMax;
    }
}
