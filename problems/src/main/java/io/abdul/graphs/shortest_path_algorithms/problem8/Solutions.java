package io.abdul.graphs.shortest_path_algorithms.problem8;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    ArrayList<ArrayList<Integer>> edges1 = new ArrayList<>();
    edges1.add(new ArrayList<>(Arrays.asList(3, 2, 6)));
    edges1.add(new ArrayList<>(Arrays.asList(5, 3, 1)));
    edges1.add(new ArrayList<>(Arrays.asList(0, 1, 5)));
    edges1.add(new ArrayList<>(Arrays.asList(1, 5, -3)));
    edges1.add(new ArrayList<>(Arrays.asList(1, 2, -2)));
    edges1.add(new ArrayList<>(Arrays.asList(3, 4, -2)));
    edges1.add(new ArrayList<>(Arrays.asList(2, 4, 3)));

    assertArrayEquals(new int[]{0, 5, 3, 3, 1, 2}, sol.bellman_ford(6, edges1, 0),
        "Example 1: Typical positive + negative edge case");

    // --- Test 2: Example 2 ---
    ArrayList<ArrayList<Integer>> edges2 = new ArrayList<>();
    edges2.add(new ArrayList<>(Arrays.asList(0, 1, 9)));
    assertArrayEquals(new int[]{0, 9}, sol.bellman_ford(2, edges2, 0),
        "Example 2: Simple two-node graph");

    // --- Test 3: Example 3 ---
    ArrayList<ArrayList<Integer>> edges3 = new ArrayList<>();
    edges3.add(new ArrayList<>(Arrays.asList(0, 1, 5)));
    edges3.add(new ArrayList<>(Arrays.asList(1, 0, 3)));
    edges3.add(new ArrayList<>(Arrays.asList(1, 2, -1)));
    edges3.add(new ArrayList<>(Arrays.asList(2, 0, 1)));
    assertArrayEquals(new int[]{1, 6, 0}, sol.bellman_ford(3, edges3, 2),
        "Example 3: Source not at 0, mixed positive/negative edges");

    // --- Test 4: Graph with unreachable node ---
    ArrayList<ArrayList<Integer>> edges4 = new ArrayList<>();
    edges4.add(new ArrayList<>(Arrays.asList(0, 1, 4)));
    edges4.add(new ArrayList<>(Arrays.asList(1, 2, 5)));
    // Node 3 unreachable
    assertArrayEquals(new int[]{0, 4, 9, 1000000000}, sol.bellman_ford(4, edges4, 0),
        "Unreachable vertex should have distance 10^9");

    // --- Test 5: Graph with negative cycle ---
    ArrayList<ArrayList<Integer>> edges5 = new ArrayList<>();
    edges5.add(new ArrayList<>(Arrays.asList(0, 1, 1)));
    edges5.add(new ArrayList<>(Arrays.asList(1, 2, -1)));
    edges5.add(new ArrayList<>(Arrays.asList(2, 0, -1)));
    assertArrayEquals(new int[]{-1}, sol.bellman_ford(3, edges5, 0),
        "Negative cycle should return -1");

    // --- Test 6: Single node graph ---
    ArrayList<ArrayList<Integer>> edges6 = new ArrayList<>();
    assertArrayEquals(new int[]{0}, sol.bellman_ford(1, edges6, 0),
        "Single node graph, source = destination, distance 0");

    // --- Test 7: Disconnected graph with self-loop ---
    ArrayList<ArrayList<Integer>> edges7 = new ArrayList<>();
    edges7.add(new ArrayList<>(Arrays.asList(0, 0, 0)));
    edges7.add(new ArrayList<>(Arrays.asList(0, 1, 5)));
    assertArrayEquals(new int[]{0, 5, 1000000000}, sol.bellman_ford(3, edges7, 0),
        "Self-loop and disconnected node test");
  }
}

class Solution {

  static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
    int[] shortest = new int[V];
    int max = (int) 1e9;
    Arrays.fill(shortest, max);
    shortest[S] = 0;

    for (int i = 0; i < V - 1; i++) {
      for (ArrayList<Integer> edge : edges) {
        int src = edge.get(0);
        int dest = edge.get(1);
        int weight = edge.get(2);

        if (shortest[src] == max) { // path to src is not known yet
          continue;
        }
        int newCostFromSource = shortest[src] + weight;

        if (newCostFromSource < shortest[dest]) {
          shortest[dest] = newCostFromSource;
        }
      }
    }

    // V th iteration to detect negative cycle
    for (ArrayList<Integer> edge : edges) {
      int src = edge.get(0);
      int dest = edge.get(1);
      int weight = edge.get(2);

      if (shortest[src] == max) { // path to src is not known yet
        continue;
      }

      int newCostFromSource = shortest[src] + weight;

      if (newCostFromSource < shortest[dest]) {
        // still reducing? negative cycle then
        return new int[]{-1};
      }
    }

    return shortest;
  }
}

