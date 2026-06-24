package com.pooke.dominio;

import com.pooke.excecoes.AtributoInexistente;
import com.pooke.ui.Printer;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    protected String nome;
    protected int nivel;
    protected int hpMax;
    protected int hpAtual;
    protected int ataque;
    protected int defesa;
    protected int velocidade;
    protected List<Golpe> golpes;

    public Pokemon(String nome, int nivel, int hpMax, int hpAtual, int ataque, int defesa, int velocidade) {
        this.nome = nome;
        this.nivel = nivel;
        this.hpMax = hpMax;
        this.hpAtual = hpAtual;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.golpes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getHpAtual() {
        return hpAtual;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void receberDano(int dano){
        this.hpAtual = this.getHpAtual() - dano;
        if(this.hpAtual <= 0){
            this.hpAtual = 0;
        }
    }

    public void receberCura(int cura){
        if (this.getHpAtual() + cura > this.getHpMax()) {
            this.hpAtual = this.hpMax;
        } else {
            this.hpAtual = this.getHpAtual() + cura;
        }
    }

    public void aumentarAtributo(String atributo, int bonus){
        switch (atributo.toLowerCase()){
            case "ataque" -> this.ataque += bonus;
            case "defesa" -> this.defesa += bonus;
            case "velocidade" -> this.velocidade += bonus;
            case "hpmax"-> {
                this.hpMax += bonus;
                this.hpAtual = Math.min(this.hpAtual, this.hpMax);
            }
            default -> throw new AtributoInexistente("Não tem como bonificar o que não existe!");
        }
    }

    public void removerAtributo(String atributo, int bonus){
        switch (atributo.toLowerCase()){
            case "ataque" -> this.ataque -= bonus;
            case "defesa" -> this.defesa -= bonus;
            case "velocidade" -> this.velocidade -= bonus;
            case "hpmax" -> {
                this.hpMax -= bonus;
                if (this.hpAtual > this.hpMax){
                    this.hpAtual = this.hpMax;
                }
            }
            default -> throw new AtributoInexistente("Não tem como reduzir o que não existe!");

        }
    }

    public abstract void subirDeNivel();

}
