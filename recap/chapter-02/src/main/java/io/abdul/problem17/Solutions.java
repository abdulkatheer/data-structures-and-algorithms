package io.abdul.problem17;

import java.util.Arrays;

// https://leetcode.com/problems/largest-prime-from-consecutive-prime-sum/
// tag:math tag:prime tag:sieve_of_eratosthenes
public class Solutions {

}

class Solution {

  public int largestPrime(int n) {
    boolean[] primes = primes(n);

    int sum = 0;
    int prime = 0;
    int i = 0;
    while (i <= n && sum <= n) {
      if (primes[i]) {
        sum += i;
        if (sum <= n && primes[sum]) {
          prime = sum;
        }
      }
      i++;
    }

    return prime;
  }

  private boolean[] primes(int n) {
    boolean[] primes = new boolean[n + 1];
    Arrays.fill(primes, true);
    primes[0] = primes[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (primes[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          primes[(int) j] = false;
        }
      }
    }

    return primes;
  }
}
