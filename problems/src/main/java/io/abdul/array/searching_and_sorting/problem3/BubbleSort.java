package io.abdul.array.searching_and_sorting.problem3;

import java.util.Arrays;

// https://www.hackerrank.com/challenges/ctci-bubble-sort/problem
// https://www.hackerearth.com/practice/algorithms/sorting/bubble-sort/visualize/
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr1 = new int[]{6, 4, 1};
        sort(arr1);
        System.out.println(Arrays.toString(arr1));
        int[] arr2 = new int[]{2, 1};
        sort(arr2);
        System.out.println(Arrays.toString(arr2));
    }

    public static void sort(int[] nums) {
        int numOfSwaps = 0;

        if (nums.length < 2) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            int bound = nums.length - i - 1; // 1 less than total lookup, to enable checking current and next element
            // This way there's always more than 1 element and a next element always exists to compare
            for (int j = 0; j < bound; j++) {
                if (nums[j] > nums[j + 1]) { // swap if current element is greater than next element
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    numOfSwaps++;
                }
            }
        }

        System.out.println("Number of swaps=" + numOfSwaps);
    }
}
