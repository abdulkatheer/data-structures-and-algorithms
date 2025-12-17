package io.abdul.prime_number.concept02;

import java.util.ArrayList;
import java.util.List;

/*
Find Primes up to n
 */
public class Solutions {

}

/*
Sieve of Eratosthenes

When to use?
- To find all primes up to n
- To find all primes between range L and R where R-L is huge
- To check primality for many queries
- To compute prime factors for many queries
 */
class Solution {

  /*
  T - O(n log logn)
  S - O(n)
   */
  public List<Integer> findPrimes(int n) {
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
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }
}
