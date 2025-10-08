package io.abdul.graphs.shortest_path_algorithms.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
//    Solution3 sol = new Solution3();

    // --- Test 1: Simple 2-node graph ---
    ArrayList<ArrayList<ArrayList<Integer>>> adj1 = new ArrayList<>();
    adj1.add(new ArrayList<>(List.of(new ArrayList<>(List.of(1, 9)))));
    adj1.add(new ArrayList<>(List.of(new ArrayList<>(List.of(0, 9)))));
    int[] result1 = sol.dijkstra(2, adj1, 0);
    assertArrayEquals(new int[]{0, 9}, result1);

    // --- Test 2: 3-node connected graph ---
    ArrayList<ArrayList<ArrayList<Integer>>> adj2 = new ArrayList<>();
    adj2.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 1)), new ArrayList<>(List.of(2, 6)))));
    adj2.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(2, 3)), new ArrayList<>(List.of(0, 1)))));
    adj2.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 3)), new ArrayList<>(List.of(0, 6)))));
    int[] result2 = sol.dijkstra(3, adj2, 2);
    assertArrayEquals(new int[]{4, 3, 0}, result2);

    // --- Test 3: 4-node weighted undirected graph ---
    ArrayList<ArrayList<ArrayList<Integer>>> adj3 = new ArrayList<>();
    adj3.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 1)), new ArrayList<>(List.of(3, 2)))));
    adj3.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(0, 1)), new ArrayList<>(List.of(2, 4)))));
    adj3.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 4)), new ArrayList<>(List.of(3, 3)))));
    adj3.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(0, 2)), new ArrayList<>(List.of(2, 3)))));
    int[] result3 = sol.dijkstra(4, adj3, 0);
    assertArrayEquals(new int[]{0, 1, 5, 2}, result3);

    // --- Test 4: Disconnected graph ---
    ArrayList<ArrayList<ArrayList<Integer>>> adj4 = new ArrayList<>();
    adj4.add(new ArrayList<>(List.of(new ArrayList<>(List.of(1, 5)))));  // node 0 → 1
    adj4.add(new ArrayList<>(List.of(new ArrayList<>(List.of(0, 5)))));  // node 1 → 0
    adj4.add(new ArrayList<>());                        // node 2 → disconnected
    int[] result4 = sol.dijkstra(3, adj4, 0);
    assertArrayEquals(new int[]{0, 5, 1000000000}, result4);

    // --- Test 5: Larger graph with multiple paths ---
    ArrayList<ArrayList<ArrayList<Integer>>> adj5 = new ArrayList<>();
    adj5.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 2)), new ArrayList<>(List.of(2, 4)))));
    adj5.add(new ArrayList<>(List.of(new ArrayList<>(List.of(0, 2)), new ArrayList<>(List.of(2, 1)),
        new ArrayList<>(List.of(3, 7)))));
    adj5.add(new ArrayList<>(List.of(new ArrayList<>(List.of(0, 4)), new ArrayList<>(List.of(1, 1)),
        new ArrayList<>(List.of(3, 3)))));
    adj5.add(
        new ArrayList<>(List.of(new ArrayList<>(List.of(1, 7)), new ArrayList<>(List.of(2, 3)))));
    int[] result5 = sol.dijkstra(4, adj5, 0);
    assertArrayEquals(new int[]{0, 2, 3, 6}, result5);
  }
}

/*
BFS approach - No Dijsktra!

Almost considers all paths in Worst-case

Time Complexity:
- For all edges, all vertices may be added again. So Queue may grow up to E*V
- While loop runs for O(E*V)
- Pop runs for O((E*V) log (E*V))
- All the edges may be visited for all vertices. So for loop runs for O(E*V)
- Add runs for O(E*V * log(E*V)

O ( V + E*V * log(E*V) + E*V * log(E*V))

T - O(E*V * log(E*V))

For sparse graphs, E = V
T = O(V^2 * log(V^2)
For dense graphs, E = V^2
T = O(V^3 * log(V^3)
 */
class Solution {

  public int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {
    int max = (int) 1e9;
    int[] cost = new int[V];
    Arrays.fill(cost, max);

    Queue<Node> q = new LinkedList<>();
    q.add(new Node(S, 0));
    cost[S] = 0;

    while (!q.isEmpty()) {
      Node n = q.poll(); // not the smallest cost for n.id

      if (cost[n.id] < n.costFromSource) { // already have better cost for n, so skip
        // This may occur at any point in time, no sorted order
        // Like cost 10, 8, 13, 11, 1, 2, 6, 2 can come in order as well
        continue;
      }

      ArrayList<ArrayList<Integer>> adjNodes = adj.get(n.id);
      for (ArrayList<Integer> adjNode : adjNodes) {
        int adjNodeId = adjNode.get(0);
        int costFromN = adjNode.get(1);

        int newCost = costFromN + n.costFromSource;
        if (newCost < cost[adjNodeId]) { // lot of duplicate nodes may be added
          q.add(new Node(adjNodeId, newCost));
          cost[adjNodeId] = newCost;
        }
      }
    }

    return cost;
  }

