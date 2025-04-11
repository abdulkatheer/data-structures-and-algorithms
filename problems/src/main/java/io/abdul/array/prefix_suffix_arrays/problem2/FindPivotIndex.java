package io.abdul.array.prefix_suffix_arrays.problem2;

public class FindPivotIndex {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
        System.out.println(solution.pivotIndex(new int[]{1, 2, 3}));
        System.out.println(solution.pivotIndex(new int[]{2, 1, -1}));
    }
}

/*
Brute-force
Time - 3 x O(n) => O(n)
Space - 2 x O(n) => O(n)
 */
class Solution {
    public int pivotIndex(int[] nums) {
        int[] prefixSums = new int[nums.length];

        // Find prefix sum
        int s = 0;
        for (int i = 0; i < nums.length; i++) { // Time - O(n)
            s += nums[i];
            prefixSums[i] = s;
        }

        // Find suffix sum
        int[] suffixSums = new int[nums.length];
        s = 0;
        for (int i = nums.length - 1; i >= 0; i--) { // Time - O(n)
            s += nums[i];
            suffixSums[i] = s;
        }

        int result = -1;
        for (int i = 0; i < nums.length; i++) { // Time - O(n)
            int left = 0;
            if (i > 0) {
                left = prefixSums[i - 1];
            }

            int right = 0;
            if (i < nums.length - 1) {
                right = suffixSums[i + 1];
            }
            if (left - right == 0) {
                result = i;
                break;
            }
        }

        return result;
    }
}

/*
Better
Time - 2 x O(n) => O(n)
Space - 1 x O(n) => O(n)
 */
class Solution2 {
    public int pivotIndex(int[] nums) {
        int[] leftSums = new int[nums.length];

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            leftSums[i] = leftSum;
            leftSum += nums[i];
        }

        int totalSum = leftSum;
        int result = -1;
        for (int i = 0; i < nums.length; i++) {
            int rightSum = totalSum - leftSums[i] - nums[i];
            if (rightSum == leftSums[i]) {
                result = i;
                break;
            }
        }
        return result;
    }
}
