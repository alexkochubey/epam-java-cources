package com.epam.university.java.core.task020;

import java.util.Collection;
import java.util.HashSet;

public class Task020Impl implements Task020 {
    @Override
    public int calculate(Collection<String> stones) {
        if (stones == null || stones.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return stones.stream()
                .map(stone -> stone.chars()
                        .collect(HashSet<Integer>::new, HashSet::add, HashSet::addAll))
                .reduce((first, second) -> {
                    first.retainAll(second);
                    return first;
                })
                .get()
                .size();
    }
}
