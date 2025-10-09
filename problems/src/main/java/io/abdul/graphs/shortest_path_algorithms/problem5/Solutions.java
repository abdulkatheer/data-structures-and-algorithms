package io.abdul.graphs.shortest_path_algorithms.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/*
Here we've two parameters:
1) Number of stops
2) Cost per travel

We need to minimize cost. But stops can be any within the limit.
If the ask is, best stops, we implement BFS with ordering by stop, and we can break as soon as we get dest in the loop.
If the ask is, best cost, we implement Dijstra's with ordering by cost, and we can break as soon as we get dest in the loop.
If the ask is, best stops and cost, we implement Dijstra's with ordering by stop and then cost, and we can break as soon as we get dest in the loop.

But the ask is, best cost with limit in stops.
Here less stops doesn't mean less cost or viceversa.
So if we implement Dijstra's with ordering by stop and then cost, we may miss a cheaper cost with higher stops, but within limits.
So we've to explore all paths for sure and just keep on updating min cost withing limit till the end for all.

If we go by Dijktra's, if we sort by cost, we may miss the destination due to increased number of stops.
We would exhaust steps faster. Less cost may not lead to dest in less steps.
But less steps will lead to dest for sure and then we may update the min of all.

So we go by Dijktra's, and we sort by steps. This way we cover as much as node possible in less steps.
So we'll reach dest in all possible and allowed steps. So the queue in worst case may have many steps with different cost for dest.
Then we'll update the min of all at the end of loop.

Anyway we've to explore almost all paths which are in limits, and using PQ is of no sense here.
Bcz we take the min of all cost at the end of loop. So we can visit k in any order.
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // --- Test 1: Example 1 ---
    int n1 = 4;
    int[][] flights1 = {
        {0, 1, 100},
        {1, 2, 100},
        {2, 0, 100},
        {1, 3, 600},
        {2, 3, 200}
    };
    int src1 = 0, dst1 = 3, k1 = 1;
    assertEquals(700, sol.CheapestFlight(n1, flights1, src1, dst1, k1));

    // --- Test 2: Example 2 ---
    int n2 = 3;
    int[][] flights2 = {
        {0, 1, 100},
        {1, 2, 100},
        {0, 2, 500}
    };
    int src2 = 0, dst2 = 2, k2 = 1;
    assertEquals(200, sol.CheapestFlight(n2, flights2, src2, dst2, k2));

    // --- Test 3: Example 3 ---
    int[][] flights3 = {
        {0, 1, 100},
        {1, 2, 100},
        {0, 2, 500}
    };
    int src3 = 0, dst3 = 2, k3 = 0;
    assertEquals(500,
        sol.CheapestFlight(3, flights3, src3, dst3, k3)); // only direct flight allowed

    // --- Test 4: No route available ---
    int n4 = 4;
    int[][] flights4 = {
        {0, 1, 100},
        {1, 2, 200}
    };
    int src4 = 0, dst4 = 3, k4 = 1;
    assertEquals(-1, sol.CheapestFlight(n4, flights4, src4, dst4, k4));

    // --- Test 5: Path exists but exceeds K stops ---
    int n5 = 4;
    int[][] flights5 = {
        {0, 1, 100},
        {1, 2, 100},
        {2, 3, 100}
    };
    int src5 = 0, dst5 = 3, k5 = 1;
    assertEquals(-1, sol.CheapestFlight(n5, flights5, src5, dst5, k5)); // requires 2 stops

    // --- Test 6: Multiple paths, choose cheapest ---
    int n6 = 5;
    int[][] flights6 = {
        {0, 1, 200},
        {0, 2, 500},
        {1, 2, 100},
        {1, 3, 300},
        {2, 3, 100},
        {3, 4, 50}
    };
    int src6 = 0, dst6 = 4, k6 = 3;
    assertEquals(450,
        sol.CheapestFlight(n6, flights6, src6, dst6, k6)); // 0→1→2→3→4 = 200+100+100+50

    int n7 = 4;
    int[][] flights7 = {
        {0, 1, 1},
        {0, 2, 5},
        {1, 2, 1},
        {2, 3, 1}
    };
    int src7 = 0, dst7 = 3, k7 = 1;
    assertEquals(6,
        sol.CheapestFlight(n7, flights7, src7, dst7, k7));

    int n8 = 5;
    int[][] flights8 = {
        {0, 1, 5},
        {1, 2, 5},
        {0, 3, 2},
        {3, 1, 2},
        {1, 4, 1},
        {4, 2, 1}};
    int src8 = 0, dst8 = 2, k8 = 2;
    assertEquals(7,
        sol.CheapestFlight(n8, flights8, src8, dst8, k8));
  }
}

/*
Dijkstra's
Explore only paths which are less than k steps in an incremental order
 */
