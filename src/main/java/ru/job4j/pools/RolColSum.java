package ru.job4j.pools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i != matrix.length; i++) {
            int[] row = matrix[i];
            int[] column = getColumn(matrix, i);
            result[i] = new Sums(Arrays.stream(row).sum(), Arrays.stream(column).sum());
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = futures.get(i).get();
        }
        return result;
    }


    private static int[] getColumn(int[][] matrix, int index) {
        return Arrays.stream(matrix)
                .mapToInt(num -> num[index])
                .toArray();
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int[] row = matrix[index];
            int[] column = getColumn(matrix, index);
            return new Sums(Arrays.stream(row).sum(), Arrays.stream(column).sum());
        });
    }
}
