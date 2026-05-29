package com.pooke.dominio;

public class GolpeEspecial extends Golpe{
    public GolpeEspecial(String nome, int poderBase, Tipo tipo) {
        super(nome, poderBase, tipo);
    }

    public int extrairPoderAtaque(PokemonStats statsAtacante){
        return statsAtacante.ataqueEspecial();
    };
    public int extrairResistenciaDefesa(PokemonStats statsDefensor){
        return statsDefensor.defesaEspecial();
    };
}
