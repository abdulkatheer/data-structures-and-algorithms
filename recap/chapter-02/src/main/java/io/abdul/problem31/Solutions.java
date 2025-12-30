package io.abdul.problem31;

// https://leetcode.com/problems/count-good-numbers/
// tag:math
public class Solutions {

}

/*
Evens - 0 2 4 6 8 (5)
Primes - 2 3 5 7 (4)

Digits = 1 -> 5^1 * 4^0
Digits = 2 -> 5^1 * 4^1
Digits = 3 -> 5^2 * 4^1
Digits = 4 -> 5^2 * 4^2
*/
class Solution {
  private static final int MOD = ((int) 1e9) + 7;

  public int countGoodNumbers(long n) {
    int evens= powMod(5, (n + 1) / 2);
    int primes = powMod(4, n / 2);

    return (int) (((long) evens * primes) % MOD);
  }

  private int powMod(long b, long e) {
    b %= MOD;
    long result = 1;
    while (e != 0) {
      if ((e & 1) == 1) {
        result = (result * b) % MOD;
        e--;
      } else {
        b = (b * b) % MOD;
        e >>= 1;
      }
    }

    return (int) result;
  }
}
