package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size;
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(SimpleBlockingQueue<Runnable> tasks) throws InterruptedException {
        this.size = Runtime.getRuntime().availableProcessors();
        this.tasks = tasks;
        for (int i = 0; i != size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }));
        }
    }

    public void run() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}