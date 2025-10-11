package io.abdul.graphs.shortest_path_algorithms.problem9;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();
//    Solution3 sol = new Solution3();

    // --- Test 1: Example 1 ---
    int[][] matrix1 = {
        {0, 2, -1, -1},
        {1, 0, 3, -1},
        {-1, -1, 0, 1},
        {3, 5, 4, 0}
    };
    sol.shortestDistance(matrix1);
    int[][] expected1 = {
        {0, 2, 5, 6},
        {1, 0, 3, 4},
        {4, 6, 0, 1},
        {3, 5, 4, 0}
    };
    assertTrue(Arrays.deepEquals(expected1, matrix1),
        "Example 1: typical weighted directed graph with reachable paths");

    // --- Test 2: Example 2 ---
    int[][] matrix2 = {
        {0, 25},
        {-1, 0}
    };
    sol.shortestDistance(matrix2);
    int[][] expected2 = {
        {0, 25},
        {-1, 0}
    };
    assertTrue(Arrays.deepEquals(expected2, matrix2),
        "Example 2: already optimal paths, no changes");

    // --- Test 3: Example 3 ---
    int[][] matrix3 = {
        {0, 1, 43},
        {1, 0, 6},
        {-1, -1, 0}
    };
    sol.shortestDistance(matrix3);
    int[][] expected3 = {
        {0, 1, 7},
        {1, 0, 6},
        {-1, -1, 0}
    };
    assertTrue(Arrays.deepEquals(expected3, matrix3),
        "Example 3: indirect paths should reduce distance (0→1→2)");

    // --- Test 4: Disconnected graph ---
    int[][] matrix4 = {
        {0, -1, -1},
        {-1, 0, -1},
        {-1, -1, 0}
    };
    sol.shortestDistance(matrix4);
    int[][] expected4 = {
        {0, -1, -1},
        {-1, 0, -1},
        {-1, -1, 0}
    };
    assertTrue(Arrays.deepEquals(expected4, matrix4),
        "Disconnected graph: no edges, distances remain same");

    // --- Test 5: Graph with negative edge (but no cycle) ---
    int[][] matrix5 = {
        {0, 4, -1},
        {-1, 0, 2},
        {1, -1, 0}
    };
    sol.shortestDistance(matrix5);
    int[][] expected5 = {
        {0, 4, 6},
        {3, 0, 2},
        {1, 5, 0}
    };
    assertTrue(Arrays.deepEquals(expected5, matrix5),
        "Graph with negative edge (no cycle)");

    // --- Test 6: Single vertex graph ---
    int[][] matrix6 = {
        {0}
    };
    sol.shortestDistance(matrix6);
    int[][] expected6 = {
        {0}
    };
    assertTrue(Arrays.deepEquals(expected6, matrix6),
        "Single node graph should remain unchanged");

    // --- Test 7: Fully connected symmetric graph ---
    int[][] matrix7 = {
        {0, 2, 3},
        {2, 0, 1},
        {3, 1, 0}
    };
    sol.shortestDistance(matrix7);
    int[][] expected7 = {
        {0, 2, 3},
        {2, 0, 1},
        {3, 1, 0}
    };
    assertTrue(Arrays.deepEquals(expected7, matrix7),
        "Fully connected graph: already optimal distances");
  }
}

/*
Recursion

In order to find the shortest path from all vertices to all other vertices, we need to consider passing via all the nodes.

Let's say we've 4 vertices from 1 to 4
shortestPath from 1 to 4 will be minimum of shortestPath from 1 to 4 considering 3rd node and not considering 3rd node
shortestPath(i, j, k) = min ( shortestPath(i, j, k-1) , shortestPath(i, k, k-1) + shortestPath(k, j, k-1))
i -> source
j -> destination
k -> number of allowed intermediate vertices {1,2,...,k}, not necessarily using them all

shortestPath(i, j, k) means the shortestPath from i to j via k vertices {1,2,3,...,k}
shortestPath(i, j, k-1) means the shortestPathFrom i to j not considering k vertices, but k-1 vertices {1,2,3,..,k-1}
shortestPath(i, k, k-1) + shortestPath(k, j, k-1) means the shortestPathFrom i to j considering k vertices. from i to k and then we go from k to j
i → ... → k → ... → j
the portion before k (from i to k) and the portion after k (from k to j) must only use vertices from {1..k-1} (because k itself is already used).

when k == 0, meaning no intermediary is allowed. So direct edge only
 */
class Solution {

  // With only positive edges
  public void shortestDistance(int[][] matrix) {
    int n = matrix.length; // number of vertices {0,1,...n-1}

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = shortest(i, j, n - 1, matrix);
      }
    }
  }

  // shortest path from i to j with k number of allowed intermediary vertices
  private int shortest(int i, int j, int k, int[][] matrix) {
    if (k == -1) {
      // No intermediary allowed
      return matrix[i][j];
    }

    if (i == j) {
      return matrix[i][j];
    }

    // Using kth vertex somewhere in the middle of path from i to j
    int withK = -1; // i .... k .... j
    int withoutK = shortest(i, j, k - 1, matrix);

    int withKLeft = shortest(i, k, k - 1, matrix);
    int withKRight = shortest(k, j, k - 1, matrix);

    if (withKLeft != -1 && withKRight != -1) {
      withK = withKLeft + withKRight;
    }

    if (withK == -1 && withoutK == -1) {
      // No path in both
      return -1;
    } else if (withoutK == -1) {
      // No path considering k-1 vertices
      return withK;
    } else if (withK == -1) {
      return withoutK;
    } else {
      // Path with both cases exists, take min of them
      // min of considering k vertices and k-1 vertices
      return Math.min(withK, withoutK);
    }
  }
}

