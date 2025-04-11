package io.abdul.array.dynamic_programming.problem2;

import java.util.Arrays;

// https://leetcode.com/problems/house-robber/description/
public class HouseRobber {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        System.out.println(solution.rob(new int[]{5}));
        System.out.println(solution.rob(new int[]{5, 10}));
        System.out.println(solution.rob(new int[]{1, 10, 2}));
        System.out.println(solution.rob(new int[]{2, 1, 1, 9, 1, 1, 2}));
    }
}

// #tag_recursion
// Step 1 -> Recursion (Top-down)
class Solution {
    public int rob(int[] nums) {
        return rob(nums, nums.length - 1); // Start from last house
    }

    private static int rob(int[] nums, int i) {
        if (i < 0) { // base case
            return 0;
        }
        return Math.max(rob(nums, i - 2) + nums[i], rob(nums, i - 1));
    }
}

// Step 1a -> Recursive + Memo
class Solution2 {
    public int rob(int[] nums) {
        int[] state = new int[nums.length];
        Arrays.fill(state, -1);
        return rob(nums, nums.length - 1, state); // Start from last house
    }

    private static int rob(int[] nums, int i, int[] state) {
        if (i < 0) { // base case
            return 0;
        }
        if (state[i] != -1) {
            return state[i];
        }
        int robFromPrevToPrevHouse = rob(nums, i - 2, state);
        int robFromPrevHouse = rob(nums, i - 1, state);
        int max = Math.max(robFromPrevToPrevHouse + nums[i], robFromPrevHouse);
        state[i] = max;
        return max;
    }
}

// Recursive to Iterative (Top-down) can be done using Stack
// Recursive to Iterative with Memoization (Top-down) can be done using Stack and Map
// Bottom-up approach removes the need for stack and can replace Map (Memoization) with Array (Tabulation)

// Step 2 -> Recursive to Iterative (Bottom-up) (Tabulation)
// 2 1 1 9 1 1 2
/*
Idea here is the problem has optimal sub-structure
So if I choose an optimal solution for smaller sub-structure, I can build the optimal solution for whole problem
Keep setting the best robbery value for an index from bottom up
For index 0, we need to keep prev robbery. So state can have 1 additional space to keep zero value
 */
class Solution3 {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int[] bestRobs = new int[nums.length];
        bestRobs[0] = nums[0]; // Starting from known solution of the smallest sub-problem

        for (int i = 1; i < nums.length; i++) {
            int currentValue = nums[i];
            int bestRobTillPrevToPrevHouse = i - 2 < 0 ? 0 : bestRobs[i - 2]; // May underflow as i starts from 1
            int bestRobTillPrevHouse = bestRobs[i - 1]; // Will never underflow as i starts from 1
            int bestRobValueAtI = Math.max(currentValue + bestRobTillPrevToPrevHouse, bestRobTillPrevHouse);
            bestRobs[i] = bestRobValueAtI;
        }

        return bestRobs[nums.length - 1];
    }
}

// Step 3 -> Iterative + Tabulation (Space optimized)
// We only need bestRobTillPrevHouse and bestRobTillPrevToPrevHouse
class Solution4 {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int bestRobTillPrevToPrevHouse = 0;
        int bestRobTillPrevHouse = nums[0]; // Starting from known solution of the smallest sub-problem

        for (int i = 1; i < nums.length; i++) {
            int currentValue = nums[i];
            int bestRobValueAtI = Math.max(currentValue + bestRobTillPrevToPrevHouse, bestRobTillPrevHouse);
            bestRobTillPrevToPrevHouse = bestRobTillPrevHouse;
            bestRobTillPrevHouse = bestRobValueAtI;
        }

        return bestRobTillPrevHouse;
    }
}
