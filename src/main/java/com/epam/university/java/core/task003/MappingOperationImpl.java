package com.epam.university.java.core.task003;

public class MappingOperationImpl implements MappingOperation {
    @Override
    public String map(String source) {
        StringBuilder src = new StringBuilder(source);
        return src.reverse().toString();
    }
}
