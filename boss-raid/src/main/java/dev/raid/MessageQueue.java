package dev.raid;

import java.util.*;

public class MessageQueue {
    private final Queue<Message> messages = new LinkedList<>();

    public synchronized void enqueue(Message message) {
        messages.offer(message);
        System.out.println("[enqueue] " + Thread.currentThread().getName() + " | damage: " + message.getDamage()
                + " | 큐 사이즈: " + messages.size());
        notifyAll();
    }

    public synchronized Message dequeue() {
        while (messages.isEmpty()) {
            try {
                wait(); // synchronized 블록 안이므로 정상 동작
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message message = messages.poll();
        System.out.println("[dequeue] " + Thread.currentThread().getName() + " | damage: " + message.getDamage()
                + " | 큐 사이즈: " + messages.size());
        return message;
    }

}
