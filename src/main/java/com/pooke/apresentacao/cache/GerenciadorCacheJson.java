package com.pooke.apresentacao.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GerenciadorCacheJson {
    private final ObjectMapper mapper = new ObjectMapper();
    private final String diretorioBase = "data/";

    public GerenciadorCacheJson() {
        File dir = new File(diretorioBase);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public <T> T carregarCache(String nomeArquivo, TypeReference<T> typeReference){
        File arquivo = new File(diretorioBase + nomeArquivo);
        if (arquivo.exists()) {
            try {
                return mapper.readValue(arquivo, typeReference);
            } catch (IOException e) {
                System.err.println("Erro ao ler cache " + nomeArquivo + ": " + e.getMessage());
            }
        }
        return null;
    }
}