/*
Top-down with memoization
 */
class Solution2 {

  // With only positive edges
  public void shortestDistance(int[][] matrix) {
    int n = matrix.length; // number of vertices {0,1,...n-1}

    int[][][] dp = new int[n][n][n];
    for (int[][] arr1 : dp) {
      for (int[] arr2 : arr1) {
        Arrays.fill(arr2, -1);
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = shortest(i, j, n - 1, matrix, dp);
      }
    }
  }

  // shortest path from i to j with k number of allowed intermediary vertices
  private int shortest(int i, int j, int k, int[][] matrix, int[][][] dp) {
    if (k == -1) {
      // No intermediary allowed
      return matrix[i][j];
    }

    if (i == j) {
      return matrix[i][j];
    }

    if (dp[i][j][k] != -1) {
      return dp[i][j][k];
    }

    // Using kth vertex somewhere in the middle of path from i to j
    int withK = -1; // i .... k .... j
    int withoutK = shortest(i, j, k - 1, matrix, dp);

    int withKLeft = shortest(i, k, k - 1, matrix, dp);
    int withKRight = shortest(k, j, k - 1, matrix, dp);

    if (withKLeft != -1 && withKRight != -1) {
      withK = withKLeft + withKRight;
    }

    int shortestPath;
    if (withK == -1 && withoutK == -1) {
      // No path in both
      shortestPath = -1;
    } else if (withoutK == -1) {
      // No path considering k-1 vertices
      shortestPath = withK;
    } else if (withK == -1) {
      shortestPath = withoutK;
    } else {
      // Path with both cases exists, take min of them
      // min of considering k vertices and k-1 vertices
      shortestPath = Math.min(withK, withoutK);
    }

    dp[i][j][k] = shortestPath;
    return shortestPath;
  }
}

/*
DP - Iterative solution
Floyd Warshall Algorithm

How this DP recurrence is formed?

Assume n = 5, vertices = {1,2,3,4,5}
shortest(1,5,5) = Min ( shortest(1,5,4), shortest(1,5,4) + shortest(5,5,4) ) -- (1)
shortest(1,5,4) = Min ( shortest(1,5,3), shortest(1,4,3) + shortest(4,5,3) ) -- (1a)
shortest(1,5,3) = Min ( shortest(1,5,2), shortest(1,3,2) + shortest(3,5,2) ) -- (1aa)
shortest(1,5,2) = Min ( shortest(1,5,1), shortest(1,2,1) + shortest(2,5,1) ) -- (1aaa)
shortest(1,5,1) = Min ( shortest(1,5,0), shortest(1,1,0) + shortest(1,5,0) ) -- (1aaaa)
shortest(1,5,0) = X -- (1aaaaa)
shortest(1,1,0) = 0 -- (1aaaab)
shortest(1,5,0) = X -- (1aaaaa) repeats
shortest(1,5,1) = Min ( X, 0 + X ) meaning, to find a path from 1 to 5, we can either go directly from 1 to 5, or go from 1 to 1 (0) and go from 1 to 5 (X)
shortest(1,5,1) = X -- (1aaaa)

shortest(1,2,1) = Min ( shortest(1,2,0), shortest(1,1,0) + shortest(1,2,0) ) -- (1aaab)
shortest(1,2,0) = Y -- (1aaaba)
shortest(1,1,0) = 0 -- (1aaabb)
shortest(1,2,0) = Y -- (1aaaba) repeats
shortest(1,2,1) = Min ( Y, 0 + Y) ) -- (1aaab)
shortest(1,2,1) = Y -- (1aaab)

shortest(2,5,1) = Min ( shortest(2,5,0), shortest(2,1,0) + shortest(1,5,0) ) -- (1aaac)
shortest(2,5,0) = Z -- (1aaaca)
shortest(2,1,0) = A -- (1aaacb)
shortest(1,5,0) = B -- (1aaacc)
shortest(2,5,1) = Min ( Z, A + B ) -- (1aaac)

shortest(1,5,2) = Min (X, Y + Min ( Z, A + B) ) -- (1aaa)

shortest(1,5,4) = (1a) repeats
shortest(5,5,4) = 0 -- (1b)

Here there are two observations,
1) Repeated recursion exists
2) shortestPath via higher k vertices depending on lower k vertices

So we can use dp.
and we need to calculate shortestPath by considering lowest vertex first and then higher. Otherwise, result will be wrong.
 */
class Solution3 {

  // With only positive edges
  public void shortestDistance(int[][] matrix) {
    // Base path
    /*
    k == 0 meaning, from i to j, no vertices are considered. Direct path.
     */

    int n = matrix.length;

    // i to j considering 0 .. k th vertex
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i == j) { // Optional optimization
            continue; // we know cost is zero
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
