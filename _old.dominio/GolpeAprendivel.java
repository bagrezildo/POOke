package com.pooke._old.dominio;

public record GolpeAprendivel(
        Golpe golpe,
        int nivelMinimo // Se for 0, pode ser TM, Egg Move ou Golpe Inicial
) {}
