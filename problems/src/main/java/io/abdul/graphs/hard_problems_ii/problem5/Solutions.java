package io.abdul.graphs.hard_problems_ii.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    int[][] stones1 = {
        {0, 0}, {0, 1}, {1, 0}, {1, 2}, {2, 1}, {2, 2}
    };
    int n1 = stones1.length;
    int expected1 = 5;
    assertEquals(expected1, sol.maxRemove(stones1, n1),
        "Example 1: Remove 5 stones, leaving one representative in each connected group");

    // --- Test 2: Example 2 ---
    int[][] stones2 = {
        {0, 0}, {0, 2}, {1, 3}, {3, 1}, {3, 2}, {4, 3}
    };
    int n2 = stones2.length;
    int expected2 = 4;
    assertEquals(expected2, sol.maxRemove(stones2, n2),
        "Example 2: Multiple disconnected clusters; max removable = 4");

    // --- Test 3: Example 3 ---
    int[][] stones3 = {
        {0, 0}, {0, 2}
    };
    int n3 = stones3.length;
    int expected3 = 1;
    assertEquals(expected3, sol.maxRemove(stones3, n3),
        "Example 3: Both in same row; one can be removed");

    // --- Test 4: Single stone ---
    int[][] stones4 = {
        {0, 0}
    };
    int n4 = stones4.length;
    int expected4 = 0;
    assertEquals(expected4, sol.maxRemove(stones4, n4),
        "Single stone cannot be removed since no shared row/column");

    // --- Test 5: All stones in same row ---
    int[][] stones5 = {
        {0, 0}, {0, 1}, {0, 2}, {0, 3}
    };
    int n5 = stones5.length;
    int expected5 = 3;
    assertEquals(expected5, sol.maxRemove(stones5, n5),
        "All stones share same row; all but one can be removed");

    // --- Test 6: All stones in same column ---
    int[][] stones6 = {
        {0, 0}, {1, 0}, {2, 0}, {3, 0}
    };
    int n6 = stones6.length;
    int expected6 = 3;
    assertEquals(expected6, sol.maxRemove(stones6, n6),
        "All stones share same column; all but one can be removed");

    // --- Test 7: Disconnected clusters ---
    int[][] stones7 = {
        {0, 0}, {0, 1}, {10, 10}, {11, 10}, {12, 10}
    };
    int n7 = stones7.length;
    int expected7 = 3;
    assertEquals(expected7, sol.maxRemove(stones7, n7),
        "Two disjoint clusters; removable stones = total - #clusters = 5 - 2 = 3");

    // --- Test 8: No shared rows or columns ---
    int[][] stones8 = {
        {0, 0}, {1, 1}, {2, 2}
    };
    int n8 = stones8.length;
    int expected8 = 0;
    assertEquals(expected8, sol.maxRemove(stones8, n8),
        "No stones share row/column; none removable");

    // --- Test 9: Complex connected component ---
    int[][] stones9 = {
        {0, 0}, {0, 1}, {1, 2}, {2, 2}, {2, 3}
        // nodes 0 to 2, 3 to 6
        // {0,3}, {0,4}, {1,5}, {2,5}, {2,6}
    };
    int n9 = stones9.length;
    int expected9 = 3;
    assertEquals(expected9, sol.maxRemove(stones9, n9),
        "Connected via chain of rows/columns; one must remain");

    // --- Test 10: Large grid diagonal spread ---
    int[][] stones10 = {
        {0, 0}, {0, 5}, {5, 5}, {5, 10}, {10, 10}
    };
    int n10 = stones10.length;
    int expected10 = 4;
    assertEquals(expected10, sol.maxRemove(stones10, n10),
        "Chain connectivity forms single component; can remove all but one");
  }
}

/*
Intuition is, we can have only one stone per row and per column. We need to keep on removing until we reach a final state where all are disconnected.

So this fits under nodes and connections and hence DisjointSet can be used.
But for DS, we need number of nodes upfront!

When we have stone at (x,y), it means it's connecting row x and y.
There can be only one after connecting it (1 per component).

So we treat x and y as separate coordinates and connect components based on it.

At the end, we'll get number of components, so we can assume 1 stone per component exists and everything else is removed.

Removed = N - num of components;

--
Here we represent each row and column as node and not the stone itself.
If there are m rows and n columns, there'll be m+n nodes.
The stone is just an edge between row and column.

if m = 10 and n = 5
rows numbered from 0 to 9
columns numbered from 10 to 14 (column [0 to 4] + m)

 */
class Solution {

  /*
  each row and column is a node
  stone is an edge connecting nodes
  After building the graph, in each built component only stone can exist
  So totalStones - numberOfValidComponents = stonesRemoved
  */
  public int maxRemove(int[][] stones, int n) {
    int maxRow = 0;
    int maxColumn = 0;

    for (int[] edge : stones) {
      maxRow = Math.max(maxRow, edge[0]);
      maxColumn = Math.max(maxColumn, edge[1]);
    }

    int rows = maxRow + 1;
    int columns = maxColumn + 1;
    int nodes = rows + columns;

    /*
    Node numbering
    row = row (no change)
    column = (rows + column)
    */
    DisjointSet set = new DisjointSet(nodes);
    Set<Integer> connected = new HashSet<>();
    for (int[] edge : stones) {
      set.union(edge[0], edge[1] + rows);
      connected.add(edge[0]);
      connected.add(edge[1] + rows);
    }

    int validComponents = 0;
    // whatever we've connected with stones are valid ones
    Set<Integer> uPars = new HashSet<>();
    for (int node : connected) {
      uPars.add(set.findUltimateParent(node));
    }

    return n - uPars.size();
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }
    }

    void union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }
    }

    int findUltimateParent(int u) {
      Stack<Integer> stack = new Stack<>();
      while (parents[u] != u) {
        stack.push(u);
        u = parents[u];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = u;
      }

      return u;
    }
  }
}
