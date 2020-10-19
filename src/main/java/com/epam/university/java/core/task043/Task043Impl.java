package com.epam.university.java.core.task043;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task043Impl implements Task043 {
    private Map<Character, String> morseMap = new HashMap<>(Map.of(
            'A', ".-",
            'B', "-...",
            'C', "-.-.",
            'D', "-..",
            'E', ".",
            'F', "..-.",
            'G', "--.",
            'H', "....",
            'I', "..",
            'J', ".---"
    ));
    private Map<Character, String> morseMap2 = new HashMap<>(Map.of(
            'K', "-.-",
            'L', ".-..",
            'M', "--",
            'N', "-.",
            'O', "---",
            'P', ".--.",
            'Q', "--.-",
            'R', ".-.",
            'S', "...",
            'T', "-"
    ));
    private Map<Character, String> morseMap3 = new HashMap<>(Map.of(
            'U', "..-",
            'V', "...-",
            'W', ".--",
            'X', "-..-",
            'Y', "-.--",
            'Z', "--..",
            ',', "--..--"
    ));

    @Override
    public String encode(String sourceString) {
        morseMap.putAll(morseMap2);
        morseMap.putAll(morseMap3);
        if (sourceString == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sourceString.length(); i++) {
            char ch = sourceString.charAt(i);
            if (!Character.isWhitespace(ch)) {
                sb.append(morseMap.get(ch)).append(" ");
            } else {
                sb.append("  ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String decode(String sourceString) {
        morseMap.putAll(morseMap2);
        morseMap.putAll(morseMap3);
        if (sourceString == null) {
            throw new IllegalArgumentException();
        }
        List<String> sourceList = new ArrayList<>(Arrays.asList(sourceString.split(" {3}")));
        StringBuilder sb = new StringBuilder();
        for (String str : sourceList) {
            List<String> buf = new ArrayList<>(Arrays.asList(str.split(" ")));
            for (String s : buf) {
                sb.append(getKey(morseMap, s));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    private  <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}