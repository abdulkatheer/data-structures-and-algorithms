package io.abdul.graphs.traversal_problems.problem6;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1
    int[][] grid1 = {
        {0, 1, 1, 0},
        {1, 1, 0, 0},
        {0, 0, 1, 1}
    };
    int[][] expected1 = {
        {1, 0, 0, 1},
        {0, 0, 1, 1},
        {1, 1, 0, 0}
    };
    assertTrue(Arrays.deepEquals(expected1, sol.nearest(grid1)), "Example 1 failed");

    // Example 2
    int[][] grid2 = {
        {1, 0, 1},
        {1, 1, 0},
        {1, 0, 0}
    };
    int[][] expected2 = {
        {0, 1, 0},
        {0, 0, 1},
        {0, 1, 2}
    };
    assertTrue(Arrays.deepEquals(expected2, sol.nearest(grid2)), "Example 2 failed");

    // Example 3
    int[][] grid3 = {
        {0, 1},
        {1, 0}
    };
    int[][] expected3 = {
        {1, 0},
        {0, 1}
    };
    assertTrue(Arrays.deepEquals(expected3, sol.nearest(grid3)), "Example 3 failed");

    // Edge case: single cell with 1
    int[][] grid4 = {
        {1}
    };
    int[][] expected4 = {
        {0}
    };
    assertTrue(Arrays.deepEquals(expected4, sol.nearest(grid4)), "Single cell with 1 failed");

    // Edge case: single row
    int[][] grid5 = {
        {0, 0, 1, 0, 0}
    };
    int[][] expected5 = {
        {2, 1, 0, 1, 2}
    };
    assertTrue(Arrays.deepEquals(expected5, sol.nearest(grid5)), "Single row failed");

    // Edge case: single column
    int[][] grid6 = {
        {0},
        {0},
        {1},
        {0}
    };
    int[][] expected6 = {
        {2},
        {1},
        {0},
        {1}
    };
    assertTrue(Arrays.deepEquals(expected6, sol.nearest(grid6)), "Single column failed");

    // Edge case: all ones
    int[][] grid7 = {
        {1, 1},
        {1, 1}
    };
    int[][] expected7 = {
        {0, 0},
        {0, 0}
    };
    assertTrue(Arrays.deepEquals(expected7, sol.nearest(grid7)), "All ones failed");
  }
}

/*
Similar to rotten eggs, but we need to update time in each cell
Initialise cost for all 1's to 0
Then when we make 0's to 1's we add up cost of current node +1
 */
class Solution {

  public int[][] nearest(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] result = new int[n][m];

    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 0) {
          result[i][j] = -1; // marker for 0
        } else {
          queue.add(new int[]{i, j});
        }
      }
    }

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        int[] pair = queue.poll();
        int i = pair[0];
        int j = pair[1];

        if (isValid(i - 1, j, n, m) && result[i - 1][j] == -1) {
          result[i - 1][j] = result[i][j] + 1;
          queue.add(new int[]{i - 1, j});
        }

        if (isValid(i + 1, j, n, m) && result[i + 1][j] == -1) {
          result[i + 1][j] = result[i][j] + 1;
          queue.add(new int[]{i + 1, j});
        }

        if (isValid(i, j - 1, n, m) && result[i][j - 1] == -1) {
          result[i][j - 1] = result[i][j] + 1;
          queue.add(new int[]{i, j - 1});
        }

        if (isValid(i, j + 1, n, m) && result[i][j + 1] == -1) {
          result[i][j + 1] = result[i][j] + 1;
          queue.add(new int[]{i, j + 1});
        }
      }
    }

    return result;
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }
}

/*
Getting TLE
In Solution1, as we check, process and then add to queue, we visit each node only once
But in Solution2, we add all adjacent nodes to queue, then check and process. Here as we start with multiple different nodes in queue, same node might come as adjacent for another node in 3rd level
In same level, we may add same node multiple times as while adding we don't set any flag that it's been added to queue already. This is common issue in BFS when we process while taking off the queue. This is why we process before adding to the queue.
 */
class Solution2 {

  public int[][] nearest(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] result = new int[n][m];

    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 0) {
          result[i][j] = -1; // marker for 0
        } else {
          queue.add(new int[]{i, j});
        }
      }
    }

    int cost = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        int[] pair = queue.poll();
        int i = pair[0];
        int j = pair[1];
        // if not processed already, bcz after adding to queue it might have been processed
        // Also we don't stop adding its adjacent nodes as we've to travel further to update count for all zeros
        if (result[i][j] == -1) {
          result[i][j] = cost;
        }

        if (isValid(i - 1, j, n, m) && result[i - 1][j] == -1) {
          queue.add(new int[]{i - 1, j});
        }

        if (isValid(i + 1, j, n, m) && result[i + 1][j] == -1) {
          queue.add(new int[]{i + 1, j});
        }

        if (isValid(i, j - 1, n, m) && result[i][j - 1] == -1) {
          queue.add(new int[]{i, j - 1});
        }

        if (isValid(i, j + 1, n, m) && result[i][j + 1] == -1) {
          queue.add(new int[]{i, j + 1});
        }
      }

      cost++;
    }

    return result;
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }
}

class Solution3 {

  public int[][] nearest(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] result = new int[n][m];
    boolean[][] visited = new boolean[n][m];

    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          queue.add(new int[]{i, j}); // visit
          visited[i][j] = true;
        }
      }
    }

    int cost = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        int[] pair = queue.poll(); // process
        int i = pair[0];
        int j = pair[1];
        result[i][j] = cost;

        visit(i - 1, j, n, m, visited, queue);

        visit(i, j + 1, n, m, visited, queue);

        visit(i + 1, j, n, m, visited, queue);

        visit(i, j - 1, n, m, visited, queue);
      }

      cost++;
    }

    return result;
  }

  private void visit(int i, int j, int n, int m, boolean[][] visited, Queue<int[]> queue) {
    if (isValid(i, j, n, m) && !visited[i][j]) {
      visited[i][j] = true;
      queue.add(new int[]{i, j});
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }
}