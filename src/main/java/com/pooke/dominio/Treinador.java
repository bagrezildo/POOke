package com.pooke.dominio;

import com.pooke.dominio.itens.Item;

import java.util.ArrayList;
import java.util.List;

public class Treinador {
    private String nome;
    protected Equipe equipe;
    private List<Item> inventario;

    public Treinador(String nome) {
        this.nome = nome;
        this.equipe = new Equipe();
        this.inventario = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public List<Item> getInventario() {
        return inventario;
    }

    public void adicionarItem(Item item){
        this.inventario.add(item);
    }

    public void removerItem(Item item){
        this.inventario.remove(item);
    }

    public void expShare(int xp){
        for (Pokemon pokemon : equipe.getPokemons()) {
            pokemon.ganharXp(xp);
        }
    }
}
