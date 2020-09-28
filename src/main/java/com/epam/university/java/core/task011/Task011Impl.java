package com.epam.university.java.core.task011;

import java.util.*;

public class Task011Impl implements Task011 {
    @Override
    public String getLastName(String[] collection) {
        if (collection == null
                || collection.length < 0
                || Arrays.asList(collection).contains(null)) {
            throw new IllegalArgumentException("Array is not provided");
        }
        if (collection.length == 1) {
            return collection[0];
        }
        int indx = 0;
        while (collection.length != 1) {
            for (int i = 0; i < collection.length; i++) {
                if (indx % 2 == 0) {
                    collection[i] = null;
                }
                indx++;
                Object[] original;
            }
            collection = Arrays.stream(collection).filter(Objects::nonNull).toArray(String[]::new);
        }
        return collection[0];
    }

    @Override
    public String getLastName(ArrayList<String> collection) {
        if (collection == null || collection.size() < 0) {
            throw new IllegalArgumentException();
        }
        if (collection.size() == 1) {
            return collection.get(0);
        }
        int indx = 0;
        while (collection.size() != 1) {
            for (int i = 0; i < collection.size(); i++) {
                if (indx % 2 == 0) {
                    collection.set(i, null);
                }
                indx++;
            }
            collection.removeAll(Collections.singleton(null));
        }
        return collection.get(0);
    }

    @Override
    public String getLastName(LinkedList<String> collection) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException();
        }
        if (collection.size() == 1) {
            return collection.get(0);
        }
        int indx = 0;
        while (collection.size() != 1) {
            for (int i = 0; i < collection.size(); i++) {
                if (indx % 2 == 0) {
                    collection.set(i, null);
                }
                indx++;
            }
            collection.removeAll(Collections.singleton(null));
        }
        return collection.get(0);
    }
}
