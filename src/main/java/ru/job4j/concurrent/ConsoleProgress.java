package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        String[] partsStar = {"\\", "|", "/"};
        int count = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (count == partsStar.length) {
                    count = 0;
                }
                Thread.sleep(500);
                System.out.print("\r load: " + partsStar[count++]);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
        progress.join();
    }
}

