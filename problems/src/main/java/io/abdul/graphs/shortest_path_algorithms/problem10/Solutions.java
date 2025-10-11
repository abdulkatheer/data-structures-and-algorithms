package io.abdul.graphs.shortest_path_algorithms.problem10;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    int N1 = 4, M1 = 4;
    int[][] edges1 = {{0, 1, 3}, {1, 2, 1}, {1, 3, 4}, {2, 3, 1}};
    int threshold1 = 4;
    int expected1 = 3; // city 0 and 3 have 2 neighbors, choose largest
    assertEquals(expected1, sol.findCity(N1, M1, edges1, threshold1),
        "Example 1: Multiple cities with min neighbors, pick largest");

    // --- Test 2: Example 2 ---
    int N2 = 3, M2 = 2;
    int[][] edges2 = {{0, 1, 1}, {0, 2, 3}};
    int threshold2 = 2;
    int expected2 = 2; // city 2 has 0 neighbors within threshold
    assertEquals(expected2, sol.findCity(N2, M2, edges2, threshold2),
        "Example 2: City with no reachable cities within threshold");

    // --- Test 3: Example 3 ---
    int N3 = 3, M3 = 3;
    int[][] edges3 = {{0, 1, 2}, {1, 2, 1}, {0, 2, 4}};
    int threshold3 = 2;
    int expected3 = 2; // city 0 has only 1 reachable city (1) within threshold
    assertEquals(expected3, sol.findCity(N3, M3, edges3, threshold3),
        "Example 3: Smallest number of reachable cities");

    // --- Test 4: All cities isolated ---
    int N4 = 4, M4 = 0;
    int[][] edges4 = {};
    int threshold4 = 10;
    int expected4 = 3; // all cities isolated, pick largest
    assertEquals(expected4, sol.findCity(N4, M4, edges4, threshold4),
        "All cities isolated: pick largest");

    // --- Test 5: Fully connected with equal weights ---
    int N5 = 5, M5 = 10;
    int[][] edges5 = {{0, 1, 2}, {0, 2, 2}, {0, 3, 2}, {0, 4, 2}, {1, 2, 2}, {1, 3, 2}, {1, 4, 2},
        {2, 3, 2}, {2, 4, 2}, {3, 4, 2}};
    int threshold5 = 2;
    int expected5 = 4; // all cities have 4 neighbors, pick largest
    assertEquals(expected5, sol.findCity(N5, M5, edges5, threshold5),
        "Fully connected: all have same neighbors, pick largest");

    // --- Test 6: Threshold smaller than all edges ---
    int N6 = 3, M6 = 3;
    int[][] edges6 = {{0, 1, 5}, {1, 2, 6}, {0, 2, 7}};
    int threshold6 = 4;
    int expected6 = 2; // no city reachable, pick largest
    assertEquals(expected6, sol.findCity(N6, M6, edges6, threshold6),
        "Threshold smaller than all edges: all isolated, pick largest");
  }
}

class Solution {

  public int findCity(int n, int m, int edges[][], int distanceThreshold) {
    int[][] matrix = toAdjacencyMatrix(n, edges);

    shortestPath(n, matrix);

    int smallestCount = Integer.MAX_VALUE;

    int answer = -1;
    for (int i = 0; i < n; i++) {
      int[] shortestPaths = matrix[i];
      int count = 0;
      for (int j = 0; j < n; j++) {
        if (i == j) {
          continue;
        }
        int shortestPath = shortestPaths[j];
        if (shortestPath != -1 && shortestPath <= distanceThreshold) {
          count++;
        }
      }
      smallestCount = Math.min(smallestCount, count);

      // We've smallest so far, and we'll see what's the matching node
      // We need larger no, so latest update will be our answer
      if (smallestCount == count) {
        answer = i;
      }
    }

    return answer; // won't come here
  }

  private int[][] toAdjacencyMatrix(int n, int[][] edges) {
    int[][] matrix = new int[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j) {
          continue;
        }
        matrix[i][j] = -1;
      }
    }

    for (int[] edge : edges) {
      int v1 = edge[0];
      int v2 = edge[1];
      int weight = edge[2];
      matrix[v1][v2] = weight;
      matrix[v2][v1] = weight;
    }

    return matrix;
  }

  // Floyd Warshall Algorithm
  private void shortestPath(int n, int[][] matrix) {
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i == j) {
            continue;
          }

          if (matrix[i][k] == -1 || matrix[k][j] == -1) {
            continue;
          } else if (matrix[i][j] == -1) {
            matrix[i][j] = matrix[i][k] + matrix[k][j];
          } else {
            matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
          }
        }
      }
    }
  }
}