  private record Node(int id, int costFromSource){}
}

/*
Dijstra using Min-heap approach:
Greedy, picks smallest cost first to avoid processing all paths.
Caveats: The min-heap may have duplicate entries for node when relaxing cost.

Process the node with the smallest cost
And relax cost of its neighbours if possible. If we can relax it, meaning it had a larger cost earlier, that will not be processed.
So duplicate entries will enter the queue, but we'll process only the smallest of them.

Similar to BFS, but
- mark as processed/visited after processing the node. Because till we process a node, we may relax its cost multiple times as we visit by the smallest cost first.
- Not level by level, nodes with the smallest cost processed first

Time Complexity:
- Initialising the cost array costs O(V)
- Queue size may go up to number of Edges as we allow duplicates and each edge visited only once as we skip processed vertices
- We pop all items from Queue. Each pop costs O(log E), E being size of Queue. So in total O(E log E)
- We push Edge number of items to Queue, as each edge is processed exactly once. Each push costs O(log E), E being size of Queue. So in total O(E log E)

So
O(V + E log E + E log E)
O(V + 2E log E)
T - O(E log E)

For sparse graphs, E = V
T - O(V log V)
For dense graph, E = V * (V-1) = Each vertex connects to every other vertices. = V^2
T - O(V^2 log V^2)
 */
class Solution2 {

  public int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {
    /*
    As per problem, max V is 10^4 and max cost per edge is 10^4. Worst case, a node takes to connect all other nodes to reach.
    Which will be 10^4 X 10^4 = 10^8.
     */
    int max = (int) 1e9;
    PriorityQueue<Node> q = new PriorityQueue<>();
    int[] cost = new int[V];
    Arrays.fill(cost, max);

    q.add(new Node(S, 0)); // Known cost
    cost[S] = 0;

    while (!q.isEmpty()) { // O(E) time as we may add duplicates while processing all edges
      // O(E log E) for entire loop
      Node n = q.poll();// take the node with the smallest cost from source

      if (n.costFromSource > cost[n.id]) {
        // already processed. Smaller costs picked first. So this is a duplicate entry that we can ignore.
        continue;
      }

      ArrayList<ArrayList<Integer>> adjNodes = adj.get(n.id);
      // O(V_E) per loop - only edges of vertex V. As we skip already visited vertices
      // below loop only iterate O(E) times for entire loop
      for (ArrayList<Integer> adjNode : adjNodes) { // relax cost of adjacent nodes
        int adjNodeId = adjNode.get(0);
        int costFromN = adjNode.get(1);

        int newCost = n.costFromSource + costFromN;
        if (newCost < cost[adjNodeId]) { // better path to reach adjNode from source found
          // O(E log E) for entire loop
          q.add(new Node(adjNodeId, newCost)); // a duplicate may already exist
          cost[adjNodeId] = newCost;
        }
      }
    }

    return cost;
  }

  private static class Node implements Comparable<Node> {

    private final int id;
    private final int costFromSource;

    Node(int id, int costFromSource) {
      this.id = id;
      this.costFromSource = costFromSource;
    }

    // By cost and then by ID
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
Dijstra using Sorted-set approach:
Avoids duplicates in the Set

Time Complexity:
- Initialising the cost[] - O(V)
- Queue will not have duplicates and processed ones will be skipped as well. So max it can have O(V)
- So Enqueue (Enqueue and Remove/Enqueue) happens for all edges. Each enqueue costs O(log V). So in total O(E log V)
- While loop runs only for V times and deque happens only V times. So in total O(V log V)

O(V + V log V + E log V)
O(V + (V+E) log V)
T - O((V+E) log V) time.

For sparse graphs, E = V
T - O(V log V)
For dense graphs, E = V^2
T - O(V^2 log V) time
 */
class Solution3 {

  public int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {
    int max = (int) 1e9;
    int[] cost = new int[V];
    Arrays.fill(cost, max);

    TreeSet<Node> q = new TreeSet<>();
    q.add(new Node(S, 0));
    cost[S] = 0;

    while (!q.isEmpty()) {
      Node n = q.pollFirst();

      ArrayList<ArrayList<Integer>> adjNodes = adj.get(n.id);
      for (ArrayList<Integer> adjNode : adjNodes) {
        int adjNodeId = adjNode.get(0);
        int costFromN = adjNode.get(1);
        int newCost = n.costFromSource + costFromN;
        if (newCost < cost[adjNodeId]) {
          if (cost[adjNodeId]
              != max) { // if max, meaning this is first update and will not be there in Set
            q.remove(new Node(adjNodeId, cost[adjNodeId]));
          }

          q.add(new Node(adjNodeId, newCost));
          cost[adjNodeId] = newCost;
        }
      }
    }

    return cost;
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
