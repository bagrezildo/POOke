package com.pooke.aplicacao;

import com.pooke.dominio.Equipe;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Treinador;
import com.pooke.dominio.itens.PocaoCura;
import com.pooke.excecoes.EquipeDerrotadaException;
import com.pooke.util.Printer;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MotorBatalha {
    private final Random random = new Random();

    public boolean batalhar(Treinador jogador, Equipe inimigos) {
        Printer.imprimir("\n =========== BATALHA ===========");

        Pokemon ultimoAtivoJogador = null;
        Pokemon ultimoAtivoInimigo = null;

        while (true) {
            Pokemon ativoJogador = jogador.getEquipe().obterProximoPokemonVivo();
            Pokemon ativoInimigo = inimigos.obterProximoPokemonVivo();


            if (ativoJogador == null) {
                throw new EquipeDerrotadaException("Toda a sua equipe foi derrotada!");
            }

            if (ativoInimigo == null) {
                Printer.imprimir("\n Você derrotou a equipe inimiga! Vitória!");
                return true;
            }

            if (ultimoAtivoInimigo != ativoInimigo) {
                Printer.imprimir(ativoInimigo.toString());
                ultimoAtivoInimigo = ativoInimigo;
            }

            if (ultimoAtivoJogador != ativoJogador) {
                Printer.imprimir("\n" + jogador.getNome() + " enviou " + ativoJogador.getNome() + " para batalha!");
                ultimoAtivoJogador = ativoJogador;
            }

            if (validarVelocidade(ativoJogador.getVelocidade(), ativoInimigo.getVelocidade())) {
                realizarAtaque(ativoJogador, ativoInimigo);

                if (ativoInimigo.getHpAtual() == 0){
                    int xpGanho = ativoInimigo.getNivel() * 50;
                    jogador.expShare(xpGanho);

                    tentarCapturar(ativoInimigo, jogador, inimigos);
                } else {
                    realizarAtaque(ativoInimigo, ativoJogador);
                }
            } else {
                realizarAtaque(ativoInimigo, ativoJogador);

                if (ativoJogador.getHpAtual() > 0){
                    realizarAtaque(ativoJogador, ativoInimigo);
                    
                    if (ativoInimigo.getHpAtual() == 0) {
                        int xpGanho = ativoInimigo.getNivel() * 50;
                        jogador.expShare(xpGanho);
                        tentarCapturar(ativoInimigo, jogador, inimigos);
                    }
                }
            }

            verificarCuraAutomatica(jogador, jogador.getEquipe().obterProximoPokemonVivo());
        }
    }

    private void tentarCapturar(Pokemon inimigoDerrotado, Treinador jogador, Equipe inimigos){
        int chance = this.random.nextInt(100);

        Scanner scanner = new Scanner(System.in);
        Printer.imprimir("\n ======== CAPTURAR ======== \n");
        Printer.imprimir("Deseja capturar " + inimigoDerrotado.getNome() + "? (S/N)");
        String resposta =  scanner.nextLine().trim().toUpperCase();

        if (resposta.equals("S") || resposta.equals("1")) {
            if (chance >= 40){
                Equipe equipe = jogador.getEquipe();
                if (equipe.tamanho() >= equipe.getLimiteMaximo()){
                    Printer.imprimir("Sua equipe está cheia! Você precisa substituir alguém.");
                    Printer.imprimirEquipe(jogador);
                    Printer.imprimir("Digite o número do pokémon para substituir (0 para cancelar):");

                    try {
                        int escolha = Integer.parseInt(scanner.nextLine());
                        if (escolha > 0 && escolha <= equipe.tamanho()){
                            Pokemon libertado = equipe.getPokemons().get(escolha - 1);
                            equipe.removerPokemon(libertado);
                            equipe.adicionarPokemon(inimigoDerrotado);
                            inimigos.removerPokemon(inimigoDerrotado);
                            Printer.imprimir("Você substituiu seu " + libertado.getNome() + " por " + inimigoDerrotado.getNome() + "!");
                            com.pooke.util.PokedexManager.registrar(inimigoDerrotado.getNome());
                        } else {
                            Printer.imprimir("Captura cancelada!");
                        }
                    } catch (Exception e) {
                        Printer.imprimir("Captura invalida!");
                    }
                } else {
                    equipe.adicionarPokemon(inimigoDerrotado);
                    inimigos.removerPokemon(inimigoDerrotado);
                    Printer.imprimir(inimigoDerrotado.getNome() + " CAPTURADO!");
                    com.pooke.util.PokedexManager.registrar(inimigoDerrotado.getNome());
                }
            } else {
                Printer.imprimir("Ah não! " + inimigoDerrotado.getNome() + " escapou!");
            }
        }
    }

    private boolean validarVelocidade(int velAliado, int velInimigo){
        return velAliado > velInimigo;
    }

    private void realizarAtaque(Pokemon atacante, Pokemon defensor){
        List<Golpe> golpes = atacante.getGolpesEquipados();

        if (golpes.isEmpty()){
            int dano = (int)(atacante.getAtaque()*0.1);
            defensor.receberDano(atacante.getAtaque());
            atacante.receberDano(dano);
            Printer.imprimir(atacante.getNome() + " não tem golpes equipados e teve que usar Sufoco!");
            Printer.imprimir(atacante.getNome() + " se feriu e recebeu " + dano + "de dano!");
            Printer.imprimir(defensor.getNome() + "recebeu " + dano + "de dano!");
            return;
        }

        Golpe golpe = golpes.get(random.nextInt(golpes.size()));

        double multiplicador = golpe.getTipo().calcularMultiplicador(defensor.getTipoPrimario(), defensor.getTipoSecundario());

        // Fórmula balanceada: (Poder Base * (Ataque / Defesa)) * Multiplicador
        int dano = (int) ((golpe.getPoderBase() * ((double)atacante.getAtaque() / Math.max(1, defensor.getDefesa()))) * multiplicador);

        if (dano < 1) { dano = 1; }

        defensor.receberDano(dano);

        Printer.narrarAtaque(atacante, golpe, defensor, dano);

        if (defensor.getHpAtual() == 0){
            Printer.narrarDesmaio(defensor);
        }
    }

    private void verificarCuraAutomatica(Treinador jogador, Pokemon ativo){
        if (ativo == null || ativo.getHpAtual() == 0) return;

        double limiteHp = ativo.getHpMax() * 0.3;

        if (ativo.getHpAtual() > limiteHp){
            return;
        }

        PocaoCura pocao = (PocaoCura) jogador.getInventario().stream()
                .filter(item -> item instanceof PocaoCura)
                .findFirst()
                .orElse(null);

        if (pocao != null){
            pocao.aplicar(ativo);
            jogador.removerItem(pocao);
            Printer.imprimir(jogador.getNome() + " usou uma Poção automaticamente em " + ativo.getNome() + "!");
        }
    }
}
