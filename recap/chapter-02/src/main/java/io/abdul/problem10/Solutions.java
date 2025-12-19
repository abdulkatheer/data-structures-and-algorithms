package io.abdul.problem10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(19, solution.mostFrequentPrime(new int[][]{{1,1},{9,9},{1,1}}));
    assertEquals(97, solution.mostFrequentPrime(new int[][]{{9,7,8},{4,6,5},{2,8,6}}));
    assertEquals(23, solution.mostFrequentPrime(new int[][]{{5, 4, 2}, {6, 2, 5}, {3, 2, 7}}));
  }
}

class Solution {
  public int mostFrequentPrime(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;

    Map<Integer, Integer> nums = new HashMap<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int num;
        int x;
        int y;

        // Top-left
        num = mat[i][j];
        x = i;
        y = j;
        x--;
        y--;
        while (x >= 0 && y >= 0) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x--;
          y--;
        }

        // Top
        num = mat[i][j];
        x = i;
        y = j;
        x--;
        while (x >= 0) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x--;
        }

        // Top-right
        num = mat[i][j];
        x = i;
        y = j;
        x--;
        y++;
        while (x >= 0 && y < n) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x--;
          y++;
        }

        // Right
        num = mat[i][j];
        x = i;
        y = j;
        y++;
        while (y < n) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          y++;
        }

        // Bottom-right
        num = mat[i][j];
        x = i;
        y = j;
        x++;
        y++;
        while (x < m && y < n) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x++;
          y++;
        }

        // Bottom
        num = mat[i][j];
        x = i;
        y = j;
        x++;
        while (x < m) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x++;
        }

        // Bottom-left
        num = mat[i][j];
        x = i;
        y = j;
        x++;
        y--;
        while (x < m && y >= 0) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          x++;
          y--;
        }

        // Left
        num = mat[i][j];
        x = i;
        y = j;
        y--;
        while (y >= 0) {
          num = (num * 10) + mat[x][y];
          nums.put(num, nums.getOrDefault(num, 0) + 1);
          y--;
        }
      }
    }

    int prime = -1;
    int count = 0;

    for (Map.Entry<Integer, Integer> numEntry : nums.entrySet()) {
      int num = numEntry.getKey();
      int numCount = numEntry.getValue();

      if (num >= 10 && isPrime(num)) {
        if ((numCount == count && num > prime) || (numCount > count)) {
          prime = num;
          count = numCount;
        }
      }
    }

    return prime;
  }

  // Brute Optimized
  private boolean isPrime(int num) {
    if (num <= 1) {
      return false;
    }

    if (num <= 3) {
      return true;
    }

    if (num % 2 == 0 || num % 3 == 0) {
      return false;
    }

    int sqrt = (int) Math.sqrt(num);
    for (int i = 5; i <= sqrt; i += 6) {
      if (num % (i) == 0) {
        return false;
      }
      if (num % (i + 2) == 0) {
        return false;
      }
    }

    return true;
  }
}
