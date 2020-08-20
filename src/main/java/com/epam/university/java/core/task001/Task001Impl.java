package com.epam.university.java.core.task001;

/*
    EPAM student:
    Alexey Kochubey
    Task 001
 */

public class Task001Impl implements Task001 {
    @Override
    public double addition(String firstNumber, String secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            throw new IllegalArgumentException("Input parameters are not set");
        }
        double result;
        try {
            result = Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber);
        } catch (Exception e) {
            throw new NumberFormatException("Impossible convert input values to numbers");
        }
        return result;
    }

    @Override
    public double subtraction(String firstNumber, String secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            throw new IllegalArgumentException("Input parameters are not set");
        }
        double result;
        try {
            result = Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber);
        } catch (Exception e) {
            throw new NumberFormatException("Impossible convert input values to numbers");
        }
        return result;
    }

    @Override
    public double multiplication(String firstNumber, String secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            throw new IllegalArgumentException("Input parameters are not set");
        }
        double result;
        try {
            result = Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber);
        } catch (Exception e) {
            throw new NumberFormatException("Impossible convert input values to numbers");
        }
        return result;
    }

    @Override
    public double division(String firstNumber, String secondNumber) {
        if (firstNumber == null || secondNumber == null) {
            throw new IllegalArgumentException("Input parameters are not set");
        }
        double result;
        try {
            result = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
        } catch (Exception e) {
            throw new NumberFormatException("Impossible convert input values to numbers");
        }
        return result;
    }
}
