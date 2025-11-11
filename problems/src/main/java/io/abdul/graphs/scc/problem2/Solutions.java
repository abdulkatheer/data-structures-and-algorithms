package io.abdul.graphs.scc.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

// https://www.spoj.com/problems/LEGO
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
//    Solution3 sol = new Solution3();
    Solution4 sol = new Solution4();

    // ---------- 1. Example from prompt ----------
    {
      int n = 4;
      int[][] coords = {
          {0, 0, 2, 2},  // Brick 1
          {1, 2, 3, 4},  // Brick 2 - touches top of 1
          {2, 0, 4, 2},  // Brick 3 - touches side/top
          {4, 0, 6, 2}   // Brick 4 - isolated
      };
      assertEquals(2, sol.lego(n, coords));
    }

    // ---------- 2. Single brick ----------
    {
      int n = 1;
      int[][] coords = {
          {0, 0, 2, 2}
      };
      assertEquals(1, sol.lego(n, coords));
    }

    // ---------- 3. Two touching vertically ----------
    {
      int n = 2;
      int[][] coords = {
          {0, 0, 2, 2},
          {0, 2, 2, 4}
      };
      assertEquals(1, sol.lego(n, coords)); // top touches bottom exactly
    }

    // ---------- 4. Two separate (no overlap / no touch) ----------
    {
      int n = 2;
      int[][] coords = {
          {0, 0, 2, 2},
          {3, 0, 5, 2}
      };
      assertEquals(2, sol.lego(n, coords));
    }

    // ---------- 5. Chain of connections ----------
    {
      int n = 4;
      int[][] coords = {
          {0, 0, 2, 2},  // base
          {1, 2, 3, 4},  // stacked on first
          {2, 4, 4, 6},  // stacked on second
          {4, 6, 6, 8}   // stacked on third
      };
      assertEquals(2, sol.lego(n, coords)); // All connected through chain
    }

    // ---------- 6. Two towers ----------
    {
      int n = 6;
      int[][] coords = {
          {0, 0, 2, 2},
          {0, 2, 2, 4},
          {0, 4, 2, 6},
          {5, 0, 7, 2},
          {5, 2, 7, 4},
          {5, 4, 7, 6}
      };
      assertEquals(2, sol.lego(n, coords)); // Two disconnected stacks
    }

    // ---------- 7. Touching horizontally at top but zero overlap ----------
    {
      int n = 2;
      int[][] coords = {
          {0, 0, 2, 2},
          {2, 2, 4, 4}
      };
      assertEquals(2, sol.lego(n, coords)); // corners touch but no overlap horizontally
    }

    // ---------- 8. Complex cluster with multiple bindings ----------
    {
      int n = 5;
      int[][] coords = {
          {0, 0, 2, 2},  // bottom left
          {2, 0, 4, 2},  // bottom right
          {1, 2, 3, 4},  // top middle (touches both)
          {4, 0, 6, 2},  // separate group
          {5, 2, 7, 4}   // touches top of #4
      };
      assertEquals(2, sol.lego(n, coords)); // 1 group of 3, 1 group of 2
    }

    // ---------- 9. Very large coordinates ----------
    {
      int n = 3;
      int[][] coords = {
          {0, 0, 1000000000, 2},
          {0, 2, 1000000000, 4},
          {2000000000, 0, 2000000002, 2}
      };
      assertEquals(2, sol.lego(n, coords)); // first two connected, last isolated
    }

    // ---------- 10. Disjoint floating bricks ----------
    {
      int n = 3;
      int[][] coords = {
          {0, 0, 2, 2},
          {5, 5, 7, 7},
          {10, 0, 12, 2}
      };
      assertEquals(3, sol.lego(n, coords));
    }
  }
}

class Solution {

