package io.abdul.recursion.practice.problem9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://www.techiedelight.com/k-partition-problem-print-all-subsets/
public class KPartition {
    public static void main(String[] args) {
        System.out.println(Solution.getSubsets(new int[]{7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4}, 2));
        System.out.println(Solution.getSubsets(new int[]{7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4}, 3));
        System.out.println(Solution.getSubsets(new int[]{7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4}, 4));
        System.out.println(Solution.getSubsets(new int[]{7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4}, 5));
        System.out.println(Solution.getSubsets(new int[]{3, 5, 7, 3, 2, 1}, 2));
    }
}

/*

Given an array of positive integers, partition it into `k` disjoint subsets that all have an equal sum, and they completely cover the array.

Input : S[] = [7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4], k = 2
Output: [[5, 3, 8, 4, 6, 4], [7, 3, 5, 12, 2, 1]] or [[4, 5, 6, 7, 8], [1, 2, 3, 3, 4, 5, 12]]
Explanation: S can be partitioned into 2 partitions, each having a sum of 30.

Input : S[] = [7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4], k = 3
Output: [[2, 1, 3, 4, 6, 4], [7, 5, 8], [3, 5, 12]]
Explanation: S can be partitioned into 3 partitions, each having a sum of 20.

Input : S[] = [7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4], k = 4
Output: [[1, 4, 6, 4], [2, 5, 8], [12, 3], [7, 3, 5]]
Explanation: S can be partitioned into 4 partitions, each having a sum of 15.

Input : S[] = [7, 3, 5, 12, 2, 1, 5, 3, 8, 4, 6, 4], k = 5
Output: [[2, 6, 4], [8, 4], [3, 1, 5, 3], [12], [7, 5]]
Explanation: S can be partitioned into 5 partitions, each having a sum of 12.

Input : S[] = [3, 5, 7, 3, 2, 1], k = 2
Output: []

*/

class Solution {
    public static List<List<Integer>> getSubsets(int[] nums, int k) {
        List<List<Integer>> subsets = new ArrayList<>();

        int sum = 0;
        for (Integer num : nums) {
            sum += num;
        }

        if (sum % k != 0) { // Can't split equally
            return Collections.emptyList();
        }

        for (int i = 0; i < k; i++) {
            if (!getSubsets(0, sum / k, nums, 0, subsets, new ArrayList<>())) {
                return Collections.emptyList(); // Can't find a subset
            }
        }

        return subsets;
    }

    private static boolean getSubsets(int sumSoFar, int sum, int[] nums, int i, List<List<Integer>> result, List<Integer> buffer) {
        if (sumSoFar == sum) {
            result.add(new ArrayList<>(buffer));
            return true;
        }

        for (int j = i; j < nums.length; j++) {
            if (nums[j] == -1) { // Already visited
                continue;
            }
            if (sumSoFar + nums[j] > sum) { // Can't add
                continue;
            }
            int num = nums[j]; // Include and find
            nums[j] = -1;
            buffer.add(num);

            if (getSubsets(sumSoFar + num, sum, nums, j + 1, result, buffer)) { // If found, return
                return true;
            }

            // If not found, backtrack and try different num

            nums[j] = num; // Exclude and find
            buffer.remove(buffer.size() - 1);
            return getSubsets(sumSoFar, sum, nums, j + 1, result, buffer);
        }

        return false;
    }
}