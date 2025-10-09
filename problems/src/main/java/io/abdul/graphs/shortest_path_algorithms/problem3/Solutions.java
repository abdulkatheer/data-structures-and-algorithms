package io.abdul.graphs.shortest_path_algorithms.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Typical reachable path ---
    int[][] grid1 = {
        {1, 1, 1, 1},
        {1, 1, 0, 1},
        {1, 1, 1, 1},
        {1, 1, 0, 0},
        {1, 0, 0, 1}
    };
    int[] source1 = {0, 1}, dest1 = {2, 2};
    assertEquals(3, sol.shortestPath(grid1, source1, dest1));

    // --- Test 2: No path exists ---
    int[][] grid2 = {
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 0},
        {1, 0, 1, 0, 1}
    };
    int[] source2 = {0, 0}, dest2 = {3, 4};
    assertEquals(-1, sol.shortestPath(grid2, source2, dest2));

    // --- Test 3: Small grid, reachable ---
    int[][] grid3 = {
        {1, 0, 1},
        {1, 1, 0},
        {1, 1, 1}
    };
    int[] source3 = {0, 0}, dest3 = {2, 2};
    assertEquals(4, sol.shortestPath(grid3, source3, dest3));

    // --- Test 4: Source equals destination ---
    int[][] grid4 = {
        {1, 1},
        {1, 1}
    };
    int[] source4 = {0, 0}, dest4 = {0, 0};
    assertEquals(0, sol.shortestPath(grid4, source4, dest4));

    // --- Test 5: Destination cell blocked ---
    int[][] grid5 = {
        {1, 1},
        {1, 0}
    };
    int[] source5 = {0, 0}, dest5 = {1, 1};
    assertEquals(-1, sol.shortestPath(grid5, source5, dest5));

    // --- Test 6: Fully open grid (straight path possible) ---
    int[][] grid6 = {
        {1, 1, 1},
        {1, 1, 1},
        {1, 1, 1}
    };
    int[] source6 = {0, 0}, dest6 = {2, 2};
    // Shortest path = down, down, right, right = 4
    assertEquals(4, sol.shortestPath(grid6, source6, dest6));
  }
}

class Solution {

  // Unit weights, so just BFS will work here
  int shortestPath(int[][] grid, int[] source, int[] destination) {
    int m = grid.length;
    int n = grid[0].length;
    Queue<int[]> q = new LinkedList<>();
    boolean[][] visited = new boolean[m][n];

    q.add(source);
    visited[source[0]][source[1]] = true;

    int level = 0;
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        int[] node = q.poll();
        int x = node[0];
        int y = node[1];
        if (x == destination[0] && y == destination[1]) { // destination reached
          return level;
        }

        // visit adjacent nodes
        // Top
        visit(x - 1, y, m, n, grid, q, visited);
        // Left
        visit(x, y - 1, m, n, grid, q, visited);
        // Bottom
        visit(x + 1, y, m, n, grid, q, visited);
        // Right
        visit(x, y + 1, m, n, grid, q, visited);
      }

      level++;
    }

    return -1; // couldn't reach destination
  }

  private void visit(int x, int y, int m, int n, int[][] grid, Queue<int[]> q,
      boolean[][] visited) {
    if (x >= 0 && x < m && y >= 0 && y < n) {
      if (grid[x][y] == 1 && !visited[x][y]) {
        q.add(new int[]{x, y});
        visited[x][y] = true;
      }
    }
  }
}
