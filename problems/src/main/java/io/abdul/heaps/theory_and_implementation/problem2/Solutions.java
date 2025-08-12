package io.abdul.heaps.theory_and_implementation.problem2;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Test Case 1: Simple unsorted array
    int[] nums1 = {6, 5, 2, 7, 1, 7};
    solution.buildMinHeap(nums1);
    assertTrue(isMinHeap(nums1), "Test Case 1 Failed");

    // Test Case 2: Mixed values
    int[] nums2 = {2, 3, 4, 1, 7, 3, 9, 4, 6};
    solution.buildMinHeap(nums2);
    assertTrue(isMinHeap(nums2), "Test Case 2 Failed");

    // Test Case 3: Already a valid min-heap
    int[] nums3 = {1, 2, 3, 4, 5, 6, 7};
    solution.buildMinHeap(nums3);
    assertTrue(isMinHeap(nums3), "Test Case 3 Failed");

    // Test Case 4: Reverse sorted array
    int[] nums4 = {9, 8, 7, 6, 5, 4, 3};
    solution.buildMinHeap(nums4);
    assertTrue(isMinHeap(nums4), "Test Case 4 Failed");

    // Test Case 5: All elements are equal
    int[] nums5 = {4, 4, 4, 4, 4, 4};
    solution.buildMinHeap(nums5);
    assertTrue(isMinHeap(nums5), "Test Case 5 Failed");

    // Test Case 6: Single element
    int[] nums6 = {42};
    solution.buildMinHeap(nums6);
    assertTrue(isMinHeap(nums6), "Test Case 6 Failed");

    // Test Case 7: Two elements
    int[] nums7 = {5, 3};
    solution.buildMinHeap(nums7);
    assertTrue(isMinHeap(nums7), "Test Case 7 Failed");

    // Test Case 8: Negative values
    int[] nums8 = {-3, -1, -7, -4};
    solution.buildMinHeap(nums8);
    assertTrue(isMinHeap(nums8), "Test Case 8 Failed");
  }

  private static boolean isMinHeap(int[] arr) {
    int n = arr.length;
    for (int i = 0; i <= (n - 2) / 2; i++) {
      if (2 * i + 1 < n && arr[i] > arr[2 * i + 1]) {
        return false;
      }
      if (2 * i + 2 < n && arr[i] > arr[2 * i + 2]) {
        return false;
      }
    }
    return true;
  }
}

/*
Heapify-down approach
 */
class Solution {

  public void buildMinHeap(int[] nums) {
    for (int i = nums.length - 1; i >= 0; i--) {
      heapifyDown(nums, i);
    }
  }

  private void heapifyDown(int[] nums, int ind) {
    while (true) {
      int left = 2 * ind + 1;
      int right = 2 * ind + 2;

      if (left < nums.length && nums[left] < nums[ind] && right < nums.length
          && nums[right] < nums[ind]) {
        if (nums[left] < nums[right]) {
          int temp = nums[left];
          nums[left] = nums[ind];
          nums[ind] = temp;
          ind = left;
        } else {
          int temp = nums[right];
          nums[right] = nums[ind];
          nums[ind] = temp;
          ind = right;
        }
      } else if (left < nums.length && nums[left] < nums[ind]) {
        int temp = nums[left];
        nums[left] = nums[ind];
        nums[ind] = temp;
        ind = left;
      } else if (right < nums.length && nums[right] < nums[ind]) {
        int temp = nums[right];
        nums[right] = nums[ind];
        nums[ind] = temp;
        ind = right;
      } else {
        break;
      }
    }
  }
}

class Solution2 {

  public void buildMinHeap(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
      heapifyUp(nums, i);
    }
  }

  private void heapifyUp(int[] nums, int i) {
    while (i > 0) {
      int parent = (i + 1) / 2 - 1;
      if (nums[parent] > nums[i]) {
        int temp = nums[parent];
        nums[parent] = nums[i];
        nums[i] = temp;
        i = parent;
      } else {
        break;
      }
    }
  }

}