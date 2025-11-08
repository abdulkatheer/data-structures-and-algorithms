package io.abdul.graphs.additional_algorithms.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    ArrayList<ArrayList<Integer>> adj1 = new ArrayList<>();
    Collections.addAll(adj1,
        new ArrayList<>(List.of(2, 3)),
        new ArrayList<>(List.of(0)),
        new ArrayList<>(List.of(1)),
        new ArrayList<>(List.of(4)),
        new ArrayList<>() // vertex 4 has no outgoing edges
    );
    int expected1 = 3;
    assertEquals(expected1, sol.kosaraju(5, adj1),
        "Example 1: 3 strongly connected components {0,1,2}, {3}, {4}");

    // --- Test 2: Example 2 ---
    ArrayList<ArrayList<Integer>> adj2 = new ArrayList<>();
    Collections.addAll(adj2,
        new ArrayList<>(List.of(1)),       // 0 -> 1
        new ArrayList<>(List.of(2)),       // 1 -> 2
        new ArrayList<>(List.of(0, 3)),    // 2 -> 0, 3
        new ArrayList<>(List.of(4)),       // 3 -> 4
        new ArrayList<>(List.of(5, 7)),    // 4 -> 5,7
        new ArrayList<>(List.of(6)),       // 5 -> 6
        new ArrayList<>(List.of(4, 7)),    // 6 -> 4,7
        new ArrayList<>()                  // 7 -> none
    );
    int expected2 = 4;
    assertEquals(expected2, sol.kosaraju(8, adj2),
        "Example 2: 4 SCCs {0,1,2}, {3}, {4,5,6}, {7}");

    // --- Test 3: Example 3 ---
    ArrayList<ArrayList<Integer>> adj3 = new ArrayList<>();
    Collections.addAll(adj3,
        new ArrayList<>(List.of(1)),   // 0 -> 1
        new ArrayList<>(List.of(2)),   // 1 -> 2
        new ArrayList<>()              // 2 -> none
    );
    int expected3 = 3;
    assertEquals(expected3, sol.kosaraju(3, adj3),
        "Example 3: Each node is disconnected chain; 3 SCCs");

    // --- Test 4: Fully connected graph ---
    ArrayList<ArrayList<Integer>> adj4 = new ArrayList<>();
    int V4 = 4;
    for (int i = 0; i < V4; i++) {
      ArrayList<Integer> list = new ArrayList<>();
      for (int j = 0; j < V4; j++) {
        if (i != j) list.add(j);
      }
      adj4.add(list);
    }
    int expected4 = 1;
    assertEquals(expected4, sol.kosaraju(V4, adj4),
        "Fully connected graph is one SCC");

    // --- Test 5: No edges ---
    ArrayList<ArrayList<Integer>> adj5 = new ArrayList<>();
    for (int i = 0; i < 5; i++) adj5.add(new ArrayList<>());
    int expected5 = 5;
    assertEquals(expected5, sol.kosaraju(5, adj5),
        "Graph with no edges has all isolated nodes; 5 SCCs");

    // --- Test 6: Simple cycle ---
    ArrayList<ArrayList<Integer>> adj6 = new ArrayList<>();
    Collections.addAll(adj6,
        new ArrayList<>(List.of(1)),
        new ArrayList<>(List.of(2)),
        new ArrayList<>(List.of(0))
    );
    int expected6 = 1;
    assertEquals(expected6, sol.kosaraju(3, adj6),
        "Single cycle 0->1->2->0 forms one SCC");

    // --- Test 7: Disconnected components with cycles ---
    ArrayList<ArrayList<Integer>> adj7 = new ArrayList<>();
    Collections.addAll(adj7,
        new ArrayList<>(List.of(1)),     // component 1: 0 <-> 1
        new ArrayList<>(List.of(0)),
        new ArrayList<>(List.of(3, 4)),  // component 2: 2->3->4->2
        new ArrayList<>(List.of(4)),
        new ArrayList<>(List.of(2))
    );
    int expected7 = 2;
    assertEquals(expected7, sol.kosaraju(5, adj7),
        "Two SCCs: {0,1} and {2,3,4}");

    // --- Test 8: Long chain ending in cycle ---
    ArrayList<ArrayList<Integer>> adj8 = new ArrayList<>();
    Collections.addAll(adj8,
        new ArrayList<>(List.of(1)), // 0->1
        new ArrayList<>(List.of(2)), // 1->2
        new ArrayList<>(List.of(3)), // 2->3
        new ArrayList<>(List.of(4)), // 3->4
        new ArrayList<>(List.of(2))  // 4->2 forms cycle among 2,3,4
    );
    int expected8 = 3;
    assertEquals(expected8, sol.kosaraju(5, adj8),
        "SCCs: {0}, {1}, {2,3,4}");

    // --- Test 9: Single vertex ---
    ArrayList<ArrayList<Integer>> adj9 = new ArrayList<>();
    adj9.add(new ArrayList<>());
    int expected9 = 1;
    assertEquals(expected9, sol.kosaraju(1, adj9),
        "Single node is its own SCC");

    // --- Test 10: Multiple isolated cycles ---
    ArrayList<ArrayList<Integer>> adj10 = new ArrayList<>();
    Collections.addAll(adj10,
        new ArrayList<>(List.of(1)),
        new ArrayList<>(List.of(0)),  // cycle {0,1}
        new ArrayList<>(List.of(3)),
        new ArrayList<>(List.of(2)),  // cycle {2,3}
        new ArrayList<>(List.of(5)),
        new ArrayList<>(List.of(4))   // cycle {4,5}
    );
    int expected10 = 3;
    assertEquals(expected10, sol.kosaraju(6, adj10),
        "Three independent cycles → 3 SCCs");
  }
}

