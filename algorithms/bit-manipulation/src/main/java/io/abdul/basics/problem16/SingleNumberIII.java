package io.abdul.basics.problem16;

import java.util.Arrays;
import java.util.HashSet;

// https://takeuforward.org/plus/dsa/bit-manipulation/problems/single-number---iii
public class SingleNumberIII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(Arrays.toString(solution.singleNumber(new int[]{1, 2, 1, 3, 5, 2})));
        System.out.println(Arrays.toString(solution.singleNumber(new int[]{-1, 0})));
        System.out.println(Arrays.toString(solution.singleNumber(new int[]{1, 9, 1, 2, 8, 2})));
    }
}

/*
Brute - HashSet
T - O(n)
S - O(n)
 */
class Solution {
    public int[] singleNumber(int[] nums) {
        HashSet<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            if (numSet.contains(num)) {
                numSet.remove(num);
            } else {
                numSet.add(num);
            }
        }
        return numSet.stream().mapToInt(i -> i).toArray();
    }
}

/*
Better - Sort
T - O(n logn)
S - O(1)

 */
class Solution2 {
    public int[] singleNumber(int[] nums) {
        int[] result = new int[2];
        Arrays.sort(nums);

        int c = 0;
        int i = 0;
        while (i < nums.length) {
            if (i + 1 < nums.length) {
                if (nums[i] != nums[i + 1]) {
                    result[c] = nums[i];
                    c++;
                    i++;
                } else {
                    i += 2;
                }
            } else {
                result[c] = nums[i];
                c++;
                i++;
            }
        }
        return result;
    }
}

/*
Optimal - xor and bucketing
T - O(n)
S - O(1)

Assume num1 is a and num2 is b
 */
class Solution3 {
    public int[] singleNumber(int[] nums) {
        int a_xor_b = 0;
        for (int num : nums) {
            a_xor_b ^= num;
        }

        // Split a and b from a_xor_b
        // Find one differing bit between a and b, we can pick the rightmost set bit
        /*
        1 1 0 0
        1 0 1 1 (-1)
        1 0 0 0 & -> Unset rightmost bit
        1 1 0 0 ^
        0 1 0 0
         */
        int rightMostSetBit = (a_xor_b & (a_xor_b - 1)) ^ a_xor_b;

        int group1 = 0;
        int group2 = 0;
        for (int num : nums) {
            if ((num & rightMostSetBit) != 0) {
                group1 ^= num;
            } else {
                group2 ^= num;
            }
        }

        return new int[]{group1, group2};
    }
}