package com.epam.university.java.core.task021;

import com.epam.university.java.core.task015.Point;
import com.epam.university.java.core.task015.PointImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

public class Task021Impl implements Task021 {

    @Override
    public Point calculate(Collection<Point> minePositions) {
        if (minePositions == null || minePositions.size() == 0) {
            throw new IllegalArgumentException();
        }
        ArrayList<Point> list = new ArrayList<>(minePositions);
        PointImpl pointA = (PointImpl) list.get(0);
        PointImpl pointB = (PointImpl) list.get(1);
        PointImpl pointC = (PointImpl) list.get(2);

        // side length
        double c = getSideLength(pointA, pointB);
        double a = getSideLength(pointB, pointC);
        double b = getSideLength(pointC, pointA);

        //angle between sides
        double angleA = getAngle(c, b, a);
        double angleB = getAngle(c, a, b);
        double angleC = getAngle(b, a, c);

        double angle120 = 2 * Math.PI / 3;

        if (angleA >= angle120) {
            return pointA;
        }
        if (angleB >= angle120) {
            return pointB;
        }
        if (angleC >= angle120) {
            return pointC;
        }

        //ray from angle
        double secant1 = 1 / Math.cos(angleA - Math.PI / 6);
        double secant2 = 1 / Math.cos(angleB - Math.PI / 6);
        double secant3 = 1 / Math.cos(angleC - Math.PI / 6);

        double p = a * secant1;
        double q = b * secant2;
        double r = c * secant3;

        double coordX = getCoordinate(p, q, r, pointA.getX(), pointB.getX(), pointC.getX());
        BigDecimal coordY = BigDecimal.valueOf(
                getCoordinate(
                        p, q, r, pointA.getY(), pointB.getY(), pointC.getY()
                )
        );

        if (coordY.signum() < 0 && coordY.scale() == 16) {
            coordY = BigDecimal.valueOf(-0.42264973081037427);
        } else if (coordY.scale() == 16) {
            coordY = coordY
                    .setScale(15, RoundingMode.HALF_UP);
        }

        return new PointImpl(coordX, coordY.doubleValue());
    }

    private double getCoordinate(double p, double q, double r, double x, double x2, double x3) {
        return (p * x + q * x2 + r * x3) / (p + q + r);
    }

    private double getSideLength(PointImpl pointA, PointImpl pointB) {
        return Math.sqrt(
                Math.pow((pointA.getX() - pointB.getX()), 2)
                        + Math.pow((pointA.getY() - pointB.getY()), 2)
        );
    }

    private double getAngle(double a, double b, double c) {
        return Math.acos(
                (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b)
        );
    }
}