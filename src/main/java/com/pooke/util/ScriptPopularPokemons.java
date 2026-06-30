package com.pooke.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.pooke.apresentacao.PokeApiClient;
import com.pooke.apresentacao.dto.PokemonApiDto;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScriptPopularPokemons {
    public static void main(String[] args) {
        PokeApiClient apiClient = new PokeApiClient();
        ObjectMapper mapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newHttpClient();
        Printer.imprimir("Iniciando varredura da PokeAPI para os Pokémons originais...");
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon/?limit=151"))
                    .GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode results = rootNode.get("results");

            int total = results.size();
            Printer.imprimir("Encontrados " + total + " Pokémons. Começando o download com streaming de arquivo...");
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            try (SequenceWriter seqWriter = writer.writeValuesAsArray(new File("data/pokemons_db.json"))) {

                int contador = 1;
                for (JsonNode node : results) {
                    String url = node.get("url").asText();

                    String[] partesUrl = url.split("/");
                    int idPokemon = Integer.parseInt(partesUrl[partesUrl.length - 1]);

                    PokemonApiDto dto = apiClient.buscarPokemon(idPokemon);

                    if (dto != null) {
                        seqWriter.write(dto);
                        Printer.imprimir("[" + contador + "/" + total + "] Salvo no disco: " + dto.name);
                    }

                    contador++;
                    Thread.sleep(100);
                }
            }

            Printer.imprimir("\nScript finalizado com sucesso! O banco de dados de Pokémons está completo.");

        } catch (Exception e) {
            System.err.println("Erro crítico no script: " + e.getMessage());
        }
    }
}