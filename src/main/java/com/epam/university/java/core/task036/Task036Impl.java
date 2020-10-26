package com.epam.university.java.core.task036;

import java.util.function.Function;

public class Task036Impl implements Task036 {
    @Override
    public double integrate(Function<Double, Double> function,
                            Integrator integrator,
                            double limitLeft, double limitRight) {
        IntegratorImpl integr = new IntegratorImpl();
        return integr.integrate(limitLeft, limitRight, function);
    }
}