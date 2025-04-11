package io.abdul.recursion.faqs.problem2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSumII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum2(new int[]{1, 1, 2}, 3));
        System.out.println(solution.combinationSum2(new int[]{1, 3, 1, 4, 2, 6, 1, 7, 5, 2, 9, 1}, 4));
        System.out.println(solution.combinationSum2(new int[]{2, 1, 2}, 5));
    }
}

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2(candidates, 0, target, combinations, new ArrayList<>());
        return combinations;
    }

    private void combinationSum2(int[] candidates, int i, int target, List<List<Integer>> combinations, List<Integer> buffer) {
        if (target == 0) { // BC1, target is met
            combinations.add(new ArrayList<>(buffer));
            return;
        }

        for (int j = i; j < candidates.length; j++) {
            if (j > i && candidates[j] == candidates[j - 1]) { // Skip exploring duplicate element as it'll repeat the same combinations as before
                continue;
            }
            if (candidates[j] > target) {
                break;
            }
            buffer.add(candidates[j]);
            combinationSum2(candidates, j + 1, target - candidates[j], combinations, buffer);
            buffer.remove(buffer.size() - 1);
        }

    }
}
