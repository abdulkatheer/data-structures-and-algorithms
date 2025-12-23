package io.abdul.problem14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.TreeMap;

// https://leetcode.com/problems/count-prime-gap-balanced-subarrays/
// tag:math tag:sieve_of_eratosthenes tag:sliding_window
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();
//    assertEquals(2, solution.primeSubarray(new int[]{1, 2, 3}, 1));
//    assertEquals(4, solution.primeSubarray(new int[]{2, 3, 5, 7}, 3));
//    assertEquals(2, solution.primeSubarray(new int[]{9551, 41039, 4411}, 41466));
    assertEquals(64, solution.primeSubarray(
        new int[]{3313, 28488, 13099, 8087, 9967, 7331, 7620, 31596, 22433, 7121, 24061, 33713,
            38420, 5549, 26821, 28661, 46317, 39301, 41941, 37957, 13975, 39983, 12577, 12421,
            14747, 45406, 4537, 24007, 24007, 167, 5, 13331, 10799}, 10453));
  }
}

// ERROR - TLE
class Solution {

  public int primeSubarray(int[] nums, int k) {
    int n = nums.length;
    boolean[] primes = new boolean[n];
    for (int i = 0; i < n; i++) {
      primes[i] = isPrime(nums[i]);
    }

    int count = 0;
    // T - O(n^2)
    for (int i = 0; i < n; i++) {
      int max = -1;
      int min = -1;
      int primeCount = 0;
      for (int j = i; j < n; j++) {
        if (primes[j]) {
          primeCount++;
          max = max == -1 || nums[j] > nums[max] ? j : max;
          min = min == -1 || nums[j] < nums[min] ? j : min;
        }
        if (primeCount > 1) {
          if (nums[max] - nums[min] <= k) {
            count++;
          }
        }
      }
    }

    return count;
  }

  private boolean isPrime(int num) {
    if (num <= 1) {
      return false;
    }

    if (num <= 3) {
      return true;
    }

    if (num % 2 == 0 || num % 3 == 0) {
      return false;
    }

    int sqrt = (int) Math.sqrt(num);
    for (int i = 5; i <= sqrt; i += 6) {
      if (num % i == 0) {
        return false;
      }
      if (num % (i + 2) == 0) {
        return false;
      }
    }

    return true;
  }
}

// ERROR - TLE
class Solution2 {

  public int primeSubarray(int[] nums, int k) {
    int n = nums.length;
    int maxNum = 0;
    for (int num : nums) {
      maxNum = Math.max(maxNum, num);
    }

    boolean[] primes = primes(maxNum);

    int count = 0;
    // T - O(n^2)
    for (int i = 0; i < n; i++) {
      int max = -1;
      int min = -1;
      int primeCount = 0;
      for (int j = i; j < n; j++) {
        if (primes[nums[j]]) {
          primeCount++;
          max = max == -1 || nums[j] > nums[max] ? j : max;
          min = min == -1 || nums[j] < nums[min] ? j : min;
        }
        if (primeCount > 1) {
          if (nums[max] - nums[min] <= k) {
            count++;
          }
        }
      }
    }

    return count;
  }

  // T - O(n log log n)
  private boolean[] primes(int num) {
    boolean[] primes = new boolean[num + 1];
    Arrays.fill(primes, true);
    primes[0] = primes[1] = false;

    int sqrt = (int) Math.sqrt(num);
    for (int i = 2; i <= sqrt; i++) {
      if (primes[i]) {
        for (long j = (long) i * i; j <= num; j += i) {
          primes[(int) j] = false;
        }
      }
    }

    return primes;
  }
}

class Solution3 {

  public int primeSubarray(int[] nums, int k) {
    boolean[] primes = primes();

    int l = 0;
    int r = 0;
    int primeCount = 0;
    int secondLastPrimePos = -1;
    int lastPrimePos = -1;
    int result = 0;

    TreeMap<Integer, Integer> primeFrequencies = new TreeMap<>();

    while (r < nums.length) {
      if (primes[nums[r]]) { // expand
        secondLastPrimePos = lastPrimePos;
        lastPrimePos = r;
        primeFrequencies.put(nums[r], primeFrequencies.getOrDefault(nums[r], 0) + 1);
        primeCount++;
      }

      // Shrink window to make it valid, worst case it will end up in single prime
      while (!primeFrequencies.isEmpty()
          && primeFrequencies.lastKey() - primeFrequencies.firstKey() > k) {
        // l may be at prime or non-prime
        // We need to remove from the left to make it valid
        if (primes[nums[l]]) {
          int currFreq = primeFrequencies.get(nums[l]);
          if (currFreq == 1) {
            primeFrequencies.remove(nums[l]);
          } else {
            primeFrequencies.put(nums[l], currFreq - 1);
          }
          primeCount--;
        }
        l++;
      }

      // Now window is valid
      // l and r may or may not be at prime
      if (primeCount >= 2) {
        // l to r is a valid subarray
        // all windows in it where 2 primes exist are valid
        // We may have more than 2 primes
        // Up to second last prime, we can count subarrays as we need at least 2 primes in a subarray
        result = result + (secondLastPrimePos - l) + 1;
      }

      r++; // increment after counting for next iteration
    }

    return result;
  }

  private boolean[] primes() {
    boolean[] isPrime = new boolean[50001];
    java.util.Arrays.fill(isPrime, true);
    isPrime[0] = false;
    isPrime[1] = false;
    for (int i = 2; i * i <= 50000; i++) {
      if (isPrime[i]) {
        for (int j = i * i; j <= 50000; j += i) {
          isPrime[j] = false;
        }
      }
    }

    return isPrime;
  }
}