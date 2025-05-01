package io.abdul.array.faq_medium.problem13;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/kadane's-algorithm
public class KadanesAlgorithm {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxSubArray(new int[]{2, 3, 5, -2, 7, -4}));
        System.out.println(solution.maxSubArray(new int[]{-2, -3, -7, -2, -10, -4}));
        System.out.println(solution.maxSubArray(new int[]{-1, 2, 3, -1, 2, -6, 5}));
    }
}

/*
T - O(n)
S - O(1)
Add an element if it doesn't leave you in negative value. Reset sum=0, meaning ignore the sub-array formed so far, as
adding this element will anyway make entire sub-array to have negative value. So keeping them is a liability!
 */
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int maxSoFar = Integer.MIN_VALUE;

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > maxSoFar) {
                maxSoFar = sum;
            }
            if (sum < 0) {
                sum = 0;
            }
        }

        return maxSoFar;
    }
}
