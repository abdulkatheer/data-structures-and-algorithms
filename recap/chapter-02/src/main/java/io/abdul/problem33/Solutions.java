package io.abdul.problem33;

import java.util.HashMap;

// https://leetcode.com/problems/subarray-sums-divisible-by-k/
// tag:array tag:prefix_sum tag:math
public class Solutions {

}

/*
Better - Prefix Sum & Hashtable
0 2
5 3
1 1

5 2 10 6 1

1 - 2 10
1 - 6
1 - 2 10 6
1 - 5 2 10 6 1
*/
class Solution {
  public int subarraysDivByK(int[] nums, int k) {
    HashMap<Integer, Integer> remainders = new HashMap<>();
    remainders.put(0, 1); // to support sum fully divisible by k

    int[] prefixSum = new int[nums.length];
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
      prefixSum[i] = i > 0 ? prefixSum[i - 1] + nums[i] : nums[i];
      // Do right modulo for negative numbers
      // if negative, after modulo, we need to + k and then % k
      int remainder = prefixSum[i] < 0 ? ((prefixSum[i] % k) + k) % k : prefixSum[i] % k;
      if (remainders.containsKey(remainder)) {
        int remainderCounts = remainders.get(remainder);
        count += remainderCounts;
        remainders.put(remainder, remainderCounts + 1);
      } else {
        remainders.put(remainder, 1);
      }
    }

    return count;
  }
}

/*
Optimal - Prefix Sum & Frequency Array (as key is bounded by k)
*/
class Solution2 {
  public int subarraysDivByK(int[] nums, int k) {
    int[] remainders = new int[k];
    remainders[0] = 1; // to support sum fully divisible by k

    int[] prefixSum = new int[nums.length];
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
      prefixSum[i] = i > 0 ? prefixSum[i - 1] + nums[i] : nums[i];
      // Do right modulo for negative numbers
      // if negative, after modulo, we need to + k and then % k
      int remainder = prefixSum[i] < 0 ? ((prefixSum[i] % k) + k) % k : prefixSum[i] % k;
      count += remainders[remainder];
      remainders[remainder]++;
    }

    return count;
  }
}
