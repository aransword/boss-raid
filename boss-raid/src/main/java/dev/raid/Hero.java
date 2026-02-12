package dev.raid;

public class Hero extends Unit {
    private final MessageQueue messageQueue;
    private final int index;
    private final GameRenderer renderer;

    public Hero(int index, long hp, long stat, MessageQueue messageQueue, GameRenderer renderer) {
        super(hp, stat);
        this.index = index;
        this.messageQueue = messageQueue;
        this.renderer = renderer;
    }

    public int getIndex() {
        return index;
    }

    public synchronized void takeDamage(long damage, String attackerName) {
        this.hp -= damage;
        renderer.addLog(attackerName + " → Hero " + String.format("%02d", index) + " 피격! (데미지: " + damage + ", HP: "
                + Math.max(0, hp) + ")");
        if (this.hp <= 0) {
            setAlive(false);
            renderer.addLog("💀 Hero " + String.format("%02d", index) + " 사망!");
        }
    }

    public synchronized void takeHeal(long heal) {
        this.hp += heal;
    }

    public void heal(long stat) {
        if (this.hp >= 100) {
            return;
        } else if (this.hp >= 95) {
            this.hp = 100;
        } else {
            this.hp += stat;
        }
        renderer.addLog("💊 Healer → Hero " + String.format("%02d", index) + " 힐! (HP: " + this.hp + ")");
    }

    @Override
    public void action() {
        while (alive) {
            Message message = new Message(getStat());
            messageQueue.enqueue(message);

            if (this.hp <= 0) {
                setAlive(false);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
