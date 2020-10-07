package com.epam.university.java.core.task023;

public class Task023Impl implements Task023 {
    @Override
    public String extract(String phoneString) {
        if (phoneString == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = phoneString.toCharArray();

        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                stringBuilder.append(ch);
            }
        }
        if (stringBuilder.length() != 11) {
            throw new IllegalArgumentException();
        }
        return stringBuilder.substring(1, 4);
    }
}
