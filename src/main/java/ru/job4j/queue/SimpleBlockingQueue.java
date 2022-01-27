package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int size;

    public SimpleBlockingQueue(int maxSize) {
        this.size = maxSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        if (queue.size() == size) {
            wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }
}