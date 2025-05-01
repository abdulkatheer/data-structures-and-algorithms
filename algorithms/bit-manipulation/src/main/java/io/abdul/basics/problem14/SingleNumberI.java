package io.abdul.basics.problem14;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/bit-manipulation/problems/single-number---i
public class SingleNumberI {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.singleNumber(new int[]{1, 2, 2, 4, 3, 1, 4}));
        System.out.println(solution.singleNumber(new int[]{5}));
        System.out.println(solution.singleNumber(new int[]{1, 3, 10, 3, 5, 1, 5}));
    }
}

/*
Brute - Sort and find
T - O(n logn)
S - O(1)
 */
class Solution {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);

        boolean found = false;
        int num = 0;
        for (int i = 1; i < nums.length; i += 2) {
            if (nums[i] != nums[i - 1]) {
                found = true;
                num = nums[i - 1];
                break;
            }
        }

        if (!found) { // the last number is left unmatched
            num = nums[nums.length - 1];
        }

        return num;
    }
}

/*
Optimal - xor
T - O(n)
S - O(1)

XOR properties used - Self-inverse, identity, associative, commutative
 */
class Solution2 {
    public int singleNumber(int[] nums) {
        int num = 0;
        for (int i : nums) {
            num ^= i;
        }

        return num;
    }
}