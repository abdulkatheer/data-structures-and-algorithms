package io.abdul.adjacency_matrix;

import io.abdul.api.exception.DuplicateEdge;
import io.abdul.api.exception.DuplicateNode;
import io.abdul.api.exception.NodeNotFound;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Graph<E> {
    private final Object[] nodes;
    private final int[][] adjMatrix;
    private final int capacity;
    private int size;

    public Graph(int capacity) {
        this.capacity = capacity;
        this.nodes = new Object[capacity];
        this.adjMatrix = new int[capacity][capacity];
    }

    public void insertNode(E node) {
        if (size >= capacity) {
            throw new RuntimeException("Graph is full");
        }
        for (int i = 0; i < size; i++) {
            if (nodes[i].equals(node)) {
                throw new DuplicateNode("Node " + node + " already exists");
            }
        }
        nodes[size++] = node;
    }

    public int size() {
        return size;
    }

    public void insertEdge(E node1, E node2) {
        int node1Pos = -1;
        int node2Pos = -1;
        for (int i = 0; i < size; i++) {
            if (nodes[i].equals(node1)) {
                node1Pos = i;
            } else if (nodes[i].equals(node2)) {
                node2Pos = i;
            }
        }

        if (node1Pos == -1) {
            throw new NodeNotFound("Node " + node1 + " not found");
        }

        if (node2Pos == -1) {
            throw new NodeNotFound("Node " + node2 + " not found");
        }

        if (adjMatrix[node1Pos][node2Pos] != 0) {
            throw new DuplicateEdge(node1 + " --> " + node2 + " edge already exists");
        }
        if (adjMatrix[node2Pos][node1Pos] != 0) {
            throw new DuplicateEdge(node2 + " --> " + node1 + " edge already exists");
        }
        adjMatrix[node1Pos][node2Pos] = 1;
        adjMatrix[node2Pos][node1Pos] = 1;
    }

    @Override
    public String toString() {
        List<String> allNodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<E> edges = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (adjMatrix[i][j] != 0) {
                    edges.add((E) nodes[j]);
                }
            }
            allNodes.add(nodes[i] + " : " + edges.stream()
                    .map(String::valueOf)
                    .collect(joining(" , ")));
        }
        return allNodes.stream()
                .collect(joining("\n"));
    }
}
