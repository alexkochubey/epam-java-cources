package com.epam.university.java.core.task048;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Task048Impl implements Task048 {
    @Override
    public Collection<Integer> getArmstrongNumbers(Integer from, Integer to) {
        if (from == null || to == null || from < 0 || to < 0) {
            throw new IllegalArgumentException();
        }
        List<Integer> result = new LinkedList<>();
        for (int i = from; i < to; i++) {
            int count = getNumOfDigits(i);
            List<Integer> arr = splitDigit(i);
            int sum = 0;
            for (Integer integer : arr) {
                sum += Math.pow(integer, count);
            }
            if (sum == i) {
                result.add(i);
            }
        }
        return result;
    }

    private List<Integer> splitDigit(int i) {
        List<Integer> arr = new LinkedList<>();
        while (i != 0) {
            arr.add(i % 10);
            i = i / 10;
        }
        return arr;
    }

    private int getNumOfDigits(int i) {
        int count = 0;
        while (i != 0) {
            i = i / 10;
            count++;
        }
        return count;
    }
}