package com.pooke.util;

import com.pooke.dominio.Golpe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Treinador;

import java.util.List;

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
        System.out.println("3 - Abrir Pokédex");
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
        StringBuilder sb = new StringBuilder();
        List<Pokemon> pokemons = treinador.getEquipe().getPokemons();
        sb.append("\n========== SUA EQUIPE ==========\n");
        sb.append("Pokémons Vivos: " + (treinador.getEquipe().obterProximoPokemonVivo() != null ? "Sim\n" : "Não\n"));
        if (treinador.getEquipe().getPokemons().isEmpty()) {
            sb.append("Sua equipe está vazia.");
        } else {
            for (Pokemon p : pokemons) {
                sb.append("[").append(pokemons.indexOf(p) + 1).append("]");
                sb.append(" ").append(p.toString());
                sb.append("\n");
            }

            System.out.println(sb.toString());
        }
    }

    public static void imprimirIniciais(List<Pokemon> pokemons) {
        StringBuilder sb = new StringBuilder();

        sb.append("========== INICIAIS ==========");
        sb.append("\n");
        sb.append("\n");
        for (Pokemon p : pokemons) {
            sb.append("[").append(pokemons.indexOf(p) + 1).append("]");
            sb.append(" ").append(p.toString());
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public static void imprimirPokedex(){
        List<String> registrados = PokedexManager.carregarPokedex();
        System.out.println("\n========== SUA POKÉDEX ==========");
        if (registrados.isEmpty()) {
            System.out.println("Sua Pokédex está vazia! Explore e capture novos Pokémons.");
        } else {
            System.out.println("Você já registrou " + registrados.size() + " espécie(s):");
            for (String p : registrados) {
                System.out.println("- " + p);
            }
        }
        System.out.println("=================================");
    }
}
