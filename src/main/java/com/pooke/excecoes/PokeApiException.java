package com.pooke.excecoes;

public class PokeApiException extends RuntimeException {
    public PokeApiException(String mensagem) {
       super(mensagem);
    }

    public PokeApiException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
