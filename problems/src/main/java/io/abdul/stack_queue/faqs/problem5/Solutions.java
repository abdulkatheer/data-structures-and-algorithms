package io.abdul.stack_queue.faqs.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    assertEquals(6, solution.maximalAreaOfSubMatrixOfAll1(new int[][]{
        {1, 0, 1, 0, 0},
        {1, 0, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 0, 0, 1, 0}
    }));

    assertEquals(1, solution.maximalAreaOfSubMatrixOfAll1(new int[][]{
        {1}
    }));

    assertEquals(0, solution.maximalAreaOfSubMatrixOfAll1(new int[][]{
        {0}
    }));

    assertEquals(3, solution.maximalAreaOfSubMatrixOfAll1(new int[][]{
        {1, 0, 1, 0, 0},
        {1, 0, 1, 1, 1}
    }));

    assertEquals(4, solution.maximalAreaOfSubMatrixOfAll1(new int[][]{
        {1, 1},
        {1, 1}
    }));

  }
}

/*
Brute-force - Explore all possible submatrices

0,0 to n-1,n-1
m*n matrices exist
check all m*n matrices, each one will take m*n time.
(m*n)^2 time complexity!
 */
class Solution {

  public int maximalAreaOfSubMatrixOfAll1(int[][] matrix) {
    return -1;
  }
}

/*
Better - Monotonic Stack
This problem is a different variation of Largest rectangle in a histogram.

T - O(n*m) - n*m to build histograms; n*5m to find largest rectangle area of each histogram;
S - O(2 n*m) - pse,nse for each row

A histogram is a 2d graph with bars in it. Each bar will have a height.
In the other problem, the heights were given upfront and we calculated area using nse,pse.

Now we've given 0's and 1's. There are n possible histograms (for each row).
We need to find the heights of each histogram. Then we can find area of each histogram and take the max.
 */
class Solution2 {

  public int maximalAreaOfSubMatrixOfAll1(int[][] matrix) {
    int[][] histograms = buildHistograms(matrix);

    int maxArea = 0;
    for (int[] histogram : histograms) {
      maxArea = Math.max(maxArea, largestRectangleAreaOfHistogram(histogram));
    }

    return maxArea;
  }

  private int[][] buildHistograms(int[][] matrix) {
    int[][] histogram = new int[matrix.length][matrix[0].length];
    System.arraycopy(matrix[0], 0, histogram[0], 0, matrix[0].length);

    for (int i = 1; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int currentValue = matrix[i][j];
        histogram[i][j] = currentValue == 0 ? 0 : histogram[i - 1][j] + 1;
      }
    }

    return histogram;
  }

  private int largestRectangleAreaOfHistogram(int[] histogram) {
    int[] nse = nextSmallerElements(histogram);
    int[] pse = previousSmallerElements(histogram);

    int maxArea = 0;
    for (int i = 0; i < histogram.length; i++) {
      maxArea = Math.max(maxArea, histogram[i] * (nse[i] - pse[i] - 1));
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
    int[] pse = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
        stack.pop();
      }
      pse[i] = stack.isEmpty() ? -1 : stack.peek();
      stack.push(i);
    }

    return pse;
  }
}

/*
Optimal - No precomputation of nse and pse

T - O(n*m) - n*m to build histograms; n*m to find largest rectangle area of each histogram
S - O(1)

 */
class Solution3 {

  public int maximalAreaOfSubMatrixOfAll1(int[][] matrix) {
    int[][] histograms = buildHistograms(matrix);

    int maxArea = 0;
    for (int[] histogram : histograms) {
      maxArea = Math.max(maxArea, largestRectangleAreaOfHistogram(histogram));
    }

    return maxArea;
  }

  private int[][] buildHistograms(int[][] matrix) {
    int[][] histogram = new int[matrix.length][matrix[0].length];
    System.arraycopy(matrix[0], 0, histogram[0], 0, matrix[0].length);

    for (int i = 1; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int currentValue = matrix[i][j];
        histogram[i][j] = currentValue == 0 ? 0 : histogram[i - 1][j] + 1;
      }
    }

    return histogram;
  }

  private int largestRectangleAreaOfHistogram(int[] histogram) {
    Stack<Integer> stack = new Stack<>();

    int maxArea = 0;
    for (int i = 0; i < histogram.length; i++) {
      while (!stack.isEmpty() && histogram[stack.peek()] > histogram[i]) {
        int nse = i;
        int height = histogram[stack.pop()];
        int pse = stack.isEmpty() ? -1 : stack.peek();
        maxArea = Math.max(maxArea, height * (nse - pse - 1));
      }
      stack.push(i);
    }

    while (!stack.isEmpty()) {
      int nse = histogram.length;
      int height = histogram[stack.pop()];
      int pse = stack.isEmpty() ? -1 : stack.peek();
      maxArea = Math.max(maxArea, height * (nse - pse - 1));
    }

    return maxArea;
  }
}

