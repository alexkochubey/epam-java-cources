package com.epam.university.java.core.task047;

public class Task047Impl implements Task047 {
    /**
     * Calculate number of inversions.
     *
     * @param n number of elements in an array.
     * @param a - array of elements.
     * @return number of inversions.
     */
    @Override
    public long calculateInversions(int n, int[] a) {
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    counter++;
                }
            }
        }
        return counter;
    }
}