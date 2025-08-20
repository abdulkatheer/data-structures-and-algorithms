package io.abdul.graphs.traversal_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    int[][] grid1 = {
        {0, 0, 0, 0},
        {1, 0, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    };
    assertEquals(3, sol.numberOfEnclaves(grid1), "Example 1 failed");

    // Example 2
    int[][] grid2 = {
        {0, 0, 0, 1},
        {0, 0, 0, 1},
        {0, 1, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 0}
    };
    assertEquals(3, sol.numberOfEnclaves(grid2), "Example 2 failed");

    // Example 3
    int[][] grid3 = {
        {0, 0, 0, 1},
        {0, 1, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    };
    assertEquals(4, sol.numberOfEnclaves(grid3), "Example 3 failed");

    // Edge case: all water
    int[][] grid4 = {
        {0, 0},
        {0, 0}
    };
    assertEquals(0, sol.numberOfEnclaves(grid4), "All water case failed");

    // Edge case: all land touching boundary (no enclaves)
    int[][] grid5 = {
        {1, 1},
        {1, 1}
    };
    assertEquals(0, sol.numberOfEnclaves(grid5), "All land touching boundary case failed");

    // Edge case: single enclave in center
    int[][] grid6 = {
        {0, 0, 0},
        {0, 1, 0},
        {0, 0, 0}
    };
    assertEquals(1, sol.numberOfEnclaves(grid6), "Single enclave case failed");

    // Edge case: large strip of land but all touching boundary
    int[][] grid7 = {
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1}
    };
    assertEquals(0, sol.numberOfEnclaves(grid7), "Large boundary-touching land failed");

    // Edge case: multiple small enclaves
    int[][] grid8 = {
        {0, 0, 0, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 0, 0, 0}
    };
    assertEquals(2, sol.numberOfEnclaves(grid8), "Multiple enclaves case failed");
  }
}

/*
Using DFS - recursive
T - O(n*m) - n*m to copy, n*m to DFS, n*m to count
S - O(n*m) - for the grid
 */
class Solution {

  public int numberOfEnclaves(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] gridCopy = new int[n][m];
    for (int i = 0; i < n; i++) {
      System.arraycopy(grid[i], 0, gridCopy[i], 0, m);
    }

    grid = gridCopy;

    // run through the boundary
    // top
    for (int i = 0; i < m; i++) {
      dfs(0, i, n, m, grid);
    }

    for (int i = 0; i < n; i++) {
      dfs(i, m - 1, n, m, grid);
    }

    for (int i = 0; i < m; i++) {
      dfs(n - 1, i, n, m, grid);
    }

    for (int i = 0; i < n; i++) {
      dfs(i, 0, n, m, grid);
    }

    // run through grid and count left outs
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          count++;
        }
      }
    }

    return count;
  }

  private void dfs(int i, int j, int n, int m, int[][] grid) {
    if (i < 0 || i >= n || j < 0 || j >= m) {
      return;
    }

    if (grid[i][j] == 0) {
      return;
    }

    if (grid[i][j] == -1) {
      return;
    }

    grid[i][j] = -1;

    // Top
    dfs(i - 1, j, n, m, grid);

    // Right
    dfs(i, j + 1, n, m, grid);

    // Bottom
    dfs(i + 1, j, n, m, grid);

    // Left
    dfs(i, j - 1, n, m, grid);
  }
}

/*
Using DFS - iterative
T - O(n*m) - n*m to copy, n*m to DFS, n*m to count
S - O(n*m) - for the grid
 */
class Solution2 {

  public int numberOfEnclaves(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] gridCopy = new int[n][m];
    for (int i = 0; i < n; i++) {
      System.arraycopy(grid[i], 0, gridCopy[i], 0, m);
    }

    grid = gridCopy;

    // run through the boundary
    // top
    for (int i = 0; i < m; i++) {
      dfs(0, i, n, m, grid);
    }

    for (int i = 0; i < n; i++) {
      dfs(i, m - 1, n, m, grid);
    }

    for (int i = 0; i < m; i++) {
      dfs(n - 1, i, n, m, grid);
    }

    for (int i = 0; i < n; i++) {
      dfs(i, 0, n, m, grid);
    }

    // run through grid and count left outs
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          count++;
        }
      }
    }

    return count;
  }

  private void dfs(int startI, int startJ, int n, int m, int[][] grid) {
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(startI, startJ));

    while (!stack.isEmpty()) {
      Pair p = stack.pop();
      int i = p.i;
      int j = p.j;
      if (isValid(i, j, n, m) && grid[i][j] == 1) {
        grid[i][j] = -1;

        stack.push(new Pair(i - 1, j));

        stack.push(new Pair(i, j + 1));

        stack.push(new Pair(i + 1, j));

        stack.push(new Pair(i, j - 1));
      }
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private record Pair(int i, int j) {

  }
}