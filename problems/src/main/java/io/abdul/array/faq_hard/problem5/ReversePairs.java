package io.abdul.array.faq_hard.problem5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/reverse-pairs
public class ReversePairs {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        System.out.println(solution.reversePairs(new int[]{6, 4, 1, 2, 7}));
        System.out.println(solution.reversePairs(new int[]{5, 4, 4, 3, 3}));
        System.out.println(solution.reversePairs(new int[]{6, 4, 4, 2, 2}));
    }
}

/*
Brute - Look all possible pairs
T - O(n^2)
S - O(1)
 */
class Solution {
    public int reversePairs(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }

        int reversePairs = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > 2 * nums[j]) {
                    reversePairs++;
                }
            }
        }

        return reversePairs;
    }
}

/*
Optimal - Modified merge sort (Recursion)
T - O(n logn) for merge sort
S - O(n) for merge sort
 */
class Solution2 {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return 0;
        }

        int count = 0;
        int mid = (start + end) / 2;
        count += mergeSort(nums, start, mid); // reverse pairs found while sorting/merging first half
        count += mergeSort(nums, mid + 1, end); // reverse pairs found while sorting/merging first half
        count += countPairs(nums, start, mid, end); // reverse pairs found while sorting/merging entire array
        merge(nums, start, mid, end);
        return count;
    }

    private void merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];

        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (nums[i] <= nums[j]) {
                temp[k] = nums[i];
                i++;
            } else {
                temp[k] = nums[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = nums[i];
            i++;
            k++;
        }

        while (j <= end) {
            temp[k] = nums[j];
            j++;
            k++;
        }

        System.arraycopy(temp, 0, nums, start, temp.length);
    }

    private int countPairs(int[] nums, int start, int mid, int end) { // two sorted arrays
        int totalPairs = 0;
        int j = mid + 1;

        for (int i = start; i <= mid; i++) {
            while (j <= end && nums[i] > 2 * nums[j]) {
                j++;
            }
            totalPairs = totalPairs + (j - (mid + 1));
        }
        return totalPairs;
    }
}