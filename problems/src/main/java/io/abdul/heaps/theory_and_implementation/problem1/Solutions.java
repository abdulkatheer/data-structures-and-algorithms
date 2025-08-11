package io.abdul.heaps.theory_and_implementation.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Test Case 1
    int[] nums1 = {1, 4, 5, 5, 7, 6};
    solution.heapify(nums1, 5, 2);
    assertArrayEquals(new int[]{1, 4, 2, 5, 7, 5}, nums1, "Test Case 1 Failed");

    // Test Case 2
    int[] nums2 = {2, 4, 3, 6, 5, 7, 8, 7};
    solution.heapify(nums2, 0, 7);
    assertArrayEquals(new int[]{3, 4, 7, 6, 5, 7, 8, 7}, nums2, "Test Case 2 Failed");

    // Test Case 3: No bubbling needed (val is already valid)
    int[] nums3 = {1, 3, 4};
    solution.heapify(nums3, 1, 3);  // 3 == current value
    assertArrayEquals(new int[]{1, 3, 4}, nums3, "Test Case 3 Failed");

    // Test Case 4: Only bubbling down required
    int[] nums4 = {1, 2, 3};
    solution.heapify(nums4, 0, 10);  // Make root large
    assertArrayEquals(new int[]{2, 10, 3}, nums4, "Test Case 4 Failed");

    // Test Case 5: Single-element heap
    int[] nums5 = {4};
    solution.heapify(nums5, 0, 2);
    assertArrayEquals(new int[]{2}, nums5, "Test Case 5 Failed");

    // Test Case 6: Deep bubble up
    int[] nums6 = {1, 5, 6, 7, 8, 9, 10, 11};
    solution.heapify(nums6, 7, 0);  // Insert 0 at bottom
    assertArrayEquals(new int[]{0, 1, 6, 5, 8, 9, 10, 7}, nums6, "Test Case 6 Failed");
  }
}

/*
Iterative
T - O(log n) as it's complete binary tree
S - O(1) - in-place replacement
 */
class Solution {

  public void heapify(int[] nums, int ind, int val) {
    if (val > nums[ind]) {
      nums[ind] = val;
      heapifyDown(nums, ind);
    } else if (val < nums[ind]) {
      nums[ind] = val;
      heapifyUp(nums, ind);
    }
  }

  private void heapifyUp(int[] nums, int ind) {
    while (true) {
      int parent = (ind + 1) / 2 - 1;

      if (parent >= 0 && nums[parent] > nums[ind]) {
        // parent is bigger than ind
        int temp = nums[ind];
        nums[ind] = nums[parent];
        nums[parent] = temp;
        ind = parent;
      } else {
        // parent is smaller than ind
        break;
      }
    }
  }

  private void heapifyDown(int[] nums, int ind) {

    while (true) {
      int left = 2 * ind + 1;
      int right = 2 * ind + 2;
      if (left < nums.length && nums[left] < nums[ind] && right < nums.length
          && nums[right] < nums[ind]) {
        // both left and right are smaller than ind
        if (nums[left] < nums[right]) {
          int temp = nums[ind];
          nums[ind] = nums[left];
          nums[left] = temp;
          ind = left;
        } else {
          int temp = nums[ind];
          nums[ind] = nums[right];
          nums[right] = temp;
          ind = right;
        }
      } else if (left < nums.length && nums[left] < nums[ind]) {
        // left alone smaller than ind
        int temp = nums[ind];
        nums[ind] = nums[left];
        nums[left] = temp;
        ind = left;
      } else if (right < nums.length && nums[right] < nums[ind]) {
        // right alone smaller than ind
        int temp = nums[ind];
        nums[ind] = nums[right];
        nums[right] = temp;
        ind = right;
      } else {
        // Both left and right are bigger than ind
        break;
      }
    }
  }
}

/*
Recursive
T - O(log n) as it's complete binary tree
S - O(log n) - stack
 */
class Solution2 {

  public void heapify(int[] nums, int ind, int val) {
    if (val > nums[ind]) {
      nums[ind] = val;
      heapifyDown(nums, ind);
    } else if (val < nums[ind]) {
      nums[ind] = val;
      heapifyUp(nums, ind);
    }
  }

  private void heapifyUp(int[] nums, int ind) {
    if (ind == 0) { // root
      return;
    }

    int parent = (ind + 1) / 2 - 1;

    if (parent >= 0 && nums[parent] > nums[ind]) {
      // parent is bigger than ind
      int temp = nums[ind];
      nums[ind] = nums[parent];
      nums[parent] = temp;
      heapifyUp(nums, parent);
    } else {
      // parent is smaller than ind
      // Base case
    }
  }

  private void heapifyDown(int[] nums, int ind) {
    int left = 2 * ind + 1;
    int right = 2 * ind + 2;
    if (left < nums.length && nums[left] < nums[ind] && right < nums.length
        && nums[right] < nums[ind]) {
      // both left and right are smaller than ind
      if (nums[left] < nums[right]) {
        int temp = nums[ind];
        nums[ind] = nums[left];
        nums[left] = temp;
        heapifyDown(nums, left);
      } else {
        int temp = nums[ind];
        nums[ind] = nums[right];
        nums[right] = temp;
        heapifyDown(nums, right);
      }
    } else if (left < nums.length && nums[left] < nums[ind]) {
      // left alone smaller than ind
      int temp = nums[ind];
      nums[ind] = nums[left];
      nums[left] = temp;
      heapifyDown(nums, left);
    } else if (right < nums.length && nums[right] < nums[ind]) {
      // right alone smaller than ind
      int temp = nums[ind];
      nums[ind] = nums[right];
      nums[right] = temp;
      heapifyDown(nums, right);
    } else {
      // Both left and right are bigger than ind
      // Base case
    }
  }
}