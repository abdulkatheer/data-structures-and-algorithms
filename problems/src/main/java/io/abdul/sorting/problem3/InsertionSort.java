package io.abdul.sorting.problem3;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.insertionSort(new int[]{10})));
        System.out.println(Arrays.toString(solution.insertionSort(new int[]{3, 0})));
        System.out.println(Arrays.toString(solution.insertionSort(new int[]{9, 10, 89, 1, 3})));
        System.out.println(Arrays.toString(solution.insertionSort(new int[]{})));
    }
}

class Solution {
    public int[] insertionSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int j = i;
            while (j > 0 && nums[j] < nums[j - 1]) {
                int temp = nums[j];
                nums[j] = nums[j - 1];
                nums[j - 1] = temp;
                j--;
            }
        }
        return nums;
    }
}
