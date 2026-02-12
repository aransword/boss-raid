package dev.raid;

public abstract class Unit {
    protected long hp;
    protected long stat;
    protected boolean alive = true;

    public Unit(long hp, long stat) {
        this.hp = hp;
        this.stat = stat;
    }

    public long getHp() {
        return hp;
    }

    public long getStat() {
        return stat;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public abstract void action();
}
