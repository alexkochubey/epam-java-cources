package com.epam.university.java.core.task015;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class Task015Impl implements Task015 {

    /**
     * Get area of intersection of two squares. Squares are defined as two
     * opposite points in 2-dimensional area.
     *
     * <p>
     * Example:
     * square 1 = (2, 2) and (4, 4)
     * square 2 = (3, 3) and (5, 5)
     * area is 1 = square (3, 3) and (4, 4)
     * </p>
     * <p>
     * Tip: paint it in the notebook.
     * </p>
     *
     * @param first  first square definition
     * @param second second square definition
     * @return value of area
     */
    @Override
    public double getArea(Square first, Square second) {
        List<Point> pointArea = new ArrayList<>();
        pointArea.addAll(getIntersectionPoints(first, second));
        pointArea.addAll(getAllPointsInside(first, second));
        pointArea.addAll(getAllPointsInside(second, first));
        pointArea.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX()) != 0
                        ? Double.compare(o1.getX(), o2.getX())
                        : Double.compare(o1.getY(), o2.getY());
            }
        });
        Point startPoint = pointArea.size() != 0 ? pointArea.remove(0) : null;
        pointArea.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX()) != 0
                        ? Double.compare(o1.getX(), o2.getX())
                        : Double.compare(o2.getY(), o1.getY());
            }
        });
        double area = 0;
        Predicate<Double> predicateTop = y -> pointArea.get(pointArea.size() - 1).getY() < y;
        area += getHalfArea(startPoint, pointArea, predicateTop);
        Collections.reverse(pointArea);
        Predicate<Double> predicateDown = y -> true;
        area += getHalfArea(startPoint, pointArea, predicateDown);
        return area;
    }

    private Double getHalfArea(Point startPoint, List<Point> pointArea,
                               Predicate<Double> predicate) {
        List<Point> deleted = new ArrayList<>();
        double area = 0;
        double a = 0;
        double b = 0;
        double c = 0;
        Point prev = new PointImpl();
        for (int i = 0; i < pointArea.size(); i++) {
            if (predicate.test(pointArea.get(i).getY()) || i == pointArea.size() - 1) {
                if (a == 0) {
                    a = Math.sqrt(Math.pow(Math.abs(pointArea.get(i).getX() - startPoint.getX()), 2)
                            + Math.pow(Math.abs(pointArea.get(i).getY() - startPoint.getY()), 2));
                    prev = pointArea.get(i);
                    deleted.add(pointArea.get(i));
                    continue;
                }
                b = Math.sqrt(Math.pow(Math.abs(pointArea.get(i).getX() - startPoint.getX()), 2)
                        + Math.pow(Math.abs(pointArea.get(i).getY() - startPoint.getY()), 2));
                c = Math.sqrt(Math.pow(Math.abs(pointArea.get(i).getX() - prev.getX()), 2)
                        + Math.pow(Math.abs(pointArea.get(i).getY() - prev.getY()), 2));
                double p = (a + b + c) / 2;
                area += Math.sqrt(p * (p - a) * (p - b) * (p - c));
                a = b;
                prev = pointArea.get(i);
                if (i != pointArea.size() - 1) {
                    deleted.add(pointArea.get(i));
                }
            }
        }
        pointArea.removeAll(deleted);
        return area;
    }

    private List<Point> getIntersectionPoints(Square first, Square second) {
        List<Point> intersectionPoints = new ArrayList<>();
        List<Point> firstList = getAllPointsSquare(first);
        List<Point> secList = getAllPointsSquare(second);
        List<Point> compareList = new ArrayList<>(firstList);
        compareList.removeAll(secList);
        if (compareList.isEmpty()) {
            return new ArrayList<>();
        }
        Iterator<Point> firstPoints = getAllPointsSquare(first).iterator();
        while (firstPoints.hasNext()) {
            Point one = firstPoints.next();
            Point sec = firstPoints.next();
            double kOne = (one.getY() - sec.getY()) / (one.getX() - sec.getX());
            double bOne = (sec.getY() * one.getX() - one.getY() * sec.getX())
                    / (one.getX() - sec.getX());
            Iterator<Point> secondPoints = getAllPointsSquare(second).iterator();
            while (secondPoints.hasNext()) {
                Point third = secondPoints.next();
                Point fourth = secondPoints.next();
                double kSec = (third.getY() - fourth.getY()) / (third.getX() - fourth.getX());
                double bSec = (fourth.getY() * third.getX() - third.getY() * fourth.getX())
                        / (third.getX() - fourth.getX());
                Point interSec = new PointImpl();
                if (Double.compare(one.getX(), sec.getX()) == 0
                        && Double.compare(third.getX(), fourth.getX()) == 0) {
                    if (Double.compare(one.getX(), third.getX()) != 0) {
                        continue;
                    }
                    if (Math.min(one.getY(), sec.getY()) < Math.max(third.getY(), fourth.getY())
                            && Math.max(one.getY(), sec.getY()) >= Math.max(third.getY(),
                            fourth.getY())) {
                        interSec.setX(one.getX());
                        interSec.setY(Math.min(one.getY(), sec.getY()));
                        intersectionPoints.add(interSec);
                    }
                    if (Math.max(one.getY(), sec.getY()) > Math.min(third.getY(), fourth.getY())
                            && Math.max(one.getY(), sec.getY()) <= Math.max(one.getY(),
                            sec.getY())) {
                        interSec.setX(one.getX());
                        interSec.setY(Math.max(one.getY(), sec.getY()));
                        intersectionPoints.add(interSec);
                    }
                    if (Math.max(one.getY(), sec.getY()) <= Math.max(third.getY(), fourth.getY())
                            && Math.min(one.getY(), sec.getY()) >= Math.min(third.getY(),
                            fourth.getY())) {
                        interSec.setX(one.getX());
                        interSec.setY(one.getY() / 2);
                        intersectionPoints.add(interSec);
                    }
                    continue;
                }
                if (Double.compare(one.getY(), sec.getY()) == 0
                        && Double.compare(third.getY(), fourth.getY()) == 0) {
                    if (Double.compare(one.getX(), third.getX()) != 0) {
                        continue;
                    }
                    if (Math.min(one.getX(), sec.getX()) < Math.max(third.getX(), fourth.getX())
                            && Math.max(one.getX(), sec.getX()) >= Math.max(third.getX(),
                            fourth.getX())) {
                        interSec.setX(Math.min(one.getX(), sec.getX()));
                        interSec.setY(one.getY());
                        intersectionPoints.add(interSec);
                    }
                    if (Math.max(one.getX(), sec.getX()) > Math.min(third.getX(),
                            fourth.getX()) && Math.max(one.getX(), sec.getX())
                            <= Math.max(one.getX(), sec.getX())) {
                        interSec.setX(Math.max(one.getX(), sec.getX()));
                        interSec.setY(one.getY());
                        intersectionPoints.add(interSec);
                    }
                    if (Math.max(one.getX(), sec.getX()) <= Math.max(third.getX(), fourth.getX())
                            && Math.min(one.getX(), sec.getX()) >= Math.min(third.getX(),
                            fourth.getX())) {
                        interSec.setX(one.getX() / 2);
                        interSec.setY(one.getY());
                        intersectionPoints.add(interSec);
                    }
                    continue;
                }
                if (Double.compare(one.getX(), sec.getX()) == 0) {
                    interSec.setX(one.getX());
                    interSec.setY(kSec * one.getX() + bSec);
                } else if (Double.compare(third.getX(), fourth.getX()) == 0) {
                    interSec.setX(third.getX());
                    interSec.setY(kOne * third.getX() + bOne);
                } else if (Double.compare(one.getY(), sec.getY()) == 0) {
                    interSec.setX((one.getY() - bSec) / kSec);
                    interSec.setY(one.getY());
                } else if (Double.compare(third.getX(), fourth.getX()) == 0) {
                    interSec.setX((third.getY() - bOne) / kOne);
                    interSec.setY(third.getY());
                } else {
                    interSec.setX((bSec - bOne) / (kOne - kSec));
                    interSec.setY((bSec * kOne - kSec * bOne) / (kOne - kSec));
                }
                if (interSec.getX() <= Math.max(third.getX(), fourth.getX())
                        && interSec.getX() >= Math.min(third.getX(), fourth.getX())
                        && interSec.getY() <= Math.max(third.getY(), fourth.getY())
                        && interSec.getY() >= Math.min(third.getY(), fourth.getY())
                        && interSec.getY() <= Math.max(one.getY(), sec.getY())
                        && interSec.getY() >= Math.min(one.getY(), sec.getY())
                        && interSec.getX() <= Math.max(one.getX(), sec.getX())
                        && interSec.getX() >= Math.min(one.getX(), sec.getX())) {
                    intersectionPoints.add(interSec);
                }
            }
        }
        return intersectionPoints;
    }

    private List<Point> getAllPointsSquare(Square first) {
        Point one = first.getFirst();
        Point second = first.getSecond();
        double kOne = (one.getY() - second.getY()) / (one.getX() - second.getX());
        double bOne = (second.getY() * one.getX() - one.getY() * second.getX())
                / (one.getX() - second.getX());
        Point middle = new PointImpl();
        middle.setX(Math.min(one.getX(), second.getX()) + Math.abs(one.getX()
                - second.getX()) / 2);
        middle.setY(Math.min(one.getY(), second.getY()) + Math.abs(one.getY()
                - second.getY()) / 2);
        double bTwo = middle.getY() + (1 / kOne) * middle.getX();
        Point third = new PointImpl();
        Point fourth = new PointImpl();
        if (Double.compare(one.getX(), middle.getX()) == 0) {
            third.setX(middle.getX() - Math.abs(middle.getY() - one.getY()));
            third.setY(middle.getY());
            fourth.setX(middle.getX() + Math.abs(middle.getY() - one.getY()));
            fourth.setY(middle.getY());
        } else if (Double.compare(one.getY(), middle.getY()) == 0) {
            third.setX(middle.getX());
            third.setY(middle.getY() + Math.abs(middle.getX() - one.getX()));
            fourth.setX(middle.getX());
            fourth.setY(middle.getY() - Math.abs(middle.getX() - one.getX()));
        } else {
            double rSquare = Math.pow(Math.abs(one.getY() - middle.getY()), 2)
                    + Math.pow(Math.abs(one.getX() - middle.getX()), 2);
            third.setX((2 * middle.getX() + Math.sqrt(4 * Math.pow(middle.getX(), 2)
                    - 4 * (Math.pow(middle.getX(), 2)
                    - rSquare / (1 + Math.pow(1 / kOne, 2))))) / 2);
            fourth.setX((2 * middle.getX() - Math.sqrt(4 * Math.pow(middle.getX(), 2)
                    - 4 * (Math.pow(middle.getX(), 2)
                    - rSquare / (1 + Math.pow(1 / kOne, 2))))) / 2);
            third.setY((-1 / kOne) * third.getX() + bTwo);
            fourth.setY((-1 / kOne) * fourth.getX() + bTwo);
        }
        List<Point> points = Arrays.asList(one, fourth, one, third, third,
                second, second, fourth);
        return points;
    }

    private List<Point> getAllPointsInside(Square first, Square second) {
        List<Point> insidePoints = new ArrayList<>();
        List<Point> lines = getAllPointsSquare(first);
        Point maxYFirst = lines.stream().max(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        }).get();
        Point minYFirst = lines.stream().min(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        }).get();
        Point maxXFirst = lines.stream().max(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        }).get();
        Point minXFirst = lines.stream().min(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        }).get();
        List<Point> points = new ArrayList<>(Arrays.asList(lines.get(0), lines.get(5),
                lines.get(3), lines.get(1)));
        points.remove(maxYFirst);
        points.remove(minYFirst);
        double kOneMax = (maxYFirst.getY() - points.get(0).getY())
                / (maxYFirst.getX() - points.get(0).getX());
        double kSecMax = (maxYFirst.getY() - points.get(1).getY())
                / (maxYFirst.getX() - points.get(1).getX());
        double bOneMax = (points.get(0).getY() * maxYFirst.getX()
                - maxYFirst.getY() * points.get(0).getX())
                / (maxYFirst.getX() - points.get(0).getX());
        double bSecMax = (points.get(1).getY() * maxYFirst.getX()
                - maxYFirst.getY() * points.get(1).getX())
                / (maxYFirst.getX() - points.get(1).getX());
        double kOneMin = (minYFirst.getY() - points.get(0).getY())
                / (minYFirst.getX() - points.get(0).getX());
        double kSecMin = (minYFirst.getY() - points.get(1).getY())
                / (minYFirst.getX() - points.get(1).getX());
        double bOneMin = (points.get(0).getY() * minYFirst.getX()
                - minYFirst.getY() * points.get(0).getX())
                / (minYFirst.getX() - points.get(0).getX());
        double bSecMin = (points.get(1).getY() * minYFirst.getX()
                - minYFirst.getY() * points.get(1).getX())
                / (minYFirst.getX() - points.get(1).getX());
        List<Point> linesSec = getAllPointsSquare(second);
        List<Point> pointsSecond = new ArrayList<>(Arrays.asList(linesSec.get(0),
                linesSec.get(5), linesSec.get(3), linesSec.get(1)));
        if ((Double.compare(maxYFirst.getY(), points.get(0).getY()) == 0
                || Double.compare(maxYFirst.getY(), points.get(1).getY()) == 0)
                && (Double.compare(minYFirst.getY(), points.get(0).getY()) == 0
                || Double.compare(minYFirst.getY(), points.get(1).getY()) == 0)) {
            kOneMax = 0;
            kOneMin = 0;
            kSecMax = 0;
            kSecMin = 0;
            bOneMax = maxYFirst.getY();
            bSecMax = maxYFirst.getY();
            bOneMin = minYFirst.getY();
            bSecMin = minYFirst.getY();
        }
        for (Point p : pointsSecond) {
            if (p.getY() <= kOneMax * p.getX() + bOneMax
                    && p.getY() >= kOneMin * p.getX() + bOneMin
                    && p.getX() >= minXFirst.getX()
                    && p.getX() <= maxXFirst.getX()
                    && p.getY() <= kSecMax * p.getX() + bSecMax
                    && p.getY() >= kSecMin * p.getX() + bSecMin
                    && p.getX() >= minXFirst.getX()
                    && p.getX() <= maxXFirst.getX()) {
                insidePoints.add(p);
            }
        }
        return insidePoints;
    }
}