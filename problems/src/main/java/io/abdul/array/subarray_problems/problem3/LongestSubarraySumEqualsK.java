package io.abdul.array.subarray_problems.problem3;

import java.util.HashMap;

// https://takeuforward.org/data-structure/longest-subarray-with-given-sum-k/
public class LongestSubarraySumEqualsK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution3a solution = new Solution3a();
//        Solution3b solution = new Solution3b();
//        System.out.println(solution.longestSubarraySum(new int[]{2, 3, 5}, 5));
//        System.out.println(solution.longestSubarraySum(new int[]{2, 3, 5, 1, 9}, 10));
//        System.out.println(solution.longestSubarraySum(new int[]{1, 2, 1, 4, 8, 7, 1, 2, 6, 0, 3, 4, 1, 1, 1, 5}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{3}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{1, 2, 3}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{1, 1, 1, 2, 1, 3}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{10, 10, 10, 20, 10, 30}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{1, 2, -3, 0, 3, -3, 1, 1, 1}, 3));
        System.out.println(solution.longestSubarraySum(new int[]{1, 2, 3, 4, 5}, 3));
//        System.out.println(solution.longestSubarraySum(new int[]{8, 8, 3, 8, 8}, 3));
    }
}

// Bruteforce
// Time - O(n^2)
// Space - O(1)
class Solution {
    // Visit all possible subarray and find
    public int longestSubarraySum(int[] nums, int k) {
        int longestSubarray = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int subarrayCount = 0;
            int sumOfSubarray = 0;
            for (int j = i; j < nums.length; j++) {
                subarrayCount++;
                sumOfSubarray += nums[j];
                if (sumOfSubarray == k) {
                    if (subarrayCount > longestSubarray) {
                        longestSubarray = subarrayCount;
                    }
                }
            }
        }
        return longestSubarray;
    }
}

// Better - DP & Greedy approach
// Best fit for positive, zero and negative values, better fit for positive non-zero numbers
// Time - Unordered map -> O(n logn); Ordered map - O(n); More collisions in hashing O(n^2)
// Space - O(n)
class Solution2 {
    public int longestSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> prefixSumAndIndex = new HashMap<>();

        int longestSubarray = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            /*
            the sum itself is matching k, either no element exists before this or first part of the array sums to
            k or previous elements is zeroed due to negative values
             */
            if (sum == k) { // 0 to upto i forms a subarray
                int length = i + 1; // as index starts from 0
                if (length > longestSubarray) {
                    longestSubarray = length;
                }
            }

            Integer subArray = prefixSumAndIndex.get(sum - k);
            if (subArray != null) {
                int length = i - subArray;
                if (length > longestSubarray) { // if sum == k, then this will never be true as that's the longer than this
                    longestSubarray = length;
                }
            }

            // We need to the earliest occurrence if same number repeats as we need the longest contiguous array
            prefixSumAndIndex.putIfAbsent(sum, i);
        }
        return longestSubarray;
    }
}

// Optimal - Two pointer and Greedy approach
// Best fit for positive, non-zero numbers
// Time
// Space
class Solution3 {
    public int longestSubarraySum(int[] nums, int k) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int longestSubarray = Integer.MIN_VALUE;

        while (i < nums.length && j < nums.length) {
            if (i == j) { // Single element subarray
                sum = nums[j];
            } else {
                sum += nums[j];
            }

            if (sum == k) { //match found
                int count = j - i + 1; // As index starts from zero
                if (count > longestSubarray) {
                    longestSubarray = count;
                }
                // Start next position i and reset data
                i++; // if i hits max, meaning no more number can make it to sum. while loop will stop
                j = i; // losing old calculations and starting over
                sum = 0;
                /*
                Can we just increment i, reduce sum and leave j as is?
                No
                Bcz we're adding sum with num[j] every iteration. So that will cause double addition
                Here moving i and adding sum are not coherent, so we always start over
                 */
            } else if (sum < k) { // not enough
                j++; // if j hits max, meaning no more number can make it to sum. while loop will stop
            } else { // much bigger
                // Start next position i and reset data
                i++; // if i hits max, meaning no more number can make it to sum. while loop will stop
                j = i;
                sum = 0;
            }
        }
        return Math.max(longestSubarray, 0);
    }
}

class Solution3a {
    public int longestSubarraySum(int[] nums, int k) {
        int i = 0;
        int j = 0;
        int sum = nums[0]; // Initializing with first number, as we add sum after incrementing j and not before.
        int longestSubarray = Integer.MIN_VALUE;

        // 1 2 3 4 5
        while (j < nums.length) {
            // Move i to the right and reduce sum till both i and are same or sum < k
            // Why shrink before check sum?
            /*
            1 2 3 4 5
            In this case, match found at 1 and 2 position.
            But if we shrink after match, we'll not be able to find out match at location 2.
            Bcz i will shrink and i and j will come to position 2. After that we increment j and add sum.
            So sum becomes 7, then we have check. That'll ignore match at position 2.
             */
            while (i <= j && sum > k) { // Shrink subarray until sum > k & i < j OR until i and j are in same position
                sum -= nums[i];
                i++;
            }
            if (sum == k) { // Match found, set longest
                System.out.println("Match found j=" + j);
                int length = j - i + 1; // As index starts from zero
                if (length > longestSubarray) {
                    longestSubarray = length;
                }
            }
            j++; // Move j to the right and increment sum if j is within bounds, if not loop will terminate
            if (j < nums.length) {
                sum += nums[j];
            }
        }
        return Math.max(longestSubarray, 0);
    }
}