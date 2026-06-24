package com.pooke._old.dominio.persistencia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonApiDto {
    public int id;
    public String name;
    public List<TypeWrapper> types;
    public List<StatWrapper> stats;
    public List<MoveWrapper> moves;

    public static class TypeWrapper {
        public NamedResourceDto type;
    }

    public static class StatWrapper {
        @JsonProperty("base_stat")
        public int baseStat;
        public NamedResourceDto stat;
    }

    public static class MoveWrapper {
        public NamedResourceDto move;

        // Campo simplificado que vamos usar no serviço!
        public Integer levelLearnedAt;

        // O Jackson vai chamar esse método automaticamente ao ver "version_group_details" no JSON
        @JsonProperty("version_group_details")
        public void unpackVersionGroupDetails(List<VersionGroupDetail> details) {
            if (details == null) return;

            for (VersionGroupDetail detalhe : details) {
                if (detalhe.moveLearnMethod != null && "level-up".equals(detalhe.moveLearnMethod.name)) {
                    if (detalhe.levelLearnedAt != null && detalhe.levelLearnedAt > 0) {
                        this.levelLearnedAt = detalhe.levelLearnedAt;
                        break; // Achou o primeiro level-up válido? Para de procurar e descarta o resto.
                    }
                }
            }
        }
    }

    public static class VersionGroupDetail {
        @JsonProperty("level_learned_at")
        public Integer levelLearnedAt;

        @JsonProperty("move_learn_method")
        public NamedResourceDto moveLearnMethod;
    }
}
