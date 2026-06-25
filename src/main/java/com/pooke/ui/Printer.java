package com.pooke.ui;

import com.pooke.dominio.Golpe;
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

    public static void narrarAtaque(Pokemon atacante, Golpe golpe, Pokemon defensor, int dano){
        System.out.println();
        System.out.println(atacante.getNome() + " usou " + golpe.getNome() + "!");
        System.out.println(defensor.getNome() + "recebeu " + dano + "de dano!");

    }

    public static void narrarDesmaio(Pokemon pokemon){
        System.out.println("O " + pokemon.getNome() + " desmaiou!");
    }

    public static void narrarCura(Pokemon pokemon, int cura){
        System.out.println(pokemon.getNome() + " curou " + cura + "de hp!");
    }
}
