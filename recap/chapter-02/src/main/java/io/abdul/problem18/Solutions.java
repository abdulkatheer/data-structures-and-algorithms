package io.abdul.problem18;

// https://leetcode.com/problems/count-primes/
// tag:math tag:prime tag:sieve_of_eratosthenes
public class Solutions {

}

class Solution {
  public int countPrimes(int n) {
    boolean[] composites = new boolean[n];

    int count = 0;
    for (int i = 2; i < n; i++) {
      if (!composites[i]) {
        count++;
        markComposites(composites, i);
      }
    }

    return count;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }
}

