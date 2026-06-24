package com.pooke;

import com.pooke.dominio.EspeciePokemon;
import com.pooke.persistencia.PokeApiClient;
import com.pooke.persistencia.RepositorioPokemon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class POOkeApplication {

	public static void main(String[] args) {
		SpringApplication.run(POOkeApplication.class, args);

		System.out.println("\n=== INICIANDO CARGA DE TESTE ===");

		PokeApiClient client = new PokeApiClient();
		RepositorioPokemon repositorio = new RepositorioPokemon();

		try {
			// 1. Tenta carregar dados existentes do disco
			List<EspeciePokemon> pokedexLocal = repositorio.carregar();
			System.out.println("Quantidade de Pokémon no disco: " + pokedexLocal.size());

			// 2. Se estiver vazio (primeira execução), busca na API e salva
			if (pokedexLocal.isEmpty()) {
				System.out.println("Populando base inicial de testes via PokeAPI...");

				pokedexLocal.add(client.buscarPokemonPorId(1));  // Bulbasaur
				pokedexLocal.add(client.buscarPokemonPorId(4));  // Charmander
				pokedexLocal.add(client.buscarPokemonPorId(7));  // Squirtle

				repositorio.salvar(pokedexLocal);
				System.out.println("Carga inicial salva com sucesso!");
			}

			// 3. Exibe o resultado para validar o mapeamento completo dos golpes e stats
			System.out.println("\n=== DADOS RECONSTRUÍDOS DO JSON ===");
			for (EspeciePokemon p : pokedexLocal) {
				System.out.println("[" + p.getId() + "] " + p.getNome() + " - Tipo: " + p.getTipoPrimario());
				System.out.println("   Stats -> HP: " + p.getStatsBase().hp() + " | ATK: " + p.getStatsBase().ataque());
				System.out.println("   Golpes Aprendidos:");
				p.getGolpesPossiveis().forEach(g ->
						System.out.println("     - " + g.golpe().getNome() + " [Nível: " + g.nivelMinimo() + "] (Tipo: " + g.golpe().getTipo() + ")")
				);
			}

		} catch (Exception e) {
			System.err.println("\n❌ Erro controlado capturado no fluxo principal:");
			System.err.println("Mensagem: " + e.getMessage());
			if (e.getCause() != null) {
				System.err.println("Causa Raiz: " + e.getCause().getClass().getName());
			}
		}
	}

}
