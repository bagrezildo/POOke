package com.pooke.dominio;

import com.pooke.ui.Printer;

public class EncontroCombate extends Encontro {
    private Equipe inimigos;

    public void processar(Treinador jogador) {
        Printer.imprimir("EncontroCombate");
    }
}
