package io.abdul.recursion.practice.problem10;

import java.util.ArrayList;
import java.util.List;

// https://www.techiedelight.com/find-ways-calculate-target-elements-array/
public class SubsetSum {
    public static void main(String[] args) {
        System.out.println(Solution.countWays(new int[]{1, 2, 3}, 6));
        System.out.println(Solution.countWays(new int[]{1, 2, 3, 3}, 6));
        System.out.println(Solution.countWays(new int[]{1, 2, 3, 3, 6}, 6));
        System.out.println(Solution.countWays(new int[]{1, 2, 3, 3, 6, 1, 4, 5, -1, 1}, 6));
        System.out.println(Solution.countWays(new int[]{5, 3, -6, 2}, 6));
        System.out.println(Solution.countWays(new int[]{5, 3, -6, 2}, 4));

        System.out.println();
        System.out.println(Solution2.countWays(new int[]{1, 2, 3}, 6));
        System.out.println(Solution2.countWays(new int[]{1, 2, 3, 3}, 6));
        System.out.println(Solution2.countWays(new int[]{1, 2, 3, 3, 6}, 6));
        System.out.println(Solution2.countWays(new int[]{1, 2, 3, 3, 6, 1, 4, 5, -1, 1}, 6));
        System.out.println(Solution2.countWays(new int[]{5, 3, -6, 2}, 6));
        System.out.println(Solution2.countWays(new int[]{5, 3, -6, 2}, 4));
    }
}

/*

Given an integer array, return the total number of ways to calculate the specified target from array elements using only the addition and subtraction operator. The use of any other operator is forbidden.

Input: nums = [5, 3, -6, 2], target = 6
Output: 4
Explanation: There are 4 ways to calculate the target of 6 using only + and - operators:

(-)-6 = 6
(+) 5 (+) 3 (-) 2 = 6
(+) 5 (-) 3 (-) -6 (-) 2 = 6
(-) 5 (+) 3 (-) -6 (+) 2 = 6

Input: nums = [5, 3, -6, 2], target = 4
Output: 4
Explanation: There are 4 ways to calculate the target of 4 using only + and - operators:

(-)-6 (-) 2 = 4
(-) 5 (+) 3 (-)-6 = 4
(+) 5 (-) 3 (+) 2 = 4
(+) 5 (+) 3 (+)-6 (+) 2 = 4

*/

class Solution {
    public static int countWays(int[] nums, int target) {
//        AtomicInteger result = new AtomicInteger(0);
        List<List<Integer>> result = new ArrayList<>();
        countWays(0, nums, 0, target, result, new ArrayList<>());
//        System.out.println(result);
        return result.size();
    }

    private static void countWays(int i, int[] nums, int sum, int target, List<List<Integer>> result, List<Integer> buffer) {
        // Only With/without case
//        if (sum == target) {
//            result.add(new ArrayList<>(buffer));
//        }
//
//        for (int j = i; j < nums.length; j++) {
//            buffer.add(nums[j]);
//            countWays(j + 1, nums, sum + nums[j], target, result, buffer);
//            buffer.remove(buffer.size() - 1);
//        }

        if (sum == target) {
            result.add(new ArrayList<>(buffer));
        }

        for (int j = i; j < nums.length; j++) {
            buffer.add(nums[j]); // With positive
            countWays(j + 1, nums, sum + nums[j], target, result, buffer);

            buffer.remove(buffer.size() - 1);
            buffer.add(-nums[j]); // With negative
            countWays(j + 1, nums, sum - nums[j], target, result, buffer);

            buffer.remove(buffer.size() - 1); // Without and try next number
        }
    }
}

class Solution2 {
    public static int countWays(int[] nums, int target) {
//        AtomicInteger result = new AtomicInteger(0);
        List<List<Integer>> result = new ArrayList<>();
        countWays(0, nums, 0, target, result, new ArrayList<>());
//        System.out.println(result);
        return result.size();
    }

    private static void countWays(int i, int[] nums, int sum, int target, List<List<Integer>> result, List<Integer> buffer) {
        // Only With/without case
//        if (i >= nums.length) {
//            if (sum == target) {
//                result.add(new ArrayList<>(buffer));
//            }
//            return;
//        }
//
//        sum += nums[i];
//        buffer.add(nums[i]);
//        countWays(i + 1, nums, sum + nums[i], target, result, buffer);
//
//        sum -= nums[i];
//        buffer.remove(buffer.size() - 1);
//        countWays(i + 1, nums, sum, target, result, buffer);

        if (i >= nums.length) {
            if (sum == target) {
                result.add(new ArrayList<>(buffer));
            }
            return;
        }

        buffer.add(nums[i]); // With positive
        countWays(i + 1, nums, sum + nums[i], target, result, buffer);

        buffer.remove(buffer.size() - 1);
        buffer.add(-nums[i]); // With negative
        countWays(i + 1, nums, sum - nums[i], target, result, buffer);

        buffer.remove(buffer.size() - 1); // Without this
        countWays(i + 1, nums, sum, target, result, buffer);
    }
}
