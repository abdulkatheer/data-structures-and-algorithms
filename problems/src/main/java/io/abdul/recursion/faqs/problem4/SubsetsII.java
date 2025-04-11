package io.abdul.recursion.faqs.problem4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.subsetsWithDup(new int[]{1, 2, 3}));
        System.out.println(solution.subsetsWithDup(new int[]{1, 2, 2}));
        System.out.println(solution.subsetsWithDup(new int[]{1, 2}));
        System.out.println(solution.subsetsWithDup(new int[]{1, 3, 3}));
    }
}

class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> subsetsWithDup = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(nums, 0, new ArrayList<>(), subsetsWithDup);
        return subsetsWithDup;
    }

    private void subsetsWithDup(int[] nums, int i, List<Integer> buffer, List<List<Integer>> result) {
        result.add(new ArrayList<>(buffer));

        for (int j = i; j < nums.length; j++) {
            if (j > i && nums[j] == nums[j - 1]) { // Skip duplicate in a lookup
                continue;
            }
            buffer.add(nums[j]);
            subsetsWithDup(nums, j + 1, buffer, result);
            buffer.remove(buffer.size() - 1);
        }
    }
}
