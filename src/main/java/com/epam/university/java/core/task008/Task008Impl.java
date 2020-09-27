package com.epam.university.java.core.task008;

import java.util.*;

public class Task008Impl implements Task008 {
    @Override
    public boolean isValid(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException();
        }
        if (sourceString.isEmpty()) {
            return true;
        }
        List<Character> openBraces = new ArrayList<>(Arrays.asList('{', '(', '['));
        List<Character> closeBraces = new ArrayList<>(Arrays.asList('}', ')', ']'));
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < sourceString.length(); i++) {
            if (openBraces.contains(sourceString.charAt(i))) {
                stack.addLast(sourceString.charAt(i));
            } else if (closeBraces.contains(sourceString.charAt(i))) {
                if (stack.size() == 0
                        || (stack.getLast() == '{' && sourceString.charAt(i) != '}')
                        || (stack.getLast() == '(' && sourceString.charAt(i) != ')')
                        || (stack.getLast() == '[' && sourceString.charAt(i) != ']')) {
                    return false;
                } else {
                    stack.removeLast();
                }
            }
        }
        return stack.size() == 0;
    }
}
