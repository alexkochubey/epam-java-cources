package com.epam.university.java.core.task029;

import java.util.ArrayList;
import java.util.Collection;

public class Task029Impl implements Task029 {
    @Override
    public Collection<String> fillCrossword(Collection<String> rows, Collection<String> words) {
        if (rows == null || words == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> rowsList = new ArrayList<>(rows);
        ArrayList<String> wordsList = new ArrayList<>(words);
        int wordNum = 0;
        for (int i = 0; i < rowsList.size(); i++) {
            long count = rowsList.get(i).chars().filter(ch -> ch == '-').count();
            if (count == 1) {
                fillVertical(rowsList, wordsList, wordNum, i);
                wordNum++;
            } else if (count > 1) {
                fillHorizontal(rowsList, wordsList, wordNum, i);
                wordNum++;
            }
        }
        return rowsList;
    }

    private ArrayList<String> fillVertical(ArrayList<String> r,
                                           ArrayList<String> w, int wordNum, int rowNum) {
        String word = w.get(wordNum);
        int count = 0;
        int charPos = r.get(rowNum).indexOf("-");
        if (charPos == -1) {
            charPos = r.get(rowNum).lastIndexOf(word.charAt(0));
        }
        for (int i = 0; i < r.size(); i++) {
            if (count == word.length()) {
                break;
            }
            if (r.get(i).charAt(charPos) == '-') {
                r.set(i, r.get(i).replaceFirst("-", String.valueOf(word.charAt(count))));
                count++;
            } else if (r.get(i).charAt(charPos) == word.charAt(0)) {
                r.set(i, r.get(i).replaceFirst(
                        String.valueOf(word.charAt(0)),
                        String.valueOf(word.charAt(count))
                ));
                count++;
            }
        }
        return r;
    }

    private ArrayList<String> fillHorizontal(ArrayList<String> r,
                                             ArrayList<String> w, int wordNum, int rowNum) {
        String word = w.get(wordNum);
        String row = r.get(rowNum);
        int count = 0;
        row = row.replaceAll("[A-Z]", "-");
        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == '-') {
                row = row.replaceFirst("-", String.valueOf(word.charAt(count)));
                count++;
            }
        }
        r.set(rowNum, row);
        return r;
    }
}