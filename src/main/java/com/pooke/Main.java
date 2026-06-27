package com.pooke;

import com.pooke.aplicacao.EstadoSessao;
import com.pooke.aplicacao.Sessao;
import com.pooke.apresentacao.RepositorioPokemon;
import com.pooke.dominio.*;
import com.pooke.dominio.itens.PocaoCura;
import com.pooke.excecoes.SessaoInvalidaException;
import com.pooke.util.Printer;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Printer.header();

        Scanner scanner =  new Scanner(System.in);

        RepositorioPokemon repositorio = new RepositorioPokemon();
        repositorio.inicializarBanco();

        Treinador treinador = new Treinador("Matri");

        treinador.adicionarItem(new PocaoCura(20));
        treinador.adicionarItem(new PocaoCura(20));
        Sessao sessao = new Sessao(treinador);

        while (sessao.getEstadoAtual() != EstadoSessao.FINALIZADA) {
            try {
                switch (sessao.getEstadoAtual()) {
                    case PREPARACAO:
                        List<Pokemon> iniciais = repositorio.sortearIniciais(3);
                        Printer.imprimirIniciais(iniciais);

                        Printer.imprimir("Escolha um pokémon inicial: ");
                        int escolha = Integer.parseInt(scanner.nextLine());
                        if (escolha < 1 || escolha > iniciais.size()) {
                            Printer.imprimir("Opção inválida! Escolha um dos três iniciais disponíveis!");
                            continue;
                        }

                        Pokemon escolhido = iniciais.get(escolha - 1);
                        treinador.getEquipe().adicionarPokemon(escolhido);

                        Printer.imprimir("Você escolheu o " +escolhido.getNome() + ", boa jornada!");
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
