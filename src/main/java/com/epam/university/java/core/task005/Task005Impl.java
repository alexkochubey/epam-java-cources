package com.epam.university.java.core.task005;

public class Task005Impl implements Task005 {
    @Override
    public PiHolder findPi(int digits) {
        if (digits <= 0 || digits > 10) {
            throw new IllegalArgumentException("You number should be in range of 10");
        }
        int numerator = 0;
        int denominator = 0;

        int firstNumber = (int) (Math.pow(10, digits - 1));
        int lastNumber = (int) Math.pow(10, digits) - 1;

        double result = 0;
        double min = 1;

        for (int i = (int) (firstNumber * Math.PI); i <= lastNumber; i++) {
            for (int j = i / 4; j <= i / 3; j++) {
                result = Math.abs(((double) i / (double) j) - Math.PI);
                if (result < min) {
                    min = result;
                    numerator = i;
                    denominator = j;
                }
            }
        }

        return new PiHolderImpl(numerator, denominator);
    }
}

