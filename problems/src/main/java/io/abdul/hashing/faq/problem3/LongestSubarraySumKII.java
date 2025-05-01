package io.abdul.hashing.faq.problem3;

// Only Positives
public class LongestSubarraySumKII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.longestSubarray(new int[]{1, 2, 3, 4, 1, 2, 1, 1, 1, 1}, 3));
        System.out.println(solution.longestSubarray(new int[]{}, 10));
    }
}

/*
Optimal
T - O(n)
S - O(1)

Ex: 10 positive natural numbers (non-zero, non-negative
0 1 2 3 4 5 6 7 8 9

1 2 3 4 1 2 1 1 1 1

Possibilities
0,1     | 1 2
2       | 3
4,5     | 1 2
6,7,8   | 1 1 1 (Ans)
7,8,9   | 1 1 1 (Ans)

- Longest subarray can be all 10 elements or any of parts.
- A Window of elements can form K, smaller than K or bigger than K
- If it can form, that could be the answer if longest of all. Then move window to find next possible subarray.
- If smaller, expand window to increase sum
- If bigger, shrink window to reduce sum
 */
class Solution {
    public int longestSubarray(int[] nums, int k) {
        if (nums.length == 0) {
            return 0;
        }

        int longest = 0;
        long sum = 0;

        int left = 0;
        int right = 0;
        sum += nums[0];

        while (right < nums.length) {
            // Shrink until <= k or single element
            while (left <= right && sum > k) {
                sum -= nums[left];
                left++;
            }

            if (sum == k) {
                longest = Math.max(longest, right - left + 1);
            }

            right++; // Now sum <= k and match might or might not be found. So go to next element if possible
            if (right < nums.length) {
                sum += nums[right];
            }
        }

        return longest;
    }
}
