package io.abdul.array.basic_traversal_and_manipulation.problem2;

import java.util.Arrays;

// https://www.hackerrank.com/challenges/arrays-ds/problem
public class ReverseArray {
    // Time - O(n)
    // Space - O(n)
    public static int[] immutableReverseArray(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            result[j] = arr[i];
        }
        return result;
    }

    // Time - O(n)
    // Space - O(1)
    public static void reverseInPlace(int[] arr) {
        if (arr.length < 2) {
            return;
        }
        int mid = arr.length / 2;
        int temp;
        for (int i = 0, j = arr.length - 1; i < mid; i++, j--) {
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{})));
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{1})));
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{1, 2})));
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{1, 0, 3, 2})));
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{4, 3, 2, 1})));
        System.out.println(Arrays.toString(immutableReverseArray(new int[]{5, 4, 3, 2, 1})));

        int[] arr1 = {};
        reverseInPlace(arr1);
        System.out.println(Arrays.toString(arr1));
        int[] arr2 = {1};
        reverseInPlace(arr2);
        System.out.println(Arrays.toString(arr2));
        int[] arr3 = {1, 2};
        reverseInPlace(arr3);
        System.out.println(Arrays.toString(arr3));
        int[] arr4 = {1, 0, 3, 2};
        reverseInPlace(arr4);
        System.out.println(Arrays.toString(arr4));
        int[] arr5 = {4, 3, 2, 1};
        reverseInPlace(arr5);
        System.out.println(Arrays.toString(arr5));
        int[] arr6 = {5, 4, 3, 2, 1};
        reverseInPlace(arr6);
        System.out.println(Arrays.toString(arr6));
    }
}
