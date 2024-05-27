package io.abdul.adjacency_list;

import io.abdul.api.exception.DuplicateEdge;
import io.abdul.api.exception.DuplicateNode;
import io.abdul.api.exception.NodeNotFound;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Undirected Unweighted
public class Graph<E> {
    private final Map<E, List<E>> nodes;

    public Graph() {
        nodes = new LinkedHashMap<>();
    }

    public void insertNode(E node) {
        if (nodes.containsKey(node)) {
            throw new DuplicateNode("Node " + node + " already exists");
        }
        nodes.put(node, new ArrayList<>());
    }

    public void insertEdge(E node1, E node2) {
        if (!nodes.containsKey(node1)) {
            throw new NodeNotFound("Node " + node1 + " not found");
        }
        if (!nodes.containsKey(node2)) {
            throw new NodeNotFound("Node " + node2 + " not found");
        }
        List<E> adjList1 = nodes.get(node1);
        if (adjList1.contains(node2)) {
            throw new DuplicateEdge(node1 + " --> " + node2 + " edge already exists");
        }
        List<E> adjList2 = nodes.get(node2);
        if (adjList2.contains(node1)) {
            throw new DuplicateEdge(node2 + " --> " + node1 + " edge already exists");
        }
        adjList1.add(node2);
        adjList2.add(node1);
    }

    public int size() {
        return nodes.size();
    }

    @Override
    public String toString() {
        List<String> nodes = this.nodes.entrySet().stream()
                .map(entry -> {
                            String adj = entry.getValue().stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(" , "));
                            return entry.getKey() + " : " + adj;
                        }
                )
                .toList();
        return String.join("\n", nodes);
    }
}
