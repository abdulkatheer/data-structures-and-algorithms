package io.abdul.recursion.implementation_problems.problem5;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.permute(new int[]{1, 2, 3}));
    }
}

/*
T - O(n!)
S - O(n)
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permute(nums, new boolean[nums.length], result, new ArrayList<>(nums.length));
        return result;
    }

    private void permute(int[] nums, boolean[] state, List<List<Integer>> permutations, List<Integer> buffer) {
        if (buffer.size() == nums.length) {
            permutations.add(new ArrayList<>(buffer));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!state[i]) {
                state[i] = true;
                buffer.add(nums[i]);
                permute(nums, state, permutations, buffer);
                state[i] = false;
                buffer.remove(buffer.size() - 1);
            }
        }
    }
}

/*
T - O(n!)
O - O(1)
 */
class Solution2 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permute(nums, 0, result);
        return result;
    }

    private void permute(int[] nums, int position, List<List<Integer>> permutations) {
        if (position == nums.length) {
            List<Integer> permutation = new ArrayList<>();
            for (int num : nums) {
                permutation.add(num);
            }
            permutations.add(permutation);
            return;
        }

        for (int i = position; i < nums.length; i++) {
            swap(nums, i, position);
            permute(nums, position + 1, permutations);
            swap(nums, position, i);
        }
    }

    private void swap(int[] nums, int from, int to) {
        int temp = nums[from];
        nums[from] = nums[to];
        nums[to] = temp;
    }
}