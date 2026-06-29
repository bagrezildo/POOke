package com.pooke.dominio;

/**
 * Representa os ataques disponíveis para os Pokémons.
 * Contém informações essenciais de poder base e Tipo para cálculos de dano.
 */
public class Golpe {
    private String nome;
    private int poderBase;
    private Tipo tipo;

    public Golpe() {}

    public Golpe(String nome, int poderBase, Tipo tipo) {
        this.nome = formataNome(nome);
        this.poderBase = poderBase;
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s - %s]", nome, tipo));
        return sb.toString();
    }

    public static String formataNome(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.split("-");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));

                if (i < words.length - 1) {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = formataNome(nome);
    }

    public int getPoderBase() {
        return poderBase;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
