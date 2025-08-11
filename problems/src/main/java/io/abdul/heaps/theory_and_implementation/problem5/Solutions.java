package io.abdul.heaps.theory_and_implementation.problem5;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    
    // Case 1: Valid min-heap
    int[] heap1 = {10, 20, 30, 21, 23};
    assertTrue(solution.isHeap(heap1), "Expected true for valid min-heap: [10, 20, 30, 21, 23]");

    // Case 2: Invalid min-heap (child < parent)
    int[] heap2 = {10, 20, 30, 25, 15};
    assertFalse(solution.isHeap(heap2), "Expected false for invalid min-heap: [10, 20, 30, 25, 15]");

    // Case 3: Valid min-heap with equal values
    int[] heap3 = {1, 2, 1, 3};
    assertTrue(solution.isHeap(heap3), "Expected true for valid min-heap: [1, 2, 1, 3]");

    // Case 4: Single element (trivially a min-heap)
    int[] heap4 = {42};
    assertTrue(solution.isHeap(heap4), "Expected true for single element heap: [42]");

    // Case 5: Two elements, valid
    int[] heap5 = {1, 2};
    assertTrue(solution.isHeap(heap5), "Expected true for valid 2-element heap: [1, 2]");

    // Case 6: Two elements, invalid
    int[] heap6 = {2, 1};
    assertFalse(solution.isHeap(heap6), "Expected false for invalid 2-element heap: [2, 1]");

    // Case 7: Large valid min-heap
    int[] heap7 = {1, 3, 5, 7, 9, 11, 13, 15, 17};
    assertTrue(solution.isHeap(heap7), "Expected true for valid large heap");

    // Case 8: Large invalid min-heap
    int[] heap8 = {1, 3, 5, 7, 0, 11, 13, 15, 17};
    assertFalse(solution.isHeap(heap8), "Expected false for invalid large heap");
  }
}

class Solution {

  public boolean isHeap(int[] nums) {
    for (int i = nums.length - 1; i > 0; i--) {
      int parent = (i + 1) / 2 - 1;
      if (nums[i] < nums[parent]) {
        return false;
      }
    }

    return true;
  }
}