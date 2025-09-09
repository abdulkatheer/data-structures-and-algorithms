package io.abdul.graphs.cycles.problem2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*
Linear graphs are always bipartite
Cycle with event length are always bipartite and odd length are non-bipartite
We can either detect cycle and find length and decide or visit and colour
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1: Bipartite graph (square cycle)
    int V1 = 4;
    List<List<Integer>> adj1 = new ArrayList<>();
    for (int i = 0; i < V1; i++) {
      adj1.add(new ArrayList<>());
    }
    adj1.get(0).addAll(Arrays.asList(1, 3));
    adj1.get(1).addAll(Arrays.asList(0, 2));
    adj1.get(2).addAll(Arrays.asList(1, 3));
    adj1.get(3).addAll(Arrays.asList(0, 2));
    assertTrue(sol.isBipartite(V1, adj1), "Example 1 failed");

    // Example 2: Not bipartite (odd cycle K3)
    int V2 = 4;
    List<List<Integer>> adj2 = new ArrayList<>();
    for (int i = 0; i < V2; i++) {
      adj2.add(new ArrayList<>());
    }
    adj2.get(0).addAll(Arrays.asList(1, 2, 3));
    adj2.get(1).addAll(Arrays.asList(0, 2));
    adj2.get(2).addAll(Arrays.asList(0, 1, 3));
    adj2.get(3).addAll(Arrays.asList(0, 2));
    assertFalse(sol.isBipartite(V2, adj2), "Example 2 failed");

    // Example 3: Bipartite graph
    int V3 = 5;
    List<List<Integer>> adj3 = new ArrayList<>();
    for (int i = 0; i < V3; i++) {
      adj3.add(new ArrayList<>());
    }
    adj3.get(0).add(1);
    adj3.get(0).add(3);
    adj3.get(1).add(0);
    adj3.get(1).add(2);
    adj3.get(2).add(1);
    adj3.get(2).add(4);
    adj3.get(3).add(0);
    adj3.get(3).add(4);
    adj3.get(4).add(2);
    adj3.get(4).add(3);
    assertFalse(sol.isBipartite(V3, adj3), "Example 3 failed");

    // Edge case: Single vertex (trivially bipartite)
    int V4 = 1;
    List<List<Integer>> adj4 = new ArrayList<>();
    adj4.add(new ArrayList<>());
    assertTrue(sol.isBipartite(V4, adj4), "Single vertex failed");

    // Edge case: Two connected vertices (bipartite)
    int V5 = 2;
    List<List<Integer>> adj5 = new ArrayList<>();
    for (int i = 0; i < V5; i++) {
      adj5.add(new ArrayList<>());
    }
    adj5.get(0).add(1);
    adj5.get(1).add(0);
    assertTrue(sol.isBipartite(V5, adj5), "Two connected vertices failed");

    // Edge case: Triangle (3-cycle, not bipartite)
    int V6 = 3;
    List<List<Integer>> adj6 = new ArrayList<>();
    for (int i = 0; i < V6; i++) {
      adj6.add(new ArrayList<>());
    }
    adj6.get(0).addAll(Arrays.asList(1, 2));
    adj6.get(1).addAll(Arrays.asList(0, 2));
    adj6.get(2).addAll(Arrays.asList(0, 1));
    assertFalse(sol.isBipartite(V6, adj6), "Triangle failed");

    // Edge case: Disconnected graph (one component bipartite, one not)
    int V7 = 5;
    List<List<Integer>> adj7 = new ArrayList<>();
    for (int i = 0; i < V7; i++) {
      adj7.add(new ArrayList<>());
    }
    // Component 1: 0-1 (bipartite)
    adj7.get(0).add(1);
    adj7.get(1).add(0);
    // Component 2: 2-3-4 cycle (odd cycle, not bipartite)
    adj7.get(2).addAll(Arrays.asList(3, 4));
    adj7.get(3).addAll(Arrays.asList(2, 4));
    adj7.get(4).addAll(Arrays.asList(2, 3));
    assertFalse(sol.isBipartite(V7, adj7), "Disconnected graph failed");

    // Edge case: Complete bipartite graph K3,3
    int V8 = 6;
    List<List<Integer>> adj8 = new ArrayList<>();
    for (int i = 0; i < V8; i++) {
      adj8.add(new ArrayList<>());
    }
    // Partition {0,1,2} and {3,4,5}
    for (int u = 0; u < 3; u++) {
      for (int v = 3; v < 6; v++) {
        adj8.get(u).add(v);
        adj8.get(v).add(u);
      }
    }
    assertTrue(sol.isBipartite(V8, adj8), "Complete bipartite K3,3 failed");
  }
}

