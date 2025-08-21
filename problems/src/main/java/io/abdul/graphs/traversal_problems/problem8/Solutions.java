package io.abdul.graphs.traversal_problems.problem8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1
    int[][] grid1 = {
        {1, 1, 0, 0, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 1, 1},
        {0, 0, 0, 1, 1}
    };
    assertEquals(1, sol.countDistinctIslands(grid1), "Example 1 failed");

    // Example 2
    int[][] grid2 = {
        {1, 1, 0, 1, 1},
        {1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1},
        {1, 1, 0, 1, 1}
    };
    assertEquals(3, sol.countDistinctIslands(grid2), "Example 2 failed");

    // Example 3
    int[][] grid3 = {
        {1, 1, 0, 0, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 1, 1}
    };
    assertEquals(2, sol.countDistinctIslands(grid3), "Example 3 failed");

    // Edge case: empty grid (all 0s)
    int[][] grid4 = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0}
    };
    assertEquals(0, sol.countDistinctIslands(grid4), "All zeros failed");

    // Edge case: single cell island
    int[][] grid5 = {
        {1}
    };
    assertEquals(1, sol.countDistinctIslands(grid5), "Single cell island failed");

    // Edge case: single row with multiple islands
    int[][] grid6 = {
        {1, 0, 1, 0, 1}
    };
    assertEquals(1, sol.countDistinctIslands(grid6), "Single row multiple islands failed");

    // Edge case: single column with multiple islands
    int[][] grid7 = {
        {1},
        {0},
        {1},
        {0},
        {1}
    };
    assertEquals(1, sol.countDistinctIslands(grid7), "Single column multiple islands failed");

    // Distinct shapes (L-shape vs line)
    int[][] grid8 = {
        {1, 1, 0},
        {1, 0, 0},
        {0, 0, 1}
    };
    assertEquals(2, sol.countDistinctIslands(grid8), "Distinct shapes failed");

    // Multiple identical islands in different places
    int[][] grid9 = {
        {1, 1, 0, 1, 1},
        {1, 0, 0, 1, 0},
        {0, 0, 0, 1, 1}
    };
    assertEquals(2, sol.countDistinctIslands(grid9), "Identical islands failed");
  }
}

/*
Using DFS
T - O(n*m* log(n*m)) - For each n*m element, hashset contains call is made
S - O(n*m) - copy of grid; hashset to store islands

How to match the shape?
By positions!
Order doesn't matter, but we need to convert all positions to zero base to map the shape

Let's say we've (0,0), (0,1), (1,0) & (3,3), (2,4), (2,3)
1st set is already zero based
2nd set - smallest of all (2,3) - (1,0), (0,1), (0,0)
Now it's matching

How to get same start of all shapes? Bcz first position is used as base in both shapes
Iterate from left to right, top to bottom. We'll hit same first position for all shapes.

(0,1), (1,0), (1,1), (1,2), (2,1) -> Plus symbol
(2,3), (3,2), (3,3), (3,4), (4,3)

(0,0), (1,-1), (1,0), (1,1), (2,0)
(0,0), (1,-1), (1,0), (1,1), (2,0)
 */

class Solution {

  public int countDistinctIslands(int[][] grid) {
    grid = copy(grid);
    int n = grid.length;
    int m = grid[0].length;

    Set<Set<Pair>> islands = new HashSet<>();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          Set<Pair> island = dfs(i, j, n, m, grid);
          if (!exists(islands, island)) {
            islands.add(island);
          }
        }
      }
    }

    return islands.size();
  }

  private boolean exists(Set<Set<Pair>> islands, Set<Pair> island) {
    for (Set<Pair> existingIsland : islands) {
      if (existingIsland.size() == island.size() && existingIsland.containsAll(island)) {
        return true;
      }
    }

    return false;
  }

  private Set<Pair> dfs(int start, int end, int n, int m, int[][] grid) {
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(start, end));

    Set<Pair> island = new HashSet<>();

    while (!stack.isEmpty()) {
      Pair p = stack.pop();
      int i = p.i;
      int j = p.j;

      if (isValid(i, j, n, m) && grid[i][j] == 1) {
        grid[i][j] = -1;
        island.add(new Pair(i - start, j - end));

        stack.push(new Pair(i - 1, j));
        stack.push(new Pair(i, j - 1));
        stack.push(new Pair(i + 1, j));
        stack.push(new Pair(i, j + 1));
      }
    }

    return island;
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

  public int countDistinctIslands(int[][] grid) {
    grid = copy(grid);
    int n = grid.length;
    int m = grid[0].length;

    Set<Set<Pair>> islands = new HashSet<>();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          Set<Pair> island = bfs(i, j, n, m, grid);
          if (!exists(islands, island)) {
            islands.add(island);
          }
        }
      }
    }

    return islands.size();
  }

  private boolean exists(Set<Set<Pair>> islands, Set<Pair> island) {
    for (Set<Pair> existingIsland : islands) {
      if (existingIsland.size() == island.size() && existingIsland.containsAll(island)) {
        return true;
      }
    }

    return false;
  }

  private Set<Pair> bfs(int start, int end, int n, int m, int[][] grid) {
    Set<Pair> island = new HashSet<>();

    Queue<Pair> queue = new LinkedList<>();
    queue.add(new Pair(start, end));

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        Pair p = queue.poll();
        int i = p.i;
        int j = p.j;
        if (isValid(i, j, n, m) && grid[i][j] == 1) {
          grid[i][j] = -1;
          island.add(new Pair(i - start, j - end));

          queue.add(new Pair(i - 1, j));
          queue.add(new Pair(i, j - 1));
          queue.add(new Pair(i + 1, j));
          queue.add(new Pair(i, j + 1));
        }
      }
    }

    return island;
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

class Solution3 {

  public int countDistinctIslands(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];

    Set<Set<Pair>> islands = new HashSet<>();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1 && !visited[i][j]) {
          Set<Pair> island = bfs(i, j, n, m, grid, visited);
          if (!exists(islands, island)) {
            islands.add(island);
          }
        }
      }
    }

    return islands.size();
  }

  private boolean exists(Set<Set<Pair>> islands, Set<Pair> island) {
    for (Set<Pair> existingIsland : islands) {
      if (existingIsland.size() == island.size() && existingIsland.containsAll(island)) {
        return true;
      }
    }

    return false;
  }

  private Set<Pair> bfs(int startI, int startJ, int n, int m, int[][] grid, boolean[][] visited) {
    Set<Pair> island = new HashSet<>();

    Queue<Pair> queue = new LinkedList<>();
    queue.add(new Pair(startI, startJ)); // visit
    visited[startI][startJ] = true;

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        Pair p = queue.poll(); // process
        int i = p.i;
        int j = p.j;
        island.add(new Pair(i - startI, j - startJ));

        visit(i - 1, j, n, m, grid, visited, queue);

        visit(i, j + 1, n, m, grid, visited, queue);

        visit(i + 1, j, n, m, grid, visited, queue);

        visit(i, j - 1, n, m, grid, visited, queue);
      }
    }

    return island;
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
