package io.abdul.recursion.subseq_pattern_problems.problem0;

import java.util.ArrayList;
import java.util.List;

public class Subsequences {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.subsequences(new int[]{1, 2, 3})); // Depth-first

        System.out.println(solution.subsequencesRecAndItr(new int[]{1, 2, 3})); //
    }
}

class Solution {
    public List<List<Integer>> subsequences(int[] nums) {
        return subsequencesRecursive(nums);
    }

    private List<List<Integer>> subsequencesRecursive(int[] nums) {
        List<List<Integer>> subsequences = new ArrayList<>();
        subsequences(nums, 0, subsequences, new ArrayList<>());
        return subsequences;
    }

    private void subsequences(int[] nums, int i, List<List<Integer>> subsequences, List<Integer> buffer) {
        if (i >= nums.length) {
            subsequences.add(new ArrayList<>(buffer));
            return;
        }

        buffer.add(nums[i]);
        subsequences(nums, i + 1, subsequences, buffer);

        buffer.remove(buffer.size() - 1);
        subsequences(nums, i + 1, subsequences, buffer);
    }


    public List<List<Integer>> subsequencesRecAndItr(int[] nums) {
        List<List<Integer>> subsequences = new ArrayList<>();
        subsequencesRecAndItr(nums, 0, subsequences, new ArrayList<>());
        return subsequences;
    }

    private void subsequencesRecAndItr(int[] nums, int index, List<List<Integer>> subsequences, List<Integer> buffer) {
        // Add current subset to result (including empty set)
        subsequences.add(new ArrayList<>(buffer));

        // Iterate through remaining elements
        for (int i = index; i < nums.length; i++) {
            buffer.add(nums[i]);  // Include current element
            subsequencesRecAndItr(nums, i + 1, subsequences, buffer);  // Recurse
            buffer.remove(buffer.size() - 1);  // Backtrack
        }
    }

}
