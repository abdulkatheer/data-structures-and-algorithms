package io.abdul.stack_queue.monotonic_stack.problem6;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

/*
10 4 1 8 11 23 5 17 2 10  1  4  7
0  1 2 3  4  5 6  7 8  8 10 11 12

12 + 11 + 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1 = 78 subarrays
we can skip 12, as they'll give 0 sum only
so 66 subarrays

 */
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertEquals(4, solution.subArrayRanges(new int[]{1, 2, 3}));
    assertEquals(4, solution.subArrayRanges(new int[]{1, 3, 3}));

    // Single element
    assertEquals(0, solution.subArrayRanges(new int[]{10}));

    // Two elements
    assertEquals(1, solution.subArrayRanges(new int[]{2, 3}));
    assertEquals(2, solution.subArrayRanges(new int[]{5, 3}));

    // All same elements
    assertEquals(0, solution.subArrayRanges(new int[]{4, 4, 4, 4}));

    // All increasing
    assertEquals(10, solution.subArrayRanges(new int[]{1, 2, 3, 4}));

    // All decreasing
    assertEquals(10, solution.subArrayRanges(new int[]{4, 3, 2, 1}));

    // Mixed with negatives
    assertEquals(70, solution.subArrayRanges(new int[]{1, -2, 3, -4, 5}));

    // Edge values
    assertEquals(0, solution.subArrayRanges(new int[]{Integer.MAX_VALUE}));
    assertEquals(1, solution.subArrayRanges(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1}));

    // Long repeating pattern
    int[] pattern = new int[1000];
    for (int i = 0; i < pattern.length; i++) {
      pattern[i] = i % 5;
    }
    assertDoesNotThrow(() -> solution.subArrayRanges(pattern)); // performance/sanity check
  }
}

/*
Brute-force - Explore all subarrays

T - O(n^2)
S - O(1)

 */
class Solution {

  public long subArrayRanges(int[] nums) {
    int n = nums.length;

    long sum = 0;
    for (int i = 0; i < n; i++) {
      int smallest = nums[i];
      int largest = nums[i];
      for (int j = i; j < n; j++) {
        if (nums[j] < nums[i]) {
          smallest = nums[j];
        }
        if (nums[j] > largest) {
          largest = nums[j];
        }
        sum += (largest - smallest);
      }
    }

    return sum;
  }
}

/*
Optimal - Monotonic stack

1 4 3 2

1 = (1-1)
1 4 = (4-1)
1 4 3 = (4-1)
1 4 3 2 = (4-1)

4 = (4-4)
4 3 = (4-3)
4 3 2 = (4-2)

3 = (3-3)
3 2 = (3-2)

2 = (2-2)

If we look as a whole, sum of largest in all subarrays - sum of smallest of all subarrays
 */
class Solution2 {

  public long subArrayRanges(int[] nums) {
    return sumOfSubarraysMax(nums) - sumOfSubarraysMin(nums);
  }

  private long sumOfSubarraysMin(int[] nums) {
    long sum = 0;

    int[] nse = nextSmallerElements(nums);
    int[] psee = previousSmallerOrEqualElements(nums);
    for (int i = 0; i < nums.length; i++) {
      sum = sum + ((long) (i - psee[i]) * (nse[i] - i)) * nums[i];
    }

    return sum;
  }

  private long sumOfSubarraysMax(int[] nums) {
    long sum = 0;

    int[] nle = nextLargerElements(nums);
    int[] plee = previousLargerOrEqualElements(nums);
    for (int i = 0; i < nums.length; i++) {
      sum = sum + ((long) (i - plee[i]) * (nle[i] - i)) * nums[i];
    }

    return sum;
  }

  private int[] nextSmallerElements(int[] nums) {
    int n = nums.length;

    int[] nse = new int[n];
    Stack<Integer> stack = new Stack<>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      nse[i] = stack.isEmpty() ? n : stack.peek();
      stack.push(i);
    }

    return nse;
  }

  private int[] previousSmallerOrEqualElements(int[] nums) {
    int n = nums.length;

    int[] psee = new int[n];
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
        stack.pop();
      }
      psee[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }

    return psee;
  }

  private int[] nextLargerElements(int[] nums) {
    int n = nums.length;

    int[] nle = new int[n];
    Stack<Integer> stack = new Stack<>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
        stack.pop();
      }
      nle[i] = stack.isEmpty() ? n : stack.peek();
      stack.push(i);
    }

    return nle;
  }

  private int[] previousLargerOrEqualElements(int[] nums) {
    int n = nums.length;

    int[] plee = new int[n];
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        stack.pop();
      }
      plee[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }

    return plee;
  }
}