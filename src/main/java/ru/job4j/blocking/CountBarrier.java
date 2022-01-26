package ru.job4j.blocking;

public class CountBarrier {

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}