package com.pooke.dominio;

import com.pooke.excecoes.AtributoInexistente;
import com.pooke.excecoes.GolpeNaoAprendido;

import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    protected String nome;
    protected Tipo tipoPrimario;
    protected Tipo tipoSecundario;
    protected int nivel;
    protected int hpMax;
    protected int hpAtual;
    protected int ataque;
    protected int defesa;
    protected int velocidade;
    protected List<Golpe> golpesAprendidos;
    protected List<Golpe> golpesEquipados;

    public Pokemon(String nome, Tipo tipoPrimario, Tipo tipoSecundario, int hpMax, int hpAtual, int ataque, int defesa, int velocidade) {
        this.nome = nome;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = 1;
        this.hpMax = hpMax;
        this.hpAtual = hpAtual;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.golpesAprendidos = new ArrayList<>();
        this.golpesEquipados = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipoPrimario() {return tipoPrimario;}

    public Tipo getTipoSecundario() {return tipoSecundario;}

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

    public List<Golpe> getGolpesAprendidos() { return golpesAprendidos; }
    public List<Golpe> getGolpesEquipados() { return golpesEquipados; }


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

    public void aprenderGolpe(Golpe golpe) {
        this.golpesAprendidos.add(golpe);

        if (this.golpesEquipados.size() < 4) {
            this.golpesEquipados.add(golpe);
        }
    }

    public void equiparGolpe(Golpe golpeEquipado, Golpe golpeRemovido) {
        if (!this.golpesEquipados.contains(golpeEquipado)) {
            throw new GolpeNaoAprendido("O Pokémon não conhece ese golpe!");
        }

        if (this.golpesEquipados.contains(golpeRemovido)) {
            this.golpesEquipados.remove(golpeRemovido);
        }

        this.golpesEquipados.add(golpeEquipado);
    }

    public abstract void subirDeNivel();

}
