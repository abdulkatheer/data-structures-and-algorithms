package io.abdul.dynamic_programming.dp_on_partitions.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixChainMultiplication {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: Example input with multiple ways to multiply
        int[] nums1 = {10, 15, 20, 25};
        assertEquals(8000, solution.matrixMultiplication(nums1),
                "The minimum number of multiplications for [10, 15, 20, 25] should be 8000");

        // Test Case 2: Example input with only one way to multiply
        int[] nums2 = {4, 2, 3};
        assertEquals(24, solution.matrixMultiplication(nums2),
                "The minimum number of multiplications for [4, 2, 3] should be 24");

        // Test Case 3: Edge case with two matrices
        int[] nums3 = {5, 10};
        assertEquals(0, solution.matrixMultiplication(nums3),
                "The minimum number of multiplications for [5, 10] should be 0");

        // Test Case 4: Large input with multiple matrices
        int[] nums4 = {10, 20, 30, 40, 50};
        assertEquals(38000, solution.matrixMultiplication(nums4),
                "The minimum number of multiplications for [10, 20, 30, 40, 50] should be 38000");

        // Test Case 5: Edge case with identical dimensions
        int[] nums5 = {10, 10, 10, 10};
        assertEquals(2000, solution.matrixMultiplication(nums5),
                "The minimum number of multiplications for [10, 10, 10, 10] should be 3000");

        // Test Case 6: Edge case with small dimensions
        int[] nums6 = {1, 2, 3, 4};
        assertEquals(18, solution.matrixMultiplication(nums6),
                "The minimum number of multiplications for [1, 2, 3, 4] should be 18");

        // Test Case 7: Edge case with large dimensions
        int[] nums7 = {100, 200, 300};
        assertEquals(6000000, solution.matrixMultiplication(nums7),
                "The minimum number of multiplications for [100, 200, 300] should be 6000000");

        int[] nums8 = {1, 2, 3, 4, 5};
        assertEquals(38, solution.matrixMultiplication(nums8));
    }
}

/*
Step 1 - Top-down recursive solution

T - O(n 2^n) - n for the loop and 2^n for the two partitions
S - O(n)

1 2 3 4 5
4 matrices = 1x2 2x3 3x4 4x5
Matrix at i = arr[i-1] x arr[i]

Base case:
when a single matrix exists, no multiplications required
f(x,x) = 0

Recursive case:
f(i,j) = Min( { (arr[i-1] * arr[k] * arr[j]) + f(i,k) + f(k+1,j) } where k=0 to j-1 )

Ex: 1 2 3 4 5
i=1 j=4
Three way partitions =
i=1,j=1 & i=2,j=4
i=1,j=2 & i=3,j=4
i=1,j=3 & i=4,j=4

In all i of first and j of second will not change
So in all ways, result of first partition will have 1_column X k_row
result of second partition will have k_row and 4_column
So multiplication of the two results will be 1_column_x_row X x_row_4_column = 1 X x X 4

So recursion is = (arr[i-1] * arr[k] * arr[j]) + f(i,k) + f(k+1,j) where k=0 to j-1
Why j-1, otherwise k+1 will go out of bounds
 */
class Solution {
    public int matrixMultiplication(int[] nums) {
        return matrixMultiplication(nums, 1, nums.length - 1);
    }

    private int matrixMultiplication(int[] nums, int i, int j) {
        // Base case
        if (i == j) { // for loop will make sure j not less than i
            return 0;
        }

        // at least i and j will have 1 difference here like 1,2 1,3 3,8
        int result = Integer.MAX_VALUE;
        for (int k = i; k <= j - 1; k++) {
            int operationsRequiredToMultiplyResult = nums[i - 1] * nums[k] * nums[j];
            result = Math.min(result, operationsRequiredToMultiplyResult + matrixMultiplication(nums, i, k) + matrixMultiplication(nums, k + 1, j));
        }

        return result;
    }
}

/*
Step 2 - Memoization

T - O(n^3)
S - O(n^2) - stack + dp

 */
class Solution2 {
    public int matrixMultiplication(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for (int[] longs : dp) {
            Arrays.fill(longs, -1);
        }
        return matrixMultiplication(nums, 1, nums.length - 1, dp);
    }

    private int matrixMultiplication(int[] nums, int i, int j, int[][] dp) {
        // Base case
        if (i == j) { // for loop will make sure j not less than i
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        // at least i and j will have 1 difference here like 1,2 1,3 3,8
        int result = Integer.MAX_VALUE;
        for (int k = i; k <= j - 1; k++) {
            int operationsRequiredToMultiplyResult = nums[i - 1] * nums[k] * nums[j];
            result = Math.min(result, operationsRequiredToMultiplyResult + matrixMultiplication(nums, i, k, dp) + matrixMultiplication(nums, k + 1, j, dp));
        }

        dp[i][j] = result;
        return result;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions:
i==j = 0

Recursive solutions:
1 2 3 4 5
We start with i,j = 4,4 (smallest problem)
3,4
2,3 2,4
1,2 1,3 1,4

Why this way?
To calculate i,j we need all possibilities between i and j like i,k & k+1,j ... i,j-1 & j,j
So it's needs the entire DP table from bottom to built the result
j,j is at the last row

Ex: i=1,j=4 => 1,1 & 2,4, 1,2 & 3,4 1,3 & 4,4 [3 possibilities]
4,4 is at 4th row
3,4 is at 2rd row
2,4 is at 1st row
Rest is at 0th row
So we need to fill in backwards, same as recursive solution
 */

class Solution3 {
    public int matrixMultiplication(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        // dp[i][j] gives the min multiplications required to multiply matrix from i to j
        // dp[1][n-1] gives the min multiplications required to multiple matrix from 1 to n-1 (the whole)

        // Known solutions
        // i==j ? 0

        // NOTE: Watchout, this is different from traditional thinking so far
        for (int i = n - 1; i > 0; i--) {
            for (int j = i + 1; j < n; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int partition1 = dp[i][k];
                    int partition2 = dp[k + 1][j];
                    int partitionMultiply = nums[i - 1] * nums[k] * nums[j];
                    min = Math.min(min, partitionMultiply + partition1 + partition2);
                }
                dp[i][j] = min;
            }
        }

        return dp[1][n - 1];
    }
}

/*
Step 4 - Space Optimization
Can't be done as the result needs the entire DP table
 */