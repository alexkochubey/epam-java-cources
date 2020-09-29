package com.epam.university.java.core.task012;

import java.util.Collection;
import java.util.*;

public class Task012Impl implements Task012 {
    @Override
    public Graph invokeActions(Graph sourceGraph, Collection<GraphAction> actions) {
        if (sourceGraph == null || actions == null || actions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (GraphAction action : actions) {
            action.run(sourceGraph);
        }
        return sourceGraph;
    }

    @Override
    public boolean pathExists(Graph graph, int from, int to) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }
        Set<Integer> visited = new LinkedHashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(from);
        visited.add(from);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int v : graph.getAdjacent(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                }
            }
        }
        return visited.contains(to);
    }
}
