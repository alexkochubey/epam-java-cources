package com.epam.university.java.core.task027;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Task027Impl implements Task027 {
    @Override
    public Collection<Integer> extract(String sourceString) {
        if (sourceString == null || sourceString.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (sourceString.length() == 1 || sourceString.charAt(0) == '0') {
            return Collections.emptyList();
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < sourceString.length(); i++) {
            list.add(Character.digit(sourceString.charAt(i), 10));
        }
        if (hasValidStructure(list)) {
            return list;
        }
        list.clear();

        if (sourceString.length() % 2 == 0) {
            for (int i = 0; i < sourceString.length(); i += 2) {
                list.add(Integer.valueOf(sourceString.substring(i, i + 2)));
            }
            if (hasValidStructure(list)) {
                return list;
            }
            list.clear();
        }

        if (!sourceString.contains("0")) {
            return Collections.emptyList();
        }

        int zero = sourceString.indexOf("0");

        int firstNum = Integer.parseInt(sourceString.substring(0, zero - 1));
        sourceString = sourceString.replaceAll(String.valueOf(firstNum), "");
        if (Integer.parseInt(sourceString) > firstNum) {
            list.add(firstNum);
            int len = String.valueOf(list.get(0)).length() + 1;
            for (int i = 0; i < sourceString.length(); i += len) {
                list.add(Integer.valueOf(sourceString.substring(i, i + len)));
            }
            if (hasValidStructure(list)) {
                return list;
            }
        }
        int len = String.valueOf(firstNum).length();

        if (len % 4 == 0) {
            list.add(Integer.valueOf(String.valueOf(firstNum).substring(0, 4)));
            list.add(Integer.valueOf(String.valueOf(firstNum).substring(4)));
            list.add(Integer.valueOf(sourceString));
            if (hasValidStructure(list)) {
                return list;
            }
        }
        return Collections.emptyList();
    }

    private boolean hasValidStructure(ArrayList<Integer> list) {
        return list.get(1) - list.get(0) == 1
                &&
                list.get(list.size() - 1) - list.get(list.size() - 2) == 1;
    }
}