class Solution {

  public int CheapestFlight(int n, int[][] flights, int src, int dst, int K) {
    List<List<int[]>> adjList = toAdjacencyList(n, flights);

    int[] cheapest = new int[n];
    Arrays.fill(cheapest, Integer.MAX_VALUE);

    // 0 - city, 1 - cost, 2 - stops
    PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
    q.add(new int[]{src, 0, 0});
    cheapest[src] = 0;

    while (!q.isEmpty()) {
      int[] nodeData = q.poll();
      int node = nodeData[0];
      int cost = nodeData[1];
      int stops = nodeData[2];

      if (stops > K) { // not allowed to go beyond K stops
        continue;
      }

      List<int[]> adjNodes = adjList.get(node);

      for (int[] adjNodeData : adjNodes) {
        int adjNode = adjNodeData[0];
        int adjCost = adjNodeData[1];

        int newCost = adjCost + cost;
        if (newCost < cheapest[adjNode]) {
          q.add(new int[]{adjNode, newCost, stops + 1});
          cheapest[adjNode] = newCost;
        }
      }
    }

    return cheapest[dst] == Integer.MAX_VALUE ? -1 : cheapest[dst];
  }

  private List<List<int[]>> toAdjacencyList(int n, int[][] edges) {
    List<List<int[]>> adjList = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      adjList.add(new ArrayList<>());
    }

    for (int[] edge : edges) {
      adjList.get(edge[0]).add(new int[]{edge[1], edge[2]});
    }

    return adjList;
  }
}

/*
BFS
Explore only paths which are less than k steps in no order
 */
class Solution2 {

  public int CheapestFlight(int n, int[][] flights, int src, int dst, int K) {
    List<List<int[]>> adjList = toAdjacencyList(n, flights);

    int[] cheapest = new int[n];
    Arrays.fill(cheapest, Integer.MAX_VALUE);

    // 0 - city, 1 - cost, 2 - stops
    Queue<int[]> q = new LinkedList<>();
    q.add(new int[]{src, 0, 0});
    cheapest[src] = 0;

    while (!q.isEmpty()) {
      int[] nodeData = q.poll();
      int node = nodeData[0];
      int cost = nodeData[1];
      int stops = nodeData[2];

      if (stops > K) { // not allowed to go beyond K stops
        continue;
      }

      List<int[]> adjNodes = adjList.get(node);

      for (int[] adjNodeData : adjNodes) {
        int adjNode = adjNodeData[0];
        int adjCost = adjNodeData[1];

        int newCost = adjCost + cost;
        if (newCost < cheapest[adjNode]) {
          q.add(new int[]{adjNode, newCost, stops + 1});
          cheapest[adjNode] = newCost;
        }
      }
    }

    return cheapest[dst] == Integer.MAX_VALUE ? -1 : cheapest[dst];
  }

  private List<List<int[]>> toAdjacencyList(int n, int[][] edges) {
    List<List<int[]>> adjList = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      adjList.add(new ArrayList<>());
    }

    for (int[] edge : edges) {
      adjList.get(edge[0]).add(new int[]{edge[1], edge[2]});
    }

    return adjList;
  }
}
