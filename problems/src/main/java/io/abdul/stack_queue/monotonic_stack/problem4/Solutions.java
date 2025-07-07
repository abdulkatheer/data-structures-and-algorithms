package io.abdul.stack_queue.monotonic_stack.problem4;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Stack;

// https://takeuforward.org/plus/dsa/problems/next-smaller-element
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertArrayEquals(new int[]{2, 5, 2, -1, -1},
        solution.nextSmallerElements(new int[]{4, 8, 5, 2, 25}));
    assertArrayEquals(new int[]{9, 8, 7, -1}, solution.nextSmallerElements(new int[]{10, 9, 8, 7}));
    assertArrayEquals(new int[]{-1, -1, -1, -1, -1},
        solution.nextSmallerElements(new int[]{1, 2, 3, 4, 5}));

    // All decreasing
    assertArrayEquals(new int[]{1, -1}, solution.nextSmallerElements(new int[]{2, 1}));

    // All same values
    assertArrayEquals(new int[]{-1, -1, -1}, solution.nextSmallerElements(new int[]{3, 3, 3}));

    // Mixed case
    assertArrayEquals(new int[]{1, -1, -1, 3, -1},
        solution.nextSmallerElements(new int[]{5, 1, 2, 6, 3}));

    // One element
    assertArrayEquals(new int[]{-1}, solution.nextSmallerElements(new int[]{42}));

    // Two elements
    assertArrayEquals(new int[]{-1, -1}, solution.nextSmallerElements(new int[]{3, 3}));
    assertArrayEquals(new int[]{-1, -1}, solution.nextSmallerElements(new int[]{4, 5}));
    assertArrayEquals(new int[]{3, -1}, solution.nextSmallerElements(new int[]{5, 3}));

    // Large numbers
    assertArrayEquals(new int[]{-1, -1},
        solution.nextSmallerElements(new int[]{1_000_000_000, 1_000_000_000}));

    // Stress test: descending input
    int[] largeDesc = new int[100000];
    for (int i = 0; i < largeDesc.length; i++) {
      largeDesc[i] = 100000 - i;
    }
    int[] expected = new int[100000];
    for (int i = 0; i < expected.length - 1; i++) {
      expected[i] = largeDesc[i + 1];
    }
    expected[expected.length - 1] = -1;
    assertArrayEquals(expected, solution.nextSmallerElements(largeDesc));
  }
}

/*
Brute-force
Explore all subarrays

T - O(n^2)
S - O(1)

 */
class Solution {

  public int[] nextSmallerElements(int[] arr) {
    int[] nse = new int[arr.length];
    Arrays.fill(nse, -1);

    for (int i = arr.length - 1; i >= 0; i--) {
      for (int j = i; j < arr.length; j++) {
        if (arr[j] < arr[i]) {
          nse[i] = arr[j];
          break;
        }
      }
    }

    return nse;
  }
}

/*
Optimal - Monotonic stack (Increasing order)

T - O(n) - 2n
S - O(n)

 */
class Solution2 {

  public int[] nextSmallerElements(int[] arr) {
    int n = arr.length;
    int[] nse = new int[n];
    Stack<Integer> stack = new Stack<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && stack.peek() >= arr[i]) {
        stack.pop();
      }
      nse[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(arr[i]);
    }

    return nse;
  }
}