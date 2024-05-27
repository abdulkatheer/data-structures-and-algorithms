package io.abdul.adjacency_list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void insertNode() {
        Graph<Integer> graph = new Graph<>();
        graph.insertNode(0);
        graph.insertNode(1);
        graph.insertNode(2);
        graph.insertNode(3);
        graph.insertNode(4);
        graph.insertNode(5);
        graph.insertNode(6);

        graph.insertEdge(0, 1);
        graph.insertEdge(0, 2);
        graph.insertEdge(1, 2);
        graph.insertEdge(1, 3);
        graph.insertEdge(2, 4);
        graph.insertEdge(3, 4);
        graph.insertEdge(4, 5);
        graph.insertEdge(5, 6);

        System.out.println(graph);
        assertEquals(7, graph.size());
    }
}