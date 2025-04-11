package io.abdul.array.basic_arrays.problem3;

import java.util.Arrays;

public class ReverseArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] input;

        input = new int[]{1};
        solution.reverse(input,1);
        System.out.println(Arrays.toString(input));

        input = new int[]{1, 2};
        solution.reverse(input,2);
        System.out.println(Arrays.toString(input));

        input = new int[]{1, 2, 3};
        solution.reverse(input,3);
        System.out.println(Arrays.toString(input));
    }
}

class Solution {
    public void reverse(int[] arr, int n) {
        int mid = n / 2;
        for (int i = 0; i < mid; i++) {
            int temp = arr[n - i - 1];
            arr[n - i - 1] = arr[i];
            arr[i] = temp;
        }
    }
}
