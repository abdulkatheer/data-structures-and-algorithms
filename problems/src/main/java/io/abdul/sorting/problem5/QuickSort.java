package io.abdul.sorting.problem5;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

// https://takeuforward.org/plus/dsa/sorting/algorithms/quick-sorting
public class QuickSort {

  public static void main(String[] args) {
    Solution sorter = new Solution();

    // Test case 1: Already sorted array
    int[] arr1 = {1, 2, 3, 4, 5};
    sorter.quickSort(arr1);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr1);

    // Test case 2: Reverse sorted array
    int[] arr2 = {5, 4, 3, 2, 1};
    sorter.quickSort(arr2);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr2);

    // Test case 3: Array with duplicates
    int[] arr3 = {4, 2, 4, 3, 1, 2};
    sorter.quickSort(arr3);
    assertArrayEquals(new int[]{1, 2, 2, 3, 4, 4}, arr3);

    // Test case 4: Single element array
    int[] arr4 = {42};
    sorter.quickSort(arr4);
    assertArrayEquals(new int[]{42}, arr4);

    // Test case 5: Empty array
    int[] arr5 = {};
    sorter.quickSort(arr5);
    assertArrayEquals(new int[]{}, arr5);

    // Test case 6: All identical elements
    int[] arr6 = {7, 7, 7, 7};
    sorter.quickSort(arr6);
    assertArrayEquals(new int[]{7, 7, 7, 7}, arr6);

    // Test case 7: Negative numbers
    int[] arr7 = {-3, -1, -7, 4, 0, 2};
    sorter.quickSort(arr7);
    assertArrayEquals(new int[]{-7, -3, -1, 0, 2, 4}, arr7);
  }
}

class Solution {

  private final Random random = new Random();

  public int[] quickSort(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
    return nums;
  }

  private void quickSort(int[] nums, int start, int end) {
    if (start > end) { // no elements
      return;
    }

    if (start == end) { // one element
      return;
    }

    int pivot = partition(nums, start, end);
    quickSort(nums, start, pivot - 1);
    quickSort(nums, pivot + 1, end);
  }

  private int partition(int[] nums, int start, int end) {
    int pivot = random.nextInt(start, end + 1);
    swap(nums, start, pivot); // moving pivot to start

    int right = start + 1;
    // right should point at the start of right segment
    // left elements are smaller or equals than pivot, right elements are bigger than pivot
    for (int i = start + 1; i <= end; i++) {
      if (nums[i] <= nums[start]) {
        swap(nums, i, right);
        right++;
      }
    }

    // right is at start of right segment, pivot is at start, swap start with end of left segment
    int pivotSortedPos = right - 1;
    swap(nums, start, pivotSortedPos);
    // now pivot is at its sorted positioon
    return pivotSortedPos;
  }

  private void swap(int[] nums, int src, int dest) {
    if (src == dest) {
      return;
    }
    int temp = nums[src];
    nums[src] = nums[dest];
    nums[dest] = temp;
  }
}
