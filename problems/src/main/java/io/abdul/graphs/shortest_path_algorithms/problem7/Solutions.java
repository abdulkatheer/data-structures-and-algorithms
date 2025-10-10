package io.abdul.graphs.shortest_path_algorithms.problem7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    List<List<Integer>> roads1 = Arrays.asList(
        Arrays.asList(0, 6, 7),
        Arrays.asList(0, 1, 2),
        Arrays.asList(1, 2, 3),
        Arrays.asList(1, 3, 3),
        Arrays.asList(6, 3, 3),
        Arrays.asList(3, 5, 1),
        Arrays.asList(6, 5, 1),
        Arrays.asList(2, 5, 1),
        Arrays.asList(0, 4, 5),
        Arrays.asList(4, 6, 2)
    );
    assertEquals(4, sol.countPaths(7, roads1));

    // --- Test 2: Example 2 ---
    List<List<Integer>> roads2 = Arrays.asList(
        Arrays.asList(0, 5, 8),
        Arrays.asList(0, 2, 2),
        Arrays.asList(0, 1, 1),
        Arrays.asList(1, 3, 3),
        Arrays.asList(1, 2, 3),
        Arrays.asList(2, 5, 6),
        Arrays.asList(3, 4, 2),
        Arrays.asList(4, 5, 2)
    );
    assertEquals(3, sol.countPaths(6, roads2));

    // --- Test 3: Example 3 ---
    List<List<Integer>> roads3 = Arrays.asList(
        Arrays.asList(0, 1, 10),
        Arrays.asList(1, 2, 7),
        Arrays.asList(2, 3, 4),
        Arrays.asList(0, 3, 3)
    );
    assertEquals(1, sol.countPaths(4, roads3)); // Only one shortest path: 0 → 3

    // --- Test 4: Multiple equal shortest paths ---
    List<List<Integer>> roads4 = Arrays.asList(
        Arrays.asList(0, 1, 1),
        Arrays.asList(1, 2, 1),
        Arrays.asList(0, 2, 2)
    );
    assertEquals(2, sol.countPaths(3, roads4)); // Paths: 0→1→2 and 0→2

    // --- Test 5: Linear chain (only one path) ---
    List<List<Integer>> roads5 = Arrays.asList(
        Arrays.asList(0, 1, 2),
        Arrays.asList(1, 2, 2),
        Arrays.asList(2, 3, 2),
        Arrays.asList(3, 4, 2)
    );
    assertEquals(1, sol.countPaths(5, roads5)); // Only 0→1→2→3→4

    // --- Test 6: Small cycle (multiple shortest paths) ---
    List<List<Integer>> roads6 = Arrays.asList(
        Arrays.asList(0, 1, 1),
        Arrays.asList(1, 2, 1),
        Arrays.asList(2, 3, 1),
        Arrays.asList(0, 3, 3),
        Arrays.asList(1, 3, 2)
    );
    assertEquals(3, sol.countPaths(4, roads6)); // 0→1→3 and 0→1→2→3 both = 3
  }
}

/*
Dijstra's

A node may have multiple path with same cost. We need to consider all paths to get final count.
 */
class Solution {

  public int countPaths(int n, List<List<Integer>> roads) {
    int max = ((int) 1e9) + 7;
    List<List<int[]>> adjList = toAdjacencyList(n, roads);
    PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
    int[] shortest = new int[n];
    Arrays.fill(shortest, Integer.MAX_VALUE);

    q.add(new int[]{0, 0}); // cost, node
    shortest[0] = 0;

    int count = 0;
    while (!q.isEmpty()) {
      int[] nodeData = q.poll();
      int node = nodeData[1];
      int cost = nodeData[0];

      if (node == n - 1) {
        if (cost == shortest[node]) {
          count++;
          count = count % max;
        } else {
          break; // must be larger, we won't find any same cost path later
        }
      }

      if (cost > shortest[node]) { // already best path found for node, so skip
        continue;
      }

      List<int[]> adjNodeData = adjList.get(node);
      for (int[] adjNodeDatum : adjNodeData) { // relaxing adjacent nodes
        int adjNode = adjNodeDatum[0];
        int edgeCost = adjNodeDatum[1];

        int newCost = cost + edgeCost;
        if (newCost
            <= shortest[adjNode]) { // accept duplicates as well as we need to count all paths
          q.add(new int[]{newCost, adjNode});
          shortest[adjNode] = newCost;
        }
      }
    }

    return count;
  }

  private List<List<int[]>> toAdjacencyList(int n, List<List<Integer>> edges) {
    List<List<int[]>> adjList = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      adjList.add(new ArrayList<>());
    }

    for (List<Integer> edge : edges) {
      adjList.get(edge.get(0)).add(new int[]{edge.get(1), edge.get(2)});
      adjList.get(edge.get(1)).add(new int[]{edge.get(0), edge.get(2)});
    }

    return adjList;
  }
}

