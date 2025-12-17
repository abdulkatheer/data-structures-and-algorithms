package io.abdul.problem03;

// https://leetcode.com/problems/prison-cells-after-n-days/
// tag:math tag:math_trick
public class Solutions {

}

class Solution {

  // T - O(n)
  // S - O(1)
  public int[] prisonAfterNDays(int[] cells, int n) {
    int[] prev = new int[cells.length];
    int[] curr = new int[cells.length];

    System.arraycopy(cells, 0, prev, 0, cells.length);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < cells.length; j++) {
        if (j == 0 || j == cells.length - 1) {
          curr[j] = 0;
          continue;
        }

        if (prev[j - 1] == 0 && prev[j + 1] == 0) {
          curr[j] = 1;
        } else if (prev[j - 1] == 1 && prev[j + 1] == 1) {
          curr[j] = 1;
        } else {
          curr[j] = 0;
        }
      }

      int[] temp = curr;
      curr = prev;
      prev = temp;
    }

    return prev;
  }
}

class Solution2 {

  // T - O(1)
  // S - O(1)
  public int[] prisonAfterNDays(int[] cells, int n) {
    int[] prev = new int[cells.length];
    int[] curr = new int[cells.length];

    n = n % 14; // reducing days 1 to n -> 1 to 14
    n = n == 0 ? 14 : n; // if 14, n to be 14 and not 0

    // Or n = (n-1) % 14 + 1;
    // Reduce n by 1, so you'll get 0 to 13 and add 1 to get actual

    System.arraycopy(cells, 0, prev, 0, cells.length);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < cells.length; j++) {
        if (j == 0 || j == cells.length - 1) {
          curr[j] = 0;
          continue;
        }

        if (prev[j - 1] == 0 && prev[j + 1] == 0) {
          curr[j] = 1;
        } else if (prev[j - 1] == 1 && prev[j + 1] == 1) {
          curr[j] = 1;
        } else {
          curr[j] = 0;
        }
      }

      int[] temp = curr;
      curr = prev;
      prev = temp;
    }

    return prev;
  }
}