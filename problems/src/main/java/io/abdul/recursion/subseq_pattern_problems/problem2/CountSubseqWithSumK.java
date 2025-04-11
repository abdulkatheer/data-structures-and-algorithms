package io.abdul.recursion.subseq_pattern_problems.problem2;

public class CountSubseqWithSumK {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countSubsequenceWithTargetSum(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.countSubsequenceWithTargetSum(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.countSubsequenceWithTargetSum(new int[]{1, 10, 4, 5}, 16));

        System.out.println(solution.countSubsequenceWithTargetSumV2(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.countSubsequenceWithTargetSumV2(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.countSubsequenceWithTargetSumV2(new int[]{1, 10, 4, 5}, 16));

        System.out.println(solution.countSubsequenceWithTargetSumItr(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.countSubsequenceWithTargetSumItr(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.countSubsequenceWithTargetSumItr(new int[]{1, 10, 4, 5}, 16));
    }
}

class Solution {
    /*
    T - O(n * 2^n) 2^n for finding all combinations and n to find sum for each combination
    O - O(n)
    */
    public int countSubsequenceWithTargetSum(int[] nums, int k) {
        return countSubsequenceWithTargetSum(nums, k, nums.length - 1, new boolean[nums.length]);
    }

    private int countSubsequenceWithTargetSum(int[] nums, int k, int i, boolean[] buffer) {
        if (i < 0) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if (buffer[j]) {
                    sum += nums[j];
                }
            }
            return sum == k ? 1 : 0;
        }

        int count = 0;

        buffer[i] = true;
        count = countSubsequenceWithTargetSum(nums, k, i - 1, buffer);

        buffer[i] = false;
        return count + countSubsequenceWithTargetSum(nums, k, i - 1, buffer);
    }

    /*
    T - O(2^n) 2^n for finding all combinations
    O - O(n)
     */
    public int countSubsequenceWithTargetSumV2(int[] nums, int k) {
        return countSubsequenceWithTargetSumV2(nums, k, nums.length - 1, 0);
    }

    private int countSubsequenceWithTargetSumV2(int[] nums, int k, int i, int sum) {
        if (i < 0) {
            return sum == k ? 1 : 0;
        }

        sum += nums[i];
        int count = countSubsequenceWithTargetSumV2(nums, k, i - 1, sum);

        sum -= nums[i];
        return count + countSubsequenceWithTargetSumV2(nums, k, i - 1, sum);
    }

    /*
    T - O(n * 2^n)
    S - (n), no stack space
     */
    // Similar to powerSubset and binaryPermutations
    public int countSubsequenceWithTargetSumItr(int[] nums, int k) {
        int subsequences = 1 << nums.length; // 2^n
        int count = 0;

        for (int i = 0; i < subsequences; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((i & 1 << j) != 0) { // j-th bit is set in binary number i
                    sum += nums[j];
                }
            }
            if (sum == k) {
                count++;
            }
        }

        return count;
    }
}