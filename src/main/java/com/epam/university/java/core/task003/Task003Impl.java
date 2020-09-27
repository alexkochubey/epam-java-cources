package com.epam.university.java.core.task003;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Task 003
 *
 * Alexey Kochubey
 */

public class Task003Impl implements Task003 {
    @Override
    public String[] invert(String[] source) {
        if (source == null){
            throw new IllegalArgumentException("Argument is not provided");
        } String[] result = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            result[source.length - i - 1] = source[i];
        }
        return result;
    }

    @Override
    public String[] join(String[] first, String[] second) {
        if (first == null
                || second == null){
            throw new IllegalArgumentException("Arguments are not provided");
        } String[] result = Stream.of(first, second)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        return result;
    }

    @Override
    public int findMax(int[] source) {
        if (source == null || source.length == 0) {
            throw new IllegalArgumentException();
        }
        Arrays.sort(source);
        return source[source.length - 1];
    }

    @Override
    public String[] filter(String[] source, FilteringCondition condition) {
        if(source == null
                || condition == null){
            throw new IllegalArgumentException("Wrong params");
        } String[] result = Arrays.stream(source).filter(condition::isValid).toArray(String[]::new);
        return result;
    }

    @Override
    public String[] removeElements(String[] source, String[] toRemove) {
        if (source == null
                || toRemove == null){
            throw new IllegalArgumentException("Wrong Arguments");
        } List<String> remove = Arrays.asList(toRemove);
        String[] result = Arrays.stream(source)
                .filter(x->!remove.contains(x))
                .toArray(String[]::new);
        return result;
    }

    @Override
    public String[] map(String[] source, MappingOperation operation) {
        if (source == null || operation == null){
            throw new IllegalArgumentException("Arguments are not provided");
        } for (int i = 0; i < source.length; i++){
            source[i] = operation.map(source[i]);
        }return source;
    }

    @Override
    public String[] flatMap(String[] source, FlatMappingOperation operation) {
        if (source == null || operation == null){
            throw new IllegalArgumentException("Arguments are not provided");
        }
        Comparator<String> comparator = new Comparator<>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o2) - Integer.parseInt(o1);
            }
        };

        Set<String> result = new TreeSet<>(comparator);
        for (String value : source) {
            Collections.addAll(result, operation.flatMap(value));
        }

        return result.toArray(new String[0]);
    }
}
