package com.pooke.excecoes;

public class SessaoInvalidaException extends RuntimeException {
    public SessaoInvalidaException(String message) {
        super(message);
    }
}
