package io.abdul.graphs.cycles.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    testTopoSortExamples();
    testTopoSortExamples();
  }

  static private boolean isValidTopoOrder(int V, List<List<Integer>> adj, int[] order) {
    if (order.length != V) {
      return false;
    }

    int[] pos = new int[V];
    for (int i = 0; i < V; i++) {
      pos[order[i]] = i;
    }

    // Check all edges u -> v satisfy pos[u] < pos[v]
    for (int u = 0; u < V; u++) {
      for (int v : adj.get(u)) {
        if (pos[u] >= pos[v]) {
          return false;
        }
      }
    }
    return true;
  }

  static void testTopoSortExamples() {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    int V1 = 6;
    List<List<Integer>> adj1 = new ArrayList<>();
    for (int i = 0; i < V1; i++) {
      adj1.add(new ArrayList<>());
    }
    adj1.get(2).add(3);
    adj1.get(3).add(1);
    adj1.get(4).add(0);
    adj1.get(4).add(1);
    adj1.get(5).add(0);
    adj1.get(5).add(2);

    int[] result1 = sol.topoSort(V1, adj1);
    assertTrue(isValidTopoOrder(V1, adj1, result1), "Example 1 failed");

    // Example 2
    int V2 = 4;
    List<List<Integer>> adj2 = new ArrayList<>();
    for (int i = 0; i < V2; i++) {
      adj2.add(new ArrayList<>());
    }
    adj2.get(1).add(0);
    adj2.get(2).add(0);
    adj2.get(3).add(0);

    int[] result2 = sol.topoSort(V2, adj2);
    assertTrue(isValidTopoOrder(V2, adj2, result2), "Example 2 failed");

    // Example 3
    int V3 = 3;
    List<List<Integer>> adj3 = new ArrayList<>();
    for (int i = 0; i < V3; i++) {
      adj3.add(new ArrayList<>());
    }
    adj3.get(0).add(1);
    adj3.get(1).add(2);

    int[] result3 = sol.topoSort(V3, adj3);
    assertTrue(isValidTopoOrder(V3, adj3, result3), "Example 3 failed");
  }

  static void testTopoSortEdgeCases() {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Edge Case 1: Single node
    int V1 = 1;
    List<List<Integer>> adj1 = new ArrayList<>();
    adj1.add(new ArrayList<>());
    int[] result1 = sol.topoSort(V1, adj1);
    assertArrayEquals(new int[]{0}, result1);

    // Edge Case 2: Two independent nodes (multiple valid answers)
    int V2 = 2;
    List<List<Integer>> adj2 = new ArrayList<>();
    adj2.add(new ArrayList<>());
    adj2.add(new ArrayList<>());
    int[] result2 = sol.topoSort(V2, adj2);
    assertTrue(isValidTopoOrder(V2, adj2, result2));

    // Edge Case 3: Linear chain 0->1->2->3
    int V3 = 4;
    List<List<Integer>> adj3 = new ArrayList<>();
    for (int i = 0; i < V3; i++) {
      adj3.add(new ArrayList<>());
    }
    adj3.get(0).add(1);
    adj3.get(1).add(2);
    adj3.get(2).add(3);
    int[] result3 = sol.topoSort(V3, adj3);
    assertArrayEquals(new int[]{0, 1, 2, 3}, result3);

    // Edge Case 4: Star DAG (all edges from 0 to others)
    int V4 = 5;
    List<List<Integer>> adj4 = new ArrayList<>();
    for (int i = 0; i < V4; i++) {
      adj4.add(new ArrayList<>());
    }
    for (int i = 1; i < V4; i++) {
      adj4.get(0).add(i);
    }
    int[] result4 = sol.topoSort(V4, adj4);
    assertTrue(isValidTopoOrder(V4, adj4, result4));
  }
}

/*
DFS

Postorder traversal
Add node to stack
Add its next adjacent node
Once all adjacent nodes are added, now we can pop and process the node
Once popped, repeat for other adjacent nodes

 */
class Solution {

  public int[] topoSort(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    List<Integer> topo = new ArrayList<>(V);

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        dfs(i, adj, visited, topo);
      }
    }

    Collections.reverse(topo);
    return topo.stream().mapToInt(value -> value).toArray();
  }

  private void dfs(int startNode, List<List<Integer>> adj, boolean[] visited, List<Integer> topo) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0}); // node, nextAdjacentNodePos
    visited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int nextAdjacentNodePos = peeked[1];

      if (nextAdjacentNodePos < adj.get(node).size()) { // exists?
        Integer adjNode = adj.get(node).get(nextAdjacentNodePos);
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
        }
        peeked[1]++; // update nextAdjacentNodePos
      } else {
        // all adjacent nodes of current 'node' are processed, so we can process current 'node' now
        stack.pop();
        topo.add(node);
      }
    }
  }
}

/*
BFS

Kahn's Algorithm
We maintain in-degree. A DAG will have at least 1 node with in-degree is zero.
We add all such nodes to queue to start with.
Then find their adjacent nodes and reduce in-degree for them.
If in-degree becomes zero, add to queue, if not skip and move on.
 */
class Solution2 {

  public int[] topoSort(int V, List<List<Integer>> adj) {
    int[] inDegree = new int[V];
    boolean[] visited = new boolean[V];
    List<Integer> topo = new ArrayList<>();

    // find in-degree
    for (List<Integer> adjNodes : adj) {
      for (Integer adjNode : adjNodes) {
        inDegree[adjNode]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < inDegree.length; i++) {
      if (inDegree[i] == 0) {
        q.add(i); // visit
        visited[i] = true;
      }
    }

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) { // queue has nodes in topo-order. So process them and then visit all its adjacent nodes.
        Integer node = q.poll();
        topo.add(node); // process

        List<Integer> adjNodes = adj.get(node);
        for (Integer adjNode : adjNodes) {
          if (!visited[adjNode]) {
            inDegree[adjNode]--;
            if (inDegree[adjNode] == 0) {
              // if zero, then all inbounds are already in queue and processed.
              // The last inbound is just processed!
              visited[adjNode] = true;
              q.add(adjNode); // visit
            }
          }
        }
      }
    }

    return topo.stream().mapToInt(v -> v).toArray();
  }
}