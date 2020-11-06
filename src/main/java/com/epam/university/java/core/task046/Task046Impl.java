package com.epam.university.java.core.task046;

import java.util.ArrayList;
import java.util.List;

public class Task046Impl implements Task046 {
    @Override
    public List<String> assembleMatryoshka(Integer k, Integer n) {
        if (k == null || n == null) {
            throw new IllegalArgumentException();
        }
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[k], 0, n - 1, 0);
        List<String> resultList = new ArrayList<>();
        StringBuilder sb;
        for (int[] buf : combinations) {
            sb = new StringBuilder();
            for (int j : buf) {
                sb.append(j).append(" ");
            }
            resultList.add(sb.toString().trim());
        }
        return resultList;
    }

    private void helper(List<int[]> combinations, int[] data, int start,
                        int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }
}