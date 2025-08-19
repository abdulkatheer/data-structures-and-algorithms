package io.abdul.graphs.theory_and_traversals.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Test 1: Single vertex, no edges
    assertEquals(1, sol.findNumberOfComponent(1, List.of()));

    // Test 2: Two vertices, no edges
    assertEquals(2, sol.findNumberOfComponent(2, List.of()));

    // Test 3: Two vertices, one edge
    assertEquals(1, sol.findNumberOfComponent(2, List.of(
        List.of(0, 1)
    )));

    // Test 4: Linear chain graph
    assertEquals(1, sol.findNumberOfComponent(4, List.of(
        List.of(0, 1), List.of(1, 2), List.of(2, 3)
    )));

    // Test 5: Star graph
    assertEquals(1, sol.findNumberOfComponent(5, List.of(
        List.of(0, 1), List.of(0, 2), List.of(0, 3), List.of(0, 4)
    )));

    // Test 6: Disconnected graph with isolated nodes
    assertEquals(3, sol.findNumberOfComponent(5, List.of(
        List.of(0, 1), List.of(2, 3)
    )));

    // Test 7: Graph with cycle
    assertEquals(1, sol.findNumberOfComponent(4, List.of(
        List.of(0, 1), List.of(1, 2), List.of(2, 0), List.of(2, 3)
    )));

    // Test 8: Multiple components (prompt example)
    assertEquals(3, sol.findNumberOfComponent(7, List.of(
        List.of(0, 1), List.of(1, 2), List.of(2, 3), List.of(4, 5)
    )));

    // Test 9: No edges at all
    assertEquals(6, sol.findNumberOfComponent(6, List.of()));

    // Test 10: Fully connected graph
    assertEquals(1, sol.findNumberOfComponent(4, List.of(
        List.of(0, 1), List.of(0, 2), List.of(0, 3),
        List.of(1, 2), List.of(1, 3), List.of(2, 3)
    )));

    assertEquals(2, sol.findNumberOfComponent(5, List.of(
        List.of(0, 1), List.of(1, 2), List.of(3, 4)
    )));
  }
}

/*
T - O(V + E) - E to convert edges to adjList; V to visit all the nodes only once
S - O(V + E) - 2E to store adjacency list; V for the queue in BFS traversal
 */
class Solution {
  public int findNumberOfComponent(int V, List<List<Integer>> edges) {
    boolean[] visited = new boolean[V];

    List<Integer>[] adjList = new ArrayList[V];

    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (List<Integer> edge : edges) {
      adjList[edge.get(0)].add(edge.get(1));
      adjList[edge.get(1)].add(edge.get(0));
    }

    int count = 0;
    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        bfs(i, visited, adjList);
        count++;
      }
    }

    return count;
  }

  private void bfs(int start, boolean[] visited, List<Integer>[] adjList) {
    visited[start] = true;
    Queue<Integer> nodes = new LinkedList<>();
    nodes.add(start);

    while (!nodes.isEmpty()) {
      Integer n = nodes.poll();

      for (Integer adjNode : adjList[n]) {
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          nodes.add(adjNode);
        }
      }
    }
  }
}
