package dev.raid;

public class Hero extends Unit {
    private final MessageQueue messageQueue;

    public Hero(long hp, long stat, MessageQueue messageQueue) {
        super(hp, stat);
        this.messageQueue = messageQueue;
    }
    
    public synchronized void takeDamage(long damage) {
    	this.hp -= damage;
    	System.out.println(" 피격! (데미지: " + damage + ", 남은 HP: " + hp + ")");
    }
    
    public synchronized void takeHeal(long heal) {
    	this.hp += heal;
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
