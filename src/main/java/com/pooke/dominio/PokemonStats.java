package com.pooke.dominio;

public record PokemonStats(
        int hp,
        int ataque,
        int defesa,
        int ataqueEspecial,
        int defesaEspecial,
        int velocidade
) {}
