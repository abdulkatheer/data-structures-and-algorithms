package io.abdul.problem34;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

// https://leetcode.com/problems/make-sum-divisible-by-p/
// tag:math tag:prefix_array
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
//    assertEquals(1, solution.minSubarray(new int[]{3, 1, 4, 2}, 6));
    assertEquals(2, solution.minSubarray(new int[]{6, 3, 5, 2}, 9));
    assertEquals(0, solution.minSubarray(new int[]{1000000000, 1000000000, 1000000000}, 3));
  }
}

/*
prefix sum
ERROR: Failed for large numbers
 */
class Solution {

  public int minSubarray(int[] nums, int p) {
    int n = nums.length;
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }

    int remainder = sum % p;
    if (remainder == 0) {
      return 0;
    }

    // Problem reduced to find length of the smallest subarray sum equals k
    return smallestSubarraySumEqualsK(nums, remainder);
  }

  private static int smallestSubarraySumEqualsK(int[] nums, int k) {
    int n = nums.length;
    int[] prefixSum = new int[n];
    HashMap<Integer, Integer> sums = new HashMap<>();
    sums.put(0, -1);
    int smallest = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
      prefixSum[i] = i > 0 ? prefixSum[i - 1] + nums[i] : nums[i];
      if (sums.containsKey(prefixSum[i] - k)) {
        smallest = Math.min(smallest, i - sums.get(prefixSum[i] - k));
      }
      sums.put(prefixSum[i], i);
    }

    return smallest == Integer.MAX_VALUE || smallest == n ? -1 : smallest;
  }
}

/*
Better
T - O(n)
S - O(n)

To handle large numbers, we'll modulo it with p. Bcz we're only dealing with remainders here.
Entire solution is turned into a mod p solution.

remainder = (remainder + num) % p;
This gives us the target remainder.
Meaning there exists at least one subarray whose sum equals k*p + remainder.
We need to find the smallest length of all.

a mod m + r mod m = b mod m
a mod m = b mod m - r mod m

where r is target remainder
b is remainder at i
a is remainder of any before i

Let's say p = 7, and r = 6, r_i = 2 (k*p + 2)
x mod m + 6 mod m = 2 mod m
x mod m = 2 mod m - 6 mod m
between 0 and i, we need to find a subarray whose sum equals m*p + 3

(k*p + 2) - (m*p + 3) = (a*p + 6) -> target remainder
k >= m

(9*7 + 2) - (5*7 + 3) = 65 - 38 = 27 = 6 mod 7

so we need remainder (current remainder - target remainder + p) % p

 */
class Solution2 {

  public int minSubarray(int[] nums, int p) {
    int n = nums.length;
    int remainder = 0;
    for (int num : nums) {
      remainder = (remainder + num) % p;
    }

    if (remainder == 0) {
      return 0;
    }

    /*
    Now we need to find subarray sum equals k*p + remainder
    k may be 0 and or more
     */

    return smallestSubarraySumEqualsKModP(nums, remainder, p);
  }

  private static int smallestSubarraySumEqualsKModP(int[] nums, int k, int p) {
    int n = nums.length;
    int[] remainderPrefixSum = new int[n];
    HashMap<Integer, Integer> sums = new HashMap<>();
    sums.put(0, -1);
    int smallest = n;
    for (int i = 0; i < n; i++) {
      remainderPrefixSum[i] = i > 0 ? (remainderPrefixSum[i - 1] + (nums[i] % p)) % p : nums[i] % p;
      // a mod p - b mod p = r mod p
      // b mod p = a mod p - r mod p
      int needed = (remainderPrefixSum[i] - k + p) % p;
      if (sums.containsKey(needed)) {
        smallest = Math.min(smallest, i - sums.get(needed));
      }
      sums.put(remainderPrefixSum[i], i);
    }

    return smallest == n ? -1 : smallest;
  }
}

/*
Optimal
T - O(n)
S - O(1)
 */
class Solution3 {

  public int minSubarray(int[] nums, int p) {
    int n = nums.length;
    int remainder = 0;
    for (int num : nums) {
      remainder = (remainder + num) % p;
    }

    if (remainder == 0) {
      return 0;
    }

    return smallestSubarraySumEqualsKModP(nums, remainder, p);
  }

  private static int smallestSubarraySumEqualsKModP(int[] nums, int k, int p) {
    int n = nums.length;
    HashMap<Integer, Integer> sums = new HashMap<>();
    sums.put(0, -1);
    int currentSum = 0; // (k*p + r) % p
    int smallest = n;
    for (int i = 0; i < n; i++) {
      currentSum = (currentSum + nums[i]) % p;
      // a mod p - b mod p = r mod p
      // b mod p = a mod p - r mod p
      int needed = (currentSum - k + p) % p;

      if (sums.containsKey(needed)) {
        smallest = Math.min(smallest, i - sums.get(needed));
      }

      sums.put(currentSum, i);
    }

    return smallest == n ? -1 : smallest;
  }
}