class Solution {

  public int kosaraju(int v, ArrayList<ArrayList<Integer>> adjList) {
    Stack<Integer> dfs = dfsTopo(v, adjList);
    ArrayList<ArrayList<Integer>> transposedAdjList = transposeGraph(v, adjList);
    List<List<Integer>> sccs = dfs(v, dfs, transposedAdjList);
    return sccs.size();
  }

  // Step 1: DFS (Topo-sort model)
  private Stack<Integer> dfsTopo(int v, ArrayList<ArrayList<Integer>> adjList) {
    boolean[] visited = new boolean[v];
    Stack<Integer> result = new Stack<>();

    for (int i = 0; i < v; i++) {
      if (!visited[i]) {
        dfsTopo(i, adjList, visited, result);
      }
    }

    return result;
  }

  private static void dfsTopo(int startNode, ArrayList<ArrayList<Integer>> adjList, boolean[] visited,
      Stack<Integer> result) {
    Stack<int[]> dfsStack = new Stack<>();

    dfsStack.push(new int[]{startNode, 0});
    visited[0] = true;

    while (!dfsStack.isEmpty()) {
      int[] peeked = dfsStack.peek();

      int lastProcessedAdjPos = peeked[1];
      int node = peeked[0];
      if (lastProcessedAdjPos < adjList.get(node).size()) {
        int adjNode = adjList.get(node).get(lastProcessedAdjPos);
        if (!visited[adjNode]) {
          dfsStack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
        }
        peeked[1]++;
      } else {
        // all descendants are processed
        dfsStack.pop();
        result.push(node);
      }
    }
  }

  // Step 2: Transpose Graph, reverse all the edges
  private ArrayList<ArrayList<Integer>> transposeGraph(int v,
      ArrayList<ArrayList<Integer>> adjList) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<>(v);
    for (int i = 0; i < v; i++) {
      result.add(new ArrayList<>());
    }

    for (int node = 0; node < adjList.size(); node++) {
      ArrayList<Integer> adjNodes = adjList.get(node);
      for (Integer adjNode : adjNodes) {
        result.get(adjNode).add(node);
      }
    }

    return result;
  }

  // Step 3: DFS (normal)
  private List<List<Integer>> dfs(int v, Stack<Integer> dfs,
      ArrayList<ArrayList<Integer>> transposedAdjList) {
    boolean[] visited = new boolean[v];
    List<List<Integer>> result = new ArrayList<>();

    while (!dfs.isEmpty()) {
      Integer node = dfs.pop();
      if (!visited[node]) {
        List<Integer> nodes = dfs(node, transposedAdjList, visited);
        result.add(nodes);
      }
    }
    return result;
  }

  private List<Integer> dfs(int startNode, ArrayList<ArrayList<Integer>> adjList, boolean[] visited) {
    List<Integer> nodes = new ArrayList<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(startNode);
    visited[startNode] = true;

    while (!stack.isEmpty()) {
      Integer node = stack.pop();
      nodes.add(node);
      for (Integer adjNode : adjList.get(node)) {
        if (!visited[adjNode]) {
          stack.push(adjNode);
          visited[adjNode] = true;
        }
      }
    }

    return nodes;
  }
}


