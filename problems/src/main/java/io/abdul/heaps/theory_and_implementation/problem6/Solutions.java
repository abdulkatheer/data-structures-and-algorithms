package io.abdul.heaps.theory_and_implementation.problem6;

import static io.abdul.heaps.theory_and_implementation.problem4.Solutions.isMaxHeap;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Test Case 1: Example 1
    int[] nums1 = {10, 20, 30, 21, 23};

    assertTrue(isMaxHeap(sol.minToMaxHeap(nums1)), "Test Case 1 Failed: Not a valid max-heap");

    // Test Case 2: Example 2 (Negative numbers)
    int[] nums2 = {-5, -4, -3, -2, -1};
    sol.minToMaxHeap(nums2);
    assertTrue(isMaxHeap(sol.minToMaxHeap(nums2)), "Test Case 2 Failed: Not a valid max-heap");

    // Test Case 3: Example 3
    int[] nums3 = {2, 6, 3, 100, 120, 4, 5};
    sol.minToMaxHeap(nums3);
    assertTrue(isMaxHeap(sol.minToMaxHeap(nums3)), "Test Case 3 Failed: Not a valid max-heap");

    // Test Case 4: Single element
    int[] nums4 = {42};
    sol.minToMaxHeap(nums4);
    assertTrue(isMaxHeap(sol.minToMaxHeap(nums4)), "Test Case 4 Failed: Not a valid max-heap");

    // Test Case 5: Already max heap
    int[] nums5 = {100, 50, 60, 10, 20};
    sol.minToMaxHeap(nums5);
    assertTrue(isMaxHeap(sol.minToMaxHeap(nums5)), "Test Case 5 Failed: Not a valid max-heap");

    // Test Case 6: All same elements
    int[] nums6 = {5, 5, 5, 5, 5, 5, 5};
    sol.minToMaxHeap(nums6);
    assertTrue(isMaxHeap(sol.minToMaxHeap(nums6)), "Test Case 6 Failed: Not a valid max-heap");
  }
}

/*
Copy conversion
T - O(n logn)
S - O(1)
 */
class Solution {

  public int[] minToMaxHeap(int[] nums) {
    int[] maxHeap = new int[nums.length];
    for (int i = 1; i < nums.length; i++) {
      maxHeap[i] = nums[i];
      heapifyUp(maxHeap, i);
    }

    return maxHeap;
  }

  private void heapifyUp(int[] nums, int index) {
    while (index > 0) {
      int parent = (index + 1) / 2 - 1;
      if (nums[parent] < nums[index]) {
        int temp = nums[parent];
        nums[parent] = nums[index];
        nums[index] = temp;
        index = parent;
      } else {
        break;
      }
    }
  }
}

/*
In-place conversion
T - O(n logn)
S - O(1)
 */
class Solution2 {

  public int[] minToMaxHeap(int[] nums) {
    int leafStart = nums.length / 2;
    for (int i = leafStart - 1; i >= 0; i--) {
      heapifyDown(nums, i);
    }

    return nums;
  }

  private void heapifyDown(int[] nums, int index) {
    while (true) {
      int left = 2 * index + 1;
      int right = 2 * index + 2;

      if (left < nums.length && nums[left] > nums[index] && right < nums.length
          && nums[right] > nums[index]) {
        if (nums[left] > nums[right]) {
          int temp = nums[left];
          nums[left] = nums[index];
          nums[index] = temp;
          index = left;
        } else {
          int temp = nums[right];
          nums[right] = nums[index];
          nums[index] = temp;
          index = right;
        }
      } else if (left < nums.length && nums[left] > nums[index]) {
        int temp = nums[left];
        nums[left] = nums[index];
        nums[index] = temp;
        index = left;
      } else if (right < nums.length && nums[right] > nums[index]) {
        int temp = nums[right];
        nums[right] = nums[index];
        nums[index] = temp;
        index = right;
      } else {
        break;
      }
    }
  }
}