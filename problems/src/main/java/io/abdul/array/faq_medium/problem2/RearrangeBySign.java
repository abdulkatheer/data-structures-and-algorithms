package io.abdul.array.faq_medium.problem2;

import java.util.Arrays;

public class RearrangeBySign {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(Arrays.toString(solution.rearrangeArray(new int[]{2, 4, 5, -1, -3, -4})));
        System.out.println(Arrays.toString(solution.rearrangeArray(new int[]{1, -1, -3, -4, 2, 3})));
        System.out.println(Arrays.toString(solution.rearrangeArray(new int[]{-4, 4, -4, 4, -4, 4})));
    }
}

class Solution {
    public int[] rearrangeArray(int[] nums) {
        int positivePos = -1;
        int negativePos = -1;
        int i = 0;
        int[] result = new int[nums.length];
        while (positivePos < nums.length && negativePos < nums.length) {
            positivePos = nextPositivePos(nums, positivePos + 1);
            negativePos = nextNegativePos(nums, negativePos + 1);
            if (positivePos < 0) {
                break;
            }
            result[i] = nums[positivePos];
            result[i + 1] = nums[negativePos];
            i += 2;
        }

        return result;
    }

    private int nextPositivePos(int[] nums, int from) {
        for (int i = from; i < nums.length; i++) {
            if (nums[i] > 0) {
                return i;
            }
        }
        return -1;
    }

    private int nextNegativePos(int[] nums, int from) {
        for (int i = from; i < nums.length; i++) {
            if (nums[i] < 0) {
                return i;
            }
        }
        return -1;
    }
}

class Solution2 {
    public int[] rearrangeArray(int[] nums) {
        int[] result = new int[nums.length];

        int pos = 0;
        int neg = 1;

        for (int num : nums) {
            if (num > 0) {
                result[pos] = num;
                pos += 2;
            } else {
                result[neg] = num;
                neg += 2;
            }
        }

        return result;
    }
}
