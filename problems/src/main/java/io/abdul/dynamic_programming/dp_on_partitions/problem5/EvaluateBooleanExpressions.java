package io.abdul.dynamic_programming.dp_on_partitions.problem5;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
T|T^F&F^T|T
1st iteration - 5 ways
2nd iteration - 4 ways
3rd iteration - 2 ways
4th iteration - 1 ways
5th iteration base case

NOTE: Learn Modulo arithmetic
 */
public class EvaluateBooleanExpressions {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Example 1: "T|T&F" -> 1 way
        assertEquals(1, solution.evaluateExpWays("T|T&F"));

        // Example 2: "F|T^F" -> 2 ways
        assertEquals(2, solution.evaluateExpWays("F|T^F"));

        // Single operand true
        assertEquals(1, solution.evaluateExpWays("T"));

        // Single operand false
        assertEquals(0, solution.evaluateExpWays("F"));

        // All true with OR: "T|T|T" -> 5 ways
        assertEquals(2, solution.evaluateExpWays("T|T|T"));

        // All false with AND: "F&F&F" -> 0 ways
        assertEquals(0, solution.evaluateExpWays("F&F&F"));

        // Mixed: "T^F|F" -> 2 ways
        assertEquals(2, solution.evaluateExpWays("T^F|F"));

        // Mixed: "T^T^F" -> 0 ways
        assertEquals(0, solution.evaluateExpWays("T^T^F"));

        // Mixed: "T|F&T" -> 2 ways
        assertEquals(2, solution.evaluateExpWays("T|F&T"));

    }
}

/*
Solution 1 - Top-down recursive solution

T - O(n 4^n)
S - O(n)

Base case:
If invalid positions, return 0
If a single operand exists, if true expected and true exists 1 or 0 and same goes to false

How many partitions?
If 1 char - 0
3 chars - 1
5 chars - 2
so, n/2

Intuition:
1) To make an expression true, we don't just need trues.
2) | operator will work for true|true, true|false and false|true
An expression may give a number of trues and number of falses if group them differently
exp1 | exp2
exp1 trues - x1
exp1 falses - x2
exp2 trues - y1
exp2 falses - y2
Now total possibilities to make entire expression true is - [x1*y1] + [x1*y2] + [x2*y1]
3) & operator will be true for only one case. true|true
exp1 | exp2
exp1 trues - x1
exp2 trues - y1
Now total possibilities to make entire expression true is - [x1*y1]
4) ^ gives true for false|true and true|false
exp1 | exp2
exp1 trues - x1
exp1 falses - x2
exp2 trues - y1
exp2 falses - y2
Total possibilities - [x1*y2] + [x2*y1]

For the entire problem we're looking for trues, but the subproblem may expect trues and falses depending on the expression
 */
class Solution {
    private static final int MAX = (int) 1e9 + 7;

    public int evaluateExpWays(String exp) {
        return evaluateExpWays(exp, 0, exp.length() - 1, true);
    }

    private int evaluateExpWays(String exp, int i, int j, boolean trueExpected) {
        if (i > j) {
            return 0;
        }

        if (i == j) {
            if (trueExpected) {
                return exp.charAt(i) == 'T' ? 1 : 0;
            } else {
                return exp.charAt(i) == 'F' ? 1 : 0;
            }
        }

        // we only look for operands to partitions
        // operands are at odd positions between i and j excluding 1 and last position
        /*
        Why long?
        We're looking to bound result to 10^7+7
        if we add two 10^7, it'll be within int
        But when we multiply, it can't hold. that's why we store temporarily in long
         */
        long totalExpected = 0;
        for (int k = i + 1; k <= j - 1; k += 2) {
            char operator = exp.charAt(k);
            int leftTrues = evaluateExpWays(exp, i, k - 1, true);
            int rightTrues = evaluateExpWays(exp, k + 1, j, true);
            int leftFalses = evaluateExpWays(exp, i, k - 1, false);
            int rightFalses = evaluateExpWays(exp, k + 1, j, false);
            long trueAndTrue = ((long) leftTrues * rightTrues) % MAX;
            long trueAndFalse = ((long) leftTrues * rightFalses) % MAX;
            long falseAndTrue = ((long) leftFalses * rightTrues) % MAX;
            long falseAndFalse = ((long) leftFalses * rightFalses) % MAX;

            if ('|' == operator) {
                if (trueExpected) {
                    totalExpected = (totalExpected + trueAndTrue + trueAndFalse + falseAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + falseAndFalse) % MAX;
                }
            } else if ('&' == operator) {
                if (trueExpected) {
                    totalExpected = (totalExpected + trueAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + falseAndFalse + trueAndFalse + falseAndTrue) % MAX;
                }
            } else {
                if (trueExpected) {
                    totalExpected = (totalExpected + trueAndFalse + falseAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + trueAndTrue + falseAndFalse) % MAX;
                }
            }
        }

        return (int) totalExpected;
    }
}

