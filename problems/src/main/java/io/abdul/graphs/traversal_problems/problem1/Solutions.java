package io.abdul.graphs.traversal_problems.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1
    int[][] adj1 = {
        {1, 0, 0, 1},
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {1, 0, 0, 1}
    };
    assertEquals(2, sol.numProvinces(adj1), "Example 1 failed");

    // Example 2
    int[][] adj2 = {
        {1, 0, 1},
        {0, 1, 0},
        {1, 0, 1}
    };
    assertEquals(2, sol.numProvinces(adj2), "Example 2 failed");

    // Example 3
    int[][] adj3 = {
        {1, 1},
        {1, 1}
    };
    assertEquals(1, sol.numProvinces(adj3), "Example 3 failed");

    // Edge case: Single node graph
    int[][] adj4 = {
        {1}
    };
    assertEquals(1, sol.numProvinces(adj4), "Single node graph failed");

    // Edge case: Fully disconnected graph of 3 nodes
    int[][] adj5 = {
        {1, 0, 0},
        {0, 1, 0},
        {0, 0, 1}
    };
    assertEquals(3, sol.numProvinces(adj5), "Disconnected graph failed");

    // Edge case: Fully connected graph of 4 nodes
    int[][] adj6 = {
        {1, 1, 1, 1},
        {1, 1, 1, 1},
        {1, 1, 1, 1},
        {1, 1, 1, 1}
    };
    assertEquals(1, sol.numProvinces(adj6), "Fully connected graph failed");

    int[][] adj7 = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
        {0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    // Expected output: number of provinces in this graph
    int expected = 5;  // <-- computed by counting connected components
    assertEquals(expected, sol.numProvinces(adj7), "Large matrix test failed");
  }
}

/*
Using DFS
T - O(n) - each node visited only once
S - O(n) - Stack

This is nothing but number of components in the graph
 */
class Solution {

  public int numProvinces(int[][] adj) {
    int n = adj.length;
    boolean[] visited = new boolean[n];

    int count = 0;
    for (int i = 0; i < n; i++) {
      if (visited[i]) {
        continue;
      }
      dfs(i, adj, visited);
      count++;
    }

    return count;
  }

  private void dfs(int node, int[][] adj, boolean[] visited) {
    Stack<Integer> nodes = new Stack<>();
    nodes.push(node);

    while (!nodes.isEmpty()) {
      Integer n = nodes.pop();

      if (!visited[n]) {
        visited[n] = true;

        for (int i = 0; i < adj[n].length; i++) {
          if (adj[n][i] == 1 && !visited[i]) {
            nodes.push(i);
          }
        }
      }
    }
  }
}

/*
Using BFS
T - O(n) - each node is visited once
S - O(n) - Queue
 */
class Solution2 {

  public int numProvinces(int[][] adj) {
    int n = adj.length;
    boolean[] visited = new boolean[n];

    int count = 0;
    for (int i = 0; i < n; i++) {
      if (visited[i]) {
        continue;
      }
      bfs(i, adj, visited);
      count++;
    }

    return count;
  }

  private void bfs(int startNode, int[][] adj, boolean[] visited) {
    Queue<Integer> nodes = new LinkedList<>();
    nodes.add(startNode);
    visited[startNode] = true;

    while (!nodes.isEmpty()) {
      Integer currentNode = nodes.poll();

      for (int i = 0; i < adj[currentNode].length; i++) {
        if (adj[currentNode][i] == 1 && !visited[i]) {
          visited[i] = true;
          nodes.add(i);
        }
      }
    }
  }
}

class Solution3 {

  public int numProvinces(int[][] adj) {
    int n = adj.length;
    boolean[] visited = new boolean[n];

    int count = 0;
    for (int i = 0; i < n; i++) {
      if (visited[i]) {
        continue;
      }
      bfs(i, adj, visited);
      count++;
    }

    return count;
  }

  private void bfs(int startNode, int[][] adj, boolean[] visited) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(startNode); // visit
    visited[startNode] = true;

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        Integer currentNode = queue.poll(); // process
        for (int i = 0; i < adj[currentNode].length; i++) {
          if (!visited[i] && adj[currentNode][i] == 1) {
            queue.add(i); // visit
            visited[i] = true;
          }
        }
      }
    }
  }
}


