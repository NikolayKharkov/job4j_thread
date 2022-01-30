package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PararrelSearch<T> extends RecursiveTask<Integer> {

    private final T[] objects;
    private final T targetObject;
    private static final int OPTIONALLENGTH = 10;
    private final int from;
    private final int to;

    public PararrelSearch(T[] objects, T targetObject, int from, int to) {
        this.objects = objects;
        this.targetObject = targetObject;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int length = objects.length;
        if (to - from <= OPTIONALLENGTH) {
            for (int i = from; i != to; i++) {
                if (objects[i].equals(targetObject)) {
                    return i;
                }
            }
            return -1;
        }
        int mid = length / 2;
        PararrelSearch<T> firstHalf =
                new PararrelSearch(objects, targetObject, from, mid);
        PararrelSearch<T> secondHalf =
                new PararrelSearch(objects, targetObject, mid + 1, to);
        firstHalf.fork();
        secondHalf.fork();
        int first = firstHalf.join();
        int second = secondHalf.join();
        return first == -1 ? second : first;
    }

    public static <V> int findTargetIndex(V[] objects, V targetObject) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new PararrelSearch<>(objects, targetObject, 0, objects.length - 1));
    }
}