/*
Step 2 - Memoization

T - O(n^2)
S - O(n^2) - stack + dp

 */
class Solution2 {
    private static final int MAX = (int) 1e9 + 7;

    public int evaluateExpWays(String exp) {
        int n = exp.length();
        int[][][] dp = new int[n][n][2];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }
        return evaluateExpWays(exp, 0, n - 1, 1, dp);
    }

    private int evaluateExpWays(String exp, int i, int j, int trueExpected, int[][][] dp) {
        // Base case 1
        if (i > j) {
            return 0;
        }

        // Base case 2
        if (i == j) {
            if (trueExpected == 1) {
                return exp.charAt(i) == 'T' ? 1 : 0;
            } else {
                return exp.charAt(i) == 'F' ? 1 : 0;
            }
        }

        if (dp[i][j][trueExpected] != -1) {
            return dp[i][j][trueExpected];
        }

        // we only look for operands to partitions
        // operands are at odd positions between i and j excluding 1 and last position
        /*
        Why long?
        We're looking to bound result to 10^7+7
        if we add two 10^7, it'll be within int
        But when we multiply, it can't hold. that's why we store temporarily in long
         */
        long totalExpected = 0;
        for (int k = i + 1; k <= j - 1; k += 2) {
            char operator = exp.charAt(k);
            int leftTrues = evaluateExpWays(exp, i, k - 1, 1, dp);
            int rightTrues = evaluateExpWays(exp, k + 1, j, 1, dp);
            int leftFalses = evaluateExpWays(exp, i, k - 1, 0, dp);
            int rightFalses = evaluateExpWays(exp, k + 1, j, 0, dp);
            long trueAndTrue = ((long) leftTrues * rightTrues) % MAX;
            long trueAndFalse = ((long) leftTrues * rightFalses) % MAX;
            long falseAndTrue = ((long) leftFalses * rightTrues) % MAX;
            long falseAndFalse = ((long) leftFalses * rightFalses) % MAX;

            if ('|' == operator) {
                if (trueExpected == 1) {
                    totalExpected = (totalExpected + trueAndTrue + trueAndFalse + falseAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + falseAndFalse) % MAX;
                }
            } else if ('&' == operator) {
                if (trueExpected == 1) {
                    totalExpected = (totalExpected + trueAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + falseAndFalse + trueAndFalse + falseAndTrue) % MAX;
                }
            } else {
                if (trueExpected == 1) {
                    totalExpected = (totalExpected + trueAndFalse + falseAndTrue) % MAX;
                } else {
                    totalExpected = (totalExpected + trueAndTrue + falseAndFalse) % MAX;
                }
            }
        }

        dp[i][j][trueExpected] = (int) totalExpected;
        return (int) totalExpected;
    }
}

/*
Step 3 - Bottom-up iterative solution

Known solutions
i > j -> 0
i == j and 1,true = 1
i == j and 0,false = 1
 */
class Solution3 {
    private static final int MAX = (int) 1e9 + 7;

    public int evaluateExpWays(String exp) {
        int n = exp.length();
        int[][][] dp = new int[n][n][2];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j <= n - 1; j++) {
                // Known solutions
                // i > j is taken care by loop parameters
                if (i == j) {
                    dp[i][j][1] = exp.charAt(i) == 'T' ? 1 : 0;
                    dp[i][j][0] = exp.charAt(i) == 'F' ? 1 : 0;
                    continue;
                }

                long totalTrues = 0;
                long totalFalses = 0;
                for (int k = i + 1; k <= j - 1; k += 2) {
                    char operator = exp.charAt(k);

                    int leftTrues = dp[i][k - 1][1];
                    int rightTrues = dp[k + 1][j][1];
                    int leftFalses = dp[i][k - 1][0];
                    int rightFalses = dp[k + 1][j][0];
                    long trueAndTrue = ((long) leftTrues * rightTrues) % MAX;
                    long trueAndFalse = ((long) leftTrues * rightFalses) % MAX;
                    long falseAndTrue = ((long) leftFalses * rightTrues) % MAX;
                    long falseAndFalse = ((long) leftFalses * rightFalses) % MAX;

                    if ('|' == operator) {
                        totalTrues = (totalTrues + trueAndTrue + trueAndFalse + falseAndTrue) % MAX;
                        totalFalses = (totalFalses + falseAndFalse) % MAX;
                    } else if ('&' == operator) {
                        totalTrues = (totalTrues + trueAndTrue) % MAX;
                        totalFalses = (totalFalses + falseAndFalse + trueAndFalse + falseAndTrue) % MAX;
                    } else {
                        totalTrues = (totalTrues + trueAndFalse + falseAndTrue) % MAX;
                        totalFalses = (totalFalses + trueAndTrue + falseAndFalse) % MAX;
                    }
                }

                dp[i][j][1] = (int) totalTrues;
                dp[i][j][0] = (int) totalFalses;
            }
        }

        return dp[0][n - 1][1];
    }
}