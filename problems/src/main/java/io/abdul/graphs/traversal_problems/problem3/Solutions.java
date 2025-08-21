package io.abdul.graphs.traversal_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1
    int[][] image1 = {
        {1, 1, 1},
        {1, 1, 0},
        {1, 0, 1}
    };
    int[][] expected1 = {
        {2, 2, 2},
        {2, 2, 0},
        {2, 0, 1}
    };
    assertArrayEquals(expected1, sol.floodFill(image1, 1, 1, 2), "Example 1 failed");

    // Example 2
    int[][] image2 = {
        {0, 1, 0},
        {1, 1, 0},
        {0, 0, 1}
    };
    int[][] expected2 = {
        {0, 1, 0},
        {1, 1, 0},
        {0, 0, 3}
    };
    assertArrayEquals(expected2, sol.floodFill(image2, 2, 2, 3), "Example 2 failed");

    // Example 3
    int[][] image3 = {
        {1, 1, 1},
        {1, 1, 0},
        {1, 0, 1}
    };
    int[][] expected3 = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 1}
    };
    assertArrayEquals(expected3, sol.floodFill(image3, 1, 1, 0), "Example 3 failed");

    // Edge case: single pixel image
    int[][] image4 = {{5}};
    int[][] expected4 = {{7}};
    assertArrayEquals(expected4, sol.floodFill(image4, 0, 0, 7), "Single pixel case failed");

    // Edge case: newColor is same as old color
    int[][] image5 = {
        {2, 2},
        {2, 2}
    };
    int[][] expected5 = {
        {2, 2},
        {2, 2}
    };
    assertArrayEquals(expected5, sol.floodFill(image5, 0, 0, 2), "Same color case failed");

    // Edge case: disconnected regions
    int[][] image6 = {
        {1, 0, 1},
        {0, 1, 0},
        {1, 0, 1}
    };
    int[][] expected6 = {
        {9, 0, 1},
        {0, 1, 0},
        {1, 0, 1}
    };
    assertArrayEquals(expected6, sol.floodFill(image6, 0, 0, 9), "Disconnected regions failed");
  }
}

/*
Recursive
T - O(n*m) - each node is visited only once
S - O(n*m) - Stack
 */
class Solution {

  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    // Problem isn't explicitly stating to modify the input image, so making a copy of it
    int n = image.length;
    int m = image[0].length;
    int[][] newImage = new int[n][m];
    for (int i = 0; i < n; i++) {
      System.arraycopy(image[i], 0, newImage[i], 0, m);
    }

    image = newImage;

    int existingColour = image[sr][sc];

    colour(sr, sc, n, m, image, existingColour, newColor);

    return image;
  }

  private void colour(int i, int j, int n, int m, int[][] image, int existingColour,
      int newColour) {
    if (i < 0 || i >= n || j < 0 || j >= m) {
      return;
    }

    if (image[i][j] != existingColour) {
      return;
    }

    if (image[i][j] == newColour) {
      return;
    }

    image[i][j] = newColour;

    // Top
    colour(i - 1, j, n, m, image, existingColour, newColour);

    // Right
    colour(i, j + 1, n, m, image, existingColour, newColour);

    // Bottom
    colour(i + 1, j, n, m, image, existingColour, newColour);

    // Left
    colour(i, j - 1, n, m, image, existingColour, newColour);
  }
}

/*
Iterative
T - O(n*m) - each node is visited only once
S - O(n*m) - Stack
 */
class Solution2 {

  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    // Problem isn't explicitly stating to modify the input image, so making a copy of it
    int n = image.length;
    int m = image[0].length;
    int[][] newImage = new int[n][m];
    for (int i = 0; i < n; i++) {
      System.arraycopy(image[i], 0, newImage[i], 0, m);
    }

    image = newImage;

    int existingColour = image[sr][sc];

    dfs(sr, sc, n, m, image, existingColour, newColor);

    return image;
  }

  private void dfs(int startI, int startJ, int n, int m, int[][] image, int existingColour,
      int newColour) {
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(startI, startJ));

    while (!stack.isEmpty()) {
      Pair p = stack.pop();
      int i = p.i;
      int j = p.j;

      if (isValidIndex(i, j, n, m) && image[i][j] == existingColour && image[i][j] != newColour) {
        image[i][j] = newColour;

        // top
        stack.push(new Pair(i - 1, j));
        // right
        stack.push(new Pair(i, j + 1));
        // bottom
        stack.push(new Pair(i + 1, j));
        // left
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

class Solution3 {

  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    // Problem isn't explicitly stating to modify the input image, so making a copy of it
    int n = image.length;
    int m = image[0].length;
    image = copy(image);
    boolean[][] visited = new boolean[image.length][image[0].length];

    int existingColour = image[sr][sc];

    dfs(sr, sc, n, m, image, visited, existingColour, newColor);

    return image;
  }

  private int[][] copy(int[][] src) {
    int[][] dest = new int[src.length][];
    for (int i = 0; i < src.length; i++) {
      dest[i] = new int[src[i].length];
      System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
    }

    return dest;
  }

  private void dfs(int startI, int startJ, int n, int m, int[][] image, boolean[][] visited,
      int existingColour, int newColour) {
    Queue<Pair> queue = new LinkedList<>();
    queue.add(new Pair(startI, startJ)); // visit
    visited[startI][startJ] = true;

    while (!queue.isEmpty()) {
      int size = queue.size();

      for (int k = 0; k < size; k++) {
        Pair p = queue.poll(); // process
        int i = p.i;
        int j = p.j;
        image[i][j] = newColour;

        visit(i - 1, j, n, m, image, visited, queue, existingColour, newColour); // top

        visit(i, j - 1, n, m, image, visited, queue, existingColour, newColour); // left

        visit(i + 1, j, n, m, image, visited, queue, existingColour, newColour); // bottom

        visit(i, j + 1, n, m, image, visited, queue, existingColour, newColour); // right
      }
    }
  }

  private void visit(int i, int j, int n, int m, int[][] image, boolean[][] visited,
      Queue<Pair> queue, int existingColour, int newColour) {
    if (isValidIndex(i, j, n, m) && !visited[i][j] && image[i][j] == existingColour
        && image[i][j] != newColour) {
      visited[i][j] = true;
      queue.add(new Pair(i, j));
    }
  }

  private boolean isValidIndex(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private record Pair(int i, int j) {

  }
}


