package io.abdul.graphs.hard_problems_ii.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    int n1 = 4, m1 = 5;
    int[][] A1 = {{1, 1}, {0, 1}, {3, 3}, {3, 4}};
    List<Integer> expected1 = Arrays.asList(1, 1, 2, 2);
    assertEquals(expected1, sol.numOfIslands(n1, m1, A1),
        "Example 1: Basic case with disconnected regions");

    // --- Test 2: Example 2 (complex merging) ---
    int n2 = 4, m2 = 5;
    int[][] A2 = {
        {0, 0}, {0, 0}, {1, 1}, {1, 0}, {0, 1},
        {0, 3}, {1, 3}, {0, 4}, {3, 2}, {2, 2}, {1, 2}, {0, 2}
    };
    List<Integer> expected2 = Arrays.asList(1, 1, 2, 1, 1, 2, 2, 2, 3, 3, 1, 1);
    assertEquals(expected2, sol.numOfIslands(n2, m2, A2),
        "Example 2: Large sequence with merges and duplicates");

    // --- Test 3: Example 3 (2x2 grid fully filled) ---
    int n3 = 2, m3 = 2;
    int[][] A3 = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    List<Integer> expected3 = Arrays.asList(1, 1, 1, 1);
    assertEquals(expected3, sol.numOfIslands(n3, m3, A3),
        "Example 3: Gradual filling of 2x2 grid merges into single island");

    // --- Test 4: Single cell grid ---
    int n4 = 1, m4 = 1;
    int[][] A4 = {{0, 0}};
    List<Integer> expected4 = Arrays.asList(1);
    assertEquals(expected4, sol.numOfIslands(n4, m4, A4),
        "Single cell grid should result in 1 island after first addition");

    // --- Test 5: Repeated operations on same cell ---
    int n5 = 3, m5 = 3;
    int[][] A5 = {{0, 0}, {0, 0}, {1, 1}, {1, 1}, {2, 2}};
    List<Integer> expected5 = Arrays.asList(1, 1, 2, 2, 3);
    assertEquals(expected5, sol.numOfIslands(n5, m5, A5),
        "Repeated land additions to same cell should not change count");

    // --- Test 6: All separate cells no adjacency ---
    int n6 = 3, m6 = 3;
    int[][] A6 = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
    List<Integer> expected6 = Arrays.asList(1, 2, 3, 4);
    assertEquals(expected6, sol.numOfIslands(n6, m6, A6),
        "Non-adjacent lands form distinct islands");

    // --- Test 7: Horizontal merging ---
    int n7 = 1, m7 = 5;
    int[][] A7 = {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}};
    List<Integer> expected7 = Arrays.asList(1, 1, 1, 1, 1);
    assertEquals(expected7, sol.numOfIslands(n7, m7, A7),
        "Continuous horizontal cells merge into one island");

    // --- Test 8: Vertical merging ---
    int n8 = 5, m8 = 1;
    int[][] A8 = {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}};
    List<Integer> expected8 = Arrays.asList(1, 1, 1, 1, 1);
    assertEquals(expected8, sol.numOfIslands(n8, m8, A8),
        "Continuous vertical cells merge into one island");

    // --- Test 9: Checkerboard pattern (no merges) ---
    int n9 = 3, m9 = 3;
    int[][] A9 = {{0, 0}, {0, 2}, {1, 1}, {2, 0}, {2, 2}};
    List<Integer> expected9 = Arrays.asList(1, 2, 3, 4, 5);
    assertEquals(expected9, sol.numOfIslands(n9, m9, A9),
        "Checkerboard pattern forms isolated islands");
  }
}

/*
m * n nodes

Node numbering: 4 * 5
0 1 2 3 4
5 6 7 8 9
10 11 12 13 14
15 16 17 18 19

(1,4) - 1*5 + 4 = 9
 */
class Solution {

  public List<Integer> numOfIslands(int rows, int columns, int[][] A) {
    boolean[][] matrix = new boolean[rows][columns];
    DisjointSet disjointSet = new DisjointSet(rows * columns);

    List<Integer> result = new ArrayList<>(A.length);
    int islands = 0;


    for (int[] island : A) {
      int row = island[0];
      int column = island[1];

      if (matrix[row][column]) {
        result.add(islands); // same count continues
        //already visited
        continue;
      }
      int currentIsland = calculateNode(row, column, columns);
      matrix[row][column] = true;

      islands++; // assuming that this is an independent island

      /*
      Let's say currently 4 islands exist and new one is assumed. So 5

      Case 1: No adjacent nodes or land, 5
      Case 2: All are land, and all are different components. -1, -1, -1, -1. So 1
      Case 3: 3 sides are water, 1 side is land. -1. So 4
      etc.,
      Original = 4
      when nothing is merged, 1 added -> 5
      when 1 is merged, count stays same -> 4
      when 2 is merged, count decreases by 1 -> 3
      when 3 is merged, count decreases by 2 -> 2
      when all 4 are merged, count decreases by 3 -> 1
       */
      if (merge(row + 1, column, rows, columns, matrix, currentIsland, disjointSet)) {
        islands--;
      }

      if (merge(row - 1, column, rows, columns, matrix, currentIsland, disjointSet)) {
        islands--;
      }

      if (merge(row, column + 1, rows, columns, matrix, currentIsland, disjointSet)) {
        islands--;
      }

      if (merge(row, column - 1, rows, columns, matrix, currentIsland, disjointSet)) {
        islands--;
      }

      result.add(islands);
    }

    return result;
  }

  private boolean merge(int i, int j, int n, int m, boolean[][] matrix, int currentIsland,
      DisjointSet disjointSet) {
    if (i >= 0 && i < n && j >= 0 && j < m) {
      if (matrix[i][j]) {
        if (disjointSet.union(currentIsland, calculateNode(i, j, m))) {
          // merging one existing island with current one, so number of islands reducing
          return true;
        }
      }
    }

    return false;
  }

  private int calculateNode(int row, int colum, int columns) {
    return (row * columns) + colum;
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

    public boolean union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return false;
      }

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }

      return true;
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


