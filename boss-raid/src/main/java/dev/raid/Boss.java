package dev.raid;

public class Boss extends Unit {
    MessageQueue messageQueue;

    public Boss(long hp, long stat, MessageQueue messageQueue) {
        super(hp, stat);
        this.messageQueue = messageQueue;
    }

    @Override
    public void action() {
        while (alive) {
            Message message = messageQueue.dequeue();
            this.hp -= message.getDamage();
            System.out.println("Boss hp: " + this.hp);
            if (this.hp <= 0) {
                setAlive(false);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
