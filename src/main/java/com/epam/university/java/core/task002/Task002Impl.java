package com.epam.university.java.core.task002;

import java.util.Arrays;


public class Task002Impl implements Task002 {
    @Override
    public boolean isEquals(String firstString, String secondString) {
        if (firstString == null
                || secondString == null) {
            throw new IllegalArgumentException("String not provided");
        }
        return firstString.equals(secondString);
    }

    @Override
    public String left(String sourceString, int number) {
        if (sourceString == null
                || number < 0) {
            throw new IllegalArgumentException("String not provided or number less then zero");
        }
        if (number > sourceString.length()) {
            number = sourceString.length();
        }
        return sourceString.substring(0, number);
    }


    @Override
    public String left(String sourceString, String separator) {
        if (sourceString == null || separator == null) {
            throw new IllegalArgumentException("String not provided");
        }
        var limit = sourceString.indexOf(separator);
        return sourceString.substring(0, limit);
    }

    @Override
    public String right(String sourceString, int number) {
        if (sourceString == null || number < 0) {
            throw new IllegalArgumentException("String not provided");
        }
        if (number > sourceString.length()) {
            number = sourceString.length();
        }
        return sourceString.substring(sourceString.length() - number);
    }

    @Override
    public String right(String sourceString, String separator) {
        if (sourceString == null || separator == null) {
            throw new IllegalArgumentException();
        }
        if (!sourceString.contains(separator)) {
            return sourceString;
        }
        return sourceString.split(separator)[1];
    }

    @Override
    public String[] split(String sourceString, String split) {
        if (sourceString == null || split == null) {
            throw new IllegalArgumentException("Arguements are not provided");
        }
        return sourceString.split(split);
    }

    @Override
    public String join(String[] sourceCollection, String glue) {
        if (sourceCollection == null
                || glue == null
                || sourceCollection.length == 0
                || Arrays.asList(sourceCollection).contains(null)) {
            throw new IllegalArgumentException();
        }
        String result = sourceCollection[0];
        for (int i = 1; i < sourceCollection.length; i++) {
            result = result.concat(glue).concat(sourceCollection[i]);
        }
        return result;
    }
}
