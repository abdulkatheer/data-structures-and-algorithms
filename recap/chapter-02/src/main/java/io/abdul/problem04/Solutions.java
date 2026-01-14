package io.abdul.problem04;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/smallest-value-after-replacing-with-sum-of-prime-factors/
// tag:math tag:prime
public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    System.out.println(sol.smallestValue(4));
  }
}

class Solution {

  public int smallestValue(int n) {
    if (n < 2) {
      return n;
    }

    boolean[] composites = new boolean[n + 1];

    List<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
        markComposites(i, composites);
      }
    }

    while (composites[n]) {
      int pfSum = primeFactorSum(n, composites, primes);
      if (pfSum == n) { // repeats, eg 4
        break;
      } else {
        n = pfSum;
      }
    }

    return n;
  }

  private int primeFactorSum(int n, boolean[] composites, List<Integer> primes) {
    int sum = 0;

    for (int prime : primes) {
      while (n % prime == 0) {
        sum += prime;
        n /= prime;
      }

      if (n == 1) { // fully divided
        break;
      }

      if (!composites[n]) { // n ended as prime, so stop
        sum += n;
        break;
      }
    }

    return sum;
  }

  private void markComposites(int x, boolean[] composites) {
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }
}

/**
 * Optimal - Prime Factorisation
 */
class Solution2 {

  // We don't need to find primes to find primeFactorSum!
  // if we start dividing by first known prime, and do until possible for every num after that, dividing numbers will be prime only!
  public int smallestValue(int n) {
    while (n >= 2) {
      int pfSum = primeFactorSum(n);
      if (pfSum == n) { // eg 4
        break;
      } else {
        n = pfSum;
      }
    }

    return n;
  }

  private int primeFactorSum(int n) {
    int sum = 0;
    for (int i = 2; i <= n; i++) {
      while (n % i == 0) {
        sum += i;
        n /= i;
      }
    }

    return sum;
  }
}

/*
Same time, but more space though
 */
class Solution3 {

  public int smallestValue(int n) {
    int[] seen = new int[n + 1];
    while (n >= 2) {
      n = primeFactorSum(n);
      if (seen[n] == 1) { // cycle detected
        break;
      }
      seen[n] = 1;
    }

    return n;
  }

  private int primeFactorSum(int n) {
    int pfSum = 0;

    while (n % 2 == 0) {
      pfSum += 2;
      n /= 2;
    }

    while (n % 3 == 0) {
      pfSum += 3;
      n /= 3;
    }

    int sqrt = (int) Math.sqrt(n);
    for (int i = 5; i <= sqrt; i += 6) {
      while (n % i == 0) {
        pfSum += i;
        n /= i;
      }

      while (n % (i + 2) == 0) {
        pfSum = pfSum + i + 2;
        n = n / (i + 2);
      }
    }

    if (n > 1) { // balance is also a prime factor
      pfSum += n;
    }

    return pfSum;
  }
}