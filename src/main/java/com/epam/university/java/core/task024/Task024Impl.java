package com.epam.university.java.core.task024;


import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Task024Impl implements Task024 {

    @Override
    public Collection<String> getWordsCount(String source) {
        if (source == null) {
            throw new IllegalArgumentException();
        }
        if (source.length() == 0) {
            return Collections.emptyList();
        }
        List<String> result = new LinkedList<>();
        String[] words = source.split("(?=\\p{Lu})");

        for (String word : words) {
            result.add(word.toLowerCase());
        }
        return result;
    }
}
