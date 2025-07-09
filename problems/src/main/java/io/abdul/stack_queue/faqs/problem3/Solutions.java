package io.abdul.stack_queue.faqs.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Basic examples
    assertEquals(6, solution.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    assertEquals(9, solution.trap(new int[]{4, 2, 0, 3, 2, 5}));
    assertEquals(10, solution.trap(new int[]{7, 4, 0, 9}));

    // Flat surface (no water can be trapped)
    assertEquals(0, solution.trap(new int[]{1, 1, 1, 1}));
    assertEquals(0, solution.trap(new int[]{0, 0, 0}));

    // Increasing / decreasing heights
    assertEquals(0, solution.trap(new int[]{1, 2, 3, 4, 5}));
    assertEquals(0, solution.trap(new int[]{5, 4, 3, 2, 1}));

    // Single pit
    assertEquals(2, solution.trap(new int[]{2, 0, 2}));
    assertEquals(6, solution.trap(new int[]{3, 0, 0, 3}));

    // Edge cases
    assertEquals(0, solution.trap(new int[]{1}));
    assertEquals(0, solution.trap(new int[]{1, 2}));

    // All zeros
    assertEquals(0, solution.trap(new int[]{0, 0, 0, 0}));

    // Large performance check
    int[] largeFlat = new int[100000];
    for (int i = 0; i < largeFlat.length; i++) {
      largeFlat[i] = 1;
    }
    assertEquals(0, solution.trap(largeFlat));

    assertEquals(16, solution.trap(new int[]{1, 8, 4, 5, 8, 20, 7, 6, 11, 3, 2}));
  }
}

/*
Brute-force

Water will be trapped only between two taller buildings.
A B C
No water can be trapped on A
No water can be trapped on C
But B might trap, bcz there is a building to its left, and to its right
But it'll trap only if both of them are bigger than B.
How much it can trap? min(leftMax, rightMax) - B
Extra will be overflown
 */
class Solution {

  public int trap(int[] height) {
    int n = height.length;
    int sum = 0;
    for (int i = 0; i < n; i++) {
      int leftMax = height[i];
      for (int j = 0; j < i; j++) {
        leftMax = Math.max(leftMax, height[j]);
      }

      int rightMax = height[i];
      for (int j = i + 1; j < n; j++) {
        rightMax = Math.max(rightMax, height[j]);
      }

      sum = sum + (Math.min(leftMax, rightMax) - height[i]);
    }

    return sum;
  }
}

/*
Better

T - O(n) - 2n
S - O(n) - 2n

If we could find the leftMax and rightMax at constant time, we can save time

We'll find next greatest and previous greatest in O(n) time
 */
class Solution2 {

  public int trap(int[] height) {
    int[] nge = nextGreatestElements(height);
    int[] pge = previousGreatestElements(height);

    int sum = 0;
    for (int i = 0; i < height.length; i++) {
      if (nge[i] == -1 || pge[i] == -1) {
        continue;
      }

      sum = sum + (Math.min(nge[i], pge[i]) - height[i]);
    }

    return sum;
  }

  private int[] nextGreatestElements(int[] nums) {
    int[] nge = new int[nums.length];
    int max = -1;
    for (int i = nums.length - 1; i >= 0; i--) {
      nge[i] = nums[i] > max ? -1 : max;
      max = Math.max(max, nums[i]);
    }

    return nge;
  }

  private int[] previousGreatestElements(int[] nums) {

    int[] pge = new int[nums.length];
    int max = -1;
    for (int i = 0; i < nums.length; i++) {
      pge[i] = nums[i] > max ? -1 : max;
      max = Math.max(max, nums[i]);
    }

    return pge;
  }
}

/*
Optimal - Two pointer approach

- Water is trapped when we have a taller building on the right and left
- We only need the smaller of left,right to find trapped water

Algorithm semantics:
- two pointers at left and right
- we either need to process left or right
- We process the SMALLER one! (if equals any one of them)
- By doing this, we can confidently say that, when we process a side, the elements left/right to it, will never be greater than the right/left element.
For ex,
0 1 0 2 1
0 1 2 3 4

left=0, right=3
left is smaller than right, so process left. leftMax=0
left=1, right=3
Both are equal, we can process either. Let's process right. rightMax=1
left=1, right=2
left is smaller than right, so process left.
At this point, we're processing nums[1], which is having a value 1.
This means that elements left to nums[1] including leftMax, will not be greater than nums[right] or rightMax.
Bcz we process smaller elements first, so whoever is the smaller side, their elements and max will be smaller or equals than the opposite side

Ex 2:
1 2 8 1 5 4

left=0, right=5
left is smaller. leftMax = 1

left=1, right=5
left is smaller. leftMax = 2

left=2, right=5
right is smaller. rightMax=4
At this point, nums[right] and rightMax is smaller than nums[left] and/or elements left to it

left=2, right=4
right is smaller. rightMax=5
At this point, nums[right] and rightMax is smaller than nums[left] and/or elements left to it

left=2, right=3
right is smaller. total=4
At this point, nums[right] and rightMax is smaller than nums[left] and/or elements left to it

left=2, right=2 stop!
 */
class Solution3 {

  public int trap(int[] height) {
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0, total = 0;

    while (left < right) {
      // process smaller of left and right first
      if (height[left] < height[right]) {
        // left is smaller, process it first. At this point, height[left] and the leftMax will never be bigger than nums[right] and rightMax
        if (height[left] > leftMax) { // can't trap water
          leftMax = height[left];
        } else {
          total = total + leftMax - height[left];
        }
        left++; // left is processed
      } else {
        if (height[right] > rightMax) { // can't trap water
          rightMax = height[right];
        } else {
          total = total + rightMax - height[right];
        }
        right--; // right is processed
      }
    }

    return total;
  }
}
