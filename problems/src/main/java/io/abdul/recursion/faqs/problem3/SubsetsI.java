package io.abdul.recursion.faqs.problem3;

import java.util.ArrayList;
import java.util.List;

public class SubsetsI {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.subsetSums(new int[]{2, 3}));
        System.out.println(solution.subsetSums(new int[]{5, 2, 1}));
        System.out.println(solution.subsetSums(new int[]{1}));
    }
}

class Solution {
    public List<Integer> subsetSums(int[] nums) {
        int resultSize = 1 << nums.length;
        ArrayList<Integer> subsetSums = new ArrayList<>(resultSize);
        subsetSums(nums, 0, 0, subsetSums);
        return subsetSums;
    }

    private void subsetSums(int[] nums, int i, int sum, List<Integer> result) {
        if (i >= nums.length) {
            result.add(sum);
            return;
        }

        sum += nums[i];
        subsetSums(nums, i + 1, sum, result);

        sum -= nums[i];
        subsetSums(nums, i + 1, sum, result);
    }
}
