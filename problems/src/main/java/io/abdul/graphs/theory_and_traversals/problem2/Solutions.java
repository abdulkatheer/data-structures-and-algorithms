package io.abdul.graphs.theory_and_traversals.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Example 1
    int V1 = 5;
    List<List<Integer>> adj1 = Arrays.asList(
        Arrays.asList(2, 3, 1),
        Arrays.asList(0),
        Arrays.asList(0, 4),
        Arrays.asList(0),
        Arrays.asList(2)
    );
    assertEquals(Arrays.asList(0, 2, 4, 3, 1), solution.dfsOfGraph(V1, adj1));
    assertEquals(Arrays.asList(0, 2, 3, 1, 4), solution.bfsOfGraph(V1, adj1));

    // Example 2
    int V2 = 4;
    List<List<Integer>> adj2 = Arrays.asList(
        Arrays.asList(1, 3),
        Arrays.asList(2, 0),
        Arrays.asList(1),
        Arrays.asList(0)
    );
    assertEquals(Arrays.asList(0, 1, 2, 3), solution.dfsOfGraph(V2, adj2));
    assertEquals(Arrays.asList(0, 1, 3, 2), solution.bfsOfGraph(V2, adj2));

    // Example 3
    int V3 = 3;
    List<List<Integer>> adj3 = Arrays.asList(
        Arrays.asList(1, 2),
        Arrays.asList(0),
        Arrays.asList(0)
    );
    assertEquals(Arrays.asList(0, 1, 2), solution.dfsOfGraph(V3, adj3));
    assertEquals(Arrays.asList(0, 1, 2), solution.bfsOfGraph(V3, adj3));

    // Edge case: Single vertex graph
    int V4 = 1;
    List<List<Integer>> adj4 = Arrays.asList(
        Collections.emptyList()
    );
    assertEquals(Arrays.asList(0), solution.dfsOfGraph(V4, adj4));
    assertEquals(Arrays.asList(0), solution.bfsOfGraph(V4, adj4));

    // Edge case: Linear chain graph 0-1-2-3
    int V5 = 4;
    List<List<Integer>> adj5 = Arrays.asList(
        Arrays.asList(1),
        Arrays.asList(0, 2),
        Arrays.asList(1, 3),
        Arrays.asList(2)
    );
    assertEquals(Arrays.asList(0, 1, 2, 3), solution.dfsOfGraph(V5, adj5));
    assertEquals(Arrays.asList(0, 1, 2, 3), solution.bfsOfGraph(V5, adj5));

    // Edge case: Complete graph of 3 vertices
    int V6 = 3;
    List<List<Integer>> adj6 = Arrays.asList(
        Arrays.asList(1, 2),
        Arrays.asList(0, 2),
        Arrays.asList(0, 1)
    );
    assertEquals(Arrays.asList(0, 1, 2), solution.dfsOfGraph(V6, adj6));
    assertEquals(Arrays.asList(0, 1, 2), solution.bfsOfGraph(V6, adj6));
  }
}

class Solution {

  public List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    List<Integer> visitedNodes = new ArrayList<>(V);

    Stack<Integer> nodes = new Stack<>();
    nodes.push(0);

    while (!nodes.isEmpty()) {
      Integer n = nodes.pop();
      if (!visited[n]) {
        visited[n] = true;
        visitedNodes.add(n);

        List<Integer> adjNodes = adj.get(n);
        for (int i = adjNodes.size() - 1; i >= 0; i--) {
          if (!visited[adjNodes.get(i)]) {
            nodes.push(adjNodes.get(i));
          }
        }
      }
    }

    return visitedNodes;
  }

  public List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    List<Integer> visitedNodes = new ArrayList<>(V);

    Queue<Integer> nodes = new LinkedList<>();
    visited[0] = true;
    visitedNodes.add(0);
    nodes.add(0);

    while (!nodes.isEmpty()) {
      Integer n = nodes.poll();

      for (Integer adjNode : adj.get(n)) {
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          visitedNodes.add(adjNode);
          nodes.add(adjNode);
        }
      }
    }

    return visitedNodes;
  }
}

class Solution2 {

  public List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    List<Integer> visitedNodes = new ArrayList<>(V);

    Stack<Integer> nodes = new Stack<>();
    nodes.push(0); // visit
    visited[0] = true;

    while (!nodes.isEmpty()) {
      Integer n = nodes.pop(); // process
      visitedNodes.add(n);

      List<Integer> adjNodes = adj.get(n);
      for (int i = adjNodes.size() - 1; i >= 0; i--) {
        if (!visited[adjNodes.get(i)]) {
          visited[adjNodes.get(i)] = true;
          nodes.push(adjNodes.get(i)); // visit
        }
      }
    }

    return visitedNodes;
  }

  public List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    List<Integer> visitedNodes = new ArrayList<>(V);

    Queue<Integer> queue = new LinkedList<>();
    queue.add(0); // visit
    visited[0] = true;

    while (!queue.isEmpty()) {
      int size = queue.size();

      // process all nodes in queue and add their adjacent nodes to queue
      for (int i = 0; i < size; i++) {
        Integer n = queue.poll(); // process
        visitedNodes.add(n);

        for (Integer adjNode : adj.get(n)) {
          if (!visited[adjNode]) {
            visited[adjNode] = true;
            queue.add(adjNode); // visit
          }
        }
      }
    }

    return visitedNodes;
  }
}

