package com.pooke.aplicacao;

import com.pooke.apresentacao.RepositorioPokemon;
import com.pooke.dominio.Equipe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Treinador;

import java.util.List;

/**
 * Fábrica de eventos dinâmica. Sorteia o tipo de encontro que o jogador terá (Batalha ou Achado de Item)
 * e nivela os inimigos dinamicamente de acordo com a força atual da equipe do jogador.
 */
public class GeradorDeEncontros {
    public static Equipe gerarEquipeInimiga(Treinador treinador, RepositorioPokemon repositorio) {
        Equipe equipeTreinador = treinador.getEquipe();

        int somaNivels = 0;
        for (Pokemon p : equipeTreinador.getPokemons()) {
            somaNivels += p.getNivel();
        }

        int nivelMedio = somaNivels / equipeTreinador.getPokemons().size();
        int maxInimigos = Math.min(6, (nivelMedio / 5) + 1);
        int tamanhoEquipe = (int) (Math.random() * maxInimigos) + 1;

        Equipe equipeInimiga = new Equipe();

        List<Pokemon> sorteados = repositorio.sortearIniciais(tamanhoEquipe);

        for (Pokemon inimigo : sorteados) {
            int variancia =  (int) (Math.random() * 4) - 1;
            int nivel = Math.max(1, nivelMedio + variancia);

            while (inimigo.getNivel() < nivel) {
                inimigo.subirDeNivel();
            }
            equipeInimiga.adicionarPokemon(inimigo);
        }

        return equipeInimiga;
    }
}
