package io.abdul.sorting.problem4;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.mergeSort(new int[]{10})));
        System.out.println(Arrays.toString(solution.mergeSort(new int[]{3, 0})));
        System.out.println(Arrays.toString(solution.mergeSort(new int[]{9, 10, 89, 1, 3})));
        System.out.println(Arrays.toString(solution.mergeSort(new int[]{})));
    }
}

class Solution {
    public int[] mergeSort(int[] nums) {
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void mergeSort(int[] nums, int start, int end) {
        if (start >= end) { // Single element
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(nums, start, mid);
        mergeSort(nums, mid + 1, end);
        merge(nums, start, mid, end);
    }

    private void merge(int[] nums, int start, int mid, int end) {
        int[] toBeMerged = Arrays.copyOfRange(nums, start, end + 1);

        int firstArrayPos = start;
        int secondArrayPos = mid + 1;
        int k = start;

        while (firstArrayPos <= mid && secondArrayPos <= end) {
            if (toBeMerged[firstArrayPos - start] < toBeMerged[secondArrayPos - start]) {
                nums[k] = toBeMerged[firstArrayPos - start];
                firstArrayPos++;
            } else {
                nums[k] = toBeMerged[secondArrayPos - start];
                secondArrayPos++;
            }
            k++;
        }

        while (firstArrayPos <= mid) {// scanned second array fully, so just scan first array
            nums[k] = toBeMerged[firstArrayPos - start];
            firstArrayPos++;
            k++;
        }

        while (secondArrayPos <= end) {// scanned first array fully, so just scan second array
            nums[k] = toBeMerged[secondArrayPos - start];
            secondArrayPos++;
            k++;
        }
    }
}
