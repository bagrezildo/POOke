package com.pooke.dominio;

import com.pooke.aplicacao.MotorBatalha;
import com.pooke.ui.Printer;

public class EncontroCombate extends Encontro {
    private Equipe inimigos;

    public EncontroCombate(Equipe inimigos){
        this.inimigos = inimigos;
    }

    @Override
    public void processar(Treinador jogador) {
        MotorBatalha motor = new MotorBatalha();

        motor.batalhar(jogador, this.inimigos);
    }
}
