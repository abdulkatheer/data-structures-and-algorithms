package io.abdul.problem08;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/prime-pairs-with-target-sum/
// tag:math tag:prime tag:sieve_of_eratosthenes
public class Solutions {

}

/*
Better
 */
class Solution {

  public List<List<Integer>> findPrimePairs(int n) {
    List<Integer> primes = sievePrimes(n);

    int i = 0;
    int j = primes.size() - 1;

    List<List<Integer>> result = new ArrayList<>();
    while (i <= j) {
      int sum = primes.get(i) + primes.get(j);
      if (sum == n) {
        result.add(List.of(primes.get(i), primes.get(j)));
        i++;
        j--;
      } else if (sum < n) { // expand
        i++;
      } else { // shrink
        j--;
      }
    }

    return result;
  }

  private List<Integer> sievePrimes(int n) {
    List<Integer> primes = new ArrayList<>();
    boolean[] composites = new boolean[n + 1];

    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
        markComposites(composites, i);
      }
    }

    return primes;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = (long) x * x; i < composites.length; i += x) {
      composites[(int) i] = true;
    }
  }
}

/*
Optimal
 */
class Solution2 {

  public List<List<Integer>> findPrimePairs(int n) {
    int sqrt = (int) Math.sqrt(n);

    boolean[] composites = new boolean[n + 1];
    for (int i = 2; i <= sqrt; i++) {
      if (!composites[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          composites[(int) j] = true;
        }
      }
    }

    /*
    to sum n, the last number can be n/2 only. n/2+n/2=n
    At each step, we're checking whether n-i and i making a sum

    For n=10, 2 3 5 7 are primes
    i from 2 to 5
    i=2, 2 + 8
    i=3, 3 + 7 - Ans
    i=4, 4 + 6
    i=5, 5 + 5 - Ans
    */
    List<List<Integer>> result = new ArrayList<>();
    int nHalf = n / 2;
    for (int i = 2; i <= nHalf; i++) {
      if (!composites[i] && !composites[n - i]) {
        result.add(List.of(i, n - i));
      }
    }

    return result;
  }
}