package io.abdul.array.logic_building.problem1;

import java.util.Arrays;

public class MoveZerosToEnd {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums = new int[]{0, 1, 4, 0, 5, 2};
//        solution.moveZeroes(nums);
//        System.out.println(Arrays.toString(nums));
//
//        nums = new int[]{0, 0, 0, 1, 3, -2};
//        solution.moveZeroes(nums);
//        System.out.println(Arrays.toString(nums));
//
//        nums = new int[]{0, 20, 0, -20, 0, 20};
//        solution.moveZeroes(nums);
//        System.out.println(Arrays.toString(nums));

        nums = new int[]{9, 2, 9, 7, 1, 8, 7, 7, 8, 9, 3, 7, 4, 6, 5, 3, 8, 8, 0, 2, 2, 1, 5, 8, 5, 0, 6, 8, 5, 5, 5, 4, 6, 4, 1, 1, 0, 4, 0, 0, 2, 2, 3, 8, 2, 7, 1, 3};
        solution.moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }
}

class Solution {
    public void moveZeroes(int[] nums) {
        boolean moved = false;

        int zeroPos = -1, onePos = -1;
        while (!moved) {
            zeroPos = findZero(nums, zeroPos + 1);
            onePos = findNonZero(nums, zeroPos + 1);

            if (zeroPos == -1 || onePos == -1) { // No zeroes or ones found to be moved
                moved = true;
                continue;
            }

            int temp = nums[zeroPos];
            nums[zeroPos] = nums[onePos];
            nums[onePos] = temp;
        }
    }

    private int findZero(int[] nums, int fromPos) {
        for (int i = fromPos; i < nums.length; i++) {
            if (nums[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private int findNonZero(int[] nums, int fromPos) {
        for (int i = fromPos; i < nums.length; i++) {
            if (nums[i] != 0) {
                return i;
            }
        }
        return -1;
    }
}