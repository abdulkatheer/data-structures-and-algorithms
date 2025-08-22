package io.abdul.graphs.cycles.problem1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1: Graph with a cycle
    int V1 = 6;
    List<Integer>[] adj1 = new List[V1];
    for (int i = 0; i < V1; i++) {
      adj1[i] = new ArrayList<>();
    }
    adj1[0].addAll(Arrays.asList(1, 3));
    adj1[1].addAll(Arrays.asList(0, 2, 4));
    adj1[2].addAll(Arrays.asList(1, 5));
    adj1[3].addAll(Arrays.asList(0, 4));
    adj1[4].addAll(Arrays.asList(1, 3, 5));
    adj1[5].addAll(Arrays.asList(2, 4));
    assertTrue(sol.isCycle(V1, adj1), "Example 1 failed");

    // Example 2: Graph without a cycle
    int V2 = 4;
    List<Integer>[] adj2 = new List[V2];
    for (int i = 0; i < V2; i++) {
      adj2[i] = new ArrayList<>();
    }
    adj2[0].addAll(Arrays.asList(1, 2));
    adj2[1].addAll(Arrays.asList(0));
    adj2[2].addAll(Arrays.asList(0, 3));
    adj2[3].addAll(Arrays.asList(2));
    assertFalse(sol.isCycle(V2, adj2), "Example 2 failed");

    // Example 3: Graph with a cycle (triangle between 0-1-2)
    int V3 = 4;
    List<Integer>[] adj3 = new List[V3];
    for (int i = 0; i < V3; i++) {
      adj3[i] = new ArrayList<>();
    }
    adj3[0].addAll(Arrays.asList(1, 2));
    adj3[1].addAll(Arrays.asList(0, 2));
    adj3[2].addAll(Arrays.asList(0, 1, 3));
    adj3[3].addAll(Arrays.asList(2));
    assertTrue(sol.isCycle(V3, adj3), "Example 3 failed");

    // Edge case: Single node, no edges
    int V4 = 1;
    List<Integer>[] adj4 = new List[V4];
    for (int i = 0; i < V4; i++) {
      adj4[i] = new ArrayList<>();
    }
    assertFalse(sol.isCycle(V4, adj4), "Single node no edges failed");

    // Edge case: Two nodes, single edge
    int V5 = 2;
    List<Integer>[] adj5 = new List[V5];
    for (int i = 0; i < V5; i++) {
      adj5[i] = new ArrayList<>();
    }
    adj5[0].add(1);
    adj5[1].add(0);
    assertFalse(sol.isCycle(V5, adj5), "Two nodes single edge failed");

    // Edge case: Two nodes, parallel edges (cycle)
    int V6 = 2;
    List<Integer>[] adj6 = new List[V6];
    for (int i = 0; i < V6; i++) {
      adj6[i] = new ArrayList<>();
    }
    adj6[0].addAll(Arrays.asList(1, 1));
    adj6[1].addAll(Arrays.asList(0, 0));
    assertTrue(sol.isCycle(V6, adj6), "Two nodes parallel edges failed");

    // Edge case: Disconnected graph (one component cyclic, one acyclic)
    int V7 = 5;
    List<Integer>[] adj7 = new List[V7];
    for (int i = 0; i < V7; i++) {
      adj7[i] = new ArrayList<>();
    }
    adj7[0].addAll(Arrays.asList(1, 2));
    adj7[1].addAll(Arrays.asList(0, 2));
    adj7[2].addAll(Arrays.asList(0, 1)); // component 0-1-2 forms cycle
    adj7[3].add(4);
    adj7[4].add(3); // component 3-4 no cycle
    assertTrue(sol.isCycle(V7, adj7), "Disconnected graph failed");
  }
}

/*
In BFS, there are only two cases where we see an already visited node
1) the parent, which is where we came from
2) not the parent, more than one node in the same level having link to the node, which is forming a cycle
 */
class Solution {

  public boolean isCycle(int V, List<Integer>[] adj) {
    boolean[] visited = new boolean[V];

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        if (bfs(i, adj, visited)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean bfs(int i, List<Integer>[] adj, boolean[] visited) {
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{-1, i});
    visited[i] = true;

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        int[] pair = queue.poll();
        int parent = pair[0];
        int node = pair[1];

        List<Integer> adjNodes = adj[node];

        for (Integer adjNode : adjNodes) {
          if (!visited[adjNode]) {
            visited[adjNode] = true;
            queue.add(new int[]{node, adjNode});
          } else if (adjNode != parent) {
            // if adjNode not the parent of current node, it has a connection to some other node at same level
            return true;
          }
        }
      }
    }

    return false;
  }
}

/*
In DFS, there are only two cases when we see an already visited node
1) the parent, from where we came from
2) not the parent, we end up visiting the visited in a different path forming a cycle
 */
class Solution2 {

  public boolean isCycle(int V, List<Integer>[] adj) {
    boolean[] visited = new boolean[V];

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        if (dfs(i, adj, visited)) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean dfs(int startNode, List<Integer>[] adj, boolean[] visited) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{-1, startNode});
    visited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] pair = stack.pop();
      int parent = pair[0];
      int node = pair[1];

      List<Integer> adjNodes = adj[node];

      for (Integer adjNode : adjNodes) {
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          stack.push(new int[]{node, adjNode});
        } else if (adjNode != parent) {
          return true;
        }
      }
    }

    return false;
  }
}