package io.abdul.recursion.subseq_pattern_problems.problem0;

import java.util.ArrayList;
import java.util.List;

public class AllSubsequences {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};  // Example array
        List<List<Integer>> result = new ArrayList<>();
        generateSubsequences(nums, 0, new ArrayList<>(), result);
        System.out.println(result);
    }

    private static void generateSubsequences(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {

    }
}
