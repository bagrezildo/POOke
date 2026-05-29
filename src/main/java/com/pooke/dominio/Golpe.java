package com.pooke.dominio;

public abstract class Golpe {
    private final String nome;
    private final int poderBase;
    private final Tipo tipo;

    public Golpe(String nome, int poderBase, Tipo tipo) {
        this.nome = nome;
        this.poderBase = poderBase;
        this.tipo = tipo;
    }

    public abstract int extrairPoderAtaque(PokemonStats statsAtacante);
    public abstract int extrairResistenciaDefesa(PokemonStats statsDefensor);

    /***
     * Calula o multiplicador de fraqueza/resistência contra o pokemon defensor.
     * @param tipoDefensor1
     * @param tipoDefensor2
     * @return
     */
    public double getMultiplicadorEficacia(Tipo tipoDefensor1, Tipo tipoDefensor2) {
        return this.tipo.calcularMultiplicador(tipoDefensor1, tipoDefensor2);
    }

    /***
     * Calcula o STAB (Same Type Attack Bonus).
     * Se o tipo do golpe for o mesmo de um dos tipos do atacante, ganha um bônus de 50%.
     * @param tipoAtacante1
     * @param tipoAtacante2
     * @return
     */

    public double getMultiplicadorStab(Tipo tipoAtacante1, Tipo  tipoAtacante2) {
        if (this.tipo == tipoAtacante1 || this.tipo == tipoAtacante2) {
            return 1.5;
        }
        return 1.0;
    }

    //Getters
    public String getNome() { return nome; }
    public Tipo getTipo() { return tipo; }
    public int getPoderBase() { return poderBase; }

}
