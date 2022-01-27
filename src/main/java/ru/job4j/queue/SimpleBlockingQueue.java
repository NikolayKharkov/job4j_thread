package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        queue.offer(value);
        notify();
    }

    public synchronized T poll() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread maker = new Thread(() -> {
            for (int i = 0; i != 3; i++) {
                queue.offer(i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i != 2; i++) {
                queue.poll();
            }
        });
        maker.start();
        consumer.start();
        maker.join();
        consumer.join();
        System.out.println(queue.poll());
    }
}