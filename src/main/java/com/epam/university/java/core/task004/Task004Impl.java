package com.epam.university.java.core.task004;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Task004Impl implements Task004 {
    @Override
    public String[] intersection(String[] first, String[] second) {
        if (first == null
                || second == null
                || first.length < 0
                || second.length < 0) {
            throw new IllegalArgumentException("arguments are not provided");
        }
        HashSet<String> set = new HashSet<>();
        set.addAll(Arrays.asList(first));
        set.retainAll(Arrays.asList(second));
        String[] intersection = {};
        return set.toArray(intersection);
    }

    @Override
    public String[] union(String[] first, String[] second) {
        if (first == null
                || second == null
                || first.length < 0
                || second.length < 0) {
            throw new IllegalArgumentException("arguments are not provided");
        }
        Set<String> result = new LinkedHashSet<>(Arrays.asList(first));
        result.addAll(Arrays.asList(second));
        return result.toArray(new String[0]);
    }
}
