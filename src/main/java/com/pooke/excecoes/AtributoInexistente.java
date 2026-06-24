package com.pooke.excecoes;

public class AtributoInexistente extends RuntimeException {
    public AtributoInexistente(String message) {
        super(message);
    }
}
