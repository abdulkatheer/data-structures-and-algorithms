package io.abdul.sorting.problem2;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(Arrays.toString(solution.bubbleSort(new int[]{10})));
        System.out.println(Arrays.toString(solution.bubbleSort(new int[]{3, 0})));
        System.out.println(Arrays.toString(solution.bubbleSort(new int[]{9, 10, 89, 1, 3})));
        System.out.println(Arrays.toString(solution.bubbleSort(new int[]{})));
    }
}

class Solution {
    public int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums;
    }
}

class Solution2 {
    public int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) { // If no swaps happened in an iteration, that means all are already in sorted order, so no further iterations needed
                break;
            }
        }
        return nums;
    }
}