class Solution {

  public boolean isBipartite(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    int[] colour = new int[V];
    Arrays.fill(colour, -1);

    for (int i = 0; i < V; i++) { // to check all components of the graph
      if (!visited[i]) {
        boolean result = bfs(i, adj, visited, colour);
        if (!result) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean bfs(int n, List<List<Integer>> adj, boolean[] visited, int[] colour) {
    Queue<Integer> q = new LinkedList<>();
    q.add(n);
    visited[n] = true;

    int level = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      int levelColour = level % 2;

      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        colour[node] = levelColour;

        List<Integer> adjNodes = adj.get(node);
        for (Integer adjNode : adjNodes) {
          if (!visited[adjNode]) {
            visited[adjNode] = true;
            q.add(adjNode);
          } else if (colour[adjNode] == levelColour) {
            // verify that a processed adjacent node is having same colour as 'node'
            /*
            1) In queue, not processed
            2) Off queue, processed
            So if Off queue (processed) and colour is same as previous level, then false
             */
            return false;
          }
        }
      }

      level++;
    }

    return true;
  }
}

class Solution2 {

  public boolean isBipartite(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    int[] colour = new int[V];
    Arrays.fill(colour, -1);

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        boolean result = dfs(i, adj, visited, colour);
        if (!result) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean dfs(int i, List<List<Integer>> adj, boolean[] visited, int[] colour) {
    Stack<Integer> stack = new Stack<>();
    stack.push(i);
    visited[i] = true;
    colour[i] = 0; // starting with 0

    while (!stack.isEmpty()) {
      Integer node = stack.pop();

      List<Integer> adjNodes = adj.get(node);
      for (Integer adjNode : adjNodes) {
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          // process while visiting itself, to know state of parent colour
          colour[adjNode] = 1 - colour[node];
          stack.push(adjNode);
        } else if (colour[adjNode] == colour[node]) {
          // verify that a visited adjacent node is having same colour as 'node'
          return false;
        }
      }
    }
    return true;
  }
}

class Solution3 {

  public boolean isBipartite(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    int[] colour = new int[V];
    Arrays.fill(colour, -1);

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        boolean result = dfs(i, adj, visited, colour);
        if (!result) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean dfs(int i, List<List<Integer>> adj, boolean[] visited, int[] colour) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{i, 0}); // visit
    // start with 0 as parent node's colour, so first level will have colour as 1 always
    visited[i] = true;

    while (!stack.isEmpty()) {
      int[] nodeData = stack.pop();
      int node = nodeData[0];
      int nodeParentColour = nodeData[1];
      colour[node] = 1 - nodeParentColour; // process

      List<Integer> adjNodes = adj.get(node);
      for (Integer adjNode : adjNodes) {
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          stack.push(new int[]{adjNode, colour[node]});
        } else if (colour[adjNode] == colour[node]) {
          // verify that a processed adjacent node is having same colour as 'node'
          /*
          Two possibilities
          1) In stack and not processed
          2) Off stack and processed
          So if Off stack (processed) and colour is same as parent, then stop
           */
          return false;
        }
      }
    }
    return true;
  }
}