  public int lego(int n, int[][] coordinates) {
    // Build a matrix
    int maxRow = 0;
    int maxColumn = 0;
    for (int[] coordinate : coordinates) {
      maxRow = Math.max(maxRow, Math.max(coordinate[1], coordinate[3]));
      maxColumn = Math.max(maxColumn, Math.max(coordinate[0], coordinate[2]));
    }
    int r = maxRow + 1;
    int c = maxColumn + 1;
    //
    int[][] matrix = new int[r][c];
    for (int[] row : matrix) {
      Arrays.fill(row, -1);
    }

    DisjointSet set = new DisjointSet(n);
    for (int i = 0; i < coordinates.length; i++) {
      int[] coordinate = coordinates[i];
      int x1 = coordinate[0];
      int y1 = coordinate[1];
      int x2 = coordinate[2];
      int y2 = coordinate[3];

      /* 2,3, 4,4; width=2, height=1
      23
      33

      0,0 2,2; width=2, height=2
      10 11
      00 01

      0,2 2,4
      12 13
      02 03
       */
      for (int column = x1; column < x2; column++) {
        for (int row = y1; row < y2; row++) {
          matrix[row][column] = i;
        }
      }
      /*
      20 21
      10 11
      00 01
      -- --
      x2 -> y1 to y2
      x1-1 -> y2 to y2
       */
      // top row
      int topColumn = y2;
      if (topColumn < r) {
        for (int x = x1; x < x2; x++) {
          if (matrix[topColumn][x] != -1) {
            set.union(i, matrix[topColumn][x]);
          }
        }
      }

      // bottom row
      int bottomColumn = y1 - 1;
      if (bottomColumn >= 0) {
        for (int x = x1; x < x2; x++) {
          if (matrix[bottomColumn][x] != -1) {
            set.union(i, matrix[bottomColumn][x]);
          }
        }
      }

    }

    return set.numberOfComponents();
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

    boolean union(int u, int v) {
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

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }

    public int numberOfComponents() {
      int count = 0;
      for (int i = 0; i < parents.length; i++) {
        if (parents[i] == i) {
          count++;
        }
      }
      return count;
    }
  }
}

class Solution2 {

  public int lego(int n, int[][] coordinates) {
    // Build a matrix
    int maxRow = 0;
    int maxColumn = 0;
    for (int[] coordinate : coordinates) {
      maxRow = Math.max(maxRow, Math.max(coordinate[1], coordinate[3]));
      maxColumn = Math.max(maxColumn, Math.max(coordinate[0], coordinate[2]));
    }
    int r = maxRow + 1;
    int c = maxColumn + 1;

    Map<Integer, Map<Integer, Integer>> matrix = new HashMap<>();

    DisjointSet set = new DisjointSet(n);
    for (int i = 0; i < coordinates.length; i++) {
      int[] coordinate = coordinates[i];
      int x1 = coordinate[0];
      int y1 = coordinate[1];
      int x2 = coordinate[2];
      int y2 = coordinate[3];

      /* 2,3, 4,4; width=2, height=1
      23
      33

      0,0 2,2; width=2, height=2
      10 11
      00 01

      0,2 2,4
      12 13
      02 03
       */
      for (int column = x1; column < x2; column++) {
        for (int row = y1; row < y2; row++) {
          if (!matrix.containsKey(row)) {
            matrix.put(row, new HashMap<>());
          }
          matrix.get(row).put(column, i);
        }
      }
      /*
      20 21
      10 11
      00 01
      -- --
      x2 -> y1 to y2
      x1-1 -> y2 to y2
       */
      // top row
      int topColumn = y2;
      if (topColumn < r) {
        for (int x = x1; x < x2; x++) {
          if (matrix.containsKey(topColumn) && matrix.get(topColumn).containsKey(x)) {
            set.union(i, matrix.get(topColumn).get(x));
          }
        }
      }

      // bottom row
      int bottomColumn = y1 - 1;
      if (bottomColumn >= 0) {
        for (int x = x1; x < x2; x++) {
          if (matrix.containsKey(bottomColumn) && matrix.get(bottomColumn).containsKey(x)) {
            set.union(i, matrix.get(bottomColumn).get(x));
          }
        }
      }

    }

    return set.numberOfComponents();
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

    boolean union(int u, int v) {
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

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }

    public int numberOfComponents() {
      int count = 0;
      for (int i = 0; i < parents.length; i++) {
        if (parents[i] == i) {
          count++;
        }
      }
      return count;
    }
  }
}

/*
Interval Theory
(x1a, x2a) & (x1b, x2b)
1) Both a and b should overlap on x-axis
Like move interval. Find early end and late start. Bcz we don't know which one comes earlier.
min(x2a, x2b) gives early end
max(x1a, x1b) gives late start
If late start is before early end, then a and b are overlapping
Why no equals?
(0,0 2,2) and (2,0 4,2)
late start = 2
early end = 2
Both are equals, so adjacent and not overlapping

2) a should be on top of b OR b should on top of a

a on top of b -> top y of a == bottom y of b
(0,0 2,2) and (1,2 3,4)
a top edge is on 1 (2-1)
b bottom edge is on 2
So b is on top of a

So let's store by y-axis
topY = X, node
bottomY = X, node

(x1, y1) and (x2, y2)
- Find nodes in bottomY using y2
- Find nodes in topY using y1

List<X-Node> with start and end

Option 1) We can iterate node by node
Update the map
Find overlapping nodes on top of it and bottom of it
(x1,y1 x2,y2)
bottomY.get(y2) and find overlapping nodes between x1 and x2
topY.get(y1-1) and find overlapping nodes between x1 and x2
2 * n * log(n)

Option 2) Build entire top and bottom Y mapping
Iterate by y-axis
For each y, get nodes whose top is y and nodes whose bottom is y
For each one in top, find overlapping node in bottom
OR For each one in bottom, find overlapping node in top
 */
