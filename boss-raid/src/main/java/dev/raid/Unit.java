package dev.raid;

public abstract class Unit {
    private long hp;
    private long state;
    private boolean alive = true;

    public Unit(long hp, long state) {
        this.hp = hp;
        this.state = state;
    }

    public long getHp() {
        return hp;
    }

    public long getState() {
        return state;
    }

    public boolean getAlive() {
        return alive;
    }

    public boolean setAlive(boolean alive) {
        this.alive = alive;
        return alive;
    }

    public abstract long action();
}
