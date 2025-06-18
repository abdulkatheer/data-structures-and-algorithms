package io.abdul.dynamic_programming.dp_on_lis.problem6;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfLIS {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

//         Test Case 1: Example input with two LIS of length 4
        int[] nums1 = {1, 3, 5, 4, 7};
        assertEquals(2, solution.numberOfLIS(nums1),
                "Number of LIS for [1, 3, 5, 4, 7] should be 2");

        // Test Case 2: Example input with all elements the same
        int[] nums2 = {2, 2, 2, 2, 2};
        assertEquals(5, solution.numberOfLIS(nums2),
                "Number of LIS for [2, 2, 2, 2, 2] should be 5");

        // Test Case 3: Example input with four LIS of length 4
        int[] nums3 = {10, 9, 2, 5, 3, 7, 101, 18};
        assertEquals(4, solution.numberOfLIS(nums3),
                "Number of LIS for [10, 9, 2, 5, 3, 7, 101, 18] should be 4");

        // Test Case 4: Edge case with single element
        int[] nums4 = {5};
        assertEquals(1, solution.numberOfLIS(nums4),
                "Number of LIS for [5] should be 1");

        // Test Case 5: Edge case with strictly increasing sequence
        int[] nums5 = {1, 2, 3, 4, 5};
        assertEquals(1, solution.numberOfLIS(nums5),
                "Number of LIS for [1, 2, 3, 4, 5] should be 1");

        // Test Case 6: Edge case with strictly decreasing sequence
        int[] nums6 = {5, 4, 3, 2, 1};
        assertEquals(5, solution.numberOfLIS(nums6),
                "Number of LIS for [5, 4, 3, 2, 1] should be 5");

        // Test Case 7: Large input with mixed values
        int[] nums7 = {1, 3, 2, 4, 6, 5, 7};
        assertEquals(4, solution.numberOfLIS(nums7),
                "Number of LIS for [1, 3, 2, 4, 6, 5, 7] should be 2");

        // Test Case 8: Edge case with duplicates and increasing sequence
        int[] nums8 = {1, 2, 2, 3, 3, 4};
        assertEquals(4, solution.numberOfLIS(nums8),
                "Number of LIS for [1, 2, 2, 3, 3, 4] should be 3");

    }
}

/*
Step 1 - Top-down Recursive solution
 */
class Solution {
    public int numberOfLIS(int[] nums) {
        Map<Integer, Integer> result = new HashMap<>();
        numberOfLis(nums, 0, -1, 0, result);

        int max = 1;
        int maxCount = 1;
        for (Map.Entry<Integer, Integer> res : result.entrySet()) {
            if (res.getKey() >= max) {
                max = res.getKey();
                maxCount = res.getValue();
            }
        }

        return maxCount;
    }

    private void numberOfLis(int[] nums, int i, int prevPos, int lis, Map<Integer, Integer> result) {
        if (i == nums.length - 1) {
            if (prevPos < 0 || nums[i] > nums[prevPos]) {
                lis++;
            }
            if (result.containsKey(lis)) {
                result.put(lis, result.get(lis) + 1);
            } else {
                result.put(lis, 1);
            }
            return;
        }

        // skip
        numberOfLis(nums, i + 1, prevPos, lis, result);

        if (prevPos < 0 || nums[i] > nums[prevPos]) {
            // take
            numberOfLis(nums, i + 1, i, lis + 1, result);
        }
    }
}

/*
Step 3 - Bottom-up iterative solution

Same as LIS, but just count the max

Known solution:
At pos 0, we've only one LIS

Recursive solution:
At each pos, we're looking for a smaller num in the past, which has highest LIS so far.
We start with dp[i] = 1, as that's the worst case when everyone is bigger in the past
Then we compare and set the max for each smaller number.
10 9 4 2 5 6 7
At 9, no one is smaller so dp 1, count 1
At 4, same
At 2, same
At 5, 2 are smaller than 5. 2 and 4. Each having lis of size 1 and 1. 1 is the max as well. So dp[i] = 2 and count[i] = 1+1
At 6, 4,2,5 are smaller and each having lis of 1,1,2. So the max is 2 Hence dp[i] = 3 and only one max exists. So count[i] = 2

When there are multiple max, we sum it all together. That many possibilities exist.
When there is only one max, we just copy it
When no smaller element exists, lis is just 1 and count is just 1
 */
class Solution2 {
    public int numberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];

        // Known solution
        dp[0] = 1;
        count[0] = 1;

        int maxOfAll = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1; // default if all are bigger
            count[i] = 1;
            for (int j = 0; j < i; j++) {
                // 10 9 4 2 5 6 7
                //  1 1 1 1 2 3 4
                //  1 1 1 1 2 2 2
                if (nums[i] > nums[j]) {
                    int lis = dp[j] + 1;
                    if (lis > dp[i]) { // new bigger LIS found, so reset
                        dp[i] = lis;
                        count[i] = count[j];
                    } else if (lis == dp[i]) { // repeats, so sum possibilities
                        count[i] += count[j];
                    }
                }
            }
            maxOfAll = Math.max(maxOfAll, dp[i]);
        }

        int maxCount = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == maxOfAll) {
                maxCount += count[i];
            }
        }

        return maxCount;
    }
}