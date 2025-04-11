package io.abdul.recursion.implementation_problems.problem3;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/implementation-problems/power-set
public class PowerSet {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.powerSet(new int[]{1}));
        System.out.println(solution.powerSet(new int[]{1, 2}));
        System.out.println(solution.powerSet(new int[]{1, 2, 3}));

        System.out.println(solution.powerSetItr(new int[]{1}));
        System.out.println(solution.powerSetItr(new int[]{1, 2}));
        System.out.println(solution.powerSetItr(new int[]{1, 2, 3}));
    }
}

class Solution {
    /*
    T - O(n * 2^n)
    S - O(n)
     */
    public List<List<Integer>> powerSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] buffer = new boolean[nums.length];
        powerSet(nums.length - 1, nums, result, buffer);
        return result;
    }

    private void powerSet(int i, int[] nums, List<List<Integer>> result, boolean[] buffer) {
        if (i < 0) {
            List<Integer> set = new ArrayList<>();
            for (int j = 0; j < buffer.length; j++) {
                if (buffer[j]) {
                    set.add(nums[j]);
                }
            }
            result.add(set);
            return;
        }

        buffer[i] = true;
        powerSet(i - 1, nums, result, buffer);

        buffer[i] = false;
        powerSet(i - 1, nums, result, buffer);
    }

    /*
    T - O(n * 2^n)
    S - O(1) (Excluding result)

    Bitwise operators
    Case 1: n = 1, 2^1 = 2
    0  [0], 0th bit is set? 0 & 1 << 0 == 0, No
    1  [1], 0th bit is set? 1 & 1 << 0 != 0, Yes
    { {}, {1} )

    Case 2: n=2, 2^2=4 (0 to 3)
    0 00, 0th bit is set? 0 & 1 << 0 == 0, No; 1st bit is set? 0 & 1 << 1 == 0, No
    1 01, 0th bit is set? 1 & 1 << 0 != 0, Yes; 1st bit is set? 1 & 1 << 1 == 0, No
    2 10, 0th bit is set? 2 & 1 << 0 == 0, No; 1st bit is set? 2 & 1 << 1 != 0, Yes
    3 11, 0th bit is set? 3 & 1 << 0 != 0, Yes; 1st bit is set? 3 & 1 << 1 != 0, Yes

    To simplify,
    Total powerSets are 2^n, from 0 to 2^n -1
    Each number in binary represents a set in powerset
    210 - Index
    000 - Empty set
    001 - only 0th element
    010 - only 1st element
    011 - only 0th and 1st element
    100 - only 2nd element
    101 - only 0th and 2nd element
    110 - only 1st and 2nd element
    111 - 0th, 1st & 2nd element

    How to know if i-th bit is set or not (from right to left)
    010
    0th bit is set or not. 010 & 1 (001) << 0 = 010 & 001 = 0, so not set
    1st bit is set of not. 010 & 001 << 1 = 010 & 010 != 0, so set
    2nd bit is set of not. 010 & 001 << 2 = 010 & 100 == 0, so not set
     */
    public List<List<Integer>> powerSetItr(int[] nums) {
        int subsets = 1 << nums.length; // 2^n in bitwise
        List<List<Integer>> result = new ArrayList<>(subsets);

        // 0 to subsets-1
        for (int i = 0; i < subsets; i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                if ((i & 1 << j) != 0) { // j-th bit is set in i
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }

        return result;
    }
}
