package io.abdul.graphs.traversal_problems.problem7;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution3 sol = new Solution3();

    // Example 1
    char[][] mat1 = {
        {'X', 'X', 'X', 'X'},
        {'X', 'O', 'O', 'X'},
        {'X', 'X', 'O', 'X'},
        {'X', 'O', 'X', 'X'}
    };
    char[][] expected1 = {
        {'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X'},
        {'X', 'O', 'X', 'X'}
    };
    assertTrue(Arrays.deepEquals(expected1, sol.fill(mat1)), "Example 1 failed");

    // Example 2
    char[][] mat2 = {
        {'X', 'X', 'X'},
        {'X', 'O', 'X'},
        {'X', 'X', 'X'}
    };
    char[][] expected2 = {
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
        {'X', 'X', 'X'}
    };
    assertTrue(Arrays.deepEquals(expected2, sol.fill(mat2)), "Example 2 failed");

    // Example 3
    char[][] mat3 = {
        {'X', 'X', 'X', 'O'},
        {'X', 'X', 'X', 'X'},
        {'O', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X'}
    };
    char[][] expected3 = {
        {'X', 'X', 'X', 'O'},
        {'X', 'X', 'X', 'X'},
        {'O', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X'}
    };
    assertTrue(Arrays.deepEquals(expected3, sol.fill(mat3)), "Example 3 failed");

    // Edge case: single cell O
    char[][] mat4 = {
        {'O'}
    };
    char[][] expected4 = {
        {'O'}
    };
    assertTrue(Arrays.deepEquals(expected4, sol.fill(mat4)), "Single cell O failed");

    // Edge case: single cell X
    char[][] mat5 = {
        {'X'}
    };
    char[][] expected5 = {
        {'X'}
    };
    assertTrue(Arrays.deepEquals(expected5, sol.fill(mat5)), "Single cell X failed");

    // Edge case: all border O's (should remain same)
    char[][] mat6 = {
        {'O', 'O', 'O'},
        {'O', 'X', 'O'},
        {'O', 'O', 'O'}
    };
    char[][] expected6 = {
        {'O', 'O', 'O'},
        {'O', 'X', 'O'},
        {'O', 'O', 'O'}
    };
    assertTrue(Arrays.deepEquals(expected6, sol.fill(mat6)), "All border O's failed");

    // Edge case: all O's (nothing can be surrounded, so remains same)
    char[][] mat7 = {
        {'O', 'O'},
        {'O', 'O'}
    };
    char[][] expected7 = {
        {'O', 'O'},
        {'O', 'O'}
    };
    assertTrue(Arrays.deepEquals(expected7, sol.fill(mat7)), "All O's failed");

    // Edge case: surrounded region in middle of large grid
    char[][] mat8 = {
        {'X', 'X', 'X', 'X', 'X'},
        {'X', 'O', 'O', 'O', 'X'},
        {'X', 'O', 'X', 'O', 'X'},
        {'X', 'O', 'O', 'O', 'X'},
        {'X', 'X', 'X', 'X', 'X'}
    };
    char[][] expected8 = {
        {'X', 'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X', 'X'},
        {'X', 'X', 'X', 'X', 'X'}
    };
    assertTrue(Arrays.deepEquals(expected8, sol.fill(mat8)),
        "Surrounded region in large grid failed");

  }
}

/*
O's connected to any of the borders can't be converted to X
Everything else can be converted to X
 */
class Solution {

  public char[][] fill(char[][] mat) {
    mat = copy(mat);
    int n = mat.length;
    int m = mat[0].length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (isBorder(i, n, j, m) && mat[i][j] == 'O') {
          dfs(i, j, mat);
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (mat[i][j] == '$') { // if reachable, make it back to O
          mat[i][j] = 'O';
        } else if (mat[i][j] == 'O') { // if unreachable, mark it as X
          mat[i][j] = 'X';
        }
      }
    }

