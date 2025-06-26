package io.abdul.dynamic_programming.dp_on_lis.problem2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintLis {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Example input with LIS [10, 22, 33, 50, 60, 80]
        int[] arr1 = {10, 22, 9, 33, 21, 50, 41, 60, 80};
        assertEquals(List.of(10, 22, 33, 50, 60, 80), solution.longestIncreasingSubsequence(arr1),
                "LIS for [10, 22, 9, 33, 21, 50, 41, 60, 80] should be [10, 22, 33, 50, 60, 80]");

        // Test Case 2: Example input with LIS [1, 3, 4, 6]
        int[] arr2 = {1, 3, 2, 4, 6, 5};
        assertEquals(List.of(1, 3, 4, 6), solution.longestIncreasingSubsequence(arr2),
                "LIS for [1, 3, 2, 4, 6, 5] should be [1, 3, 4, 6]");

        // Test Case 3: Example input with LIS [1, 2, 3, 4, 7]
        int[] arr3 = {5, 6, 1, 2, 3, 4, 7};
        assertEquals(List.of(1, 2, 3, 4, 7), solution.longestIncreasingSubsequence(arr3),
                "LIS for [5, 6, 1, 2, 3, 4, 7] should be [1, 2, 3, 4, 7]");

        // Test Case 4: Edge case with single element
        int[] arr4 = {5};
        assertEquals(List.of(5), solution.longestIncreasingSubsequence(arr4),
                "LIS for [5] should be [5]");

        // Test Case 5: Edge case with strictly increasing sequence
        int[] arr5 = {1, 2, 3, 4, 5};
        assertEquals(List.of(1, 2, 3, 4, 5), solution.longestIncreasingSubsequence(arr5),
                "LIS for [1, 2, 3, 4, 5] should be [1, 2, 3, 4, 5]");

        // Test Case 6: Edge case with strictly decreasing sequence
        int[] arr6 = {5, 4, 3, 2, 1};
        assertEquals(List.of(5), solution.longestIncreasingSubsequence(arr6),
                "LIS for [5, 4, 3, 2, 1] should be [5]");

        // Test Case 7: Large input with duplicates
        int[] arr7 = {1, 3, 5, 4, 7, 4, 8};
        assertEquals(List.of(1, 3, 5, 7, 8), solution.longestIncreasingSubsequence(arr7),
                "LIS for [1, 3, 5, 4, 7, 4, 8] should be [1, 3, 4, 7, 8]");

        // Test Case 8: Large input with mixed values
        int[] arr8 = {3, 10, 2, 1, 20};
        assertEquals(List.of(3, 10, 20), solution.longestIncreasingSubsequence(arr8),
                "LIS for [3, 10, 2, 1, 20] should be [3, 10, 20]");

    }
}

/*
Step 1 - Recursion

T - O(2^n)
S - O(2^n)

Very high space requirement

 */
class Solution {
    public List<Integer> longestIncreasingSubsequence(int[] arr) {
        ArrayList<Integer> temp = new ArrayList<>();
        List<List<Integer>> lisList = new ArrayList<>();
        lis(arr, -1, 0, temp, lisList);
        int max = lisList.get(0).size();
        List<Integer> lis = null;
        for (List<Integer> integers : lisList) {
            /*
            Bcz there could be multiple with same size. We need lexicographically increasing LIS
            We need 10, 22, 33, 50, 60, 80 and NOT 10, 22, 33, 41, 60, 80
            As we do tail recursion, 41 will be considered first and hence comes first in the result.
            So we skip it for 50
             */
            if (integers.size() >= max) {
                max = integers.size();
                lis = integers;
            }
        }
        return lis;
    }

    private void lis(int[] nums, int prevPos, int i, List<Integer> result, List<List<Integer>> allLis) {
        if (i == nums.length - 1) {
            if (prevPos < 0 || nums[i] > nums[prevPos]) {
                result.add(nums[i]);
                allLis.add(new ArrayList<>(result));
                result.remove(result.size() - 1);
            } else {
                allLis.add(new ArrayList<>(result));
            }
            return;
        }

        // skip current
        lis(nums, prevPos, i + 1, result, allLis);
        // take
        if (prevPos < 0) {
            result.add(nums[i]);
            lis(nums, i, i + 1, result, allLis);
            result.remove(result.size() - 1);
        } else if (nums[i] > nums[prevPos]) {
            result.add(nums[i]);
            lis(nums, i, i + 1, result, allLis);
            result.remove(result.size() - 1);
        }
    }
}

/*
Step 2 skipped, as anyway memoizing it will not reduce space much

Step 3 - Bottom-up iterative solution

T - O(n^2) - main logic, backtracking and reversal
S - O(n) - dp and lis

Similar to finding LIS length. But when we find the path, we also keep track of previous index position which has been taken for max length
 */
class Solution2 {
    public List<Integer> longestIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        int[] lis = new int[n];
        /*
        dp[i] stores the length of LIS ending at i
        lis[i] stores the previous index of LIS ending at i
         */

        // Known solutions
        dp[0] = 1;
        lis[0] = -1;

        // default if no one is smaller than i
        int maxOfAll = 1;
        int lastPosOfMaxOfAll = 0;
        for (int i = 1; i < n; i++) {
            // default if no one is smaller than i
            int maxPrev = 1;
            int prevPos = -1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    int taken = 1 + dp[j];
                    if (taken > maxPrev) {
                        maxPrev = taken;
                        prevPos = j;
                    }
                }
            }
            dp[i] = maxPrev;
            lis[i] = prevPos;

            if (maxPrev > maxOfAll) {
                maxOfAll = maxPrev;
                lastPosOfMaxOfAll = i;
            }
        }

        // Now we know max LIS ending at each pos in dp and prev index of each pos in lis
        List<Integer> result = new ArrayList<>();
        int next = lastPosOfMaxOfAll;
        while (next != -1) {
            result.add(arr[next]);
            next = lis[next];
        }

        Collections.reverse(result);
        return result;
    }
}
