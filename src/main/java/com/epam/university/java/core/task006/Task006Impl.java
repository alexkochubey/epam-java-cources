package com.epam.university.java.core.task006;

import java.util.Collection;


public class Task006Impl implements Task006 {
    @Override
    public double resistance(Collection<Measurement> measurements) {
        if (measurements == null) {
            throw new IllegalArgumentException();
        }
        double averageVoltage = 0.0;
        double averageAmperage = 0.0;
        double intNum = 0.0;
        double semicolon = 0.0;

        for (Measurement el : measurements) {
            averageAmperage += el.getAmperage();
            averageVoltage += el.getVoltage();
        }
        averageAmperage = averageAmperage / measurements.size();
        averageVoltage = averageVoltage / measurements.size();

        for (Measurement m : measurements) {
            intNum += (m.getAmperage() - averageAmperage) * (m.getVoltage() - averageVoltage);
            semicolon += Math.pow(m.getAmperage() - averageAmperage, 2);
        }
        return Math.round((intNum / semicolon) * 1000) / 1000.0;
    }
}
