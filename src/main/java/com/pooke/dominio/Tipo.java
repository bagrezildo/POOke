package com.pooke.dominio;

import java.util.Collections;
import java.util.List;

public enum Tipo {
    NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG, GHOST, STEEL, FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON, DARK, FAIRY, STELLAR, UNKNOWN;

    private List<Tipo> doubleDamageTo = Collections.emptyList();
    private List<Tipo> halfDamageTo = Collections.emptyList();
    private List<Tipo> noDamageTo = Collections.emptyList();

    static {
        NORMAL.halfDamageTo = List.of(ROCK, STEEL);
        NORMAL.noDamageTo = List.of(GHOST);

        FIRE.doubleDamageTo = List.of(GRASS, ICE, BUG, STEEL);
        FIRE.halfDamageTo = List.of(FIRE, WATER, ROCK, DRAGON);

        WATER.doubleDamageTo = List.of(FIRE, GROUND, ROCK);
        WATER.halfDamageTo = List.of(WATER, GRASS, DRAGON);

        GRASS.doubleDamageTo = List.of(WATER, GROUND, ROCK);
        GRASS.halfDamageTo = List.of(FIRE, GRASS, POISON, FLYING, BUG, DRAGON, STEEL);

        ELECTRIC.doubleDamageTo = List.of(WATER, FLYING);
        ELECTRIC.halfDamageTo = List.of(ELECTRIC, GRASS, DRAGON);
        ELECTRIC.noDamageTo = List.of(GROUND);

        ICE.doubleDamageTo = List.of(GRASS, GROUND, FLYING, DRAGON);
        ICE.halfDamageTo = List.of(FIRE, WATER, ICE, STEEL);

        FIGHTING.doubleDamageTo = List.of(NORMAL, ICE, ROCK, DARK, STEEL);
        FIGHTING.halfDamageTo = List.of(POISON, FLYING, PSYCHIC, BUG, FAIRY);
        FIGHTING.noDamageTo = List.of(GHOST);

        POISON.doubleDamageTo = List.of(GRASS, FAIRY);
        POISON.halfDamageTo = List.of(POISON, GROUND, ROCK, GHOST);
        POISON.noDamageTo = List.of(STEEL);

        GROUND.doubleDamageTo = List.of(FIRE, ELECTRIC, POISON, ROCK, STEEL);
        GROUND.halfDamageTo = List.of(GRASS, BUG);
        GROUND.noDamageTo = List.of(FLYING);

        FLYING.doubleDamageTo = List.of(GRASS, FIGHTING, BUG);
        FLYING.halfDamageTo = List.of(ELECTRIC, ROCK, STEEL);

        PSYCHIC.doubleDamageTo = List.of(FIGHTING, POISON);
        PSYCHIC.halfDamageTo = List.of(PSYCHIC, STEEL);
        PSYCHIC.noDamageTo = List.of(DARK);

        BUG.doubleDamageTo = List.of(GRASS, PSYCHIC, DARK);
        BUG.halfDamageTo = List.of(FIRE, FIGHTING, POISON, FLYING, GHOST, STEEL, FAIRY);

        ROCK.doubleDamageTo = List.of(FIRE, ICE, FLYING, BUG);
        ROCK.halfDamageTo = List.of(FIGHTING, GROUND, STEEL);

        GHOST.doubleDamageTo = List.of(PSYCHIC, GHOST);
        GHOST.halfDamageTo = List.of(DARK);
        GHOST.noDamageTo = List.of(NORMAL);

        DRAGON.doubleDamageTo = List.of(DRAGON);
        DRAGON.halfDamageTo = List.of(STEEL);
        DRAGON.noDamageTo = List.of(FAIRY);

        DARK.doubleDamageTo = List.of(PSYCHIC, GHOST);
        DARK.halfDamageTo = List.of(FIGHTING, DARK, FAIRY);

        STEEL.doubleDamageTo = List.of(ICE, ROCK, FAIRY);
        STEEL.halfDamageTo = List.of(FIRE, WATER, ELECTRIC, STEEL);

        FAIRY.doubleDamageTo = List.of(FIGHTING, DRAGON, DARK);
        FAIRY.halfDamageTo = List.of(FIRE, POISON, STEEL);
    }

    public double calcularMultiplicador(Tipo tipoDefensor){
        if (this.doubleDamageTo.contains(tipoDefensor)) return 2.0;
        if (this.halfDamageTo.contains(tipoDefensor)) return 0.5;
        if (this.noDamageTo.contains(tipoDefensor)) return 0;
        return 1.0;
    }

    public double calcularMultiplicador(Tipo tipoDefensor1, Tipo tipoDefensor2){
        if (tipoDefensor2 == null) {
            return calcularMultiplicador(tipoDefensor1);
        }

        return calcularMultiplicador(tipoDefensor1) * calcularMultiplicador(tipoDefensor2);
    }
}
