package io.abdul.problem07;

import java.util.ArrayList;
import java.util.List;

public class Solutions {

}

class Solution {

  public boolean primeSubOperation(int[] nums) {
    int max = -1;
    for (int num : nums) {
      max = Math.max(num, max);
    }

    List<Integer> primes = primes(max);

    for (int i = nums.length - 1; i > 0; i--) {
      if (nums[i] <= nums[i - 1]) {
        int k = 0;
        // trying to make nums[i-1] lesser than nums[i] by taking smallest possible prime
        while (k < primes.size() && primes.get(k) < nums[i - 1]) {
          int newNum = nums[i - 1] - primes.get(k);
          if (newNum < nums[i]) {
            nums[i - 1] = newNum;
            break;
          }
          k++;
        }

        if (nums[i] <= nums[i - 1]) {
          return false; // can't make it lesser by any prime lesser than it
        }
      }
    }

    return true;
  }

  private List<Integer> primes(int n) {
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

/*
Optimal

Instead of trying to minus only when there's a diff, we try to minimize values since the beginning. Greedy approach.
If we keep the smallest possible number from first position onwards, whether its required or not, we'll be able to get sorted array if at all possible.
Why from first? Bcz we can only minus, so we try to minimize values from left.
If we could do addition, we should have maximized values from end.
 */
class Solution2 {

  // Try to minimize values from the beginning
  public boolean primeSubOperation(int[] nums) {
    int max = -1;
    for (int num : nums) {
      max = Math.max(max, num);
    }

    int[] prevPrime = new int[max + 1]; // stores largest prime <= i
    for (int i = 2; i <= max; i++) {
      if (isPrime(i)) {
        prevPrime[i] = i;
      } else {
        prevPrime[i] = prevPrime[i - 1];
      }
    }

    for (int i = 0; i < nums.length; i++) {
      int bound;

      if (i == 0) { // first number has to smallest number
        bound = nums[i];
      } else {
        bound = nums[i] - nums[i - 1];
      }

      if (bound <= 0) { // if i is smaller than or equals to i-1, we can stop
        return false;
      }

      // try to reduce the value at i to smallest value larger than i-1
      // bound is the gap between i and i-1
      // we need to reduce gap as much as possible
      // we need to minus some prime
      // the prime has to be lesser than i and also lesser than bound
      // why not equals bound? if bound is a prime and we minus that for i, i and i-1 will become same
      // so we need to minus prime less than or equals bound-1, which will definitely be less than i
      nums[i] -= prevPrime[bound - 1];
    }

    return true; // bound was never <= 0, so all are in order
  }

  private boolean isPrime(int num) {
    if (num < 2) {
      return false;
    }

    int sqrt = (int) Math.sqrt(num);
    for (int i = 2; i <= sqrt; i++) {
      if (num % i == 0) {
        return false;
      }
    }

    return true;
  }
}
