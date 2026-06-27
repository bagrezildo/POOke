package com.pooke.ui;

import com.pooke.dominio.Equipe;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Treinador;

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
        System.out.println(defensor.getNome() + " recebeu " + dano + " de dano!");

    }

    public static void narrarDesmaio(Pokemon pokemon){
        System.out.println("O " + pokemon.getNome() + " desmaiou!");
    }

    public static void narrarCura(Pokemon pokemon, int cura){
        System.out.println(pokemon.getNome() + " curou " + cura + "de hp!");
    }

    public static void imprimirMenuExploracao() {
        System.out.println("\n[MENU EXPLORAÇÃO]");
        System.out.println("1 - Avançar na Rota");
        System.out.println("2 - Ver Status da Equipe");
        System.out.println("0 - Desistir (Finalizar Jogo)");
        System.out.print("Sua escolha: ");
    }
    public static void imprimirMenuAcampamento() {
        System.out.println("\n[MENU ACAMPAMENTO - FOGUEIRA]");
        System.out.println("1 - Descansar e Voltar para a Rota");
        System.out.println("2 - Usar Item de Cura");
        System.out.print("Sua escolha: ");
    }
    public static void imprimirEquipe(Treinador treinador) {
        System.out.println("\n========== SUA EQUIPE ==========");
        System.out.println("Pokémons Vivos: " + (treinador.getEquipe().obterProximoPokemonVivo() != null ? "Sim" : "Não"));
        if (treinador.getEquipe().getPokemons().isEmpty()) {
            System.out.println("Sua equipe está vazia.");
        } else {
            for (Pokemon p : treinador.getEquipe().getPokemons()) {
                System.out.println(p.toString());
            }
        }
    }

}
