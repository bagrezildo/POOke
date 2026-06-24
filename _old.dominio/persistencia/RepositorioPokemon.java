package com.pooke._old.dominio.persistencia;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.pooke._old.dominio.EspeciePokemon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPokemon {
    private final String CAMINHO_ARQUIVO = "database_pokemons.json";
    private final ObjectMapper objectMapper;

    public RepositorioPokemon() {
        // A instância é criada AQUI DENTRO
        this.objectMapper = new ObjectMapper();

        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        this.objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        this.objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        this.objectMapper.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
    }

    public void salvar(List<EspeciePokemon> pokemons){
        try {
            objectMapper.writeValue(new File(CAMINHO_ARQUIVO), pokemons);
            System.out.println("Banco de dados `"+ CAMINHO_ARQUIVO +"' atualizado com sucesso!");
        } catch (IOException e) {
            throw new PersistenciaException("Não foi possível persistir a lista de Pokémon no disco.", e);
        }
    }

    public List<EspeciePokemon> carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) {
           System.out.println("Base de dados local não encontrada. Inicializando lista vazia.");
           return new ArrayList<>();
        }

        try {
            // Usamos o TypeReference para o Jackson reconstruir a lista tipada corretamente
            return objectMapper.readValue(arquivo, new TypeReference<List<EspeciePokemon>>() {});
        } catch (IOException e) {
            throw new PersistenciaException("Falha ao ler ou interpretar o ficheiro JSON local.", e);
        }
    }
}
