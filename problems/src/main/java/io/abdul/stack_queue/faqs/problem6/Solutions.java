package io.abdul.stack_queue.faqs.problem6;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Stack;

// https://takeuforward.org/plus/dsa/stack-and-queues/faqs/stock-span-problem
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    Solution2 solution = new Solution2();

    assertArrayEquals(
        new int[]{1, 1, 1, 2, 3, 5, 6},
        solution.stockSpan(new int[]{120, 100, 60, 80, 90, 110, 115}, 7)
    );

    assertArrayEquals(
        new int[]{1, 1, 1, 3, 5, 6},
        solution.stockSpan(new int[]{15, 13, 12, 14, 16, 20}, 6)
    );

    assertArrayEquals(
        new int[]{1, 1, 2, 3, 1, 5, 7, 8},
        solution.stockSpan(new int[]{30, 20, 25, 28, 27, 29, 35, 40}, 8)
    );

    assertArrayEquals(
        new int[]{1},
        solution.stockSpan(new int[]{100}, 1)
    );

    assertArrayEquals(
        new int[]{1, 2, 3, 4, 5},
        solution.stockSpan(new int[]{10, 20, 30, 40, 50}, 5)
    );

    assertArrayEquals(
        new int[]{1, 1, 1, 1, 1},
        solution.stockSpan(new int[]{50, 40, 30, 20, 10}, 5)
    );
  }
}

/*
Brute-force

T - O(n^2)
S - O(1)
For every element in array, look at the past from i-1 to 0 and count all smaller
 */
class Solution {

  public int[] stockSpan(int[] arr, int n) {
    int[] result = new int[n];

    for (int i = 0; i < n; i++) {
      int count = 1;
      for (int j = i-1; j >= 0; j--) {
        if (arr[j] > arr[i]) {
          break;
        }
        count++;
      }
      result[i] = count;
    }
    return result;
  }
}

/*
Optimal - Monotonically decreasing stack

T - O(n)
S - O(n)

 */
class Solution2 {

  public int[] stockSpan(int[] arr, int n) {
    Stack<Integer> stack = new Stack<>();

    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
        stack.pop();
      }

      int pge = stack.isEmpty() ? -1 : stack.peek();
      result[i] = i - pge;
      stack.push(i);
    }

    return result;
  }
}



