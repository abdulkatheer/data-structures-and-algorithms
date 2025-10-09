package io.abdul.graphs.shortest_path_algorithms.problem4;

//import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

//    // --- Test 1: Standard case (from example 1) ---
//    List<List<Integer>> heights1 = Arrays.asList(
//        Arrays.asList(1, 2, 2),
//        Arrays.asList(3, 8, 2),
//        Arrays.asList(5, 3, 5)
//    );
//    assertEquals(2, sol.MinimumEffort(heights1));
//
//    // --- Test 2: Alternate path has lower max difference (example 2) ---
//    List<List<Integer>> heights2 = Arrays.asList(
//        Arrays.asList(1, 2, 3),
//        Arrays.asList(3, 8, 4),
//        Arrays.asList(5, 3, 5)
//    );
//    assertEquals(1, sol.MinimumEffort(heights2));
//
//    // --- Test 3: Large grid with narrow passages (example 3) ---
//    List<List<Integer>> heights3 = Arrays.asList(
//        Arrays.asList(1, 2, 1, 1, 1),
//        Arrays.asList(1, 2, 1, 2, 1),
//        Arrays.asList(1, 2, 1, 2, 1),
//        Arrays.asList(1, 2, 1, 2, 1),
//        Arrays.asList(1, 1, 1, 2, 1)
//    );
//    assertEquals(0,
//        sol.MinimumEffort(heights3));  // all valid routes have equal heights between moves
//
//    // --- Test 4: Single cell grid ---
//    List<List<Integer>> heights4 = Arrays.asList(
//        Arrays.asList(5)
//    );
//    assertEquals(0, sol.MinimumEffort(heights4));
//
//    // --- Test 5: 2x2 grid with diagonal difference ---
//    List<List<Integer>> heights5 = Arrays.asList(
//        Arrays.asList(1, 10),
//        Arrays.asList(2, 3)
//    );
//    assertEquals(1, sol.MinimumEffort(heights5)); // path 1→2→3 gives max diff=2
//
//    // --- Test 6: Monotonically increasing path ---
//    List<List<Integer>> heights6 = Arrays.asList(
//        Arrays.asList(1, 2, 3),
//        Arrays.asList(4, 5, 6),
//        Arrays.asList(7, 8, 9)
//    );
//    assertEquals(3, sol.MinimumEffort(heights6)); // differences are always 1
  }
}

class Solution {

  // non-unit weight, so Dijkstra's algo
  public int MinimumEffort(List<List<Integer>> heights) {
    int m = heights.size();
    int n = heights.get(0).size();
    int[][] effort = new int[m][n];
    int max = (int) 1e10; // max 100*100 = 10^4 nodes and heights = 10^6

    // 0 - effort (abs difference from parent), 1&2 - coordinates
    PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
    for (int[] eff : effort) {
      Arrays.fill(eff, max);
    }

    q.add(new int[]{0, 0, 0});
    effort[0][0] = 0;

    while (!q.isEmpty()) {
      int[] nodeData = q.poll();
      int eff = nodeData[0];
      int nodeX = nodeData[1];
      int nodeY = nodeData[2];

      if (nodeX == m && nodeY == n) { // reached target
        break;
      }

      if (eff > effort[nodeX][nodeY]) { // max than current better, so skip, already processed
        continue;
      }

      visit(nodeX - 1, nodeY, m, n, nodeX, nodeY, heights, effort, q);

      visit(nodeX, nodeY - 1, m, n, nodeX, nodeY, heights, effort, q);

      visit(nodeX + 1, nodeY, m, n, nodeX, nodeY, heights, effort, q);

      visit(nodeX, nodeY + 1, m, n, nodeX, nodeY, heights, effort, q);
    }

    return effort[m - 1][n - 1];
  }

  private void visit(int x, int y, int m, int n, int pX, int pY, List<List<Integer>> heights,
      int[][] effort, PriorityQueue<int[]> q) {
    if (x >= 0 && x < m && y >= 0 && y < n) {
      int parentHeight = heights.get(pX).get(pY);
      int currentNodeHeight = heights.get(x).get(y);
      int currentEffort = Math.abs(parentHeight - currentNodeHeight);
      int maxEffortTillParent = effort[pX][pY];
      int maxEffort = Math.max(currentEffort, maxEffortTillParent);

      if (maxEffort < effort[x][y]) { // if any better, relax max effort for x,y
        q.add(new int[]{maxEffort, x, y});
        effort[x][y] = maxEffort;
      }
    }
  }
}
