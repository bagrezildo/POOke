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

public class MotorBatalha {
    private final Random random = new Random();

    public boolean batalhar(Treinador jogador, Equipe inimigos) {
        Printer.imprimir("\n =========== BATALHA ===========");

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

            if (validarVelocidade(ativoJogador.getVelocidade(), ativoInimigo.getVelocidade())) {
                realizarAtaque(ativoJogador, ativoInimigo);

                if (ativoInimigo.getHpAtual() > 0){
                    realizarAtaque(ativoInimigo, ativoJogador);
                }
            } else {
                realizarAtaque(ativoInimigo, ativoJogador);

                if (ativoJogador.getHpAtual() > 0){
                    realizarAtaque(ativoJogador, ativoInimigo);
                }
            }

            verificarCuraAutomatica(jogador, jogador.getEquipe().obterProximoPokemonVivo());
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

        int dano = (int) ((golpe.getPoderBase() + atacante.getAtaque()) * multiplicador) - defensor.getDefesa();

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
