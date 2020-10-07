package com.epam.university.java.core.task025;

public class Task025Impl implements Task025 {
    @Override
    public int getAmountOfAlteredLetters(String sourceMessage) {
        if (sourceMessage == null) {
            throw new IllegalArgumentException();
        }
        int count = 0;
        String[] s = sourceMessage.split("");
        for (int i = 0; i < s.length; i += 3) {
            if (!s[i].equals("S") && !s[i].equals("")) {
                count++;
            }
            if (s.length >= i + 2 && !s[i + 1].equals("O")) {
                count++;
            }
            if (s.length >= i + 3 && !s[i + 2].equals("S")) {
                count++;
            }
        }
        return count;
    }
}
