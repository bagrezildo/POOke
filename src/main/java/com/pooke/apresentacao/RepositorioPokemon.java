package com.pooke.apresentacao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pooke.apresentacao.cache.GerenciadorCacheJson;
import com.pooke.apresentacao.dto.PokemonApiDto;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Tipo;
import com.pooke.util.ScriptPopularGolpes;
import com.pooke.util.ScriptPopularPokemons;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioPokemon {
    private final GerenciadorCacheJson cache;
    private List<PokemonApiDto> bancoDePokemons;
    private Map<String, Golpe> bancoDeGolpes;

    public RepositorioPokemon() {
        this.cache = new GerenciadorCacheJson();
        this.bancoDePokemons = new ArrayList<>();
        this.bancoDeGolpes = new HashMap<>();
    }

    public void inicializarBanco() {
        File arquivoGolpes = new File("data/golpes_db.json");
        if (!arquivoGolpes.exists()) {
            System.out.println("==== Banco de Golpes não encontrado. Iniciando gerador... ====");
            ScriptPopularGolpes.main(new String[]{});
        }

        File arquivoPokemons = new File("data/pokemons_db.json");
        if (!arquivoPokemons.exists()) {
            System.out.println("==== Banco de Pokémons não encontrado. Iniciando gerador... ====");
            ScriptPopularPokemons.main(new String[]{});
        }

        List<Golpe> golpesSalvos = cache.carregarCache("golpes_db.json", new TypeReference<List<Golpe>>() {});
        if (golpesSalvos != null) {
            for (Golpe g : golpesSalvos) {
                bancoDeGolpes.put(g.getNome().toLowerCase(), g);
            }
        }

        List<PokemonApiDto> cacheados = cache.carregarCache("pokemons_db.json", new TypeReference<List<PokemonApiDto>>() {});
        if (cacheados != null && !cacheados.isEmpty()) {
            System.out.println("==== Todos os dados foram carregados na memória com sucesso! ====");
            this.bancoDePokemons = cacheados;
        }
    }

    public List<Pokemon> sortearIniciais(int quantidade) {
        List<Pokemon> starters = new ArrayList<>();
        List<Integer> indicesSorteados = new ArrayList<>();
        while (starters.size() < quantidade) {
            int indexSorteado = (int) (Math.random() * this.bancoDePokemons.size());

            if (!indicesSorteados.contains(indexSorteado)) {
                indicesSorteados.add(indexSorteado);

                PokemonApiDto dto = this.bancoDePokemons.get(indexSorteado);
                Pokemon pokemonInstanciado = PokemonFactory.obterDoDto(dto);

                if (dto.moves != null && !dto.moves.isEmpty()) {
                    String nomeGolpeApi = dto.moves.get(0).move.name.toLowerCase();

                    if (bancoDeGolpes.containsKey(nomeGolpeApi)) {
                        pokemonInstanciado.aprenderGolpe(bancoDeGolpes.get(nomeGolpeApi));
                    } else {
                        pokemonInstanciado.aprenderGolpe(new Golpe(nomeGolpeApi, 40, Tipo.NORMAL));
                    }
                }

                starters.add(pokemonInstanciado);
            }
        }
        return starters;
    }
}
