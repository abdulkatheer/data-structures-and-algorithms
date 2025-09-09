package io.abdul.graphs.cycles.problem4;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1: Cycle exists (1 -> 2 -> 3 -> 4 -> 1)
    int V1 = 6;
    List<List<Integer>> adj1 = new ArrayList<>();
    for (int i = 0; i < V1; i++) {
      adj1.add(new ArrayList<>());
    }
    adj1.get(0).add(1);
    adj1.get(1).add(2);
    adj1.get(1).add(5);
    adj1.get(2).add(3);
    adj1.get(3).add(4);
    adj1.get(4).add(1);
    boolean result1 = sol.isCyclic(V1, adj1);
    assertTrue(result1, "Example 1 should detect a cycle");

    // Example 2: No cycle
    int V2 = 4;
    List<List<Integer>> adj2 = new ArrayList<>();
    for (int i = 0; i < V2; i++) {
      adj2.add(new ArrayList<>());
    }
    adj2.get(0).add(1);
    adj2.get(0).add(2);
    adj2.get(1).add(2);
    adj2.get(3).add(0);
    adj2.get(3).add(2);
    boolean result2 = sol.isCyclic(V2, adj2);
    assertFalse(result2, "Example 2 should NOT detect a cycle");

    // Example 3: Simple cycle (0 -> 1 -> 2 -> 0)
    int V3 = 3;
    List<List<Integer>> adj3 = new ArrayList<>();
    for (int i = 0; i < V3; i++) {
      adj3.add(new ArrayList<>());
    }
    adj3.get(0).add(1);
    adj3.get(1).add(2);
    adj3.get(2).add(0);
    boolean result3 = sol.isCyclic(V3, adj3);
    assertTrue(result3, "Example 3 should detect a cycle");

    // Edge Case 1: Single node with no edges → No cycle
    int V4 = 1;
    List<List<Integer>> adj4 = new ArrayList<>();
    adj4.add(new ArrayList<>());
    boolean result4 = sol.isCyclic(V4, adj4);
    assertFalse(result4, "Single node with no edges should not form a cycle");

    // Edge Case 2: Self-loop (0 -> 0) → Cycle exists
    int V5 = 1;
    List<List<Integer>> adj5 = new ArrayList<>();
    adj5.add(new ArrayList<>());
    adj5.get(0).add(0);
    boolean result5 = sol.isCyclic(V5, adj5);
    assertTrue(result5, "Self-loop should be detected as a cycle");

    // Edge Case 3: Disconnected DAG (no cycles)
    int V6 = 5;
    List<List<Integer>> adj6 = new ArrayList<>();
    for (int i = 0; i < V6; i++) {
      adj6.add(new ArrayList<>());
    }
    adj6.get(0).add(1);
    adj6.get(2).add(3);
    boolean result6 = sol.isCyclic(V6, adj6);
    assertFalse(result6, "Disconnected graph without cycles should return false");

    // Edge Case 4: Chain with back-edge (0 -> 1 -> 2 -> 1) → Cycle exists
    int V7 = 3;
    List<List<Integer>> adj7 = new ArrayList<>();
    for (int i = 0; i < V7; i++) {
      adj7.add(new ArrayList<>());
    }
    adj7.get(0).add(1);
    adj7.get(1).add(2);
    adj7.get(2).add(1);
    boolean result7 = sol.isCyclic(V7, adj7);
    assertTrue(result7, "Back-edge should create a cycle");
  }
}

/*
DFS

visited solution which worked for undirected graph will not work for directed. Bcz for directed, visited should be on the same path and not just any path.
So we'll maintain special visited array which tells whether it's visited in the same path.

Similar to toposort. Process all adjacent nodes to the depth and then visit itself.
 */
class Solution {

  public boolean isCyclic(int N, List<List<Integer>> adj) {
    boolean[] visited = new boolean[N];
    boolean[] pathVisited = new boolean[N];
    for (int i = 0; i < N; i++) {
      if (!visited[i]) {
        boolean cycle = dfs(i, adj, visited, pathVisited);
        if (cycle) {
          return true;
        }
      }
    }

    return false;
  }

  private boolean dfs(int V, List<List<Integer>> adj, boolean[] visited, boolean[] pathVisited) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{V, 0});
    visited[V] = true;
    pathVisited[V] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjacentVisitedCount = peeked[1];

      if (adjacentVisitedCount < adj.get(node).size()) { // adj nodes
        Integer adjNode = adj.get(node).get(adjacentVisitedCount);
        if (!visited[adjNode]) {
          visited[adjNode] = true;
          pathVisited[adjNode] = true;
          stack.push(new int[]{adjNode, 0});
        } else if (pathVisited[adjNode]) {
          return true; // visited as well as pathVisited, so a cycle!
        }
        peeked[1]++;
      } else { // all adj nodes of 'node' are visited, so we can remove this from path
        stack.pop();
        pathVisited[node] = false;
      }
    }

    return false;
  }
}

/*
BFS

Kahn's Algorithm
 */
class Solution2 {

  public boolean isCyclic(int N, List<List<Integer>> adj) {
    boolean[] visited = new boolean[N];
    int[] inDegree = new int[N];

    for (List<Integer> adjNodes : adj) {
      for (Integer adjNode : adjNodes) {
        inDegree[adjNode]++;
      }
    }

    // start with nodes having 0 in-degree
    Queue<Integer> q = new LinkedList<>();
    List<Integer> topoOrder = new ArrayList<>();
    for (int i = 0; i < inDegree.length; i++) {
      if (inDegree[i] == 0) {
        q.add(i);
        visited[i] = true;
        topoOrder.add(i);
      }
    }

    while (!q.isEmpty()) { // if there's a cycle q will be empty before visiting all nodes
      int size = q.size();

      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        List<Integer> adjNodes = adj.get(node);
        for (Integer adjNode : adjNodes) {
          inDegree[adjNode]--;
          if (inDegree[adjNode] == 0) {
            q.add(adjNode);
            visited[adjNode] = true;
            topoOrder.add(adjNode);
          }
        }
      }
    }

    return topoOrder.size() != N;
  }
}