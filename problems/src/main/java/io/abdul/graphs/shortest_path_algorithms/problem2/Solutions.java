package io.abdul.graphs.shortest_path_algorithms.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // --- Test 1: Basic 5-node example ---
    int[][] edges1 = {
        {1, 2, 2}, {2, 5, 5}, {2, 3, 4},
        {1, 4, 1}, {4, 3, 3}, {3, 5, 1}
    };
    List<Integer> result1 = sol.shortestPath(5, 6, edges1);
    assertEquals(Arrays.asList(5, 1, 4, 3, 5), result1);

    // --- Test 2: Direct path to destination ---
    int[][] edges2 = {
        {1, 2, 2}, {2, 3, 4}, {1, 4, 1}, {4, 3, 3}
    };
    List<Integer> result2 = sol.shortestPath(4, 4, edges2);
    assertEquals(Arrays.asList(1, 1, 4), result2);

    // --- Test 3: No path to destination (disconnected) ---
    int[][] edges3 = {
        {1, 2, 2}
    };
    List<Integer> result3 = sol.shortestPath(3, 1, edges3);
    assertEquals(Arrays.asList(-1), result3);

    // --- Test 4: Multiple paths, choose min-weight ---
    int[][] edges4 = {
        {1, 2, 2}, {2, 4, 2}, {1, 3, 3}, {3, 4, 1}, {2, 3, 2}
    };
    List<Integer> result4 = sol.shortestPath(4, 5, edges4);
    // Possible paths: 1-2-4 (4) and 1-3-4 (4), both equal -> 1 2 4 valid
    assertTrue(
        result4.equals(Arrays.asList(4, 1, 2, 4)) ||
            result4.equals(Arrays.asList(4, 1, 3, 4))
    );

    // --- Test 5: Only one node (source = destination) ---
    int[][] edges5 = {};
    List<Integer> result5 = sol.shortestPath(1, 0, edges5);
    assertEquals(Arrays.asList(0, 1), result5);
  }
}

class Solution {
  public List<Integer> shortestPath(int n, int m, int[][] edges) {
    List<List<int[]>> adjList = toAdjacencyList(n, edges);
    int max = (int) 1e10;

    int[] shortestPath = new int[n+1];
    Arrays.fill(shortestPath, max);
    PriorityQueue<Node> q = new PriorityQueue<>();
    q.add(new Node(1, 0, List.of(1)));
    shortestPath[1] = 0;

    while (!q.isEmpty()) {
      Node node = q.poll();
      int nodeId = node.id;
      int nodeCostFromSource = node.costFromSource;
      List<Integer> nodePath = node.path;

      if (nodeId == n) { // reached target
        List<Integer> result = new ArrayList<>();
        result.add(nodeCostFromSource);
        result.addAll(nodePath);
        return result;
      }

      if (shortestPath[nodeId] < nodeCostFromSource) {
        continue;
      }

      List<int[]> adjNodes = adjList.get(nodeId);

      for (int[] adjNode : adjNodes) {
        int adjId = adjNode[0];
        int costFromN = adjNode[1];

        int newCost = nodeCostFromSource + costFromN;
        if (newCost < shortestPath[adjId]) {
          List<Integer> newPath = new ArrayList<>(nodePath);
          newPath.add(adjId);
          q.add(new Node(adjId, newCost, newPath));
          shortestPath[adjId] = newCost;
        }
      }
    }

    return List.of(-1);
  }

  private List<List<int[]>> toAdjacencyList(int n, int[][] edgeList) {
    List<List<int[]>> adjList = new ArrayList<>(n+1);
    for (int i = 0; i < n+1; i++) {
      adjList.add(new ArrayList<>());
    }

    for (int[] edge : edgeList) {
      int src = edge[0];
      int dest = edge[1];
      int cost = edge[2];

      adjList.get(src).add(new int[]{dest, cost});
      adjList.get(dest).add(new int[]{src, cost});
    }

    return adjList;
  }

  private record Node(int id, int costFromSource, List<Integer> path) implements Comparable<Node> {
    @Override
    public int compareTo(Node o) {
      if (costFromSource != o.costFromSource) {
        return Integer.compare(costFromSource, o.costFromSource);
      }
      return Integer.compare(id, o.id);
    }
  }
}

/*
Optimal

Same as above, but instead of keeping all paths in memory, we update the parent of nodes as soon as we find the best path.
This is possible as this algo is Greedy and we always keep optimal solution in result.
 */
class Solution2 {

  public List<Integer> shortestPath(int n, int m, int[][] edges) {
    List<List<int[]>> adjList = toAdjacencyList(n, edges);
    int max = (int) 1e10;

    int[] shortestPath = new int[n + 1];
    Arrays.fill(shortestPath, max);
    int[] parent = new int[n+1];
    for (int i = 1; i < n + 1; i++) {
      parent[i] = i;
    }

    PriorityQueue<Node> q = new PriorityQueue<>();
    q.add(new Node(1, 0));
    shortestPath[1] = 0;

    while (!q.isEmpty()) {
      Node node = q.poll();
      int nodeId = node.id;
      int nodeCostFromSource = node.costFromSource;

      if (nodeId == n) { // target found
        List<Integer> result = new ArrayList<>();
        List<Integer> path = findPathFromOneToN(n, parent);
        result.add(nodeCostFromSource);
        result.addAll(path);
        return result;
      }

      if (shortestPath[nodeId] < nodeCostFromSource) {
        continue;
      }

      List<int[]> adjNodes = adjList.get(nodeId);
      for (int[] adjNode : adjNodes) {
        int adjNodeId = adjNode[0];
        int edgeCost = adjNode[1];

        int newCost = nodeCostFromSource + edgeCost;
        if (newCost < shortestPath[adjNodeId]) {
          q.add(new Node(adjNodeId, newCost));
          shortestPath[adjNodeId] = newCost;
          parent[adjNodeId] = nodeId; // adjNode can be reached from node at overall cost of newCost
        }
      }
    }

    return List.of(-1);
  }

  private static List<Integer> findPathFromOneToN(int n, int[] parent) {
    List<Integer> path = new ArrayList<>();
    int k = n;
    while (parent[k] != k) {
      path.add(k);
      k = parent[k];
    }
    path.add(1);
    Collections.reverse(path);
    return path;
  }

  private List<List<int[]>> toAdjacencyList(int n, int[][] edgeList) {
    List<List<int[]>> adjList = new ArrayList<>(n+1);
    for (int i = 0; i < n+1; i++) {
      adjList.add(new ArrayList<>());
    }

    for (int[] edge : edgeList) {
      int src = edge[0];
      int dest = edge[1];
      int cost = edge[2];

      adjList.get(src).add(new int[]{dest, cost});
      adjList.get(dest).add(new int[]{src, cost});
    }

    return adjList;
  }

  private record Node(int id, int costFromSource) implements Comparable<Node> {
    @Override
    public int compareTo(Node o) {
      if (costFromSource != o.costFromSource) {
        return Integer.compare(costFromSource, o.costFromSource);
      }
      return Integer.compare(id, o.id);
    }
  }
}