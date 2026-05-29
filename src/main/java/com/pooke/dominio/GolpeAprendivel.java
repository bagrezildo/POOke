package com.pooke.dominio;

public record GolpeAprendivel(
        Golpe golpe,
        int nivelMinimo // Se for 0, pode ser TM, Egg Move ou Golpe Inicial
) {}
