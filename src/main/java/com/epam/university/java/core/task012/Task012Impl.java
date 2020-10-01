package com.epam.university.java.core.task012;

import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;

public class Task012Impl implements Task012 {
    @Override
    public Graph invokeActions(Graph sourceGraph, Collection<GraphAction> actions) {
        if (sourceGraph == null || actions == null || actions.size() == 0) {
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
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> toVisit = new LinkedList<>();

        toVisit.add(from);
        while (!toVisit.isEmpty()) {
            int vertex = toVisit.poll();
            visited.add(vertex);
            if (graph.getAdjacent(vertex).contains(to)) {
                return true;
            }
            for (int child : graph.getAdjacent(vertex)) {
                if (!visited.contains(child) && !toVisit.contains(child)) {
                    toVisit.add(child);
                }
            }
        }
        return false;
    }
}
