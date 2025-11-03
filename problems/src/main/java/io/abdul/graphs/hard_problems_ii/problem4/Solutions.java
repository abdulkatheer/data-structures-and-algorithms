package io.abdul.graphs.hard_problems_ii.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    int[][] grid1 = {
        {1, 0},
        {0, 1}
    };
    int expected1 = 3;
    assertEquals(expected1, sol.largestIsland(grid1),
        "Example 1: Flipping one 0 connects two 1s, max area = 3");

    // --- Test 2: Example 2 ---
    int[][] grid2 = {
        {1, 1},
        {1, 1}
    };
    int expected2 = 4;
    assertEquals(expected2, sol.largestIsland(grid2),
        "Example 2: Already full of 1s, largest island = 4");

    // --- Test 3: Example 3 ---
    int[][] grid3 = {
        {1, 1},
        {1, 0}
    };
    int expected3 = 4;
    assertEquals(expected3, sol.largestIsland(grid3),
        "Example 3: Changing single 0 connects entire island of 4 cells");

    // --- Test 4: Single cell grid (0) ---
    int[][] grid4 = {
        {0}
    };
    int expected4 = 1;
    assertEquals(expected4, sol.largestIsland(grid4),
        "Single 0 grid: flipping 0 gives single island of size 1");

    // --- Test 5: Single cell grid (1) ---
    int[][] grid5 = {
        {1}
    };
    int expected5 = 1;
    assertEquals(expected5, sol.largestIsland(grid5),
        "Single 1 grid: already one island of size 1");

    // --- Test 6: Line grid with alternate pattern ---
    int[][] grid6 = {
        {1, 0, 1, 0, 1}
    };
    int expected6 = 3;
    assertEquals(expected6, sol.largestIsland(grid6),
        "Alternate 1-0 pattern: best flip connects two adjacent 1s");

    // --- Test 7: Cross shape ---
    int[][] grid7 = {
        {0, 1, 0},
        {1, 0, 1},
        {0, 1, 0}
    };
    int expected7 = 5;
    assertEquals(expected7, sol.largestIsland(grid7),
        "Cross pattern: flipping center 0 connects all arms");

    // --- Test 8: Checkerboard 4x4 ---
    int[][] grid8 = {
        {1, 0, 1, 0},
        {0, 1, 0, 1},
        {1, 0, 1, 0},
        {0, 1, 0, 1}
    };
    int expected8 = 5;
    assertEquals(expected8, sol.largestIsland(grid8),
        "Checkerboard 4x4: flipping one 0 connects 5 total cells");

    // --- Test 9: All zeros grid ---
    int[][] grid9 = {
        {0, 0},
        {0, 0}
    };
    int expected9 = 1;
    assertEquals(expected9, sol.largestIsland(grid9),
        "All 0 grid: flipping any one cell creates a single island of size 1");

    // --- Test 10: Large uneven island ---
    int[][] grid10 = {
        {1, 1, 0, 0},
        {1, 0, 0, 1},
        {0, 0, 1, 1},
        {0, 1, 1, 0}
    };
    int expected10 = 6;
    assertEquals(expected10, sol.largestIsland(grid10),
        "Larger irregular grid: best flip connects islands to size 7");
  }
}

class Solution {

  public int largestIsland(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    DisjointSet disjointSet = new DisjointSet(m * n);

    int max = Integer.MIN_VALUE;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == 1) {
          int currentIsland = calculateIslandNumber(i, j, n);
          // Top
          merge(i - 1, j, m, n, currentIsland, grid, disjointSet);

          // Left
          merge(i, j - 1, m, n, currentIsland, grid, disjointSet);

          max = Math.max(max, disjointSet.numberOfNodes(currentIsland));
        }
      }
    }

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        Set<Integer> uniqueUltimateParents = new HashSet<>();
        if (grid[i][j] == 0) {
          uniqueUltimateParents.add(findUltimateParent(i + 1, j, m, n, grid, disjointSet));

          uniqueUltimateParents.add(findUltimateParent(i - 1, j, m, n, grid, disjointSet));

          uniqueUltimateParents.add(findUltimateParent(i, j + 1, m, n, grid, disjointSet));

          uniqueUltimateParents.add(findUltimateParent(i, j - 1, m, n, grid, disjointSet));
        }

        uniqueUltimateParents.remove(-1); // invalid parent

        int size = 1; // for the newly added
        for (Integer uniqueUltimateParent : uniqueUltimateParents) {
          size += disjointSet.numberOfNodes(uniqueUltimateParent);
        }

        max = Math.max(max, size);
      }
    }

    return max;
  }

  private void merge(int i, int j, int m, int n, int currentIsland, int[][] grid,
      DisjointSet disjointSet) {
    if (i >= 0 && i < m && j >= 0 && j < n) {
      if (grid[i][j] == 1) {
        disjointSet.union(currentIsland, calculateIslandNumber(i, j, n));
      }
    }
  }

  private int findUltimateParent(int i, int j, int m, int n, int[][] grid,
      DisjointSet disjointSet) {
    if (i >= 0 && i < m && j >= 0 && j < n) {
      if (grid[i][j] == 1) {
        return disjointSet.findUltimateParent(calculateIslandNumber(i, j, n));
      }
    }

    return -1;
  }

  private int calculateIslandNumber(int i, int j, int c) {
    return (i * c) + j;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] sizes;

    DisjointSet(int n) {
      parents = new int[n];
      sizes = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }

      Arrays.fill(sizes, 1);
    }

    public boolean union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return false;
      }

      if (sizes[uUltimateParent] < sizes[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
        sizes[vUltimateParent] += sizes[uUltimateParent];
      } else if (sizes[vUltimateParent] < sizes[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
        sizes[uUltimateParent] += sizes[vUltimateParent];
      } else {
        parents[vUltimateParent] = uUltimateParent;
        sizes[uUltimateParent] += sizes[vUltimateParent];
      }

      return true;
    }

    public int numberOfNodes(int u) {
      int ultimateParent = findUltimateParent(u);
      return sizes[ultimateParent];
    }

    private int findUltimateParent(int u) {
      Stack<Integer> stack = new Stack<>();

      int i = u;
      while (parents[i] != i) {
        stack.push(i);
        i = parents[i];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = i;
      }

      return i;
    }

  }
}

