package ru.job4j.io;

import java.io.*;

final public class WriterFile {
    private final File file;

    public WriterFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
