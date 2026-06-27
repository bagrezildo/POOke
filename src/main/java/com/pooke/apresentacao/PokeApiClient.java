package com.pooke.apresentacao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pooke.apresentacao.dto.MoveApiDto;
import com.pooke.apresentacao.dto.PokemonApiDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeApiClient {
    private final String BASE_URL = "https://pokeapi.co/api/v2/";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public PokeApiClient() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public PokemonApiDto buscarPokemon(int id){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "pokemon/" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), PokemonApiDto.class);
            }
        } catch (Exception e){
            System.err.println("Erro ao buscar pokemon: " + e.getMessage());
        }
        return null;
    }

    public MoveApiDto buscarGolpe(String urlGolpe) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlGolpe))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), MoveApiDto.class);
            }
        } catch (Exception e) {
            System.err.println("Falha de rede ao buscar golpe: " + e.getMessage());
        }
        return null;
    }
}
