package com.epam.university.java.core.task049;

import java.util.LinkedList;
import java.util.List;

public class Task049Impl implements Task049 {
    @Override
    public String getResultList(String first, String second) {
        if (first == null || second == null
                || first.trim().length() == 0
                || second.trim().length() == 0) {
            throw new IllegalArgumentException();
        }

        List<String> variants = new LinkedList<>();
        String test = "";
        String result;
        for (Character character : first.toCharArray()) {
            test += character;
            if (second.contains(test)) {
                result = test;
                variants.add(result);
            } else {
                test = "";
            }
        }
        int max = 0;
        String maxString = "";
        for (String string : variants) {
            if (string.length() > max) {
                maxString = string;
                max = string.length();
            }
        }

        return maxString;
    }
}