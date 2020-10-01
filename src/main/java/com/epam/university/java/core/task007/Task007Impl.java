package com.epam.university.java.core.task007;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Task007Impl implements Task007 {
    @Override
    public Collection<Integer> multiplyPolynomial(Collection<Integer> first,
                                                  Collection<Integer> second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException();
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < first.size() + second.size() - 1; i++) {
            result.add(i, 0);
        }

        for (int i = 0; i < first.size(); i++) {
            for (int j = 0; j < second.size(); j++) {
                int product = result.get(i + j)
                        + ((List<Integer>) first).get(i) * ((List<Integer>) second).get(j);
                result.remove(i + j);
                result.add(i + j, product);
            }

        }
        return result;
    }
}
