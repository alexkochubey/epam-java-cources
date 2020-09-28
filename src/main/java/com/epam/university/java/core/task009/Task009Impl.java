package com.epam.university.java.core.task009;

import java.io.BufferedReader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Task009Impl implements Task009 {
    @Override
    public Collection<String> countWords(File sourceFile) {
        if (sourceFile == null) {
            throw new IllegalArgumentException();
        }
        String line = "";
        String[] lines;
        try (BufferedReader bufRdr  = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(sourceFile), StandardCharsets.UTF_8))) {
            line = bufRdr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines = line.trim().toLowerCase().split("\\s*[.]\\s+|\\s*[,]\\s+|\\s+");

        HashSet<String> words = new HashSet<String>(Arrays.asList(lines));

        return words;
    }
}
