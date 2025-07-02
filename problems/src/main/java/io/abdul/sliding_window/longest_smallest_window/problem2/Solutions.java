package io.abdul.sliding_window.longest_smallest_window.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

    // Example 1
    assertEquals(10, solution.longestOnes(
        new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 3));

    // Example 2
    assertEquals(9, solution.longestOnes(
        new int[]{0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1}, 3));

    // Example 3
    assertEquals(5, solution.longestOnes(
        new int[]{1, 1, 0, 0, 1}, 3));

    // All ones, no flips needed
    assertEquals(5, solution.longestOnes(
        new int[]{1, 1, 1, 1, 1}, 2));

    // All zeros, flip all
    assertEquals(4, solution.longestOnes(
        new int[]{0, 0, 0, 0}, 4));

    // k = 0, no flips allowed
    assertEquals(2, solution.longestOnes(
        new int[]{1, 1, 0, 1, 1}, 0));

    // Single element, one flip allowed
    assertEquals(1, solution.longestOnes(
        new int[]{0}, 1));

    // Single element, no flip
    assertEquals(1, solution.longestOnes(
        new int[]{1}, 0));

    assertEquals(0, solution.longestOnes(
        new int[]{0, 0, 0, 0, 0, 0, 0}, 0));

    assertEquals(1, solution.longestOnes(
        new int[]{0, 0, 0, 1, 0, 0, 1}, 0));

    assertEquals(512, solution.longestOnes(
        new int[]{1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1,
            1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1,
            1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1,
            1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0,
            1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1,
            0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1,
            0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0,
            1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1,
            1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0,
            0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0,
            0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0,
            0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1,
            0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1,
            1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0,
            1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1,
            1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0,
            0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
            1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0},
        254));

  }
}

/*
Step 1 - Brute force
Explore all subarrays and find max

T - O(n^2)
S - O(1)
 */
class Solution {

  public int longestOnes(int[] nums, int k) {
    int n = nums.length;

    int max = 0;
    for (int i = 0; i < n; i++) {
      int zeroes = 0;
      for (int j = i; j < n; j++) {
        if (nums[j] == 0) {
          zeroes++;
        }
        if (zeroes > k) { // zero limits exhausted
          break;
        }
        max = Math.max(max, j - i + 1);
      }
    }

    return max;
  }
}

/*
Step 2 - Better
Sliding window approach

T - O(n) -> 2n; right moves from 0 to n-1; left moves from 0 to n-1 (if all are zeroes and k=0)
S - O(1)

 */
class Solution2 {

  public int longestOnes(int[] nums, int k) {
    int left = 0, right = 0, zeroCount = 0;

    int max = 0;
    while (right < nums.length) {
      if (nums[right] == 0) {
        zeroCount++;
      }
      /*
      if zero count limit exhausted, we try to reduce it to keep within limit
      if k=0, we try to move to non-zero place. we won't find any, so we'll end up at left == right, and still zeroCount=1, zeroCount>k
      if k=1, we'll move left to current position. left == right and zeroCount=1, zeroCount<=k
      if k=x, we'll definitely have some zeroes in the past, we'll move to one of prev positions of right, zeroCount < k
       */
      while (zeroCount > k && left < right) {
        if (nums[left] == 0) {
          zeroCount--;
        }
        left++;
      }

      if (zeroCount <= k) { // when k == 0, we may not make zeroCount <= k, so double check here
        max = Math.max(max, right - left + 1);
      }
      right++;
    }

    return max;
  }
}

/*
Step 2 - Optimal
Sliding window approach

T - O(n) -> n; right moves from 0 to n-1; left moves along with right if zero limit exhausted
S - O(1)

 */
class Solution3 {

  public int longestOnes(int[] nums, int k) {
    int left = 0, right = 0, zeroCount = 0;

    int max = 0;
    while (right < nums.length) {
      if (nums[right] == 0) {
        zeroCount++;
      }
      /*
      Just do once, not until!

      we move only once, irrespective of whether we're able to reduce the count or not.
      We need to do this, irrespective of right is at zero or not!
      Why?
      we've a max let's say 10
      So we the best value will be anything greater than 10.
      If we reduce the window below this, we'll have to expand to 10 and beyond to find a result.
      Unnecessary loops. So we'll move by one step, which keeps the window size 10 only!
      If at all, we're able to reduce zeroe's and bring it down to the limit in the future, we'll be able to find a result in the result if condition if (zeroCount <= k)
       */
      if (zeroCount > k) {
        if (nums[left] == 0) {
          zeroCount--;
        }
        left++;
      }

      if (zeroCount <= k) { // do only if valid
        max = Math.max(max, right - left + 1);
      }
      right++;
    }

    return max;
  }
}