package com.pooke.persistencia;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pooke.dominio.*;
import com.pooke.excecoes.PokeApiException;
import com.pooke.persistencia.dto.MoveApiDto;
import com.pooke.persistencia.dto.PokemonApiDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PokeApiClient {
    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper; // Transformado em atributo da classe

    public PokeApiClient() {
        this.restTemplate = new RestTemplate();

        // Configura o Jackson puro, sem depender dos conversores depreciados do Spring
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public EspeciePokemon buscarPokemonPorId(int id) {
        try {
            String url = BASE_URL + id;

            // 1. Busca o JSON bruto como String
            String jsonBruto = restTemplate.getForObject(url, String.class);

            if (jsonBruto == null || jsonBruto.isBlank()) {
                throw new PokeApiException("A API não retornou dados para o ID: " + id);
            }

            // 2. O nosso ObjectMapper faz a conversão direta e segura
            PokemonApiDto dto = mapper.readValue(jsonBruto, PokemonApiDto.class);

            if (dto == null || dto.name == null) {
                throw new PokeApiException("A API retornou um formato inválido para o ID: " + id);
            }

            return converterDtoParaDominio(dto);
        } catch (Exception e) {
            throw new PokeApiException("Falha crítica ao buscar a espécie de ID " + id + " na PokeAPI", e);
        }
    }

    private EspeciePokemon converterDtoParaDominio(PokemonApiDto dto) {
        if (dto.types == null || dto.types.isEmpty()) throw new PokeApiException("Tipos não encontrados na API.");
        if (dto.stats == null || dto.stats.isEmpty()) throw new PokeApiException("Status não encontrados na API.");

        Tipo tipo1 = Tipo.valueOf(dto.types.get(0).type.name.toUpperCase());
        Tipo tipo2 = dto.types.size() > 1 ? Tipo.valueOf(dto.types.get(1).type.name.toUpperCase()) : null;

        PokemonStats statsBase = mapearStats(dto);

        EspeciePokemon.Builder builderEspecie = new EspeciePokemon.Builder()
                .id(dto.id)
                .nome(dto.name)
                .tipoPrimario(tipo1)
                .tipoSecundario(tipo2)
                .statsBase(statsBase);

        if (dto.moves != null) {
            List<CompletableFuture<GolpeAprendivel>> futuros = dispararBuscaDeGolpesEmParalelo(dto);

            futuros.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .forEach(builderEspecie::adicionarGolpe);
        }

        return builderEspecie.build();
    }

    private int extrairStat(PokemonApiDto dto, String nomeDoStat) {
        // Usando o foreach clássico, o debugger consegue enxergar a variável 'wrapper' perfeitamente!
        for (PokemonApiDto.StatWrapper wrapper : dto.stats) {

            if (wrapper.stat != null && nomeDoStat.equals(wrapper.stat.name)) {
                // Achou o stat correto! Retorna o valor lido do JSON
                return wrapper.baseStat;
            }
        }

        // Se o loop terminar e não achar, a API mandou um Pokémon defeituoso
        throw new PokeApiException("O stat '" + nomeDoStat + "' não foi encontrado!");
    }

    private PokemonStats mapearStats(PokemonApiDto dto) {
        int hp = extrairStat(dto, "hp");
        int ataque = extrairStat(dto, "attack");
        int defesa = extrairStat(dto, "defense");
        int spAtaque = extrairStat(dto, "special-attack");
        int spDefesa = extrairStat(dto, "special-defense");
        int velocidade = extrairStat(dto, "speed");

        return new PokemonStats(hp, ataque, defesa, spAtaque, spDefesa, velocidade);
    }

    private List<CompletableFuture<GolpeAprendivel>> dispararBuscaDeGolpesEmParalelo(PokemonApiDto dto) {
        List<CompletableFuture<GolpeAprendivel>> futuros = new ArrayList<>();

        if (dto.moves == null) {
            return futuros;
        }

        for (PokemonApiDto.MoveWrapper wrapper : dto.moves) {
            // Agora verificamos o levelLearnedAt diretamente no wrapper
            if (wrapper.move == null || wrapper.move.url == null || wrapper.levelLearnedAt == null) {
                continue; // Pula se vier lixo ou se não aprender por level-up
            }

            String urlDoGolpe = wrapper.move.url;
            final int nivelFinal = wrapper.levelLearnedAt;

            CompletableFuture<GolpeAprendivel> futuro = CompletableFuture.supplyAsync(() -> {
                try {
                    String jsonGolpeBruto = restTemplate.getForObject(urlDoGolpe, String.class);

                    if (jsonGolpeBruto != null && !jsonGolpeBruto.isBlank()) {
                        MoveApiDto moveDto = mapper.readValue(jsonGolpeBruto, MoveApiDto.class);

                        if (moveDto != null && moveDto.type != null && moveDto.damage_class != null) {
                            Tipo tipoGolpe = Tipo.valueOf(moveDto.type.name.toUpperCase());

                            Golpe golpeInstanciado = GolpeFactory.criar(
                                    moveDto.name,
                                    moveDto.power,
                                    tipoGolpe,
                                    moveDto.damage_class.name
                            );

                            return new GolpeAprendivel(golpeInstanciado, nivelFinal);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("⚠️ Alerta: Falha ao mapear golpe na URL " + urlDoGolpe + ": " + e.getMessage());
                }
                return null;
            });

            futuros.add(futuro);
        }

        return futuros;
    }
}