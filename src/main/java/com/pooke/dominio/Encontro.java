package com.pooke.dominio;

/**
 * Classe base polimórfica que representa um evento no mapa durante a exploração.
 * Suas filhas (EncontroCombate, EncontroItem, etc.) implementam lógicas
 * distintas de processamento.
 */
public abstract class Encontro {
    public abstract void processar(Treinador jogador);
}
