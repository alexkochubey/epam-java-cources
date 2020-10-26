package com.epam.university.java.core.task052;

public class Task052Impl implements Task052 {

    @Override
    public boolean validateCard(long number) {
        if (number <= 0) {
            throw new IllegalArgumentException();
        }

        String numberString = String.valueOf(number);
        String checkDigit = String.valueOf(numberString.charAt(numberString.length() - 1));

        return luhnTest(numberString.substring(0, numberString.length() - 1)).equals(checkDigit);
    }

    private String luhnTest(final String value) {
        int sum = 0;
        int nDigits = value.length();
        int parity = nDigits % 2;
        for (int i = nDigits; i > 0; i--) {
            int digit = Character.getNumericValue(value.charAt(i - 1));
            if (i % 2 == parity) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
                sum += digit;
            }
        }
        for (int i = nDigits; i > 0; i--) {
            int digit = Character.getNumericValue(value.charAt(i - 1));
            if (i % 2 != parity) {
                sum += digit;
            }
        }
        if ((((sum % 10) - 10) * -1) == 10) {
            return ("0");
        } else {
            return ("" + (((sum % 10) - 10) * -1));
        }
    }
}