package ru.job4j.io;

import java.io.*;
import java.io.BufferedReader;
import java.util.function.Predicate;

final public class ReaderFile {
    private final File file;

    public ReaderFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                line.chars().mapToObj(cr -> Character.valueOf((char) cr)).filter(filter).forEach(result::append);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
