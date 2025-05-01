package io.abdul.basics.problem21;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.powerSet(new int[]{}));
        System.out.println(solution.powerSet(new int[]{1}));
        System.out.println(solution.powerSet(new int[]{1,2}));
        System.out.println(solution.powerSet(new int[]{1,2,3}));
    }
}

class Solution {
    public List<List<Integer>> powerSet(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        powerSet(nums, nums.length-1, 0, result);
        return result;
    }

    private void powerSet(int[] nums, int i, int bit, List<List<Integer>> result) {
        if (i < 0) {
            List<Integer> set = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                if ((bit & (1 << j)) != 0) {
                    set.add(nums[j]);
                }
            }
            result.add(set);
            return;
        }

        bit = bit | 1 << i; // with
        powerSet(nums, i-1, bit, result);
        bit = bit & ~(1 << i); // without
        powerSet(nums, i-1, bit, result);
    }
}
