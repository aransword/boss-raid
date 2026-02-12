package dev.raid;

public class Hero extends Unit {
    private final MessageQueue messageQueue;

    public Hero(long hp, long stat, MessageQueue messageQueue) {
        super(hp, stat);
        this.messageQueue = messageQueue;
    }

    public synchronized void takeDamage() {
        Message message = messageQueue.dequeue();
        this.hp -= message.getDamage();
    }

    public void heal(long stat) {
        boolean a = false;

        if (this.hp >= 100) {
            return;
        } else if (this.hp >= 95) {
            this.hp = 100;
            a = true;
        } else {
            this.hp += stat;
            a = true;
        }

        if (a) {
            System.out.println("Healer heals " + this.hp);
        }
    }

    @Override
    public void action() {
        while (true) {
            takeDamage();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
