package dev.raid;

import java.util.List;
import java.util.Random;

public class Healer extends Unit {
    private final List<Hero> heroes;
    private final Random random = new Random();

    public Healer(long hp, long stat, List<Hero> heroes) {
        super(hp, stat);
        this.heroes = heroes;
    }

    public void checkAliveHeroes() {
        heroes.removeIf(hero -> !hero.getAlive());
    }

    public void takeHeal() {
        checkAliveHeroes();
        int idx = random.nextInt(heroes.size());
        heroes.get(idx).heal(stat);
    }

    @Override
    public void action() {
        while (true) {
            takeHeal();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}