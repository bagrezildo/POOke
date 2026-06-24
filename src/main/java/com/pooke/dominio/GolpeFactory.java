package com.pooke.dominio;

public class GolpeFactory {
    public static Golpe criar(String nome, Integer poderBase, Tipo tipo, String classeDano) {
        int poder = (poderBase != null) ? poderBase : 0; //golpes de status podem vir com o poderBase null

        // Capitaliza o nome
        nome = nome.substring(0, 1).toUpperCase() + nome.substring(1);

        if ("physical".equalsIgnoreCase(classeDano)) {
            return new GolpeFisico(nome, poder, tipo);
        } else if ("special".equalsIgnoreCase(classeDano)) {
            return new GolpeEspecial(nome, poder, tipo);
        } else {
            return new GolpeFisico(nome, 0, tipo); //Agora não vamos lidar com golpes de status, vamos tratar como físico com dano 0
        }
    }
}
