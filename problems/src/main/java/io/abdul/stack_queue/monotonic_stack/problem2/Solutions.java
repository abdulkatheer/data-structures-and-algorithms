package io.abdul.stack_queue.monotonic_stack.problem2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Basic examples
    assertArrayEquals(
        new int[]{10, -1, 6, 6, 2, 6, 7, 7, 9, 9, 10},
        solution.nextGreaterElements(new int[]{3, 10, 4, 2, 1, 2, 6, 1, 7, 2, 9})
    );

    assertArrayEquals(
        new int[]{7, -1, 7, -1, 7, 5},
        solution.nextGreaterElements(new int[]{5, 7, 1, 7, 6, 0})
    );

    assertArrayEquals(
        new int[]{2, 3, 4, 5, -1},
        solution.nextGreaterElements(new int[]{1, 2, 3, 4, 5})
    );

    // All decreasing
    assertArrayEquals(
        new int[]{-1, 5, 5, 5, 5},
        solution.nextGreaterElements(new int[]{5, 4, 3, 2, 1})
    );

    // All same elements
    assertArrayEquals(
        new int[]{-1, -1, -1, -1},
        solution.nextGreaterElements(new int[]{3, 3, 3, 3})
    );

    // Single element
    assertArrayEquals(
        new int[]{-1},
        solution.nextGreaterElements(new int[]{99})
    );

    // Two elements
    assertArrayEquals(
        new int[]{5, -1},
        solution.nextGreaterElements(new int[]{3, 5})
    );
    assertArrayEquals(
        new int[]{-1, 5},
        solution.nextGreaterElements(new int[]{5, 3})
    );

    // Alternating up and down
    assertArrayEquals(
        new int[]{3, 4, 5, -1, 2},
        solution.nextGreaterElements(new int[]{2, 3, 4, 5, 1})
    );

    // Stress test: large array with same value
    int[] large = new int[100000];
    Arrays.fill(large, 7);
    int[] expected = new int[100000];
    Arrays.fill(expected, -1);
    assertArrayEquals(expected, solution.nextGreaterElements(large));
  }
}

/*
Brute-force

T - O(n^2)
S - O(1)

 */
class Solution {

  public int[] nextGreaterElements(int[] arr) {
    int[] nge = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      boolean found = false;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] > arr[i]) {
          nge[i] = arr[j];
          found = true;
          break;
        }
      }

      if (!found) {
        for (int j = 0; j < i + 1; j++) {
          if (arr[j] > arr[i]) {
            nge[i] = arr[j];
            found = true;
            break;
          }
        }
      }

      if (!found) {
        nge[i] = -1;
      }
    }

    return nge;
  }
}

/*
Brute-force
Treating circular array as 1 virtual array

T - O(n^2)
S - O(1)

5, 7, 1, 7, 6, 0

5, 7, 1, 7, 6, 0, 5, 7, 1, 7, 6, 0
For i=0, we need to look from 1 to 5
i=1, 2 to 6
i=2, 3 to 7
i=4, 4 to 8
i=5, 5 to 9
 */
class Solution2 {

  public int[] nextGreaterElements(int[] arr) {
    int[] nge = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      boolean found = false;
      for (int j = i + 1; j < i + arr.length; j++) {
        if (arr[j % arr.length] > arr[i]) {
          nge[i] = arr[j % arr.length];
          found = true;
          break;
        }
      }

      if (!found) {
        nge[i] = -1;
      }
    }

    return nge;
  }
}

/*
Optimal - Monotonic stack

T - O(n) - 2 * 2n
S - O(n)

For circular array, consider it as 2x array.
[2, 10, 12, 1, 11]

[2, 10, 12, 1, 11]
For 11, 0 to 3 will be a possibility. 1st bigger in them.

 */
class Solution3 {

  public int[] nextGreaterElements(int[] arr) {
    Stack<Integer> stack = new Stack<>();
    int[] nge = new int[arr.length];
    for (int i = arr.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && stack.peek() <= arr[i]) {
        stack.pop();
      }
      stack.push(arr[i]);
    }

    for (int i = arr.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && stack.peek() <= arr[i]) {
        stack.pop();
      }
      nge[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(arr[i]);
    }

    return nge;
  }
}