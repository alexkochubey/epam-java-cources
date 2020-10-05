package com.epam.university.java.core.task014;

public class VampireNumberImpl implements VampireNumber {
    private final int first;
    private final int second;
    private final int multiplication;

    /**
     * Default constructor.
     * @param first value of first number
     * @param second value of second number
     */


    public VampireNumberImpl(int first, int second) {
        this.first = first;
        this.second = second;
        this.multiplication = first * second;
    }


    @Override
    public int getMultiplication() {
        return this.multiplication;
    }


    @Override
    public int getFirst() {
        return this.first;
    }


    @Override
    public int getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VampireNumberImpl)) {
            return false;
        }
        VampireNumberImpl that = (VampireNumberImpl) o;
        return (getFirst() == that.getFirst()
                && getSecond() == that.getSecond())
                || (getFirst() == that.getSecond()
                && getSecond() == that.getFirst());
    }

    @Override
    public int hashCode() {
        return getFirst() * getSecond();
    }
}
