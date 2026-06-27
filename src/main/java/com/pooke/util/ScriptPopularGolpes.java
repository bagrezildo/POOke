package com.pooke.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.pooke.apresentacao.PokeApiClient;
import com.pooke.apresentacao.dto.MoveApiDto;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.Tipo;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ScriptPopularGolpes {
    public static void main(String[] args) {
        PokeApiClient apiClient = new PokeApiClient();
        ObjectMapper mapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newHttpClient();
        System.out.println("Iniciando varredura completa da PokeAPI...");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/move/?limit=1000"))
                    .GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode results = rootNode.get("results");

            int total = results.size();
            System.out.println("Encontrados " + total + " golpes na API. Começando o download com streaming de arquivo...");
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            try (SequenceWriter seqWriter = writer.writeValuesAsArray(new File("data/golpes_db.json"))) {

                int contador = 1;
                for (JsonNode node : results) {
                    String url = node.get("url").asText();
                    MoveApiDto moveDto = apiClient.buscarGolpe(url);

                    if (moveDto != null && moveDto.type != null) {
                        Tipo tipoGolpe;
                        try {
                            tipoGolpe = Tipo.valueOf(moveDto.type.name.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            tipoGolpe = Tipo.UNKNOWN;
                        }

                        int poder = (moveDto.power != null && moveDto.power > 0) ? moveDto.power : 40;
                        Golpe golpe = new Golpe(moveDto.name, poder, tipoGolpe);

                        seqWriter.write(golpe);
                        System.out.println("[" + contador + "/" + total + "] Salvo: " + golpe.getNome());
                    }

                    contador++;
                    Thread.sleep(100);
                }
            }
            System.out.println("\nScript finalizado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro crítico no script: " + e.getMessage());
        }
    }
}
