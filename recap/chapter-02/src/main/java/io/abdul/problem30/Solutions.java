package io.abdul.problem30;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    int partA = powMod(superPowRec(a, b, i-1), 10); // a^allPrevDigits ^ 10
    int partB = powMod(a, b[i]); // a^digit

    return (partA * partB) % MOD; // (a^allPrevDigits ^ 10) * a^digit = a^((allPrevDigits * 10) + digit)
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
