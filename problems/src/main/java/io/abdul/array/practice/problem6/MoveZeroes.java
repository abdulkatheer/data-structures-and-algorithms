package io.abdul.array.practice.problem6;

import java.util.Arrays;

// #tag_array
public class MoveZeroes {
    // Using additional space
    // O(n)
    private static void moveZeroesV1(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return;
        }

        int[] result = Arrays.copyOf(nums, nums.length);

        int currentIndexAtResult = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] != 0) {
                nums[currentIndexAtResult] = result[i];
                currentIndexAtResult++;
            }
        }

        for (int i = currentIndexAtResult; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    // O(n)
    /*
    1. Two cursors, both starting at index 0
    2. Cursor 1 keeps track of next position to insert the element
    3. Cursor 2 keeps track of current index in the iteration
    4. If element at cursor 2 is non-zero, put at cursor 1 position and increment it
    5. If element at cursor 2 is zero, don't care
    6. Finally, fill remaining spaces with zero
     */
    private static void moveZeroesV2(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return;
        }

        int currentIndexAtResult = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[currentIndexAtResult] = nums[i];
                currentIndexAtResult++;
            }
        }

        for (int i = currentIndexAtResult; i < nums.length; i++) {
            nums[i] = 0;
        }
    }


    public static void main(String[] args) {
        int[] set1 = new int[]{0, 1, 0, 3, 12};
        moveZeroesV1(set1);
        System.out.println(Arrays.toString(set1));

        int[] set2 = new int[]{0};
        moveZeroesV1(set2);
        System.out.println(Arrays.toString(set2));

        int[] set3 = new int[]{0, 1, 0, 3, 12};
        moveZeroesV2(set3);
        System.out.println(Arrays.toString(set1));

        int[] set4 = new int[]{0};
        moveZeroesV2(set4);
        System.out.println(Arrays.toString(set2));
    }
}
