package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PararrelSearch<T> extends RecursiveTask<Integer> {

    private final T[] objects;
    private final T targetObject;
    private static final int OPTIONALLENGTH = 10;

    public PararrelSearch(T[] objects, T targetObject) {
        this.objects = objects;
        this.targetObject = targetObject;
    }

    @Override
    protected Integer compute() {
        Integer result = -1;
        int length = objects.length;
        if (length <= OPTIONALLENGTH) {
            for (int i = 0; i != length; i++) {
                if (objects[i].equals(targetObject)) {
                    result = i;
                    break;
                }
            }
        } else {
            int mid = length / 2;
            PararrelSearch<T> firstHalf =
                    new PararrelSearch(Arrays.copyOfRange(objects, 0, mid), targetObject);
            PararrelSearch<T> secondHalf =
                    new PararrelSearch(Arrays.copyOfRange(objects, mid, length), targetObject);
            firstHalf.fork();
            secondHalf.fork();
            int first = firstHalf.join();
            int second = secondHalf.join();
            if (first == -1 && second == -1) {
                result = -1;
            } else {
                result = first == -1 ? second + mid : first;
            }
        }
        return result;
    }

    public static <V> int findTarget(V[] objects, V targetObject) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new PararrelSearch<V>(objects, targetObject));
    }
}
