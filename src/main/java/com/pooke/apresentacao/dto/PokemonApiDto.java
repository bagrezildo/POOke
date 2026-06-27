package com.pooke.apresentacao.dto;

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
        public int base_stat;
        public NamedResourceDto stat;
    }

    public static class MoveWrapper {
        public NamedResourceDto move;

        public Integer level_learned_at;

        @JsonProperty("version_group_details")
        public void unpackVersionGroupDetails(List<VersionGroupDetail> details) {
            if (details == null) return;

            for (VersionGroupDetail detalhe : details) {
                if (detalhe.moveLearnMethod != null && "level-up".equals(detalhe.moveLearnMethod.name)) {
                    if (detalhe.level_learned_at != null && detalhe.level_learned_at > 0) {
                        this.level_learned_at = detalhe.level_learned_at;
                        break;
                    }
                }
            }
        }
    }

    public static class VersionGroupDetail {
        @JsonProperty("level_learned_at")
        public Integer level_learned_at;

        @JsonProperty("move_learn_method")
        public NamedResourceDto moveLearnMethod;
    }
}
