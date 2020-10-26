package com.epam.university.java.core.task050;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Task050Impl implements Task050 {


    @Override
    public double calculate(int size, Map<Double, Double> items) {
        if (size == 0 || items == null || items.isEmpty()) {
            throw new IllegalArgumentException();
        }
        double max = 0;
        List<Double> prices = items.entrySet().stream().sorted(
                new Comparator<Map.Entry<Double, Double>>() {
                    @Override
                    public int compare(Map.Entry<Double, Double> o1,
                                       Map.Entry<Double, Double> o2) {
                        return Double.compare(o2.getKey() / o2.getValue(),
                                o1.getKey() / o1.getValue());
                    }
                }).map(k -> k.getKey()).collect(Collectors.toList());
        for (Double price : prices) {
            double weight = items.get(price);
            size -= weight;
            max += price;
            if (size == 0) {
                break;
            }
            if (size < 0) {
                max -= price;
                size += weight;
                max += (size / weight) * price;
                break;
            }
        }
        return Math.round(max * 1000) / 1000d;
    }
}