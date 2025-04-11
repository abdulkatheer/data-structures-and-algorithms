package io.abdul.array.fundamentals.problem6;

import java.util.Arrays;

public class RotateArrayByK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        int[] nums = new int[]{1};
        solution.rotateArray(nums, 1);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 2, 3, 4, 5, 6};
        solution.rotateArray(nums, 2);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{3, 4, 1, 5, 3, -5};
        solution.rotateArray(nums, 8);
        System.out.println(Arrays.toString(nums));
    }
}

/*
Using additional space
T - O(n + k)
S - O(k)
 */
class Solution {
    public void rotateArray(int[] nums, int k) {
        // Rotations are limited to nums.length. It'll repeat afterward.
        k = k % nums.length;
        if (k == 0) {
            return;
        }

        int offset = nums.length - k;

        int[] temp = new int[k];
        for (int i = 0; i < k; i++) {
            temp[i] = nums[i];
        }

        for (int i = 0; i < nums.length - k; i++) {
            nums[i] = nums[i + k];
        }

        for (int i = 0; i < k; i++) {
            nums[i + offset] = temp[i];
        }
    }
}

/*
Reversal Algorithm for Array Rotation
T - O(n)
S - O(1)
 */
class Solution2 {
    /*
    1 2 3 4 5 6 & 3 -> 4 5 6 1 2 3
    1 2 3 4 5 6 & 2 -> 3 4 5 6 1 2
     */
    public void rotateArray(int[] nums, int k) {
        // Rotations are limited to nums.length. It'll repeat afterward.
        k = k % nums.length;
        if (k == 0) {
            return;
        }

        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, nums.length - k - 1);
        reverse(nums, nums.length - k, nums.length - 1);
    }

    private void reverse(int[] nums, int from, int to) {
        int mid = (from + to) / 2;
        for (int i = from; i <= mid; i++) {
            int temp = nums[i];
            nums[i] = nums[to + from - i];
            nums[to + from - i] = temp;
        }
    }
}