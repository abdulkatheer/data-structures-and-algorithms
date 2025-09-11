package io.abdul.graphs.hard_problems.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*
Node part of the cycle and nodes leading to a cycle are not safe nodes!
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1: Mixed graph with cycles and terminal nodes
    int V1 = 7;
    int[][] adj1 = {
        {1, 2},  // 0 -> [1, 2]
        {2, 3},  // 1 -> [2, 3]
        {5},     // 2 -> [5]
        {0},     // 3 -> [0]
        {5},     // 4 -> [5]
        {},      // 5 -> terminal node
        {}       // 6 -> terminal node
    };
    int[] expected1 = {2, 4, 5, 6};
    assertArrayEquals(expected1, sol.eventualSafeNodes(V1, adj1),
        "Example 1 should return [2, 4, 5, 6]");

    // Example 2: Simple terminal node
    int V2 = 4;
    int[][] adj2 = {
        {1},     // 0 -> [1]
        {2},     // 1 -> [2]
        {0, 3},  // 2 -> [0, 3]
        {}       // 3 -> terminal node
    };
    int[] expected2 = {3};
    assertArrayEquals(expected2, sol.eventualSafeNodes(V2, adj2),
        "Example 2 should return [3]");

    // Example 3: Self-loop cycle
    int V3 = 4;
    int[][] adj3 = {
        {1},  // 0 -> [1]
        {2},  // 1 -> [2]
        {0},  // 2 -> [0] creates a cycle
        {}    // 3 -> terminal node
    };
    int[] expected3 = {3};
    assertArrayEquals(expected3, sol.eventualSafeNodes(V3, adj3),
        "Example 3 should return [3]");

    // Edge Case 1: All nodes are terminal
    int V4 = 3;
    int[][] adj4 = {
        {}, {}, {}
    };
    int[] expected4 = {0, 1, 2};
    assertArrayEquals(expected4, sol.eventualSafeNodes(V4, adj4),
        "All terminal nodes should be safe");

    // Edge Case 2: No safe nodes (fully cyclic)
    int V5 = 3;
    int[][] adj5 = {
        {1}, {2}, {0}
    };
    int[] expected5 = {};
    assertArrayEquals(expected5, sol.eventualSafeNodes(V5, adj5),
        "Fully cyclic graph should return empty array");

    // Edge Case 3: Single node self-loop
    int V6 = 1;
    int[][] adj6 = {
        {0}
    };
    int[] expected6 = {};
    assertArrayEquals(expected6, sol.eventualSafeNodes(V6, adj6),
        "Self-loop node should not be safe");

    // Edge Case 4: Disconnected terminal nodes
    int V7 = 5;
    int[][] adj7 = {
        {}, {}, {}, {}, {}
    };
    int[] expected7 = {0, 1, 2, 3, 4};
    assertArrayEquals(expected7, sol.eventualSafeNodes(V7, adj7),
        "All disconnected terminal nodes should be safe");
  }
}

/*
DFS (Toposort type)

If all adjacent nodes of a node are processed, then we can pop it out of stack and path_visited and mark as safe node
If a node in the same path is visited again, meaning a cycle. We can remove all out of stack and keep path_visited for those nodes.
Meaning all nodes part of the cycle and leading to the cycle will have path_visited set to true.
 */
class Solution {

  public int[] eventualSafeNodes(int V, int[][] adj) {
    boolean[] visited = new boolean[V];
    boolean[] pathVisited = new boolean[V];
    boolean[] safe = new boolean[V];
    List<Integer> safeNodes = new ArrayList<>();

    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        dfs(i, adj, visited, pathVisited, safe);
      }
    }

    for (int i = 0; i < safe.length; i++) {
      if (safe[i]) {
        safeNodes.add(i);
      }
    }

    return safeNodes.stream().mapToInt(i -> i).toArray();
  }

  private void dfs(int startNode, int[][] adj, boolean[] visited, boolean[] pathVisited,
      boolean[] safe) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;
    pathVisited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjVisitedCount = peeked[1];

      if (adjVisitedCount < adj[node].length) {
        int adjNode = adj[node][adjVisitedCount];
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
          pathVisited[adjNode] = true;
        } else if (pathVisited[adjNode]) { // cycle detected
          // All nodes in the stack are not safe, as they're part of cycle or leading to a cycle
          // pop all out of stack, keeping pathVisited as is
          while (!stack.isEmpty()) {
            stack.pop();
          }
        }
        peeked[1]++;
      } else {
        stack.pop();
        pathVisited[node] = false;
        safe[node] = true;
      }
    }
  }
}

/*
BFS (Kahn's algorithm type)

Toposort starts collecting nodes from 0 indegrees and stops as soon as we can't find more
Here we want the reverse of it. We want nodes with 0 indegrees. Nodes having cycles will not have 0 indegree/outdegree.
We can't directly go from bottom level and upwards. So we reverse all the edges and then count indegrees.

Why it doesn't have any impact in result?
Cycles - even if we reverse, cycle will not change
It only helps navigate from bottom most level to top
Result is also a toposort.
 */
class Solution2 {

  public int[] eventualSafeNodes(int V, int[][] adj) {
    // reverse edges
    ArrayList[] adjReversed = new ArrayList[V];
    for (int i = 0; i < adjReversed.length; i++) {
      adjReversed[i] = new ArrayList<>();
    }

    for (int i = 0; i < adj.length; i++) {
      for (int node : adj[i]) {
        adjReversed[node].add(i);
      }
    }

    // calculate in-degree
    int[] inDegree = new int[V];
    for (ArrayList<Integer> adjNodes : adjReversed) {
      for (Integer adjNode : adjNodes) {
        inDegree[adjNode]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < V; i++) {
      if (inDegree[i] == 0) {
        q.add(i);
      }
    }

    boolean[] safe = new boolean[V];
    bfs(q, adjReversed, inDegree, safe);

    // Find safe nodes
    ArrayList<Integer> safeNodes = new ArrayList<>();
    for (int i = 0; i < safe.length; i++) {
      if (safe[i]) {
        safeNodes.add(i);
      }
    }

    return safeNodes.stream().mapToInt(i -> i).toArray();
  }

  private void bfs(Queue<Integer> q, ArrayList[] adj, int[] inDegree, boolean[] safe) {

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        safe[node] = true; // instead of adding to toposort, we set flag

        ArrayList<Integer> adjNodes = adj[node];

        for (int adjNode : adjNodes) {
          inDegree[adjNode]--;
          if (inDegree[adjNode] == 0) {
            q.add(adjNode);
          }
        }
      }
    }
  }
}