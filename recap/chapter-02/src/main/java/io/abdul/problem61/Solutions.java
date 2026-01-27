package io.abdul.problem61;

// https://leetcode.com/problems/missing-number/
// tag:math tag:bit_manipulation
public class Solutions {

}

class Solution {
  public int missingNumber(int[] nums) {
    int n = nums.length;
    int missing = 0;

    for (int i = 1; i <= n; i++) {
      missing ^= i;
    }

    for (int num : nums) {
      missing ^= num;
    }

    return missing;
  }
}

class Solution2 {
  /*
  sum of first n natural numbers n * (n+1) / 2
  */
  public int missingNumber(int[] nums) {
    int n = nums.length;
    int expectedSum = (n * (n+1)) / 2; // n max is 10^4, so won't overflow

    int actualSum = 0;
    for (int num : nums) {
      actualSum += num;
    }

    return expectedSum - actualSum;
  }
}

class Solution3 {
  public int missingNumber(int[] nums) {
    int n = nums.length;

    // do xor of all nums and available nums in same loop
    // as num n will be available in the loop, do it outside
    int missing = n;
    for (int i = 0; i < n; i++) {
      missing ^= i;
      missing ^= nums[i];
    }

    return missing;
  }
}