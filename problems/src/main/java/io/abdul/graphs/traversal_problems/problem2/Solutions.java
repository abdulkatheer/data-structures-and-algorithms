package io.abdul.graphs.traversal_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    char[][] grid1 = {
        {'1', '1', '1', '0', '1'},
        {'1', '0', '0', '0', '0'},
        {'1', '1', '1', '0', '1'},
        {'0', '0', '0', '1', '1'}
    };
    assertEquals(2, sol.numIslands(grid1), "Example 1 failed");

    // Example 2
    char[][] grid2 = {
        {'1', '0', '0', '0', '1'},
        {'0', '1', '0', '1', '0'},
        {'0', '0', '1', '0', '0'},
        {'0', '1', '0', '1', '0'}
    };
    assertEquals(1, sol.numIslands(grid2), "Example 2 failed");

    // Example 3
    char[][] grid3 = {
        {'1', '1', '1', '1', '0'},
        {'1', '1', '0', '1', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '0', '0', '0'}
    };
    assertEquals(1, sol.numIslands(grid3), "Example 3 failed");

    // Edge case: single cell land
    char[][] grid4 = {
        {'1'}
    };
    assertEquals(1, sol.numIslands(grid4), "Single land cell failed");

    // Edge case: single cell water
    char[][] grid5 = {
        {'0'}
    };
    assertEquals(0, sol.numIslands(grid5), "Single water cell failed");

    // Edge case: disconnected lands
    char[][] grid6 = {
        {'1', '0', '1'},
        {'0', '0', '0'},
        {'1', '0', '1'}
    };
    assertEquals(4, sol.numIslands(grid6), "Disconnected lands failed");

    // Edge case: all land
    char[][] grid7 = {
        {'1', '1'},
        {'1', '1'}
    };
    assertEquals(1, sol.numIslands(grid7), "All land failed");
  }
}

/*
Recursion
T - O(n*m) - each node visited once
S - O(n*m) - visited array; stack
 */
class Solution {

  public int numIslands(char[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];

    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == '1' && !visited[i][j]) {
          traverseIsland(i, j, n, m, grid, visited);
          count++;
        }
      }
    }

    return count;
  }

  private void traverseIsland(int i, int j, int n, int m, char[][] grid, boolean[][] visited) {
    if (i < 0 || i >= n || j < 0 || j >= m) { // Invalid route
      return;
    }

    if (visited[i][j]) { // already visited
      return;
    }

    if (grid[i][j] == '0') { // No island
      return;
    }

    visited[i][j] = true;

    // Left top
    traverseIsland(i - 1, j - 1, n, m, grid, visited);

    // Top
    traverseIsland(i - 1, j, n, m, grid, visited);

    // Right top
    traverseIsland(i - 1, j + 1, n, m, grid, visited);

    // Right
    traverseIsland(i, j + 1, n, m, grid, visited);

    // Right bottom
    traverseIsland(i + 1, j + 1, n, m, grid, visited);

    // Bottom
    traverseIsland(i + 1, j, n, m, grid, visited);

    // Left Bottom
    traverseIsland(i + 1, j - 1, n, m, grid, visited);

    // Left
    traverseIsland(i, j - 1, n, m, grid, visited);
  }
}

/*
Iterative
T - O(n*m) - each node visited once
S - O(n*m) - Stack
 */
class Solution2 {

  public int numIslands(char[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];

    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == '1' && !visited[i][j]) {
          traverseIsland(i, j, n, m, grid, visited);
          count++;
        }
      }
    }

    return count;
  }

  private void traverseIsland(int startI, int startJ, int n, int m, char[][] grid,
      boolean[][] visited) {
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(startI, startJ));

    while (!stack.isEmpty()) {
      Pair p = stack.pop();
      int i = p.i;
      int j = p.j;
      if (isValidIndex(i, j, n, m) && !visited[i][j] && grid[i][j] == '1') {
        visited[i][j] = true;

        // Left top
        stack.push(new Pair(i - 1, j - 1));

        // Top
        stack.push(new Pair(i - 1, j));

        // Right top
        stack.push(new Pair(i + 1, j - 1));

        // Right
        stack.push(new Pair(i, j + 1));

        // Right bottom
        stack.push(new Pair(i + 1, j + 1));

        // Bottom
        stack.push(new Pair(i + 1, j));

        // Left bottom
        stack.push(new Pair(i - 1, j + 1));

        // Left
        stack.push(new Pair(i, j - 1));
      }
    }
  }

  private boolean isValidIndex(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private record Pair(int i, int j) {

  }
}
