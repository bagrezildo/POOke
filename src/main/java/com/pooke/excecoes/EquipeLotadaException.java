package com.pooke.excecoes;

/**
 * Disparada quando o Treinador tenta capturar um novo Pokémon,
 * mas a equipe já atingiu o limite máximo de 6 integrantes.
 */
public class EquipeLotadaException extends RuntimeException {
    public EquipeLotadaException(String message) {
        super(message);
    }
}