    return mat;
  }

  private static boolean isBorder(int i, int n, int j, int m) {
    return i == 0 || i == n - 1 || j == 0 || j == m - 1;
  }

  private void dfs(int start, int end, char[][] matrix) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{start, end});

    while (!stack.isEmpty()) {
      int[] pair = stack.pop();

      int i = pair[0];
      int j = pair[1];
      if (isValid(i, j, matrix.length, matrix[0].length) && matrix[i][j] == 'O') {
        matrix[i][j] = '$';

        stack.push(new int[]{i - 1, j});
        stack.push(new int[]{i, j - 1});
        stack.push(new int[]{i + 1, j});
        stack.push(new int[]{i, j + 1});
      }
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private char[][] copy(char[][] src) {
    char[][] dest = new char[src.length][];

    for (int i = 0; i < dest.length; i++) {
      dest[i] = new char[src[i].length];
      System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
    }

    return dest;
  }
}

class Solution2 {

  public char[][] fill(char[][] mat) {
    mat = copy(mat);
    int n = mat.length;
    int m = mat[0].length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (isBorder(i, n, j, m) && mat[i][j] == 'O') {
          dfs(i, j, mat);
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (mat[i][j] == '$') { // if reachable, make it back to O
          mat[i][j] = 'O';
        } else if (mat[i][j] == 'O') { // if unreachable, mark it as X
          mat[i][j] = 'X';
        }
      }
    }

    return mat;
  }

  private static boolean isBorder(int i, int n, int j, int m) {
    return i == 0 || i == n - 1 || j == 0 || j == m - 1;
  }

  private void dfs(int start, int end, char[][] matrix) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{start, end});

    while (!stack.isEmpty()) {
      int[] pair = stack.pop();

      int i = pair[0];
      int j = pair[1];
      if (isValid(i, j, matrix.length, matrix[0].length) && matrix[i][j] == 'O') {
        matrix[i][j] = '$';

        stack.push(new int[]{i - 1, j});
        stack.push(new int[]{i, j - 1});
        stack.push(new int[]{i + 1, j});
        stack.push(new int[]{i, j + 1});
      }
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private char[][] copy(char[][] src) {
    char[][] dest = new char[src.length][];

    for (int i = 0; i < dest.length; i++) {
      dest[i] = new char[src[i].length];
      System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
    }

    return dest;
  }
}

class Solution3 {

  public char[][] fill(char[][] mat) {
    mat = copy(mat);
    int n = mat.length;
    int m = mat[0].length;
    boolean[][] visited = new boolean[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (isBorder(i, n, j, m) && mat[i][j] == 'O') {
          dfs(i, j, n, m, mat, visited);
        }
      }
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (mat[i][j] == '$') { // if reachable from border, make it back to O
          mat[i][j] = 'O';
        } else if (mat[i][j] == 'O') { // if unreachable, mark it as X
          mat[i][j] = 'X';
        }
      }
    }

    return mat;
  }

  private static boolean isBorder(int i, int n, int j, int m) {
    return i == 0 || i == n - 1 || j == 0 || j == m - 1;
  }

  private void dfs(int startI, int startJ, int n, int m, char[][] matrix, boolean[][] visited) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startI, startJ}); // visit
    visited[startI][startJ] = true;

    while (!stack.isEmpty()) {
      int[] pair = stack.pop(); // process
      int i = pair[0];
      int j = pair[1];
      matrix[i][j] = '$';

      visit(i - 1, j, n, m, matrix, visited, stack); // top

      visit(i, j + 1, n, m, matrix, visited, stack); // right

      visit(i + 1, j, n, m, matrix, visited, stack); // bottom

      visit(i, j - 1, n, m, matrix, visited, stack); // left
    }
  }

  private void visit(int i, int j, int n, int m, char[][] matrix, boolean[][] visited,
      Stack<int[]> stack) {
    if (isValid(i, j, n, m) && matrix[i][j] == 'O' && !visited[i][j]) {
      visited[i][j] = true;
      stack.push(new int[]{i, j});
    }
  }

  private boolean isValid(int i, int j, int n, int m) {
    return i >= 0 && i < n && j >= 0 && j < m;
  }

  private char[][] copy(char[][] src) {
    char[][] dest = new char[src.length][];

    for (int i = 0; i < dest.length; i++) {
      dest[i] = new char[src[i].length];
      System.arraycopy(src[i], 0, dest[i], 0, src[i].length);
    }

    return dest;
  }
}