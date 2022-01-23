package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading: ");
                        for (int i = 0; i != 101; i++) {
                            Thread.sleep(50);
                            System.out.print("\rLoading : " + i + "%");
                        }
                        System.out.println("\nLoaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        loading.start();
    }
}
