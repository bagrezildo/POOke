package com.pooke.dominio;

import com.pooke.excecoes.EquipeLotadaException;
import com.pooke.excecoes.EspecieDuplicadaException;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private List<Pokemon> pokemons;
    private int limiteMaximo;

    public Equipe() {
        this.pokemons = new ArrayList<>();
        this.limiteMaximo = 6;
    }

    public void adicionarPokemon(Pokemon p){
        if(pokemons.size() >= limiteMaximo){
            throw new EquipeLotadaException("A equipe já possui "+limiteMaximo+" membros!");
        }

        for(Pokemon membro: pokemons){
            if (membro.getNome().equalsIgnoreCase(p.getNome())){
                throw new EspecieDuplicadaException("A equipe já possui um " + p.getNome() + " membro!");
            }
        }
        this.pokemons.add(p);
    }

    public Pokemon obterProximoPokemonVivo(){
        for(Pokemon membro: pokemons){
            if (membro.getHpAtual() > 0){
                return membro;
            }
        }
        return null;
    }

    public boolean equipeDesmaiada() {
        for(Pokemon membro: pokemons){
            if (membro.getHpAtual() > 0){
                return false;
            }
        }
        return true;
    }
}
