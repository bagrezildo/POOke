package com.pooke.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PokedexManager {
    private static final String POKEDEX_FILE = "pokedex.txt";

    public static List<String> carregarPokedex() {
        File file = new File(POKEDEX_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(Paths.get(POKEDEX_FILE));
        } catch (IOException e) {
            System.out.println("Erro ao carregar a Pokedex: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void registrar(String nomePokemon) {
        List<String> registrados = carregarPokedex();
        Set<String> registradosSet = new HashSet<>(registrados);

        if (!registradosSet.contains(nomePokemon)) {
            try {
                String linha = nomePokemon + System.lineSeparator();
                Files.write(Paths.get(POKEDEX_FILE), linha.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Printer.imprimir("\n[Novo Registro na Pokedex] " + nomePokemon + " foi adicionado a sua pokedex!");
            } catch (IOException e) {
                System.out.println("Erro ao salvar na Pokedex: " + e.getMessage());
            }
        }
    }
}
