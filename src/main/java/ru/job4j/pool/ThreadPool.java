package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() throws InterruptedException {
        for (int i = 0; i != size; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        run();
    }

    private void run() {
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

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i != 10; i++) {
            threadPool.work(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " Start");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(Thread.currentThread().getName() + " Finish");

                }
            });
        }
        Thread.sleep(1000);
        threadPool.shutdown();
    }
}