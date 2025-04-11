package io.abdul.recursion.subseq_pattern_problems.problem1;

public class SubseqWithKSum {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.checkSubsequenceSum(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.checkSubsequenceSum(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.checkSubsequenceSum(new int[]{1, 10, 4, 5}, 16));

        System.out.println(solution.checkSubsequenceSumV2(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.checkSubsequenceSumV2(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.checkSubsequenceSumV2(new int[]{1, 10, 4, 5}, 16));

        System.out.println(solution.checkSubsequenceSumItr(new int[]{1, 2, 3, 4, 5}, 8));
        System.out.println(solution.checkSubsequenceSumItr(new int[]{4, 3, 9, 2}, 10));
        System.out.println(solution.checkSubsequenceSumItr(new int[]{1, 10, 4, 5}, 16));
    }
}

class Solution {
    /*
    T - O(n * 2^n) 2^n for finding all combinations and n to find sum for each combination
    O - O(n)
     */
    public boolean checkSubsequenceSum(int[] nums, int k) {
        return checkSubsequenceSum(nums, k, nums.length - 1, new boolean[nums.length]);
    }

    private boolean checkSubsequenceSum(int[] nums, int k, int i, boolean[] buffer) {
        if (i < 0) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if (buffer[j]) {
                    sum += nums[j];
                }
            }
            return sum == k;
        }

        boolean found = false;

        buffer[i] = true;
        found = checkSubsequenceSum(nums, k, i - 1, buffer);

        if (found) {
            return found;
        }

        buffer[i] = false;
        return checkSubsequenceSum(nums, k, i - 1, buffer);
    }

    /*
    T - O(2^n) 2^n for finding all combinations
    O - O(n)
     */
    public boolean checkSubsequenceSumV2(int[] nums, int k) {
        return checkSubsequenceSumV2(nums, k, nums.length - 1, 0);
    }

    private boolean checkSubsequenceSumV2(int[] nums, int k, int i, int sum) {
        if (i < 0) {
            return sum == k;
        }

        sum += nums[i];

        boolean found = checkSubsequenceSumV2(nums, k, i - 1, sum);

        if (found) {
            return true;
        }

        sum -= nums[i];
        return checkSubsequenceSumV2(nums, k, i - 1, sum);
    }


    /*
    T - O(n * 2^n)
    S - (n), no stack space
     */
    // Similar to powerSubset and binaryPermutations
    public boolean checkSubsequenceSumItr(int[] nums, int k) {
        int subsequences = 1 << nums.length; // 2^n

        for (int i = 0; i < subsequences; i++) {
            int sum = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((i & 1 << j) != 0) { // j-th bit is set in binary number i
                    sum += nums[j];
                }
            }
            if (sum == k) {
                return true;
            }
        }

        return false;
    }
}