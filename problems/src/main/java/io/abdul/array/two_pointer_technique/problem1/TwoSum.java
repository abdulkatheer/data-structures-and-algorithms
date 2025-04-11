package io.abdul.array.two_pointer_technique.problem1;

import java.util.Arrays;

public class TwoSum {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 18)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 3)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 11)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 13)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-10, -7, -2, -1, 0, 6, 67, 190, 256, 1000}, -17)));
        System.out.println(Arrays.toString(solution.twoSum(new int[]{-10, -7, -2, -1, 0, 6, 67, 190, 256, 1000}, 1256)));
    }
}

class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int match1 = -1;
        int match2 = -2;
        int i = 0;
        int j = numbers.length - 1;
        /*
        1 2 3 4 5 6 7 8 9 10, target=18
        i=0, j=9 1 10
        i=1, j=9 2 10
        i=2, j=9 3 10
        i=3, j=9 4 10
        i=5, j=9 5 10
        i=6, j=9 6 10
        i=7, j=9 7 10
        i=8, j=9 8 10
         */
        while (i < j) {
            int sum = numbers[i] + numbers[j];

            if (sum > target) { // Increase by moving j to the left
                j--;
                sum = numbers[i] + numbers[j];
            }

            if (sum < target) { // Decrease by moving i to the right
                i++;
                sum = numbers[i] + numbers[j];
            }

            if (sum == target) {
                match1 = i;
                match2 = j;
                break;
            }
        }
        return new int[]{match1 + 1, match2 + 1};
    }
}
