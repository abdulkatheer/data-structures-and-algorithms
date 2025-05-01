package io.abdul.array.faq_medium.problem12;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/sort-an-array-of-0's-1's-and-2's
public class SortZeroOneTwos {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        int[] nums = new int[]{1, 0, 2, 1, 0};
        solution.sortZeroOneTwo(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{0, 0, 1, 1, 1};
        solution.sortZeroOneTwo(nums);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{1, 1, 2, 2, 1};
        solution.sortZeroOneTwo(nums);
        System.out.println(Arrays.toString(nums));
    }
}

/*
Brute - Sorting algorithm
T - O(n logn)
S - O(1)
 */
class Solution {
    public void sortZeroOneTwo(int[] nums) {
        Arrays.sort(nums);
    }
}

/*
Better - Count and populate
 */
class Solution2 {
    public void sortZeroOneTwo(int[] nums) {
        int numOfZeros = 0, numberOfOnes = 0, numberOfTwos = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                numOfZeros++;
            } else if (nums[i] == 1) {
                numberOfOnes++;
            } else {
                numberOfTwos++;
            }
        }

        int i = 0;
        while (numOfZeros > 0) {
            nums[i] = 0;
            i++;
            numOfZeros--;
        }

        while (numberOfOnes > 0) {
            nums[i] = 1;
            i++;
            numberOfOnes--;
        }

        while (numberOfTwos > 0) {
            nums[i] = 2;
            i++;
            numberOfTwos--;
        }
    }
}