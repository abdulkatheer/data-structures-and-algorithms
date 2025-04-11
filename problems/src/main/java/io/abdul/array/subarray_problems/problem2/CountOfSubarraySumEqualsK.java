package io.abdul.array.subarray_problems.problem2;

import java.util.HashMap;

// https://leetcode.com/problems/subarray-sum-equals-k/description/
public class CountOfSubarraySumEqualsK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
//        System.out.println(solution.subarraySum(new int[]{1, 2, 3, 4}, 5));
//        System.out.println(solution.subarraySum(new int[]{5}, 5));
//        System.out.println(solution.subarraySum(new int[]{4}, 5));
//        System.out.println(solution.subarraySum(new int[]{0, 0, 0, 0}, 0));
//        System.out.println(solution.subarraySum(new int[]{1, 2, 3}, 6));
//        System.out.println(solution.subarraySum(new int[]{1, 2, 3}, 10));
//        System.out.println(solution.subarraySum(new int[]{-1, -2, -3, -4}, -5));
        System.out.println(solution.subarraySum(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7));
//        System.out.println(solution.subarraySum(new int[]{1, 1, 1, 1}, 2));
//
//        int n = 1_00_000;
//        int[] arr = IntStream.range(0, n)
//                .map((i) -> 1)
//                .toArray();
//        System.out.println(solution.subarraySum(arr, 10));
//        System.out.println(solution.subarraySum(new int[]{1, 2, -3, 0, 3, -3, 1, 1, 1}, 3));
    }
}

// Brute force approach
// Time - O(n^2)
// Space - O(1)
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                if (currentSum == k) {
                    count++;
                }
            }
        }

        return count;
    }
}

// Better - DP based
// Time - O(n)
// Space - O(n)
/*
1 2 3, k=3
1, 1
sum == k, 1 subarray, p(3, 1)
Get count for 3, which is 1
Count=2

3 4 7 2 -3 1 4 2 k=7
p(3,1)
p(7,1)
p(14,1)
p(16,1)
p(13,1)
p(14,1)
p(18,1)
p(20,1)

count = 1+1+1+1
0 1
2
7 2 -3 1
1 4 2
 */
class Solution2 {

    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixSumAndCount = new HashMap<>();

        int count = 0;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            /*
            the sum itself is matching k, either no element exists before this or first part of the array sums to
            k or previous elements is zeroed due to negative values
             */
            if (sum == k) {
                count++;
            }

            Integer occurrence = prefixSumAndCount.get(sum - k);
            if (occurrence != null) {
                count += occurrence;
            }

            Integer sumOcc = prefixSumAndCount.get(sum);
            prefixSumAndCount.put(sum, sumOcc == null ? 1 : ++sumOcc);
        }
        return count;
    }
}