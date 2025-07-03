package io.abdul.sliding_window.counting_subarrays_and_substrings.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertEquals(4, solution.numSubarraysWithSum(new int[]{1, 1, 0, 1, 0, 0, 1}, 3));
    assertEquals(10, solution.numSubarraysWithSum(new int[]{0, 0, 0, 0, 1}, 0));

    // Single element cases
    assertEquals(1, solution.numSubarraysWithSum(new int[]{1}, 1));
    assertEquals(1, solution.numSubarraysWithSum(new int[]{0}, 0));
    assertEquals(0, solution.numSubarraysWithSum(new int[]{0}, 1));

    // All zeros
    assertEquals(15, solution.numSubarraysWithSum(new int[]{0, 0, 0, 0, 0}, 0)); // n*(n+1)/2

    // All ones
    assertEquals(1, solution.numSubarraysWithSum(new int[]{1, 1, 1}, 3));
    assertEquals(2, solution.numSubarraysWithSum(new int[]{1, 1, 1}, 2)); // [1,1] three times

    // Goal not possible
    assertEquals(0, solution.numSubarraysWithSum(new int[]{0, 0, 0}, 1));
    assertEquals(0, solution.numSubarraysWithSum(new int[]{1, 1, 1}, 5));

    // Mixed case with multiple valid subarrays
    assertEquals(4,
        solution.numSubarraysWithSum(new int[]{1, 0, 1, 0, 1}, 2)); // [1,0,1], [0,1,0,1], etc.

    // Full array equals goal
    assertEquals(1, solution.numSubarraysWithSum(new int[]{1, 1, 0, 0, 1}, 3));

    // Stress case
    int[] large = new int[30000];
    Arrays.fill(large, 0);
    assertEquals(450015000, solution.numSubarraysWithSum(large, 0)); // n*(n+1)/2
  }
}

/*
Step 1 - Brute-force
Explore all possibilities

 */
class Solution {

  public int numSubarraysWithSum(int[] nums, int goal) {
    int n = nums.length;

    int result = 0;
    for (int i = 0; i < n; i++) {
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += nums[j];

        if (sum == goal) {
          result++;
        }
        if (sum > goal) {
          break;
        }
      }
    }

    return result;
  }
}

/*
Step 2 - Optimal

T - O(n) - 2 * 2n
S - O(1)

It's difficult to find the exact sum using two pointer.
But easy to count subarrays lesser than equal to k.

Let's say we've n subarrays in total
a subarrays - less than or equal to k
b subarrays - less than or equal to k-1
a includes - b and remaining are equals to k
if we've 10 subarrays with sum less than or equal to k
and 7 subarrays with sum less than or equal to k-1
then out of 10, subarrays with sum equals to k is a-b.

Edge case, when sum is less than 0, we can't achieve it as we only have 0s and 1s. So it's zero.

numSubarraysWithSumLessThanOrEqualTo goal
goal
goal-1
goal-2
.
.
0

numSubarraysWithSumLessThanOrEqualTo goal-1
goal-1
goal-2
.
.
0

When we subtract both, subarrays with sum goal can be found!
 */
class Solution2 {

  public int numSubarraysWithSum(int[] nums, int goal) {
    return numSubarraysWithSumLessThanOrEqualTo(nums, goal) -
        numSubarraysWithSumLessThanOrEqualTo(nums, goal - 1);
  }

  public int numSubarraysWithSumLessThanOrEqualTo(int[] nums, int goal) {
    /* If goal is negative, there can't be any valid subarray sum */
    if (goal < 0) {
      return 0;
    }

    int n = nums.length;

    int result = 0;
    int left = 0, right = 0;

    int sum = 0;
    while (right < n) {
      sum += nums[right];

      while (sum > goal && left < n) {
        sum -= nums[left];
        left++;
      }

      // All subarrays from left, ending at right has sum less than or equal to goal.
      result += (right - left + 1);

      right++;
    }

    return result;
  }
}