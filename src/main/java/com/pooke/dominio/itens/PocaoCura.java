package com.pooke.dominio.itens;

import com.pooke.dominio.Pokemon;

public class PocaoCura implements Item {
    private int poderCura;

    public PocaoCura(int poderCura) {
        this.poderCura = poderCura;
    }

    public void aplicar(Pokemon alvo) {
        if(alvo.getHpAtual() == 0){
            throw new CurarDesmaiadoException("Não dá pra curar um Pokémon desmaiado!");
        }

        if (alvo.getHpAtual() == alvo.getHpMax()){
            throw new VidaCheiaException("Não dá pra curar um Pokémon com vida cheia!");
        }

        alvo.receberCura(this.poderCura);
    }
}
