package com.pooke.dominio;

import com.pooke.excecoes.EquipeLotadaException;
import com.pooke.excecoes.EspecieDuplicadaException;

import java.util.ArrayList;
import java.util.List;

/**
 * Agregador que gerencia a lista de Pokémons de um treinador.
 * Enforça o limite de 6 espécies e lida com validações de duplicidade.
 */
public class Equipe {
    private List<Pokemon> pokemons;
    private int limiteMaximo;

    public Equipe() {
        this.pokemons = new ArrayList<>();
        this.limiteMaximo = 6;
    }

    /**
     * Adiciona um Pokémon à equipe.
     * @param p O Pokémon a ser adicionado.
     * @throws EquipeLotadaException se a equipe já possuir 6 membros.
     * @throws EspecieDuplicadaException se o treinador já possuir este Pokémon.
     */
    public void adicionarPokemon(Pokemon p){
        if(pokemons.size() >= limiteMaximo){
            throw new EquipeLotadaException("A equipe já possui "+limiteMaximo+" membros!");
        }

        for(Pokemon membro: pokemons){
            if (membro.getNome().equalsIgnoreCase(p.getNome())){
                throw new EspecieDuplicadaException("A equipe já possui um " + p.getNome() + " membro!");
            }
        }
        p.receberCura(p.getHpMax());
        this.pokemons.add(p);
    }

    public void removerPokemon(Pokemon p){
        this.pokemons.remove(this.pokemons.indexOf(p));
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

    public int tamanho(){ return this.pokemons.size(); }
    public int getLimiteMaximo(){ return this.limiteMaximo; }

    public List<Pokemon> getPokemons() { return this.pokemons;}
}
