package io.abdul.problem05;

import java.util.HashSet;

// https://leetcode.com/problems/distinct-prime-factors-of-product-of-array/
// tag:math tag:prime
public class Solutions {

}
class Solution {
  public int distinctPrimeFactors(int[] nums) {
    HashSet<Integer> pf = new HashSet<>();

    for (int num : nums) {
      for (int i = 2; i <= num; i++)
        while (num % i == 0) {
          pf.add(i);
          num /= i;
        }
    }

    return pf.size();
  }
}

class Solution2 {
  public int distinctPrimeFactors(int[] nums) {
    HashSet<Integer> pf = new HashSet<>();

    for (int num : nums) {
      for (int i = 2; i <= num; i++) {
        while (num % i == 0) {
          pf.add(i);
          num /= i;
        }
        if (num < 2) {
          break;
        }
      }
    }

    return pf.size();
  }
}

class Solution3 {
  public int distinctPrimeFactors(int[] nums) {
    int[] copy = new int[nums.length];
    System.arraycopy(nums, 0, copy, 0, nums.length);
    nums = copy;

    int count = 0;
    for (int i = 2; i <= 1000; i++) {
      boolean used = false;
      for (int j = 0; j < nums.length; j++) {
        while (nums[j] % i == 0) {
          used = true;
          nums[j] /= i;
        }
      }

      if (used) {
        count++;
      }
    }

    return count;
  }
}

/*
Optimal - To check if a number is prime or not, we need try dividing with primes up to sqrt(n)
 */
class Solution4 {
  public int distinctPrimeFactors(int[] nums) {
    // This is because it is sufficient to try divisors up to sqrt(n) to check if a number is a prime.
    // After trying all primes up to sqrt(n), if remainder is still > 1, then it's a prime
    int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };
    HashSet<Integer> pf = new HashSet<>();

    for (int i = 0; i < nums.length; i++) {
      int num = nums[i];
      for (int prime : primes) {
        while (num % prime == 0) {
          pf.add(prime);
          num /= prime;
        }
      }

      // after dividing by all up to sqrt(n), if it's still not 1, then it's a prime number
      if (num > 1) {
        pf.add(num); // larger prime
      }
    }

    return pf.size();
  }
}
