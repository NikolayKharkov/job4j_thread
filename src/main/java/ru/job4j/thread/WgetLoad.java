package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class WgetLoad implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public WgetLoad(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            long start = System.currentTimeMillis();
            try {
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    bytesWrited += bytesRead;
                    long deltaTime = System.currentTimeMillis() - start;
                    if (bytesWrited >= speed) {
                        bytesWrited = 0;
                        if (deltaTime < 1000) {
                            Thread.sleep(1000 - deltaTime);
                        }
                    }
                    start = System.currentTimeMillis();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validator(int args) throws InterruptedException {
        if (args != 2) {
            throw new InterruptedException("Incorrect number of arguments!");
        }
    }

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        validator(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = Paths.get(new URI(url).getPath()).getFileName().toString();
        Thread wget = new Thread(new WgetLoad(url, speed, fileName));
        wget.start();
        wget.join();
    }
}