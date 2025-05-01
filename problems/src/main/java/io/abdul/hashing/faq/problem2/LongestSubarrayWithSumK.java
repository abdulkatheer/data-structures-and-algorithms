package io.abdul.hashing.faq.problem2;

import java.util.HashMap;

// https://takeuforward.org/plus/dsa/hashing/faqs/longest-subarray-with-sum-k
public class LongestSubarrayWithSumK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.longestSubarray(new int[]{10, 5, 2, 7, 1, 9}, 15));
        System.out.println(solution.longestSubarray(new int[]{657, 178, -940, -767, -961, 87, 761, -750, -23, -893}, 761));
    }
}

/*
Brute - Try all combinations
T - O(n^2)
S - O(1)

Ex1: 10, 5, 2, 7, 1, 9
0 10 - 0
1 15 - 0,1
2 17 - 0,1,2
3 24 - 0,1,2,3
4 25 - 0,1,2,3,4
5 34 - 0,1,2,3,4,5

5 9  - 5
4 10 - 4,5
3 17 - 3,4,5
2 19 - 2,3,4,5
2 24 - 1,2,3,4,5
0 34 - 0,1,2,3,4,5

0,5 -> 34-10+10 = 34

 */

class Solution {
    public int longestSubarray(int[] nums, int k) {
        int longest = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            int totalElements = nums.length - i;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
            }
            for (int j = nums.length - 1; j >= i; j--) {
                if (sum == k && totalElements > longest) {
                    longest = totalElements;
                }
                totalElements--;
                sum -= nums[j];
            }
        }

        return longest;
    }
}

/*
Optimal - Negatives & Positives, using Prefix sum technique
T - O(n)
S - O(n)

Ex: 10 elements
0 1 2 3 4 5 6 7 8 9
How many subarrays can end with 3 pos?
23, 123, 0123 - 3 prefix sums needed
So if prefix sum at pos 3 is x, there'll be 3 prefix sums stored already for 0,1,2
To find out if any of those 3 subarrays, we can check if x-k exists. Meaning remaining of that (+ or -) should exist as one of prefix sum.
If exists, then our subarray starts from that pos+1 upto 3 positions.

- How to handle full array match? Store 0 with pos -1
- How to find longest? Let's say the expected number is occurring multiple times in the prefix, which one to take, the longest/farest one.
 */
class Solution2 {
    public int longestSubarray(int[] nums, int k) {
        int longestSubarray = 0;
        HashMap<Integer, Integer> prefixSumByPos = new HashMap<>();
        prefixSumByPos.put(0, -1); // to match full array or we can do this other way look Solution 3
        int sumSoFar = 0;
        for (int i = 0; i < nums.length; i++) {
            sumSoFar += nums[i];
            int balance = sumSoFar - k;
            if (prefixSumByPos.containsKey(balance)) { // If any of subarrays that ends with i match
                Integer pos = prefixSumByPos.get(balance);
                int length = i - pos;
                if (length > longestSubarray) {
                    longestSubarray = length;
                }
            }
            if (!prefixSumByPos.containsKey(sumSoFar)) {
                prefixSumByPos.put(sumSoFar, i); // Insert only if not available already to retain the oldest/farest occurrence as we need longest
            }
        }

        return longestSubarray;
    }
}

class Solution3 {
    public int longestSubarray(int[] nums, int k) {
        int longestSubarray = 0;
        HashMap<Integer, Integer> prefixSumByPos = new HashMap<>();
        int sumSoFar = 0;
        for (int i = 0; i < nums.length; i++) {
            sumSoFar += nums[i];
            // full left subarray matches
            if (sumSoFar == k) {
                longestSubarray = Math.max(longestSubarray, i + 1);
                continue;
            }

            int balance = sumSoFar - k;
            // Partial left subarray matches
            if (prefixSumByPos.containsKey(balance)) { // If any of subarrays that ends with i match
                Integer pos = prefixSumByPos.get(balance);
                int length = i - pos;
                if (length > longestSubarray) {
                    longestSubarray = length;
                }
            }
            if (!prefixSumByPos.containsKey(sumSoFar)) {
                prefixSumByPos.put(sumSoFar, i); // Insert only if not available already to retain the oldest/farest occurrence as we need longest
            }
        }

        return longestSubarray;
    }
}
