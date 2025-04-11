package io.abdul.array.dynamic_programming.problem4;

// https://leetcode.com/problems/climbing-stairs/description/
public class ClimbingStairs {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.climbStairs(3));
    }
}

// #tag_recursion
// Permutation, order matters
// 1, 2 and 2,1 are not same!
// Step 1: Recursion (Top down)
/*
Known solution - if n is 1, 1 way to climb
if n is 2, 2 ways to climb
If is > 2,
Ex: 3
To reach 3, last step can be 1 or 2.
If 1, go back 1 step and see in how many steps you can climb to n-1
If 2, go back 2 steps and see in how many steps you can climb to n-2

climb(3) -> climb(2) + climb(1)
climb(2) -> 2
climb(1) -> 1
so climb(3) -> 3

Ex: 6
climb(6) -> climb(5) + climb(4) -> 11
climb(5) -> climb(4) + climb(3) -> 7
climb(4) -> climb(3) + climb(2) -> 4
climb(3) -> climb(2) + climb(1) -> 3
climb(4) -> 3 + 1 -> 4
climb(5) -> 4+3 -> 7
climb(6) -> 7+4 -> 11
 */
class Solution {
    public int climbStairs(int n) {
        return climb(n);
    }

    private static int climb(int nLeft) {
        if (nLeft == 1) {
            return 1;
        }
        if (nLeft == 2) {
            return 2;
        }

        return climb(nLeft - 1) + climb(nLeft - 2);
    }
}

// Step 2 -> Iterative (Bottom-up) + Tabulation
/*
Number of paths taken at n is, you either would have come from n-1 or n-2.
So how many paths you've taken to reach n-1 and n-2
Similar to Unique Paths problem. Where known solution is for 2x1 and 1x2, path is 1. So we start building from 2x2 from 2x1 and 1x2.
 */
class Solution2 {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int[] paths = new int[n + 1]; // As n starts from 1
        paths[1] = 1;
        paths[2] = 2;
        for (int i = 3; i <= n; i++) {
            paths[i] = paths[i - 1] + paths[i - 2];
        }
        return paths[n];
    }
}

// Step 3 -> Iterative (Bottom-up) + Tabulation (Space Optimized)
// We only need prev and prevToPrev paths and not all
class Solution3 {
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int prevToPrevPath = 1;
        int prevPath = 2;
        for (int i = 3; i <= n; i++) {
            int currentPath = prevPath + prevToPrevPath;
            prevToPrevPath = prevPath;
            prevPath = currentPath;
        }
        return prevPath;
    }
}