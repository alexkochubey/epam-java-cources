package com.epam.university.java.core.task051;

import java.util.Arrays;

public class Task051Impl implements Task051 {

    @Override
    public String replace(String source) {
        if (source == null || source.trim().equals("")
                || source.equals("the") || source.contains("THE")) {
            throw new IllegalArgumentException();
        }

        String[] sourceArray = source.split(" ");
        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i].equals("the")) {
                sourceArray [i] = String.valueOf(sourceArray[i + 1].charAt(0))
                        .matches("[aeyoui]") ? "an" : "a";
            }
        }

        return String.join(" ", sourceArray);
    }
}