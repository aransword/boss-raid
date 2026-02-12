package dev.raid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Unit {
    private MessageQueue messageQueue;
    private List<Hero> insects;
    private Random random = new Random();

    public Enemy(long hp, long stat, List<Hero> heroes, MessageQueue messageQueue) {
        super(hp, stat);
        this.insects = heroes;
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

            List<Hero> liveInsects = new ArrayList<>();

            for (Hero h : insects) {
                if (h.getAlive())
                    liveInsects.add(h);
            }

            if (liveInsects.isEmpty()) {
                break;
            }

            int targetIndex = random.nextInt(liveInsects.size());
            Hero targetInsect = liveInsects.get(targetIndex);

            if (targetInsect.getAlive()) {
                targetInsect.takeDamage(stat);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
