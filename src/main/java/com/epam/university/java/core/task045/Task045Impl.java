package com.epam.university.java.core.task045;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Task045Impl implements Task045 {
    @Override
    public String doAnagram(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        if (input.matches("\\s+")) {
            return input;
        }
        String[] line = input.split(" ");
        String result = "";
        for (String word : line) {
            List<String> letters = new ArrayList<>(Arrays
                    .asList(word.split("")));
            List<String> characters = new ArrayList<>(Arrays
                    .asList(word.split(""))).stream()
                    .filter(s -> s.matches("[a-zA-Z]"))
                    .collect(Collectors.toList());
            Collections.reverse(characters);
            Iterator<String> iterator = characters.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                if (letters.get(i++).matches("[a-zA-Z]")) {
                    letters.set(i - 1, iterator.next());
                }
            }
            result += String.join("", letters) + " ";
        }
        return result.trim();
    }
}