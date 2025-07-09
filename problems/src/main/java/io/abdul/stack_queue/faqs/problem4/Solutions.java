package io.abdul.stack_queue.faqs.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

// https://takeuforward.org/plus/dsa/stack-and-queues/faqs/largest-rectangle-in-a-histogram
public class Solutions {

  public static void main(String[] args) {
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    assertEquals(10, solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    assertEquals(15, solution.largestRectangleArea(new int[]{3, 5, 1, 7, 5, 9}));
    assertEquals(4, solution.largestRectangleArea(new int[]{2, 4}));
    assertEquals(6, solution.largestRectangleArea(new int[]{2, 2, 2}));
    assertEquals(6, solution.largestRectangleArea(new int[]{1, 2, 3, 4}));
    assertEquals(6, solution.largestRectangleArea(new int[]{4, 3, 2, 1}));
    assertEquals(10, solution.largestRectangleArea(new int[]{0, 10, 0}));
  }
}

/*
Brute-force - Explore all possible subarrays

T - O(n^2)
S - O(1)

 */
class Solution {

  public int largestRectangleArea(int[] heights) {
    return -1;
  }
}

/*
Better - Monotonic stack

T - O(n) - 2 * 2n
S - O(n) - 2n

 */
class Solution2 {

  public int largestRectangleArea(int[] heights) {
    int[] nse = nextSmallerElements(heights);
    int[] pse = previousSmallerElements(heights);

    int maxArea = 0;
    for (int i = 0; i < heights.length; i++) {
      // Max length of subarray in which heights[i] is the min
      int subarrays = (nse[i] - pse[i]) - 1;
      int area = subarrays * heights[i];
      maxArea = Math.max(maxArea, area);
    }

    return maxArea;
  }

  private int[] nextSmallerElements(int[] nums) {
    Stack<Integer> stack = new Stack<>();

    int[] nse = new int[nums.length];
    for (int i = nums.length - 1; i >= 0; i--) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      nse[i] = stack.isEmpty() ? nums.length : stack.peek();
      stack.push(i);
    }
    return nse;
  }

  private int[] previousSmallerElements(int[] nums) {
    Stack<Integer> stack = new Stack<>();

    int[] psee = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      psee[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }
    return psee;
  }
}

/*
Optimal
Can I avoid the precomputation?

PSE we compute on the go.
NSE?
if we maintain a monotonically increasing stack, and when we add a smaller element, we've to pop a few elements from the stack.
That means the smaller element is the nse for the elements being removed..

Ex: 3 2 10 11 5 10 6 3
    0 1  2  3 4  5 6 7

s []
s [0]
2 is smaller than 3, so 2 is nse of 3
No pse available for 3
Now we know who is pse and nse of 3.
3 (height) * (1 - (-1) - 1) [width] = 3
s [1]
s [1 2]
s [1 2 3]
5 is the nse of 11
10 is the pse of 11
11 [height] * (4 - 2 -1) [width] = 11

5 is the nse of 10
2 is the pse of 10
10 [height] * (4 - 1 - 1) [width] = 20

s [1 4]
s [1 4 5]

6 is the nse of 10
5 is the pse of 10
10 [height] * (6 - 4 - 1) [width] = 10

s [1 4 6]

3 is the nse of 6
5 is the pse of 6

6 [height] * (7 - 4 - 1) [width] = 12

3 is the nse of 5
2 is the pse of 5

5 [height] * (7 - 1 - 1) [width] = 25

s [2 3]
--
n is the nse of 3
2 is the nse of 3

3 [height] * (8-1-1) = 18

n is the nse of 2
-1 is the pse of 2

2 [height] * (8 - (-1) - 1) [width] = 16

So answer is 25
 */
class Solution3 {

  public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();

    int maxArea = 0;
    for (int i = 0; i < heights.length; i++) {
      while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
        int nse = i;
        int height = heights[stack.pop()];
        int pse = stack.isEmpty() ? -1 : stack.peek();
        maxArea = Math.max(maxArea, height * (nse - pse - 1));
      }
      stack.push(i);
    }

    // stack is not empty, then no nse exists for those elements
    while (!stack.isEmpty()) {
      int nse = heights.length;
      int height = heights[stack.pop()];
      int pse = stack.isEmpty() ? -1 : stack.peek();
      maxArea = Math.max(maxArea, height * (nse - pse - 1));
    }

    return maxArea;
  }
}