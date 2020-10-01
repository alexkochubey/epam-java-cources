package com.epam.university.java.core.task013;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Task013Impl implements Task013 {
    @Override
    public Figure invokeActions(Figure figure, Collection<FigureAction> actions) {
        if (figure == null || actions == null || actions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (FigureAction action : actions) {
            action.run(figure);
        }
        return figure;
    }

    @Override
    public boolean isConvexPolygon(Figure figure) {
        if (figure == null) {
            throw new IllegalArgumentException();
        }
        FigureFactory factory = new FigureFactoryImpl();

        int size = figure.getVertexes().size();
        if (size < 3) {
            return false;
        }
        if (size == 3) {
            return true;
        }

        Vertex first;
        Vertex second;
        Vertex third;

        List<Vertex> vertexes = (List<Vertex>) figure.getVertexes();
        Vertex startVertex = vertexes.stream()
                .min(Comparator.comparing(Vertex::getX))
                .get();

        vertexes.sort(new SortVertexes(startVertex.getX(), startVertex.getY()));

        int crossProduct = 0;
        for (int i = 0; i < size; i++) {
            first = vertexes.get(i);
            second = vertexes.get((i + 1) % size);
            third = vertexes.get((i + 2) % size);

            Vertex firstVector = factory
                    .newInstance(second.getX() - first.getX(), second.getY() - first.getY());
            Vertex secondVector = factory
                    .newInstance(third.getX() - second.getX(), third.getY() - second.getY());

            if (i == 0) {
                crossProduct = vectorProduct(firstVector, secondVector);
            } else {
                int currentCrossProduct = vectorProduct(firstVector, secondVector);
                if (currentCrossProduct * crossProduct < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int vectorProduct(Vertex firstVector, Vertex secondVector) {
        return firstVector.getX() * secondVector.getY() - secondVector.getX() * firstVector.getY();
    }

    static class SortVertexes implements Comparator<Vertex> {
        private final int startX;
        private final int startY;

        public SortVertexes(int startX, int startY) {
            this.startX = startX;
            this.startY = startY;
        }

        @Override
        public int compare(Vertex o1, Vertex o2) {
            if (o1.getX() - startX == 0 && o1.getY() - startY == 0) {
                return -1;
            }
            if (o2.getX() - startX == 0 && o2.getY() - startY == 0) {
                return 1;
            }
            double tg1 = (double) (o1.getY() - startY) / (double) (o1.getX() - startX);
            double tg2 = (double) (o2.getY() - startY) / (double) (o2.getX() - startX);
            if (tg1 > tg2) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
