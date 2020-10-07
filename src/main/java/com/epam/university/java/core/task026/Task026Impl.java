package com.epam.university.java.core.task026;

public class Task026Impl implements Task026 {
    @Override
    public String encrypt(String sourceString, int shift) {
        if (sourceString == null || sourceString.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (shift == 0) {
            return sourceString;
        }
        shift = shift % 26;
        StringBuilder result = new StringBuilder();
        for (char character : sourceString.toCharArray()) {
            if (Character.isLetter(character)) {
                int originalAlphabetPosition;
                int newAlphabetPosition;
                char newCharacter;
                if (Character.isUpperCase(character)) {
                    originalAlphabetPosition = character - 'A';
                    newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                    newCharacter = (char) ('A' + newAlphabetPosition);
                } else {
                    originalAlphabetPosition = character - 'a';
                    newAlphabetPosition = (originalAlphabetPosition + shift) % 26;
                    newCharacter = (char) ('a' + newAlphabetPosition);
                }
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    @Override
    public String decrypt(String encryptedString, int shift) {
        if (encryptedString == null || encryptedString.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (shift == 0) {
            return encryptedString;
        }
        return encrypt(encryptedString, 26 - (shift % 26));
    }
}