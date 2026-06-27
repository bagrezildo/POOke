package com.pooke.dominio;

public class PokemonFisico extends Pokemon{

    public PokemonFisico(String nome, Tipo tipoPrimario, Tipo tipoSecundario, int hpMax, int ataque, int defesa, int velocidade) {
        super(nome, tipoPrimario, tipoSecundario, hpMax, ataque, defesa, velocidade);
    }

    public void subirDeNivel(){
        this.nivel += 1;
        this.ataque += 5;
        this.defesa += 4;
        this.velocidade += 2;
        this.hpMax +=10;
        this.hpAtual = this.hpMax;
    }
}
