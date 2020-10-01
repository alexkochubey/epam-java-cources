package com.epam.university.java.core.task012;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


public class GraphImpl implements Graph {

    private final Map<Integer, Set<Integer>> graphMap;


    public GraphImpl() {
        this.graphMap = new HashMap<>();
    }

    public int getSize() {
        return graphMap.size();
    }

    public void createVertex(int vertex) {
        this.graphMap.put(vertex, new HashSet<>());
    }

    @Override
    public void createEdge(int from, int to) {
        if (graphMap.containsKey(from) && graphMap.containsKey(to)) {
            graphMap.get(from).add(to);
            graphMap.get(to).add(from);
        } else {
            throw new IllegalArgumentException("One or both vertexes doesn't exist");
        }
    }

    @Override
    public boolean edgeExists(int from, int to) {
        if (!graphMap.containsKey(from) || !graphMap.containsKey(to)) {
            throw new IllegalArgumentException("One or both vertexes doesn't exist");
        }
        return graphMap.get(from).contains(to);
    }

    @Override
    public void removeEdge(int from, int to) {
        if (!graphMap.containsKey(from) || !graphMap.containsKey(to)) {
            throw new IllegalArgumentException("One or both vertexes doesn't exist");
        }
        graphMap.get(from).remove(to);
        graphMap.get(to).remove(from);
    }

    @Override
    public Collection<Integer> getAdjacent(int from) {
        if (!graphMap.containsKey(from)) {
            throw new IllegalArgumentException("Vertex doesn't exist");
        }
        return graphMap.get(from);
    }
}
