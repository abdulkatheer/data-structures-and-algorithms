package io.abdul.hashing.faq.problem4;

import java.util.HashMap;

// https://takeuforward.org/plus/dsa/hashing/faqs/count-subarrays-with-given-sum
public class CountSubarraysWithSumK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.subarraySum(new int[]{1, 1, 1}, 2));
        System.out.println(solution.subarraySum(new int[]{1, 2, 3}, 3));
        System.out.println(solution.subarraySum(new int[]{3, 1, 2, 4}, 6));

    }
}

/*
Optimal - HashMap with Prefix sum and count. Similar to LongestSubarrayWithSumK
T - O(n)
S - O(n)
 */
class Solution {
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixSumAndCount = new HashMap<>();
        int sum = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) { // Entire subarray to the left forms k
                count++;
            }
            int balance = sum - k;
            if (prefixSumAndCount.containsKey(balance)) {
                count = count + prefixSumAndCount.get(balance);
            }

            Integer c = prefixSumAndCount.getOrDefault(sum, 0);
            prefixSumAndCount.put(sum, c + 1); // Incrementing occurrences to calculate count
        }

        return count;
    }
}

/*
Better - find all possible subarrays, but use same sum and don't calculate every time
T - O(n^2)
S - O(1)
 */
class Solution2 {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            int sumAtI = sum;
            for (int j = nums.length - 1; j >= i; j--) {
                if (sumAtI == k) {
                    count++;
                }
                sumAtI -= nums[j];
            }
            sum -= nums[i];
        }

        return count;
    }
}

/*
Brute - Calculate all sum
T - O(n^3)
S - O(1)
 */
class Solution3 {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int a = 0; a < nums.length; a++) {
            for (int b = a; b < nums.length; b++) {
                int sum = 0;
                for (int c = 0; c < nums.length - b; c++) {
                    sum += nums[c];
                }
                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }
}