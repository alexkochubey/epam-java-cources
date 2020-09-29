package com.epam.university.java.core.task012;

import java.util.Collection;
import java.util.*;

public class GraphImpl implements Graph {

    private final Map<Integer, List<Integer>> verticesCollection;


    public GraphImpl() {
        this.verticesCollection = new HashMap<>();
    }

    public void addVertex(int label) {
        verticesCollection.put(label, new ArrayList<>());
    }

    @Override
    public void createEdge(int from, int to) {
        if (!verticesCollection.containsKey(from)) {
            throw new IllegalArgumentException();
        }
        verticesCollection.get(from).add(to);
        verticesCollection.get(to).add(from);
    }

    @Override
    public boolean edgeExists(int from, int to) {
        if (!verticesCollection.containsKey(from)) {
            return false;
        } else if (!verticesCollection.containsKey(to)) {
            return false;
        } else {
            return verticesCollection.get(from).contains(to) || verticesCollection.get(to).contains(from);
        }
    }

    @Override
    public void removeEdge(int from, int to) {
        if (!verticesCollection.containsKey(from)) {
            throw new IllegalArgumentException();
        } else {
            verticesCollection.get(from).remove(Integer.valueOf(to));
        }
        if (!verticesCollection.containsKey(to)) {
            throw new IllegalArgumentException();
        } else {
            verticesCollection.get(to).remove(Integer.valueOf(from));
        }
    }

    @Override
    public Collection<Integer> getAdjacent(int from) {
        if (!verticesCollection.containsKey(from)) {
            throw new IllegalArgumentException();
        }
        return verticesCollection.get(from);
    }
}
