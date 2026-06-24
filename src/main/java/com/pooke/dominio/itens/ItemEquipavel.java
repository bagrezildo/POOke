package com.pooke.dominio.itens;

import com.pooke.dominio.Pokemon;

public class ItemEquipavel implements Item{
    private String nome;
    private String atributoAlvo;
    private int bonus;

    public ItemEquipavel(String nome, String atributoAlvo, int bonus) {
        this.nome = nome;
        this.atributoAlvo = atributoAlvo;
        this.bonus = bonus;
    }

    public void aplicar(Pokemon alvo){
        alvo.aumentarAtributo(this.atributoAlvo, this.bonus);
    }

    public void remover(Pokemon alvo){
        alvo.removerAtributo(this.atributoAlvo, this.bonus);
    }

    public String getNome() {
        return nome;
    }

    public String getAtributoAlvo() {
        return atributoAlvo;
    }

    public int getBonus() {
        return bonus;
    }
}
