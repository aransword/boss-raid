package dev.raid;

import java.util.*;

public class MessageQueue {
    private final Queue<Message> messages = new LinkedList<>();

    public synchronized void enqueue(Message message) {
        messages.offer(message);
        notifyAll();
    }

    public synchronized Message dequeue() {
        while (messages.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message message = messages.poll();
        return message;
    }
}
