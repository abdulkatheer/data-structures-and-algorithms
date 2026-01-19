package io.abdul.problem51;

import java.util.Arrays;

// https://leetcode.com/problems/prime-arrangements/
public class Solutions {

}

class Solution {
  /*
  n = 10, no prime at all
  nPr = 10P10 = 10!

  n = 10, 4 primes
  6P6 * 4P4

  6! * 4!
  */
  public int numPrimeArrangements(int n) {
    int mod = ((int) 1e9) + 7;
    int p = countPrimes(n);
    int nonPrimePerm = factorialMod(n - p, mod);
    int primePerm = factorialMod(p, mod);
    long result = ((long) primePerm * nonPrimePerm) % mod;
    return (int) result;
  }

  // Sieve of Eratosthenes
  private int countPrimes(int n) {
    boolean[] prime = new boolean[n + 1];
    Arrays.fill(prime, true);

    int count = 0;
    for (int i = 2; i <= n; i++) {
      if (prime[i]) {
        count++;
        for (long j = (long) i * i; j <= n; j += i) {
          prime[(int) j] = false;
        }
      }
    }

    return count;
  }

  private int factorialMod(int n, int mod) {
    long fact = 1;
    for (int i = 1; i <= n; i++) {
      fact = (fact * i) % mod;
    }

    return (int) fact;
  }
}
