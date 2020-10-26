package com.epam.university.java.core.task036;

import java.util.function.Function;

public class IntegratorImpl implements Integrator {
    public static final double INCREMENT = 1E-4;

    @Override
    public double integrate(double left, double right, Function<Double, Double> function) {
        double area = 0;
        double modifier = 1;
        if (left > right) {
            double tempA = left;
            left = right;
            right = tempA;
            modifier = -1;
        }
        for (double i = left + INCREMENT; i < right; i += INCREMENT) {
            double dFromA = i - left;
            area += (INCREMENT / 2)
                    *
                    (function.apply(left + dFromA)
                            +
                            function.apply(left + dFromA - INCREMENT));
        }
        return (Math.round(area * 10000.0) / 10000.0) * modifier;
    }
}