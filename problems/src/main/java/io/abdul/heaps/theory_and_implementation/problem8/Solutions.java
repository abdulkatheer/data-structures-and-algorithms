package io.abdul.heaps.theory_and_implementation.problem8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
    Solution3 sol = new Solution3();

    // Example 1
    assertEquals(4, sol.kthLargestElement(new int[]{1, 2, 3, 4, 5}, 2),
        "2nd largest in [1, 2, 3, 4, 5] should be 4");

    // Example 2
    assertEquals(-5, sol.kthLargestElement(new int[]{-5, 4, 1, 2, -3}, 5),
        "5th largest in [-5, 4, 1, 2, -3] should be -5");

    // Example 3
    assertEquals(7, sol.kthLargestElement(new int[]{11, 9, 8, 7, 3, 1}, 4),
        "4th largest in [11, 9, 8, 7, 3, 1] should be 7");

    // Duplicate elements
    assertEquals(4, sol.kthLargestElement(new int[]{4, 4, 4, 4}, 2),
        "2nd largest in [4, 4, 4, 4] should be 4");

    // Single element
    assertEquals(10, sol.kthLargestElement(new int[]{10}, 1),
        "1st largest in [10] should be 10");

    // Negative values only
    assertEquals(-2, sol.kthLargestElement(new int[]{-10, -20, -2, -5}, 1),
        "1st largest in [-10, -20, -2, -5] should be -2");

    // Mixed values
    assertEquals(0, sol.kthLargestElement(new int[]{-1, 0, 1, 2}, 3),
        "3rd largest in [-1, 0, 1, 2] should be 0");
  }
}

/*
Brute - Sort
T - O(n logn)
S - O(1)
 */
class Solution {

  public int kthLargestElement(int[] nums, int k) {
    Arrays.sort(nums);

    return nums[nums.length - k];
  }
}

/*
Better
T - O(n logk) - logk to insert an element to the min-heap
S - O(k)
 */
class Solution2 {

  public int kthLargestElement(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

    for (int i = 0; i < k; i++) {
      minHeap.add(nums[i]);
    }

    for (int i = k; i < nums.length; i++) {
      if (minHeap.peek() < nums[i]) {
        minHeap.poll();
        minHeap.add(nums[i]);
      }
    }

    return minHeap.peek();
  }
}

/*
Optimal - Quick sort
T - O(n^2) -> Avg case O(n) depending on the randomization
S - O(1)
 */
class Solution3 {

  private final Random random = new Random();

  public int kthLargestElement(int[] nums, int k) {
    int kthLargest;

    int left = 0;
    int right = nums.length - 1;
    while (true) {
      int pivot = partition(nums, left, right);
      int pivotSortedPos = pivot + 1;
      if (pivotSortedPos == k) {
        kthLargest = pivot;
        break;
      } else if (pivotSortedPos < k) {
        left = pivot + 1;
      } else {
        right = pivot - 1;
      }
    }

    return nums[kthLargest];
  }

  private int partition(int[] nums, int l, int r) {
    int pivot = randomPivot(l, r);
    swap(nums, l, pivot);

    int right = l + 1;
    int i = l + 1;
    while (i <= r) {
      if (nums[right] > nums[l]) { // i will be same as right, so no need of additional check
        right++;
        i++;
      } else if (nums[i] > nums[l]) { // i != right and nums[i] should be on left, we may swap
        swap(nums, right, i);
        right++;
        i++;
      } else { // nums[i] to be on right too
        i++;
      }
    }

    int pivotSortedPos = right - 1; // right is the start of right elements
    swap(nums, l, pivotSortedPos); // move pivot to its sorted position
    return pivotSortedPos;
  }

  private int randomPivot(int start, int end) {
    return random.nextInt(start, end + 1);
  }

  private void swap(int[] nums, int source, int destination) {
    if (source == destination) {
      return;
    }
    int temp = nums[source];
    nums[source] = nums[destination];
    nums[destination] = temp;
  }
}
