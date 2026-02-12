package dev.raid;

public class Hero extends Unit {
    private final MessageQueue messageQueue;

    public Hero(long hp, long stat, MessageQueue messageQueue) {
        super(hp, stat);
        this.messageQueue = messageQueue;
    }

    @Override
    public void action() {
        while (true) {
            Message message = new Message(getStat());
            messageQueue.enqueue(message);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
