package io.abdul.problem32;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

// https://leetcode.com/problems/continuous-subarray-sum
// tag:array tag:prefix_sum tag:math
public class Solutions {

  public static void main(String[] args) {
    Solution2 solution = new Solution2();
    assertTrue(solution.checkSubarraySum(new int[]{0, 0}, 1));
  }
}

/*
Brute-force
T - O(n^2)

ERROR - TLE
*/
class Solution {

  public boolean checkSubarraySum(int[] nums, int k) {
    for (int i = 0; i < nums.length - 1; i++) {
      int sum = nums[i] + nums[i + 1];
      if (sum % k == 0) {
        return true;
      }
      for (int j = i + 2; j < nums.length; j++) {
        sum += nums[j];
        if (sum % k == 0) {
          return true;
        }
      }
    }

    return false;
  }
}

/*
Prefix Sum & Hashtable
T - O(n)
S - O(n)
*/
class Solution2 {

  /*
  If sum at i has multiple of k, then modulo will be 0. So we need a dummy entry to match 0.
  If sum at i has multiple of k + x. sum%k=x. We see if this x can be dropped by trimming the subarray.
  If any of the previous subarray has x extra, it means that part may have some multiples of k and extra x. If we cut that out, we lose some multiples, that's fine. and we lose this extra x as well making the sum full divisible by k;
  */
  public boolean checkSubarraySum(int[] nums, int k) {
    HashMap<Integer, Integer> remainders = new HashMap<>();
    remainders.put(0,
        -1); // to match exact multiples. Why pos -1? Bcz we need at least subarray length of 2

    int[] prefixSum = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      prefixSum[i] = i > 0 ? prefixSum[i - 1] + nums[i] : nums[i]; // handle i=0
      int remainder = prefixSum[i] % k;
      if (remainders.containsKey(
          remainder)) { // we can make subarray ending at i as divisible by k, by cutting off some parts
        if (i - remainders.get(remainder) >= 2) {
          return true;
        }
      } else {
        remainders.put(remainder, i);
      }
    }

    return false;
  }
}