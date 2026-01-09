package io.abdul.problem43;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
// tag:math
public class Solutions {

}

/*
Brute
T - O(n) - more constant operations
S - O(1)
*/
class Solution {
  public int countPrimeSetBits(int left, int right) {
    Set<Integer> primes = new HashSet<>();
    primes.add(2);
    primes.add(3);
    primes.add(5);
    primes.add(7);
    primes.add(11);
    primes.add(13);
    primes.add(17);
    primes.add(19);
    primes.add(23);
    primes.add(29);
    primes.add(31);

    int result = 0;
    for (int i = left; i <= right; i++) {
      if (primes.contains(countBits(i))) {
        result++;
      }
    }

    return result;
  }

  private int countBits(int num) {
    int count = 0;
    while (num != 0) {
      if ((num & 1) == 1) {
        count++;
      }
      num >>= 1;
    }

    return count;
  }
}
