package io.abdul.recursion.faqs.problem7;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/faqs-medium/combination-sum-iii
public class CombinationSumIII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum3(3, 7));
        System.out.println(solution.combinationSum3(3, 9));
        System.out.println(solution.combinationSum3(3, 8));
    }
}

class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        combinationSum3(k, n, 1, new ArrayList<>(k), 0, result);
        return result;
    }

    /*
    n is limited from 1 to 9
     */
    private void combinationSum3(int k, int targetSum, int i, List<Integer> buffer, int sum, List<List<Integer>> result) {
        if (buffer.size() == k) {
            if (sum == targetSum) {
                result.add(new ArrayList<>(buffer));
            }
            return;
        }

        for (int j = i; j <= 9; j++) {
            buffer.add(j);
            sum += j;
            combinationSum3(k, targetSum, j + 1, buffer, sum, result);
            buffer.remove(buffer.size() - 1);
            sum -= j;
        }
    }
}