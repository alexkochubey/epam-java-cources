package com.epam.university.java.core.task040;

import java.util.List;
import java.util.stream.Collectors;

public class Task040Impl implements Task040 {
    @Override
    public int countScore(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException();
        }
        str = str.replace(" ", "");
        List<Character> list;
        list = str.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        list.add(' ');
        list.add(' ');
        int count = 0;
        for (int i = 0; i < list.size() - 2; i++) {
            if (count == 300) {
                return count;
            }
            if (i == 15 && list.get(i - 1) == 'X') {
                return count;
            }
            if (list.get(i) == 'X') {
                count += 10;
                Character buf = list.get(i + 2);
                if (Character.isDigit(buf)) {
                    count += Integer.parseInt(String.valueOf(buf));
                    buf = list.get(i + 1);
                    if (Character.isDigit(buf)) {
                        count += Integer.parseInt(String.valueOf(buf));
                    } else if (buf == 'X') {
                        count += 10;
                    }
                } else if (buf == 'X') {
                    count += 10;
                    buf = list.get(i + 1);
                    if (buf == 'X') {
                        count += 10;
                    } else {
                        count += Integer.parseInt(String.valueOf(buf));
                    }
                } else if (buf == '/') {
                    count += 10;
                }
            } else if (list.get(i) == '/') {
                count += 10;
                Character buf = list.get(i + 1);
                if (Character.isDigit(buf)) {
                    count += Integer.parseInt(String.valueOf(buf));
                } else if (buf == 'X') {
                    count += 10;
                }
            } else if (Character.isDigit(list.get(i)) && list.get(i + 1) != '/') {
                count += Integer.parseInt(String.valueOf(list.get(i)));
            }
        }

        return count;
    }
}