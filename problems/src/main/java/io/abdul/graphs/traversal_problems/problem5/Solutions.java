package io.abdul.graphs.traversal_problems.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    int[][] grid1 = {
        {2, 1, 1},
        {0, 1, 1},
        {1, 0, 1}
    };
    assertEquals(-1, sol.orangesRotting(grid1), "Example 1 failed");

    // Example 2
    int[][] grid2 = {
        {2, 1, 1},
        {1, 1, 0},
        {0, 1, 1}
    };
    assertEquals(4, sol.orangesRotting(grid2), "Example 2 failed");

    // Example 3
    int[][] grid3 = {
        {0, 1, 2},
        {0, 1, 2},
        {2, 1, 1}
    };
    assertEquals(1, sol.orangesRotting(grid3), "Example 3 failed");

    // Edge case: all empty cells
    int[][] grid4 = {
        {0, 0},
        {0, 0}
    };
    assertEquals(0, sol.orangesRotting(grid4), "All empty cells failed");

    // Edge case: all fresh oranges but no rotten
    int[][] grid5 = {
        {1, 1},
        {1, 1}
    };
    assertEquals(-1, sol.orangesRotting(grid5), "All fresh oranges failed");

    // Edge case: all rotten oranges
    int[][] grid6 = {
        {2, 2},
        {2, 2}
    };
    assertEquals(0, sol.orangesRotting(grid6), "All rotten oranges failed");

    // Edge case: single fresh orange surrounded by rotten
    int[][] grid7 = {
        {2, 2, 2},
        {2, 1, 2},
        {2, 2, 2}
    };
    assertEquals(1, sol.orangesRotting(grid7), "Single surrounded fresh failed");

    // Edge case: large unreachable fresh orange
    int[][] grid8 = {
        {2, 0, 0},
        {0, 0, 0},
        {0, 0, 1}
    };
    assertEquals(-1, sol.orangesRotting(grid8), "Unreachable fresh orange failed");
  }
}

/*
If we go with DFS, we'll always end up getting max time.
But here as there can be many rotten eggs, it expands level by level.
 */
class Solution {

  public int orangesRotting(int[][] grid) {
    grid = copy(grid);

    Queue<Pair> queue = new LinkedList<>();
    int n = grid.length;
    int m = grid[0].length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 2) {
          queue.add(new Pair(i, j));
        }
      }
    }

    int count = bfs(grid, queue, n, m);

    for (int[] ints : grid) {
      for (int j = 0; j < m; j++) {
        if (ints[j] == 1) {
          return -1;
        }
      }
    }

    return count;
  }

  private int bfs(int[][] grid, Queue<Pair> queue, int n, int m) {
    int count = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();

      // Process all nodes at current level and add their valid adjacent nodes as next level
      for (int k = 0; k < size; k++) {
        Pair p = queue.poll();
        int i = p.i;
        int j = p.j;

        if (isValid(i - 1, j, n, m) && grid[i - 1][j] == 1) {
          grid[i - 1][j] = 2;
          queue.add(new Pair(i - 1, j));
        }

        if (isValid(i, j + 1, n, m) && grid[i][j + 1] == 1) {
          grid[i][j + 1] = 2;
          queue.add(new Pair(i, j + 1));
        }

        if (isValid(i + 1, j, n, m) && grid[i + 1][j] == 1) {
          grid[i + 1][j] = 2;
          queue.add(new Pair(i + 1, j));
        }

        if (isValid(i, j - 1, n, m) && grid[i][j - 1] == 1) {
          grid[i][j - 1] = 2;
          queue.add(new Pair(i, j - 1));
        }
      }

      // If at least 1 valid adjacent node exists, that means it took one min to rotten the eggs at this level
      if (!queue.isEmpty()) {
        count++;
      }
    }
    return count;
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private int[][] copy(int[][] src) {
    int[][] dest = new int[src.length][];
    for (int i = 0; i < src.length; i++) {
      dest[i] = new int[src[i].length];
      System.arraycopy(src[i], 0, dest[i], 0, dest[i].length);
    }

    return dest;
  }

  private record Pair(int i, int j) {

  }
}

class Solution2 {

  public int orangesRotting(int[][] grid) {
    boolean[][] visited = new boolean[grid.length][grid[0].length];

    Queue<Pair> queue = new LinkedList<>();
    int n = grid.length;
    int m = grid[0].length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 2) {
          queue.add(new Pair(i, j));
          visited[i][j] = true;
        }
      }
    }

    int count = bfs(grid, queue, n, m, visited);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1 && !visited[i][j]) {
          return -1;
        }
      }
    }

    return count;
  }

  private int bfs(int[][] grid, Queue<Pair> queue, int n, int m, boolean[][] visited) {
    int count = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();

      // Process all nodes at current level and add their valid adjacent nodes as next level
      for (int k = 0; k < size; k++) {
        Pair p = queue.poll();
        int i = p.i;
        int j = p.j;

        visit(i - 1, j, n, m, grid, visited, queue); // top

        visit(i, j + 1, n, m, grid, visited, queue); // right

        visit(i + 1, j, n, m, grid, visited, queue); // bottom

        visit(i, j - 1, n, m, grid, visited, queue); // left
      }

      // If at least 1 valid adjacent node exists, that means it took one min to rotten the eggs at this level
      if (!queue.isEmpty()) {
        count++;
      }
    }
    return count;
  }

  private void visit(int i, int j, int n, int m, int[][] grid, boolean[][] visited,
      Queue<Pair> queue) {
    if (isValid(i, j, n, m) && grid[i][j] == 1 && !visited[i][j]) {
      visited[i][j] = true;
      queue.add(new Pair(i, j));
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private record Pair(int i, int j) {

  }
}
