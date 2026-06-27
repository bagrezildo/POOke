package com.pooke.apresentacao;

import com.pooke.apresentacao.dto.PokemonApiDto;
import com.pooke.dominio.Pokemon;
import com.pooke.dominio.PokemonFisico;
import com.pooke.dominio.PokemonSpecial;
import com.pooke.dominio.Tipo;

public class PokemonFactory {

    public static Pokemon obterDoDto(PokemonApiDto dto){
        Tipo tipoPrimario = Tipo.valueOf((dto.types.get(0).type.name.toUpperCase()));
        Tipo tipoSecundario = dto.types.size() > 1 ? Tipo.valueOf((dto.types.get(1).type.name.toUpperCase())) : null;

        int hp = extrairStat(dto, "hp");
        int attack = extrairStat(dto, "attack");
        int defense = extrairStat(dto, "defense");
        int spAttack = extrairStat(dto, "special-attack");
        int spDefense = extrairStat(dto, "special-defense");
        int speed = extrairStat(dto, "speed");

        if (attack >= spAttack) {
            return new PokemonFisico(dto.name, tipoPrimario, tipoSecundario, hp, attack, defense, speed);
        } else {
            return new PokemonSpecial(dto.name, tipoPrimario, tipoSecundario, hp, spAttack, spDefense, speed);
        }

    }

    private static int extrairStat(PokemonApiDto dto, String nomeStat) {
        for (PokemonApiDto.StatWrapper wrapper : dto.stats) {
            if (wrapper.stat.name.equals(nomeStat)) {
                return wrapper.base_stat;
            }
        }
        return 10;
    }
}
