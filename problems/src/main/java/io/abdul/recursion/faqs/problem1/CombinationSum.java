package io.abdul.recursion.faqs.problem1;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/faqs-medium/combination-sum
public class CombinationSum {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum(new int[]{1}, 7));
        System.out.println(solution.combinationSum(new int[]{2, 1}, 7));
        System.out.println(solution.combinationSum(new int[]{2, 3, 5, 4}, 7));
    }
}

class Solution {
    /*
    Given that elements are all positive, we can derive T and S with the least value 1 and target t
    T - O(2^t * t) 2^t is to find t combinations with value 1; t is for adding combination to result operation
    S - O(t) can't grow beyond t with value 1
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        combinationSum(candidates, target, 0, combinations, new ArrayList<>());
        return combinations;
    }

    private void combinationSum(int[] candidates, int target, int i, List<List<Integer>> combinations, ArrayList<Integer> combination) {
        if (target == 0) { // B1 - irrespective of which i we're in. Bcz adding more positive elements is impossible
            combinations.add(new ArrayList<>(combination));
            return;
        }
        if (i >= candidates.length) { // B2 - Meet dead end without making a combination
            return;
        }

        if (candidates[i] <= target) { // With current element
            combination.add(candidates[i]);
            combinationSum(candidates, target - candidates[i], i, combinations, combination); // Explore all combinations with current element.
            // i is not incremented, that makes this call recursive for same i
            // Above will come to an end when B2 is met
            combination.remove(combination.size() - 1); // You would have reached a base case. Now remove this and try with next possible combination
        }

        combinationSum(candidates, target, i + 1, combinations, combination); // Without current element, Move to next element
        // Above will come to an end when B1 is met
    }
}
