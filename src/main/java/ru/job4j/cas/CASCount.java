package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int current;
        int increment;
        do {
            current = count.get();
            increment = current + 1;
        } while (!count.compareAndSet(current, increment));
    }

    public int get() {
        return count.get();
    }
}