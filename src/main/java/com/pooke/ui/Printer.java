package com.pooke.ui;

import com.pooke.dominio.Pokemon;

public class Printer {

    public static void imprimir(String texto){
        System.out.println(texto);
    }

    public static void header(){
        System.out.println("=======================================================");
        System.out.println("| Bem-vindo à POOke - Simulador de batalhas CLI, v1.0 |");
        System.out.println("=======================================================");
    }

    public static void narrarDano(Pokemon pokemon, int dano){
        System.out.println(pokemon.getNome() + " recebeu " + dano + "de dano!");
    }

    public static void narrarDesmaio(Pokemon pokemon){
        System.out.println("O " + pokemon.getNome() + " desmaiou!");
    }

    public static void narrarCura(Pokemon pokemon, int cura){
        System.out.println(pokemon.getNome() + " curou " + cura + "de hp!");
    }
}
