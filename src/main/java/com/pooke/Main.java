package com.pooke;

import com.pooke.aplicacao.EstadoSessao;
import com.pooke.aplicacao.Sessao;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.PokemonSpecial;
import com.pooke.dominio.Tipo;
import com.pooke.dominio.Treinador;
import com.pooke.dominio.itens.PocaoCura;
import com.pooke.excecoes.SessaoInvalidaException;
import com.pooke.ui.Printer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Printer.header();

        Scanner scanner =  new Scanner(System.in);

        Treinador treinador = new Treinador("Matri");

        PokemonSpecial pikachu = new PokemonSpecial("Pikachu", Tipo.ELECTRIC, null, 25, 10, 10, 12);
        pikachu.getGolpesEquipados().add(new Golpe("Choque do Trovão", 40, Tipo.ELECTRIC));
        treinador.getEquipe().adicionarPokemon(pikachu);

        PokemonSpecial charmander = new PokemonSpecial("Charmander", Tipo.FIRE, null, 22, 11, 11, 11);
        charmander.getGolpesEquipados().add(new Golpe("Brasas", 40, Tipo.FIRE));
        treinador.getEquipe().adicionarPokemon(charmander);
        treinador.adicionarItem(new PocaoCura(20));
        treinador.adicionarItem(new PocaoCura(20));
        Sessao sessao = new Sessao(treinador);

        while (sessao.getEstadoAtual() != EstadoSessao.FINALIZADA) {
            try {
                switch (sessao.getEstadoAtual()) {
                    case PREPARACAO:
                        sessao.preparar();
                        break;
                    case EXPLORACAO:
                        Printer.imprimirMenuExploracao();

                        String opcao = scanner.nextLine();
                        if (opcao.equals("1")) {
                            sessao.explorar();
                        } else if (opcao.equals("2")) {
//                            Printer.imprimir("Pokémons Vivos: " + (treinador.getEquipe().obterProximoPokemonVivo() != null ? "Sim" : "Não"));
                            Printer.imprimirEquipe(treinador);
                        } else if (opcao.equals("0")) {
                            Printer.imprimir("Você desistiu da jornada.");
                            System.exit(0);
                        }
                        break;
                    case NO_ACAMPAMENTO:
                        Printer.imprimirMenuAcampamento();

                        String acamp = scanner.nextLine();
                        if (acamp.equals("1")) {
                            sessao.sairDoAcampamento();
                        } else if (acamp.equals("2")) {
                            Printer.imprimir("Funcionalidade de usar item em construção...");
                        }
                        break;
                }
            } catch (SessaoInvalidaException e) {
                System.out.println("\n[ERRO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERRO CRÍTICO] " + e.getMessage());
            }
        }
        Printer.imprimir("\nObrigado por jogar POOke!");
        scanner.close();
    }
}
