package io.abdul.array.greedy_or_optimization.problem4;

import java.util.Arrays;

// TODO solve it
// https://www.geeksforgeeks.org/problems/minimize-the-heights3351/1
public class MinimizeHeights {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.getMinDiff(new int[]{1, 5, 8, 10}, 2));
        System.out.println(solution.getMinDiff(new int[]{3, 9, 12, 16, 20}, 3));
        System.out.println(solution.getMinDiff(new int[]{1}, 10));
        System.out.println(solution.getMinDiff(new int[]{5, 7}, 1));
        System.out.println(solution.getMinDiff(new int[]{1, 8, 10, 6, 4, 6, 9, 1}, 7));
    }
}

class Solution {
    int getMinDiff(int[] arr, int k) {
        /*
        Assume the array is sorted
        Can have duplicates
        Can have only positives. Zeroes?
        Cannot have negatives after +/- k

        So to reduce the heights, the distance between smallest and largest element should shrink
        So we try to increase the smallest element's height and reduce the largest element's height
        In the process, when smallest element is hiked, it may not be the smallest element anymore.
        So we move right to find its still smallest, if not change the smallest pointer towards right. Stop when meeting first bigger element.
        Same case with the largest element. Move pointer to right and stop when you meet the smaller element.

        We're greedy here!
        First of all, this problem has an optimal substructure.
         */

        int n = arr.length;
        if (n == 1) {
            return 0;
        }

        Arrays.sort(arr);
        // 1 1 4 6 6 8 9 10, 7
        /*
        Take two elements every time. Apply +k and -k
        After applying find the min and max compared to smallest and largest element
        After minimizing
         */

//        int smallest = arr[]
        return -1;
    }
}