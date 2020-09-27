package com.epam.university.java.core.task003;

import java.util.Arrays;
import java.util.stream.Stream;

public class FlatMappingOperationImpl implements FlatMappingOperation {
    @Override
    public String[] flatMap(String source) {
        String[] result = source.split(",");
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }
}
