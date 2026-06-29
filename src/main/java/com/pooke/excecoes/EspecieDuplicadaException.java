package com.pooke.excecoes;

/**
 * Impede que o jogador capture múltiplos Pokémons da mesma espécie.
 */
public class EspecieDuplicadaException extends RuntimeException {
    public EspecieDuplicadaException(String message) {
        super(message);
    }
}
