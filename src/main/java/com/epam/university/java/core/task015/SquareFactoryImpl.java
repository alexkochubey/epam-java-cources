package com.epam.university.java.core.task015;

public class SquareFactoryImpl implements SquareFactory {

    @Override
    public Square newInstance(Point first, Point second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException();
        }
        Square square = new SquareImpl();
        square.setFirst(first);
        square.setSecond(second);
        return square;
    }
}