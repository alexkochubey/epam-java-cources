package com.epam.university.java.core.task043;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Task043Impl implements Task043 {
    public static final Map<String, String> MORSE_DECODE = new HashMap<>() {{
        put(".-", "A");
        put("-...", "B");
        put("-.-.", "C");
        put("-..", "D");
        put(".", "E");
        put("..-.", "F");
        put("--.", "G");
        put("....", "H");
        put("..", "I");
        put(".---", "J");
        put("-.-", "K");
        put(".-..", "L");
        put("--", "M");
        put("-.", "N");
        put("---", "O");
        put(".--.", "P");
        put("--.-", "Q");
        put(".-.", "R");
        put("...", "S");
        put("-", "T");
        put("..-", "U");
        put("...-", "V");
        put(".--", "W");
        put("-..-", "X");
        put("-.--", "Y");
        put("--..", "Z");
        put("--..--", ",");
    }
    };
    public static final Map<String, String> MORSE_ENCODE = MORSE_DECODE
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    /**
     * Encode source string with Morse Code.
     *
     * @param sourceString source string
     * @return encoded string
     */
    @Override
    public String encode(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder builder = new StringBuilder();
        String[] words = sourceString.trim().split(" ");
        for (String word : words) {
            String[] letters = word.split("");
            for (String letter : letters) {
                builder.append(MORSE_ENCODE.get(letter));
                builder.append(" ");
            }
            builder.append("  ");
        }
        return builder.substring(0, builder.length() - 3);
    }


    @Override
    public String decode(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder builder = new StringBuilder();
        String[] words = sourceString.trim().split("   ");
        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                builder.append(MORSE_DECODE.get(letter));
            }
            builder.append(" ");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
