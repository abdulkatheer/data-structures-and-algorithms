package io.abdul.dynamic_programming.lis.problem3;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongestDivisibleSubset {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: Example input with subset [5, 10, 20]
        int[] nums1 = {3, 5, 10, 20};
        List<Integer> result1 = solution.largestDivisibleSubset(nums1);
        assertTrue(isValidSubset(result1, nums1), "Result should be a valid divisible subset");

        // Test Case 2: Example input with subset [2, 4, 8, 16, 32]
        int[] nums2 = {16, 8, 2, 4, 32};
        List<Integer> result2 = solution.largestDivisibleSubset(nums2);
        assertTrue(isValidSubset(result2, nums2), "Result should be a valid divisible subset");

        // Test Case 3: Example input with subset [7, 14, 28]
        int[] nums3 = {7, 14, 28, 3};
        List<Integer> result3 = solution.largestDivisibleSubset(nums3);
        assertTrue(isValidSubset(result3, nums3), "Result should be a valid divisible subset");

        // Test Case 4: Edge case with single element
        int[] nums4 = {5};
        List<Integer> result4 = solution.largestDivisibleSubset(nums4);
        assertEquals(List.of(5), result4, "Result should be [5]");

        // Test Case 5: Edge case with all elements being the same
        int[] nums5 = {4, 4, 4, 4};
        List<Integer> result5 = solution.largestDivisibleSubset(nums5);
        assertEquals(List.of(4, 4, 4, 4), result5, "Result should be [4, 4, 4, 4]");

        // Test Case 6: Edge case with no divisible pairs
        int[] nums6 = {7, 11, 13};
        List<Integer> result6 = solution.largestDivisibleSubset(nums6);
        assertEquals(1, result6.size(), "Result should contain only one element");

        // Test Case 7: Large input with increasing powers of 2
        int[] nums7 = {1, 2, 4, 8, 16, 32, 64};
        List<Integer> result7 = solution.largestDivisibleSubset(nums7);
        assertTrue(isValidSubset(result7, nums7), "Result should be a valid divisible subset");

        int[] nums8 = {36, 419, 329, 744, 442, 432, 417, 193, 432, 305, 406, 933, 3, 889, 9, 36, 299, 126, 12, 48, 455, 611, 3, 604, 339, 564, 578, 266, 9, 33, 411, 42, 57, 9, 65, 703, 481};
        List<Integer> result8 = solution.largestDivisibleSubset(nums8);
        assertTrue(isValidSubset(result8, nums8), "Result should be a valid divisible subset");
    }

    // Helper method to validate if the result is a valid divisible subset
    private static boolean isValidSubset(List<Integer> subset, int[] nums) {
        for (int i = 0; i < subset.size(); i++) {
            for (int j = i + 1; j < subset.size(); j++) {
                if (subset.get(j) % subset.get(i) != 0 && subset.get(i) % subset.get(j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

/*
Step 1 - Top-down recursive solution

T - O(2^n)
S - O(2^n) stack + all subsets

Sort it, so that we can iterate only in one direction.
For ex, 1 2 3 4 9 18 27 28 29 54
We pick 1, 3
To pick 3, we only have to check 1%3
To pick 9, we only have to check 9%3
To pick 27, we only have to check 27%9
To pick 54, we only have to check 54%27
If a bigger number is divisible, then all smaller divisible numbers will also be divisible

Let's take unsorted, 1 2 54 4 27 18 9 28 29 3
We pick 1
54 will work for 2 and 1
27 will work for 54, so we count 4 (1,2,54,27)
9 will work for 27
3 works for 9
2 is divisible by 54, but not other numbers. So if we start picking smaller numbers, then this divisibility issue can be solved

NOTE: We need subset and no subsequence. That's why sorting is allowed.
 */
class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);

        ArrayList<List<Integer>> result = new ArrayList<>();
        largestDivisibleSubset(nums, 0, -1, new ArrayList<>(), result);

        int max = Integer.MIN_VALUE;
        List<Integer> res = null;
        for (List<Integer> integers : result) {
            if (integers.size() > max) {
                max = integers.size();
                res = integers;
            }
        }

        return res;
    }

    private void largestDivisibleSubset(int[] nums, int i, int prevPos, List<Integer> temp, List<List<Integer>> result) {
        if (i == nums.length - 1) { // last num
            // take
            if (prevPos < 0 || nums[i] % nums[prevPos] == 0) { // as it's sorted, nums[i] will always be greater than nums[prevPos]
                temp.add(nums[i]);
                result.add(new ArrayList<>(temp));
                temp.remove(temp.size() - 1);
            } else { //  or skip
                result.add(new ArrayList<>(temp));
            }
            return;
        }

        // without current
        largestDivisibleSubset(nums, i + 1, prevPos, temp, result);

        // with current (if possible)
        if (prevPos < 0 || nums[i] % nums[prevPos] == 0) {
            temp.add(nums[i]);
            largestDivisibleSubset(nums, i + 1, i, temp, result);
            temp.remove(temp.size() - 1);
        }
    }
}

/*
Step 3 - Bottom-up iterative solution

T - O(n^2) - sorting, core logic, finding max
S - O(n)

1 16 4 7 8
1 16 4 8 - ans
1 & 16 divisible, 1 & 4 divisible, 1 & 8 divisible
16 & 4 divisible, 16 & 8 divisible
4 & 8 divisible

Let's sort it
1 4 7 8 16
At pos 0, we can take 1
4 divisible by 1 - 2
7 not divisible by 4, but by 1 - so 2
8 divisible by 4 (first match from right) - 3
Idea is if 8 / 4, then 8 / by any number which was divisible by 4 in earlier steps
16 divisible by 8, so 4
 */
class Solution3 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int[] dp = new int[n];
        int[] prev = new int[n];

        // Known solutions
        dp[0] = 1;
        prev[0] = -1;

        int maxOfAll = 1;
        int lastPosOfMaxOfAll = 0;
        for (int i = 1; i < n; i++) {
            int max = 1;
            int pre = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    if (1 + dp[j] > max) {
                        max = 1 + dp[j];
                        pre = j;
                    }
                }
            }
            dp[i] = max;
            prev[i] = pre;

            if (max > maxOfAll) {
                maxOfAll = max;
                lastPosOfMaxOfAll = i;
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        int next = lastPosOfMaxOfAll;
        while (next != -1) {
            result.add(nums[next]);
            next = prev[next];
        }

        return result;
    }
}
