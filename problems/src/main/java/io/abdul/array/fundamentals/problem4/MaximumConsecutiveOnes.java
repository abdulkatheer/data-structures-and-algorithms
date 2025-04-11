package io.abdul.array.fundamentals.problem4;

public class MaximumConsecutiveOnes {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1, 1, 0, 0, 1, 1, 1, 0}));
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 0, 0, 0, 0}));
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 1, 0, 1, 1, 1}));
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1}));
    }
}

class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0;
        int count = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
            }
            if (num == 0) {
                if (count > maxCount) {
                    maxCount = count;
                }
                count = 0;
            }
        }
        return Math.max(count, maxCount);
    }
}