package io.abdul.stack_queue.monotonic_stack.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    final int MOD = 1_000_000_007;

//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    // Basic examples
    assertEquals(18, solution.sumSubarrayMins(new int[]{3, 1, 2, 5}));
    assertEquals(10, solution.sumSubarrayMins(new int[]{2, 3, 1}));

    // Single element
    assertEquals(7, solution.sumSubarrayMins(new int[]{7}));

    // Two elements
    assertEquals(2 + 3 + 2, solution.sumSubarrayMins(new int[]{2, 3}));
    assertEquals(3 + 2 + 2, solution.sumSubarrayMins(new int[]{3, 2}));

    // All increasing
    assertEquals(1 + 2 + 3 + 1 + 2 + 1, solution.sumSubarrayMins(new int[]{1, 2, 3}));

    // All decreasing
    assertEquals(3 + 2 + 1 + 2 + 1 + 1, solution.sumSubarrayMins(new int[]{3, 2, 1}));

    // All same values
    assertEquals((1 * 6) % MOD, solution.sumSubarrayMins(new int[]{1, 1, 1}));

    // Edge values
    assertEquals((int) ((1_000_000L + 1_000_000 + Math.min(1_000_000, 1_000_000)) % MOD),
        solution.sumSubarrayMins(new int[]{1_000_000, 1_000_000}));

    // Large array with same values
    int[] same = new int[100000];
    Arrays.fill(same, 1);
    long expected = ((long) 100000 * (100000 + 1)) / 2; // sum of 1 * count of all subarrays
    assertEquals((int) (expected % MOD), solution.sumSubarrayMins(same));

    assertEquals(444, solution.sumSubarrayMins(new int[]{11, 81, 94, 43, 3}));
  }
}

/*
Brute-force - Explore all subarrays

T - O(n^2)
S - O(1)

 */
class Solution {

  private static final int MAX = (int) 1e9 + 7;

  public int sumSubarrayMins(int[] arr) {
    int n = arr.length;

    int sum = 0;
    for (int i = 0; i < n; i++) {
      int min = arr[i];
      for (int j = i; j < n; j++) {
        if (arr[j] < min) {
          min = arr[j];
        }
        sum = (sum + min) % MAX;
      }
    }

    return sum;
  }
}

/*
Optimal - Monotonic Stack

T - O(n) - 2n for nse; 2n for psee
S - O(1)

0  1  2  3  4  5  6  7
1  4  6  7  3  7  8  1
-1 0  1  2  0  6  5 -1 = psee
-1 4  4  4  7  7  7 -1 = nse

At pos=4
(4-0) * (7-4) subarrays has 3 as min
so 12 subarrays
So add 12 * 3 to the sum

pos=4, 3 is the min for right side 3 subarrays, 3 is the min for left side 4 subarrays
so 4*2=12, for all 12 subarrays, 3 is the min [combinations multiplies!!!]

So we need next smaller element and previous smaller element for 3 to find how many subarrays we form with 3 as min
 */
class Solution2 {

  private static final int MAX = (int) 1e9 + 7;

  public int sumSubarrayMins(int[] arr) {
    int n = arr.length;

    int[] nse = nextSmallerElement(arr);
    int[] pse = previousSmallerOrEqualsElement(arr);

    int sum = 0;
    for (int i = 0; i < n; i++) {
      long subarrays = (long) (i - pse[i]) * (nse[i] - i);
      int minSum = (int) ((subarrays * arr[i]) % MAX);
      sum = (sum + minSum) % MAX;
    }

    return sum;
  }

  private int[] nextSmallerElement(int[] arr) {
    int n = arr.length;
    int[] nse = new int[n];

    Stack<Integer> stack = new Stack<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
        /* why >=? Bcz we need the longest subarray in the right in which arr[i] is smaller
        [1 1 1 1]
        s []
        nse [4 4 4 4]

        if we do just >
        s [1 1 1 1]
        nse [1 2 3 4]
        */
        stack.pop();
      }

      nse[i] = stack.isEmpty() ? n : stack.peek(); // n instead of -1 for logic in min calculation
      stack.push(i);
    }

    return nse;
  }

  private int[] previousSmallerOrEqualsElement(int[] arr) {
    int n = arr.length;
    int[] pse = new int[n];

    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        /* why >? Bcz we need the longest subarray in the left in which arr[i] is smaller
        [1 1 1 1]
        s [1 1 1 1]
        pse [-1 -1 -1 -1]

        if we do >=
        s []
        nse [1 2 3 4]
        */
        stack.pop();
      }
      pse[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }

    return pse;
  }
}

/*
Optimal (similar as above)

T - O(n) - 2n for nse; 2n for psee
S - O(1)

 */
class Solution3 {

  private static final int MAX = (int) 1e9 + 7;

  public int sumSubarrayMins(int[] arr) {
    int n = arr.length;

    int[] nse = nextSmallerElement(arr);
    int[] pse = previousSmallerOrEqualsElement(arr);

    int sum = 0;
    for (int i = 0; i < n; i++) {
      long subarrays = (long) (i - pse[i]) * (nse[i] - i);
      int minSum = (int) ((subarrays * arr[i]) % MAX);
      sum = (sum + minSum) % MAX;
    }

    return sum;
  }

  private int[] nextSmallerElement(int[] arr) {
    int n = arr.length;
    int[] nse = new int[n];

    Stack<Integer> stack = new Stack<>();

    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        /* why >=? Bcz we need the longest subarray in the right in which arr[i] is smaller
        [1 1 1 1]
        s []
        nse [4 4 4 4]

        if we do just >
        s [1 1 1 1]
        nse [1 2 3 4]
        */
        stack.pop();
      }

      nse[i] = stack.isEmpty() ? n : stack.peek(); // n instead of -1 for logic in min calculation
      stack.push(i);
    }

    return nse;
  }

  private int[] previousSmallerOrEqualsElement(int[] arr) {
    int n = arr.length;
    int[] pse = new int[n];

    Stack<Integer> stack = new Stack<>();

    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
        /* why >? Bcz we need the longest subarray in the left in which arr[i] is smaller
        [1 1 1 1]
        s [1 1 1 1]
        pse [-1 -1 -1 -1]

        if we do >=
        s []
        nse [1 2 3 4]
        */
        stack.pop();
      }
      pse[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }

    return pse;
  }
}