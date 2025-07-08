package io.abdul.stack_queue.faqs.problem2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Deque;
import java.util.LinkedList;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    Solution2 solution = new Solution2();

    // Basic examples
    assertArrayEquals(new int[]{4, 3, 5, 5, 6, 8},
        solution.maxSlidingWindow(new int[]{4, 0, -1, 3, 5, 3, 6, 8}, 3));
    assertArrayEquals(new int[]{25}, solution.maxSlidingWindow(new int[]{20, 25}, 2));
    assertArrayEquals(new int[]{3, 3, 5, 5, 6, 7},
        solution.maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));

    // Single element window
    assertArrayEquals(new int[]{1, 3, 2, 5, 4},
        solution.maxSlidingWindow(new int[]{1, 3, 2, 5, 4}, 1));

    // Window size = array length
    assertArrayEquals(new int[]{5}, solution.maxSlidingWindow(new int[]{1, 3, 2, 5, 4}, 5));

    // Increasing sequence
    assertArrayEquals(new int[]{3, 4, 5}, solution.maxSlidingWindow(new int[]{1, 2, 3, 4, 5}, 3));

    // Decreasing sequence
    assertArrayEquals(new int[]{5, 4, 3}, solution.maxSlidingWindow(new int[]{5, 4, 3, 2, 1}, 3));

    // All elements same
    assertArrayEquals(new int[]{7, 7, 7, 7},
        solution.maxSlidingWindow(new int[]{7, 7, 7, 7, 7}, 2));

    // Edge: k = 1
    assertArrayEquals(new int[]{-1}, solution.maxSlidingWindow(new int[]{-1}, 1));

    // Edge: negative numbers
    assertArrayEquals(new int[]{-1, -2, -2},
        solution.maxSlidingWindow(new int[]{-1, -2, -3, -2}, 2));

    // Large input performance test
    int[] large = new int[100000];
    for (int i = 0; i < large.length; i++) {
      large[i] = i;
    }
    assertDoesNotThrow(() -> solution.maxSlidingWindow(large, 50000));

  }
}

/*
Brute-force

T - O(n*k)
S - O(1)

 */
class Solution {

  public int[] maxSlidingWindow(int[] arr, int k) {
    int n = arr.length;
    int resultSize = n - k + 1;
    int[] result = new int[resultSize];

    for (int i = 0; i < resultSize; i++) {
      int max = Integer.MIN_VALUE;
      for (int j = i; j < i + k; j++) {
        max = Math.max(max, arr[j]);
      }
      result[i] = max;
    }

    return result;
  }
}


/*
Optimal - Monotonic stack using Deque

T - O(n) - 2n
S - O(n)

1) We need to keep max accessible in O(1) time
2) Able to remove elements when the window is moving

To access max in constant time, we'll maintain a monotonically decreasing stack. Max will be at the front always.
To remove elements out of the window, we'll store indices in the stack rather than the values. Remove based on simple math.

 */
class Solution2 {

  public int[] maxSlidingWindow(int[] arr, int k) {
    Deque<Integer> deque = new LinkedList<>();

    int n = arr.length;
    int resultSize = n - k + 1;
    int[] result = new int[resultSize];

    for (int i = 0; i < k; i++) {
      while (!deque.isEmpty() && arr[deque.peek()] <= arr[i]) {
        deque.pop();
      }
      deque.push(i);
    }

    result[0] = arr[deque.getLast()];

    int resPos = 1;
    for (int i = k; i < n; i++, resPos++) {
      // if k == 3, i == 4, we can only have 2,3,4 in the deque.
      if (deque.getLast() <= i - k) {
        // we add one by one, so not more than one can exist in the head which is beyond the window
        deque.removeLast();
      }

      while (!deque.isEmpty() && arr[deque.peek()] <= arr[i]) {
        deque.pop();
      }
      deque.push(i);

      result[resPos] = arr[deque.getLast()];
    }

    return result;
  }
}
