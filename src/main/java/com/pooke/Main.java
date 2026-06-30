package com.pooke;

import com.pooke.aplicacao.EstadoSessao;
import com.pooke.aplicacao.Sessao;
import com.pooke.apresentacao.RepositorioPokemon;
import com.pooke.dominio.*;
import com.pooke.dominio.itens.PocaoCura;
import com.pooke.excecoes.SessaoInvalidaException;
import com.pooke.util.PokedexManager;
import com.pooke.util.Printer;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Printer.header();
        int numeroEncontro = 0;
        
        List<String> pokedex = PokedexManager.carregarPokedex();
        if (!pokedex.isEmpty()) {
            Printer.imprimir("== Bem-vindo de volta! Sua Pokédex possui " + pokedex.size() + " espécies registradas! ==");
        }

        Scanner scanner =  new Scanner(System.in);

        RepositorioPokemon repositorio = new RepositorioPokemon();
        repositorio.inicializarBanco();

        System.out.print("Digite o nome do jogador: ");
        String nome = scanner.nextLine();

        Treinador treinador = new Treinador(nome);

        treinador.adicionarItem(new PocaoCura(20));
        treinador.adicionarItem(new PocaoCura(20));
        Sessao sessao = new Sessao(treinador, repositorio);

        while (sessao.getEstadoAtual() != EstadoSessao.FINALIZADA) {
            numeroEncontro++;
            Printer.imprimir("Encontro número " + numeroEncontro);
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
                        PokedexManager.registrar(escolhido.getNome());

                        Printer.imprimir("Você escolheu o " +escolhido.getNome() + ", boa jornada!");
                        sessao.preparar();
                        break;
                    case EXPLORACAO:
                        Printer.imprimirMenuExploracao();

                        String opcao = scanner.nextLine();
                        if (opcao.equals("1")) {
                            sessao.explorar();
                        } else if (opcao.equals("2")) {
                            Printer.imprimirEquipe(treinador);
                        } else if (opcao.equals("3")) {
                            Printer.imprimirPokedex();
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
                System.err.println("\n[ERRO] " + e.getMessage());
            } catch (Exception e) {
                System.err.println("\n[ERRO CRÍTICO] " + e.getMessage());
            }
        }
        Printer.imprimir("\nObrigado por jogar POOke!");
        scanner.close();
    }
}
