package io.abdul.problem41;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(6,
        solution.minOperations(new int[]{3, 2, 6, 2, 35, 5, 35, 2, 5, 8, 7, 3, 4},
            new int[]{105, 70, 70, 175, 105, 105, 105}));
  }
}

/*
T - O(n * log(num))
S - O(1)

gcd of entire array is the greatest common divisor.
So we have a number which divides this fully, we can use that to divide all the numbers
 */
class Solution {

  public int minOperations(int[] nums, int[] numsDivide) {
    Arrays.sort(nums);
    if (numsDivide.length == 1) {
      for (int i = 0; i < nums.length; i++) {
        if (numsDivide[0] % nums[i] == 0) {
          return i;
        }
      }
    } else {
      int gcd = numsDivide[0];

      for (int i = 1; i < numsDivide.length; i++) {
        gcd = gcd(Math.max(gcd, numsDivide[i]), Math.min(gcd, numsDivide[i]));
      }

      for (int i = 0; i < nums.length; i++) {
        if (gcd % nums[i] == 0) {
          return i;
        }
      }
    }

    return -1;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}

/*
Optimal
 */
class Solution2 {
  public int minOperations(int[] nums, int[] numsDivide) {
    Arrays.sort(nums);
    if (numsDivide.length == 1) {
      for (int i = 0; i < nums.length; i++) {
        if (numsDivide[0] % nums[i] == 0) {
          return i;
        }
      }
    } else {
      int gcd = 0;

      for (int i = 0; i < numsDivide.length; i++) {
        gcd = gcd(numsDivide[i], gcd);
      }

      for (int i = 0; i < nums.length; i++) {
        if (gcd % nums[i] == 0) {
          return i;
        }
      }
    }

    return -1;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}
