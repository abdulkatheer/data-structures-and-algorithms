package io.abdul.problem35;

import java.util.HashMap;

// https://leetcode.com/problems/two-sum/
// tag:math tag:array
public class Solutions {

}

/*
Brute-force
T - O(n^2)
S - O(1)
*/
class Solution {

  public int[] twoSum(int[] nums, int target) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i == j) {
          continue;
        }
        if (nums[i] + nums[j] == target) {
          return new int[]{i, j};
        }
      }
    }

    return new int[]{-1, -1};
  }
}

/*
Optimal
T - O(n)
S - O(n)
*/
class Solution2 {

  public int[] twoSum(int[] nums, int target) {
    int n = nums.length;
    HashMap<Integer, Integer> numPos = new HashMap<>(n);
    for (int i = 0; i < n; i++) {
      int need = target - nums[i];
      if (numPos.containsKey(need)) { // Handles duplicate when x + x = target
        return new int[]{numPos.get(need), i};
      }
      numPos.put(nums[i], i);
    }

    return new int[]{-1, -1};
  }
}
