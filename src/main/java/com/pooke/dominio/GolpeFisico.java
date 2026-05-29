package com.pooke.dominio;

public class GolpeFisico extends Golpe{
    public GolpeFisico(String nome, int poderBase, Tipo tipo) {
        super(nome, poderBase, tipo);
    }

    @Override
    public int extrairPoderAtaque(PokemonStats statsAtacante){
        return statsAtacante.ataque();
    }

    @Override
    public int extrairResistenciaDefesa(PokemonStats statsDefensor){
        return statsDefensor.defesa();
    }
}
