package io.abdul.problem8;

import java.util.Arrays;

public class RotateArray {
    // Time: O(m*n)
    // Space: O(1)
    private static void rotateV1(int[] nums, int k) {
        if (nums.length == 0 || nums.length == k) { // Same location even after rotation
            return;
        }

        for (int i = 0; i < k; i++) {
            int temp = nums[0];
            int nextIndex;
            for (int j = 0; j < nums.length; j++) {
                nextIndex = (j + 1) % nums.length;
                int temp1 = nums[nextIndex];
                nums[nextIndex] = temp;
                temp = temp1;
            }
            /*
            0 1 2
            1 2 3
            3 1 2
            2 3 1

            1
            temp = 1
            1 1 3 (temp = 2)
            1 1 2 (temp = 3)
            3 1 2


             */
        }
    }

    /*
    Go to a position, find swap position, move it there and keep the swap index and element in temp
    Now find where the swapped element to be moved and move it and keep swapped index and element in temp
    Repeat for nums.length times.
     */
    // Time: O(n)
    // Space: O(n) for keeping bit vector
    private static void rotateV2(int[] nums, int k) {
        if (nums.length == 0 || nums.length == k) { // Same location even after rotation
            return;
        }

        int count = 0;
        int currentIndex = 0;
        int currentElement = nums[0];
        boolean[] placedBit = new boolean[nums.length];
        while (count < nums.length) {
            int toBeMovedIndex = (currentIndex + k) % nums.length;
            if (placedBit[toBeMovedIndex]) {
                currentIndex = (currentIndex + 1) % nums.length;
                currentElement = nums[currentIndex];
                continue;
            }
            int temp = nums[toBeMovedIndex];
            nums[toBeMovedIndex] = currentElement;
            currentIndex = toBeMovedIndex;
            currentElement = temp;
            placedBit[toBeMovedIndex] = true;
            count++;
        }

        /*
        rotate = 3
        size = 10
        currentIndex = 0
        currentElement = nums[0]
        toBeSwappedIndex = currentIndex+rotate % size = 3
        temp = nums[toBeSwappedIndex]
        move currentElement to nums[toBeSwappedIndex]
        currentIndex = toBeSwappedIndex
        currentElement = temp
        count++
         */
    }

    /*
    Using some algo. TODO check and learn this algo
     */
    // Time: O(n)
    // Space: O(1)
    private static void rotateV3(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        reverse(nums, 0, n-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, n-1);
    }

    private static void reverse(int[] nums, int i, int j){
        while(i<j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
//        int[] set1 = new int[]{1, 2, 3, 4, 5, 6, 7};
//        rotateV1(set1, 7);
//        System.out.println(Arrays.toString(set1));
//
//        int[] set2 = new int[]{1, 2, 3, 4, 5, 6, 7};
//        rotateV2(set2, 3);
//        System.out.println(Arrays.toString(set2));
//
        int[] set3 = new int[]{-1, -100, 3, 99};
        rotateV1(set3, 2);
        System.out.println(Arrays.toString(set3));

        int[] set4 = new int[]{-1, -100, 3, 99};
        rotateV2(set4, 2);
        System.out.println(Arrays.toString(set4));
    }


}
