package dev.raid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Unit {
    private final String name;
    private final long maxHp;
    private MessageQueue messageQueue;
    private List<Hero> insects;
    private Random random = new Random();
    private GameRenderer renderer;

    public Enemy(String name, long hp, long stat, List<Hero> heroes, MessageQueue messageQueue, GameRenderer renderer) {
        super(hp, stat);
        this.name = name;
        this.maxHp = hp;
        this.insects = heroes;
        this.messageQueue = messageQueue;
        this.renderer = renderer;
    }

    public long getMaxHp() {
        return maxHp;
    }

    public String getName() {
        return name;
    }

    @Override
    public void action() {
        while (alive) {
            Message message = messageQueue.dequeue();
            this.hp -= message.getDamage();

            renderer.addLog(name + "에게 " + message.getDamage() + " 데미지! (HP: " + Math.max(0, this.hp) + ")");

            if (this.hp <= 0) {
                renderer.addLog("💥 " + name + " 처치!");
                setAlive(false);
            }

            List<Hero> liveInsects = new ArrayList<>();

            for (Hero h : insects) {
                if (h != null && h.getAlive())
                    liveInsects.add(h);
            }

            if (liveInsects.isEmpty()) {
                return;
            }

            int targetIndex = random.nextInt(liveInsects.size());
            Hero targetInsect = liveInsects.get(targetIndex);

            if (targetInsect.getAlive()) {
                targetInsect.takeDamage(stat, name);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
