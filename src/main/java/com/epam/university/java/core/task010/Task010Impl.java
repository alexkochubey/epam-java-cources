package com.epam.university.java.core.task010;

import com.epam.university.java.core.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Александр on 12.09.2017.
 * Task010Impl
 */
public class Task010Impl implements Task010 {
    private static Validator VALIDATOR = Validator.newInstance(Task010Impl.class);

    /**
     * Given a textual file, you should count frequency of words in this file.
     *
     * @param source source file
     * @return map word to frequency of it
     */
    @Override
    public Map<String, Integer> countWordNumbers(File source) {
        VALIDATOR.assertNotNull(source);

        Map<String, Integer> result = new HashMap<>();
        List<String> lines = new ArrayList<>();

        try {
            lines.addAll(Files.readAllLines(source.toPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : lines) {
            Arrays.stream(line
                    .replaceAll("[^A-Za-z ]", "")
                    .toLowerCase().split(" "))
                    .forEach();
        }

        return result;
    }

    private void addToMap(Map map, String key) {
        int count = 0;
        map.get(key)
    }
}