// Option 1
class Solution3 {

  public int lego(int n, int[][] coordinates) {
    DisjointSet set = new DisjointSet(n);
    Map<Integer, Set<int[]>> topY = new HashMap<>();
    Map<Integer, Set<int[]>> bottomY = new HashMap<>();

    Comparator<int[]> x1Comparator = Comparator.comparingInt(ints -> ints[0]);
    for (int i = 0; i < coordinates.length; i++) {
      int[] coordinate = coordinates[i];
      int x1 = coordinate[0];
      int y1 = coordinate[1];
      int x2 = coordinate[2];
      int y2 = coordinate[3];

      Set<int[]> bottomYAxisNodes = topY.get(y1);
      Set<int[]> topYAxisNodes = bottomY.get(y2);

      if (bottomYAxisNodes != null) {
        for (int[] bottomYAxisNode : bottomYAxisNodes) {
          int x1T = bottomYAxisNode[0];
          int x2T = bottomYAxisNode[1];
          int i2 = bottomYAxisNode[2];
          if (Math.min(x2, x2T) > Math.max(x1, x1T)) {
            set.union(i, i2);
          }
        }
      }

      if (topYAxisNodes != null) {
        for (int[] topYAxisNode : topYAxisNodes) {
          int x1T = topYAxisNode[0];
          int x2T = topYAxisNode[1];
          int i2 = topYAxisNode[2];
          if (Math.min(x2, x2T) > Math.max(x1, x1T)) { // early end > late start
            set.union(i, i2);
          }
        }
      }

      topY.computeIfAbsent(y2, k -> new TreeSet<>(x1Comparator)).add(new int[]{x1, x2, i});
      bottomY.computeIfAbsent(y1, k -> new TreeSet<>(x1Comparator)).add(new int[]{x1, x2, i});
    }
    return set.numberOfComponents();
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

    boolean union(int u, int v) {
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

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }

    public int numberOfComponents() {
      int count = 0;
      for (int i = 0; i < parents.length; i++) {
        if (parents[i] == i) {
          count++;
        }
      }
      return count;
    }
  }
}

// Option 2
class Solution4 {

  public int lego(int n, int[][] coordinates) {
    DisjointSet set = new DisjointSet(n);
    Map<Integer, Set<int[]>> topY = new HashMap<>();
    Map<Integer, Set<int[]>> bottomY = new HashMap<>();

    Comparator<int[]> x1Comparator = Comparator.comparingInt(ints -> ints[0]);
    for (int i = 0; i < coordinates.length; i++) {
      int[] coordinate = coordinates[i];
      int x1 = coordinate[0];
      int y1 = coordinate[1];
      int x2 = coordinate[2];
      int y2 = coordinate[3];

      topY.computeIfAbsent(y2, k -> new TreeSet<>(x1Comparator)).add(new int[]{x1, x2, i});
      bottomY.computeIfAbsent(y1, k -> new TreeSet<>(x1Comparator)).add(new int[]{x1, x2, i});
    }

    for (Integer y : topY.keySet()) {
      /*
      For each y, get nodes having y as top
      and find nodes having y as bottom and overlapping
       */
      for (int[] topYAxisNode : topY.get(y)) {
        int x1 = topYAxisNode[0];
        int x2 = topYAxisNode[1];
        int i = topYAxisNode[2];
        Set<int[]> bottomYAxisNodes = bottomY.get(y);
        if (bottomYAxisNodes != null) {
          for (int[] bottomYAxisNode : bottomYAxisNodes) {
            int x1T = bottomYAxisNode[0];
            int x2T = bottomYAxisNode[1];
            int i2 = bottomYAxisNode[2];

            if (Math.min(x2, x2T) > Math.max(x1, x1T)) { // early end > late start
              set.union(i, i2);
            }
          }
        }
      }
    }
    return set.numberOfComponents();
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

    boolean union(int u, int v) {
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

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }

    public int numberOfComponents() {
      int count = 0;
      for (int i = 0; i < parents.length; i++) {
        if (parents[i] == i) {
          count++;
        }
      }
      return count;
    }
  }
}