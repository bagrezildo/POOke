package com.pooke.aplicacao;

import com.pooke.dominio.*;
import com.pooke.dominio.itens.Item;
import com.pooke.excecoes.EquipeDerrotadaException;
import com.pooke.excecoes.SessaoInvalidaException;
import com.pooke.util.Printer;

public class Sessao {
    EstadoSessao estadoAtual;
    Treinador treinador;
    com.pooke.apresentacao.RepositorioPokemon repositorio;
    int contBatalhas;

    public Sessao(Treinador treinador, com.pooke.apresentacao.RepositorioPokemon repositorio) {
        this.estadoAtual = EstadoSessao.PREPARACAO;
        this.treinador = treinador;
        this.repositorio = repositorio;
        this.contBatalhas = 0;
    }

    public void preparar(){
        if (this.estadoAtual != EstadoSessao.PREPARACAO){
            throw new SessaoInvalidaException("Sem meia volta! O momento de preparação já passou, siga na jornada!");
        }

        Printer.imprimir("Iniciando uma nova jornada!");
        this.estadoAtual = EstadoSessao.EXPLORACAO;
    }

    public void explorar(){
        if (this.estadoAtual != EstadoSessao.EXPLORACAO)
            throw new SessaoInvalidaException("Tem hora e lugar para tudo, mas você não pode fazer isso agora.");

        if (this.contBatalhas > 0 && this.contBatalhas % 3 == 0 ) {
            this.estadoAtual = EstadoSessao.NO_ACAMPAMENTO;
            return;
        }


        Equipe equipeInimiga = GeradorDeEncontros.gerarEquipeInimiga(this.treinador, this.repositorio);
        Printer.imprimir("\n Um "+ equipeInimiga.getPokemons().get(0).getNome() + " selvagem apareceu!");

        EncontroCombate encontroCombate = new EncontroCombate(equipeInimiga);

        try {
            encontroCombate.processar(this.treinador);
            this.contBatalhas++;
        } catch (EquipeDerrotadaException e){
            Printer.imprimir("FIM DE JOGO: " + e.getMessage());
            this.estadoAtual = EstadoSessao.FINALIZADA;
        }
    }

    public void sairDoAcampamento() {
        if (this.estadoAtual != EstadoSessao.NO_ACAMPAMENTO) {
            throw new SessaoInvalidaException("Tem hora e lugar para tudo, você não pode fazer isso agora.");
        }

        // TODO remover lógica de cura no acampamento enquanto não implementamos o uso de itens
        for (Pokemon p : treinador.getEquipe().getPokemons()){
            p.receberCura(p.getHpMax());
        }

        Printer.imprimir("Voltando para a rota");
        this.contBatalhas++;

        this.estadoAtual = EstadoSessao.EXPLORACAO;
    }

    public void acamparUsarItem(Item item, Pokemon alvo) {
        if (this.estadoAtual != EstadoSessao.NO_ACAMPAMENTO) {
            throw new SessaoInvalidaException("Tem hora e lugar para tudo, você não pode usar itens agora.");
        }
        item.aplicar(alvo);
        this.treinador.removerItem(item);
    }

    public void acamparEquiparGolpe(Pokemon pokemon, Golpe novoGolpe, Golpe antigoGolpe) {
        if (this.estadoAtual != EstadoSessao.NO_ACAMPAMENTO) {
            throw new SessaoInvalidaException("Tem hora e lugar para tudo, você não pode equiper golpes isso agora.");
        }
        pokemon.equiparGolpe(novoGolpe, antigoGolpe);
    }

    public EstadoSessao getEstadoAtual() { return   this.estadoAtual; }
}
