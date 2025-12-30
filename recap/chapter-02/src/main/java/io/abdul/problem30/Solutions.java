package io.abdul.problem30;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://leetcode.com/problems/super-pow
// tag:math tag:divide_and_conquer
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(8, solution.superPow(2, new int[]{3}));
  }
}

class Solution {

  private static final int MOD = 1337;

  /*
  a^100000 = ((((((a^1) ^ a^10) ^ a^10) ^ a^10) ^ a^10) ^ a^10)
  a^1 = 1 * a^1
  a^12 = ((1 * a^1) ^ a^10) * a^2
  a^123 = ((((1 * a^1) ^ a^10) * a^2) ^ a^10) * a^3

  This is actually building number from array of nums
  123 => (((1 * 10) + 2) * 10) + 3
  */
  public int superPow(int a, int[] b) {
    a = a % MOD;
    return superPowRec(a, b, b.length - 1);
  }

  public int superPowRec(int a, int[] b, int i) {
    if (i == -1) {
      return 1;
    }

    int partA = powMod(superPowRec(a, b, i - 1), 10); // a^allPrevDigits ^ 10
    int partB = powMod(a, b[i]); // a^digit

    return (partA * partB)
        % MOD; // (a^allPrevDigits ^ 10) * a^digit = a^((allPrevDigits * 10) + digit)
  }

  private int powMod(int b, int e) {
    int result = 1;
    while (e != 0) {
      if ((e & 1) == 1) { // odd
        result = (b * result) % MOD;
        e--;
      } else { // even
        b = (b * b) % MOD;
        e >>= 1;
      }
    }

    return result;
  }
}

class Solution2 {

  private static final int MOD = 1337;

  public int superPow(int a, int[] b) {
    a = a % MOD;
    return superPowItr(a, b);
  }

  private int superPowItr(int a, int[] b) {
    int power = 1;
    for (int i = 0; i < b.length; i++) {
      int partA = powMod(power, 10); // power for previous digits ^ 10
      int partB = powMod(a, b[i]);

      power = (partA * partB) % MOD;
    }

    return power;
  }

  private int powMod(int b, int e) {
    int result = 1;
    while (e != 0) {
      if ((e & 1) == 1) { // odd
        result = (b * result) % MOD;
        e--;
      } else { // even
        b = (b * b) % MOD;
        e >>= 1;
      }
    }

    return result;
  }
}