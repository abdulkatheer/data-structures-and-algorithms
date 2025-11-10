package io.abdul.graphs.additional_algorithms.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // ---------- 1. Example Test Case 1 ----------
    {
      int V = 7;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
      adj.add(new ArrayList<>(Arrays.asList(0)));
      adj.add(new ArrayList<>(Arrays.asList(0, 3, 4, 5)));
      adj.add(new ArrayList<>(Arrays.asList(2, 0)));
      adj.add(new ArrayList<>(Arrays.asList(2, 6)));
      adj.add(new ArrayList<>(Arrays.asList(2, 6)));
      adj.add(new ArrayList<>(Arrays.asList(4, 5)));

      assertEquals(Arrays.asList(0, 2), sol.articulationPoints(V, adj));
    }

    // ---------- 2. Example Test Case 2 ----------
    {
      int V = 5;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1)));
      adj.add(new ArrayList<>(Arrays.asList(0, 4)));
      adj.add(new ArrayList<>(Arrays.asList(3, 4)));
      adj.add(new ArrayList<>(Arrays.asList(2, 4)));
      adj.add(new ArrayList<>(Arrays.asList(1, 2, 3)));

      assertEquals(Arrays.asList(1, 4), sol.articulationPoints(V, adj));
    }

    // ---------- 3. Fully Connected (no articulation) ----------
    {
      int V = 3;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1, 2)));
      adj.add(new ArrayList<>(Arrays.asList(0, 2)));
      adj.add(new ArrayList<>(Arrays.asList(0, 1)));

      assertEquals(Arrays.asList(-1), sol.articulationPoints(V, adj));
    }

    // ---------- 4. Disconnected Graph ----------
    {
      int V = 6;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1)));
      adj.add(new ArrayList<>(Arrays.asList(0, 2)));
      adj.add(new ArrayList<>(Arrays.asList(1)));
      adj.add(new ArrayList<>(Arrays.asList(4, 5)));
      adj.add(new ArrayList<>(Arrays.asList(3, 5)));
      adj.add(new ArrayList<>(Arrays.asList(3, 4)));

      assertEquals(Arrays.asList(1), sol.articulationPoints(V, adj));
    }

    // ---------- 5. Chain ----------
    {
      int V = 5;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1)));
      adj.add(new ArrayList<>(Arrays.asList(0, 2)));
      adj.add(new ArrayList<>(Arrays.asList(1, 3)));
      adj.add(new ArrayList<>(Arrays.asList(2, 4)));
      adj.add(new ArrayList<>(Arrays.asList(3)));

      assertEquals(Arrays.asList(1, 2, 3), sol.articulationPoints(V, adj));
    }

    // ---------- 6. Star ----------
    {
      int V = 6;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
      adj.add(new ArrayList<>(Arrays.asList(0)));
      adj.add(new ArrayList<>(Arrays.asList(0)));
      adj.add(new ArrayList<>(Arrays.asList(0)));
      adj.add(new ArrayList<>(Arrays.asList(0)));
      adj.add(new ArrayList<>(Arrays.asList(0)));

      assertEquals(Arrays.asList(0), sol.articulationPoints(V, adj));
    }

    // ---------- 7. Self Loops ----------
    {
      int V = 4;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(0, 1)));  // self-loop
      adj.add(new ArrayList<>(Arrays.asList(0, 2)));
      adj.add(new ArrayList<>(Arrays.asList(1, 3)));
      adj.add(new ArrayList<>(Arrays.asList(2, 3)));  // self-loop

      assertEquals(Arrays.asList(1, 2), sol.articulationPoints(V, adj));
    }

    // ---------- 8. Multiple Bridges ----------
    {
      int V = 6;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1)));
      adj.add(new ArrayList<>(Arrays.asList(0, 2)));
      adj.add(new ArrayList<>(Arrays.asList(1, 3)));
      adj.add(new ArrayList<>(Arrays.asList(2, 4, 5)));
      adj.add(new ArrayList<>(Arrays.asList(3)));
      adj.add(new ArrayList<>(Arrays.asList(3)));

      assertEquals(Arrays.asList(1, 2, 3), sol.articulationPoints(V, adj));
    }

    // ---------- 9. Fully Connected ----------
    {
      int V = 4;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
      adj.add(new ArrayList<>(Arrays.asList(0, 2, 3)));
      adj.add(new ArrayList<>(Arrays.asList(0, 1, 3)));
      adj.add(new ArrayList<>(Arrays.asList(0, 1, 2)));

      assertEquals(Arrays.asList(-1), sol.articulationPoints(V, adj));
    }

    // ---------- 10. Single Node ----------
    {
      int V = 1;
      ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
      adj.add(new ArrayList<>());

      assertEquals(Arrays.asList(-1), sol.articulationPoints(V, adj));
    }
  }
}

class Solution {

  public ArrayList<Integer> articulationPoints(int n, ArrayList<ArrayList<Integer>> adjList) {

    boolean[] visited = new boolean[n];
    boolean[] articulationPoints = new boolean[n];

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfs(i, n, adjList, visited, articulationPoints);
      }
    }

    ArrayList<Integer> result = new ArrayList<>();
    for (int i = 0; i < articulationPoints.length; i++) {
      if (articulationPoints[i]) {
        result.add(i);
      }
    }

    if (result.isEmpty()) {
      result.add(-1);
    }
    return result;
  }

  private void dfs(int startNode, int n, ArrayList<ArrayList<Integer>> adjList, boolean[] visited,
      boolean[] articulationPoints) {
    int[] disc = new int[n];
    int[] lowDisc = new int[n];
    int[] parents = new int[n];
    int[] child = new int[n];
    Arrays.fill(parents, -1);
    int discTime = 1;

    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;
    disc[startNode] = discTime;
    lowDisc[startNode] = discTime;
    parents[startNode] = -1;
    discTime++;

    while (!stack.isEmpty()) {
      int[] nodeData = stack.peek();
      int node = nodeData[0];
      int nextAdjNodePos = nodeData[1];

      if (nextAdjNodePos < adjList.get(node).size()) {
        Integer adjNode = adjList.get(node).get(nextAdjNodePos);
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
          disc[adjNode] = discTime;
          lowDisc[adjNode] = discTime;
          parents[adjNode] = node;
          child[node]++;
          discTime++;
        } else if (adjNode != parents[node]) {
          // Back edge
          if (disc[adjNode] < lowDisc[node]) {
            // adjNode has back edge to node's parent or its ancestors
            lowDisc[node] = disc[adjNode];
          }
        }
        nodeData[1]++;
      } else {
        // all adjNodes are processed
        stack.pop();

        // backtracking
        int parentNode = parents[node];
        if (parentNode != -1) {
          lowDisc[parentNode] = Math.min(lowDisc[parentNode], lowDisc[node]);

          // Articulation point (Root node)
          if (parents[parentNode] == -1) {
            if (child[parentNode] > 1) {
              articulationPoints[parentNode] = true;
            }
          } else {
            // Articulation point (Non-root node)
            if (lowDisc[node] >= disc[parentNode]) {
              articulationPoints[parentNode] = true;
            }
          }
        }
      }
    }
  }
}

