package io.abdul.sorting.problem1;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.selectionSort(new int[]{10})));
        System.out.println(Arrays.toString(solution.selectionSort(new int[]{3, 0})));
        System.out.println(Arrays.toString(solution.selectionSort(new int[]{9, 10, 89, 1, 3})));
        System.out.println(Arrays.toString(solution.selectionSort(new int[]{})));
    }
}

class Solution {
    public int[] selectionSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int smallest = i;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[smallest]) {
                    smallest = j;
                }
            }
            if (smallest != i) {
                int temp = nums[i];
                nums[i] = nums[smallest];
                nums[smallest] = temp;
            }
        }
        return nums;
    }
}
