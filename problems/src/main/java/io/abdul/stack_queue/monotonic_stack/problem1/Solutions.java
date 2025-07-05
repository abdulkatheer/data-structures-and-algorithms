package io.abdul.stack_queue.monotonic_stack.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertArrayEquals(new int[]{3, 4, 4, -1}, solution.nextLargerElement(new int[]{1, 3, 2, 4}));
    assertArrayEquals(new int[]{8, -1, 1, 3, -1}, solution.nextLargerElement(new int[]{6, 8, 0, 1, 3}));
    assertArrayEquals(new int[]{3, -1, -1}, solution.nextLargerElement(new int[]{1, 3, 2}));

    // Single element
    assertArrayEquals(new int[]{-1}, solution.nextLargerElement(new int[]{10}));

    // All increasing
    assertArrayEquals(new int[]{2, 3, 4, 5, -1}, solution.nextLargerElement(new int[]{1, 2, 3, 4, 5}));

    // All decreasing
    assertArrayEquals(new int[]{-1, -1, -1, -1, -1},
        solution.nextLargerElement(new int[]{5, 4, 3, 2, 1}));

    // Mixed pattern
    assertArrayEquals(new int[]{4, 6, 6, 9, -1}, solution.nextLargerElement(new int[]{2, 4, 1, 6, 9}));

    // Repeating values
    assertArrayEquals(new int[]{5, 5, 7, 7, -1}, solution.nextLargerElement(new int[]{2, 2, 5, 5, 7}));

    // Edge values
    assertArrayEquals(new int[]{1_000_000_000, -1},
        solution.nextLargerElement(new int[]{0, 1_000_000_000}));
    assertArrayEquals(new int[]{-1, -1},
        solution.nextLargerElement(new int[]{1_000_000_000, 1_000_000_000}));

    // Stress case: all same
    int[] large = new int[100000];
    Arrays.fill(large, 5);
    int[] expected = new int[100000];
    Arrays.fill(expected, -1);
    assertArrayEquals(expected, solution.nextLargerElement(large));
  }
}

/*
Brute-force

T - O(n^2)
S - O(1)

 */
class Solution {

  public int[] nextLargerElement(int[] arr) {
    int[] nge = new int[arr.length];
    Arrays.fill(nge, -1);

    for (int i = 0; i < arr.length; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] > arr[i]) {
          nge[i] = arr[j];
          break;
        }
      }
    }

    return nge;
  }
}

/*
Optimal - Monotonic Stack (Increasing order)

T - O(n) - 2n; n to iterate the array; n to remove elements from the stack
S - O(n)

100 1 2 3 4 5 6 7 8 9 10
For 100, all elements from 1 to 10 will be in stack and will be removed to empty as no one greater than 100.
Here it takes full stack space and 2n time

Intent:
4 12 5 3 1 2 5 3 1 2 4 6
stack []
nge [0,0,0,0,0,0,0,0,0,0,0,0]

stack [6]
nge [0,0,0,0,0,0,0,0,0,0,0,-1]

stack[4,6]
nge [0,0,0,0,0,0,0,0,0,0,6,-1]

stack[2,4,6]
nge [0,0,0,0,0,0,0,0,0,4,6,-1]

stack[1,2,4,6]
nge [0,0,0,0,0,0,0,0,2,4,6,-1]

For 3, the next bigger element is 4.
1 and 2 can be removed from the stack, as they can never be the bigger element for any of the previous elements.
3 > 1&2, for any previous elements lesser than 3, 3 itself will be the answer and not 1 and 2
stack[3,4,6]
nge [0,0,0,0,0,0,0,4,2,4,6,-1]

stack[5,6]
nge [0,0,0,0,0,0,6,4,2,4,6,-1]

stack[2,5,6]
nge [0,0,0,0,0,5,6,4,2,4,6,-1]

stack[1,2,5,6]
nge [0,0,0,0,2,5,6,4,2,4,6,-1]

stack[3,5,6]
nge [0,0,0,5,2,5,6,4,2,4,6,-1]

stack[5,6]
nge [0,0,6,5,2,5,6,4,2,4,6,-1]

stack[12]
nge [0,-1,6,5,2,5,6,4,2,4,6,-1]

stack[4,12]
nge [12,-1,6,5,2,5,6,4,2,4,6,-1] -> result

 */
class Solution2 {

  public int[] nextLargerElement(int[] arr) {
    Stack<Integer> stack = new Stack<>();
    int[] nge = new int[arr.length];
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