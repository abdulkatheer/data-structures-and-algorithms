package io.abdul.heaps.theory_and_implementation.problem7;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Test 1: Example case
    int[] nums1 = {7, 4, 1, 5, 3};
    solution.heapSort(nums1);
    assertArrayEquals(new int[]{1, 3, 4, 5, 7}, nums1, "Should sort basic array");

    // Test 2: Array with duplicates
    int[] nums2 = {5, 4, 4, 1, 1};
    solution.heapSort(nums2);
    assertArrayEquals(new int[]{1, 1, 4, 4, 5}, nums2, "Should handle duplicates correctly");

    // Test 3: Already sorted array
    int[] nums3 = {1, 2, 3, 4, 5};
    solution.heapSort(nums3);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums3, "Should keep sorted array as is");

    // Test 4: Reverse sorted array
    int[] nums4 = {5, 4, 3, 2, 1};
    solution.heapSort(nums4);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums4, "Should sort reverse array");

    // Test 5: Single element array
    int[] nums5 = {42};
    solution.heapSort(nums5);
    assertArrayEquals(new int[]{42}, nums5, "Should handle single element");

    // Test 6: Negative numbers and mixed
    int[] nums6 = {0, -1, 3, -5, 2};
    solution.heapSort(nums6);
    assertArrayEquals(new int[]{-5, -1, 0, 2, 3}, nums6, "Should sort with negatives");

    // Test 7: All same numbers
    int[] nums7 = {2, 2, 2, 2, 2};
    solution.heapSort(nums7);
    assertArrayEquals(new int[]{2, 2, 2, 2, 2}, nums7, "Should handle all equal values");
  }
}

/*
T - O(n logn) - n logn to build max heap; n logn to sort
 */
class Solution {

  public void heapSort(int[] nums) {
    convertToMaxHeap(nums);

    for (int i = nums.length - 1; i > 0; i--) {
      int temp = nums[i];
      nums[i] = nums[0]; // move max to end
      nums[0] = temp;
      // now element at nums[0] has to repositioned
      heapifyDown(nums, 0, i - 1);
      // heapify only till i-1 as i has the max element we move just now
    }
  }

  private void convertToMaxHeap(int[] nums) {
    int leaf = nums.length / 2;
    for (int i = leaf - 1; i >= 0; i--) {
      heapifyDown(nums, i, nums.length - 1);
    }
  }

  private void heapifyDown(int[] nums, int i, int lastPos) {
    while (true) {
      int left = 2 * i + 1;
      int right = 2 * i + 2;

      if (left <= lastPos && nums[left] > nums[i] && right <= lastPos
          && nums[right] > nums[i]) {
        if (nums[left] > nums[right]) {
          int temp = nums[left];
          nums[left] = nums[i];
          nums[i] = temp;
          i = left;
        } else {
          int temp = nums[right];
          nums[right] = nums[i];
          nums[i] = temp;
          i = right;
        }
      } else if (left <= lastPos && nums[left] > nums[i]) {
        int temp = nums[left];
        nums[left] = nums[i];
        nums[i] = temp;
        i = left;
      } else if (right <= lastPos && nums[right] > nums[i]) {
        int temp = nums[right];
        nums[right] = nums[i];
        nums[i] = temp;
        i = right;
      } else {
        break;
      }
    }
  }
}