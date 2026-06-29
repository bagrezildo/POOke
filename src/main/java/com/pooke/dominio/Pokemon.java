package com.pooke.dominio;

import com.pooke.excecoes.AtributoInexistenteException;
import com.pooke.excecoes.GolpeNaoAprendido;
import com.pooke.util.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

    protected int xpAtual;
    protected int xpProxNivel;
    protected Map<Integer, Golpe> filaDeAprendizado;

    public Pokemon(String nome, Tipo tipoPrimario, Tipo tipoSecundario, int hpMax, int ataque, int defesa, int velocidade) {
        this.nome = formataNome(nome);
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = 1;
        this.hpMax = hpMax;
        this.hpAtual = hpMax;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.golpesAprendidos = new ArrayList<>();
        this.golpesEquipados = new ArrayList<>();
        this.xpAtual = 0;
        this.xpProxNivel = 20;
        this.filaDeAprendizado = new HashMap<>();
    }

    public Map<Integer, Golpe> getFilaDeAprendizado() { return filaDeAprendizado; }

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
            default -> throw new AtributoInexistenteException("Não tem como bonificar o que não existe!");
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
            default -> throw new AtributoInexistenteException("Não tem como reduzir o que não existe!");

        }
    }

    public void aprenderGolpe(Golpe golpe) {
        this.golpesAprendidos.add(golpe);

        if (this.golpesEquipados.size() < 4) {
            this.golpesEquipados.add(golpe);
        } else {
            Golpe esquecido = this.golpesEquipados.remove(0);
            this.golpesEquipados.add(golpe);
            Printer.imprimir(this.nome + " esqueceu " + esquecido.getNome() + " e equipou " + golpe.getNome() + "!");
        }
    }

    public void ganharXp(int xp) {
        this.xpAtual += xp;
        Printer.imprimir(nome  + " ganhou " + xp + " de experiência");
        while (this.xpAtual >= this.xpProxNivel) {
            this.xpAtual -= this.xpProxNivel;
            this.subirDeNivel();
            this.xpProxNivel = this.nivel * 100;

            Printer.imprimir(this.getNome() + " subiu de nível!");
            
            if (this.filaDeAprendizado.containsKey(this.nivel)) {
                Golpe golpeNovo = this.filaDeAprendizado.get(this.nivel);
                this.aprenderGolpe(golpeNovo); 
                Printer.imprimir(this.nome + " aprendeu " + golpeNovo.getNome() + "!");
            }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("#%s (Nv. %d)", nome, nivel));
        if (hpAtual <= 0) sb.append(String.format(" [DESMAIADO]"));
        if(tipoSecundario != null){
            sb.append(String.format(" - %s | %s", tipoPrimario, tipoSecundario));
        } else{
            sb.append(String.format(" - %s", tipoPrimario));
        }
        sb.append("\n");
        sb.append(String.format("\tHP: %d/%d | Atk: %d | Def: %d | Vel: %d", hpAtual, hpMax, ataque, defesa, velocidade ));
        sb.append("\n\tGolpes: \n");
        if (golpesEquipados.isEmpty()) {
            sb.append("\n Nenhum golpe equipado!");
        } else{
            for  (Golpe golpe : golpesEquipados) {
                sb.append("\t").append(golpe.toString());
            }
        }
        sb.append("\n Exp: ").append(xpAtual).append("/").append(xpProxNivel);
        return sb.toString();
    }

    public static String formataNome(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("-");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));

                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }

    public int getNivel() { return this.nivel; }

    public abstract void subirDeNivel();

}
