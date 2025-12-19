package io.abdul.problem11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(3, solution.maximumPrimeDifference(new int[]{4, 2, 9, 5, 3}));
    assertEquals(0, solution.maximumPrimeDifference(new int[]{4, 8, 2, 8}));
  }
}

class Solution {

  public int maximumPrimeDifference(int[] nums) {
    boolean[] primes = primes(100); // Bcz nums[i] <= 100

    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
      if (primes[nums[left]] && primes[nums[right]]) {
        return right - left;
      } else if (primes[nums[left]]) {
        right--;
      } else if (primes[nums[right]]) {
        left++;
      } else {
        left++;
        right--;
      }
    }

    return 0;
  }

  // Sieve of Eratosthenes
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
