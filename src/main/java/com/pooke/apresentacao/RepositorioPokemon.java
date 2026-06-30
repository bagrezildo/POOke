package com.pooke.apresentacao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pooke.apresentacao.cache.GerenciadorCacheJson;
import com.pooke.apresentacao.dto.PokemonApiDto;
import com.pooke.dominio.Golpe;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.Tipo;
import com.pooke.util.Printer;
import com.pooke.util.ScriptPopularGolpes;
import com.pooke.util.ScriptPopularPokemons;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório central que atua como Banco de Dados in-memory durante a execução.
 * Carrega e armazena os dados vindos do `GerenciadorCacheJson` populando as coleções locais.
 */
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
            Printer.imprimir("==== Banco de Golpes não encontrado. Iniciando gerador... ====");
            ScriptPopularGolpes.main(new String[]{});
        }

        File arquivoPokemons = new File("data/pokemons_db.json");
        if (!arquivoPokemons.exists()) {
            Printer.imprimir("==== Banco de Pokémons não encontrado. Iniciando gerador... ====");
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
            Printer.imprimir("==== Todos os dados foram carregados ====");
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
                    dto.moves.sort((m1, m2) -> {
                        int l1 = m1.level_learned_at == null ? 999 : m1.level_learned_at;
                        int l2 = m2.level_learned_at == null ? 999 : m2.level_learned_at;
                        return Integer.compare(l1, l2);
                    });

                    int golpesEquipados = 0;
                    for (PokemonApiDto.MoveWrapper wrapper : dto.moves) {
                        if (wrapper.level_learned_at != null) {
                            Golpe golpe = obterGolpeDoBanco(wrapper.move.name.toLowerCase());
                            if (golpesEquipados < 4) {
                                pokemonInstanciado.aprenderGolpe(golpe);
                                golpesEquipados++;
                            } else {
                                pokemonInstanciado.getFilaDeAprendizado().put(wrapper.level_learned_at, golpe);
                            }
                        }
                    }

                    if (golpesEquipados == 0) {
                        String golpeInicial = dto.moves.get(0).move.name.toLowerCase();
                        pokemonInstanciado.aprenderGolpe(obterGolpeDoBanco(golpeInicial));
                    }
                }

                starters.add(pokemonInstanciado);
            }
        }
        return starters;
    }

    private Golpe obterGolpeDoBanco(String nomeGolpeApi) {
        String chaveBusca = nomeGolpeApi.replace("-", " ").toLowerCase();
        if (bancoDeGolpes.containsKey(chaveBusca)) {
            return bancoDeGolpes.get(chaveBusca);
        } else {
            return new Golpe(nomeGolpeApi, 40, Tipo.NORMAL);
        }
    }
}
