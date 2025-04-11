package io.abdul.array.basic_traversal_and_manipulation.problem3;

import java.util.Arrays;

public class RotateArray {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
//        int[] arr1 = {1, 2};
//        solution.rotate(arr1, 1);
//        System.out.println(Arrays.toString(arr1));
//        solution.rotate(arr1, 2);
//        System.out.println(Arrays.toString(arr1));
//        solution.rotate(arr1, 3);
//        System.out.println(Arrays.toString(arr1));
//        solution.rotate(arr1, 4);
//        System.out.println(Arrays.toString(arr1));
        int[] arr2 = new int[]{1, 2, 3, 4, 5, 6};
        solution.rotate(arr2, 2);
        System.out.println(Arrays.toString(arr2));
        // 1 2 3 4 5
        // 5 1 2 3 4
        // 4 5 1 2 3
        // 3 4 5 1 2


    }
}

class Solution {
    // Time
    // 1, 2, 3, 4, 5
    // k can be 1 to 4
    // k = 4
    //
    public void rotate(int[] nums, int k) {
        if (nums.length < 2) {
            return;
        }
        // If array of length 2 is rotated 2 times, it'll reach same state.
        // So any rotation can be simplified to 1 to length-1 rotations
        // So k should be between 1 and

        k = k % nums.length;
        if (k == 0) { // No rotations needed
            return;
        }

        /*
        1 2 3 4 5 6 7 8 9 10
        10 1 2 3 4 5 6 7 8 9
        9 10 1 2 3 4 5 6 7 8
        8 9 10 1 2 3 4 5 6 7
        7 8 9 10 1 2 3 4 5 6
        6 7 8 9 10 1 2 3 4 5
         */
        int mid = nums.length / 2;
        if (k % 2 == 0 && k == mid) { // even and exact half number of rotations, swap the elements
            for (int i = 0, j = mid; i < mid; i++, j++) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
            return;
        }

        // Step 1 - Start with 1st element, find its position by adding k. If it goes beyond index, minus by length, so that it fits in index
        // Step 2 - Copy it to the find location. Update the position and value in the temp var and next iteration
        // Step 3 - Repeat Step 1 to 2 until count equals length, meaning rotated all elements
        int count = 0;

        boolean[] visited = new boolean[nums.length];

        /*
        1 2 3 4 5 6
        0 0 0 0 0 0
        i=0, v=1

        1 2 1 4 5 6
        1 0 0 0 0 0
        i=2, v=3

        1 2 1 4 3 6
        1 0 1 0 0 0
        i=4, v=5

        5 2 1 4 3 6
        1 0 1 0 1 0
        i=0, v=1 -> already visited
        --

        5 2 1 4 3 6
        1 0 1 0 1 0
        i=1, v=2

        5 2 1 2 3 6
        1 1 1 0 1 0
        i=3, v=4

        5 2 1 2 3 4
        1 1 1 1 1 0
        i=5, v=6

        5 6 1 2 3 4
        1 1 1 1 1 1
        i=1, v=2 -> Already visited
         */
        for (int v = 0; v < visited.length; v++) {
            if (visited[v]) {
                continue;
            }
            int currentIndex = v;
            int currentValue = nums[v];
            while (count != nums.length) { // Cyclic swapping
                if (visited[currentIndex]) {
                    break;
                }
                int nextPosition = currentIndex + k;
                if (nextPosition >= nums.length) {
                    nextPosition = nextPosition - nums.length;
                }
                int nextValue = nums[nextPosition];
                nums[nextPosition] = currentValue;
                visited[currentIndex] = true;

                currentIndex = nextPosition;
                currentValue = nextValue;
                count++;
            }
        }

    }
}

// #tag_recursion
class Solution2 {
    // Time
    // 1, 2, 3, 4, 5
    // k can be 1 to 4
    // k = 4
    //
    public void rotate(int[] nums, int k) {
        if (nums.length < 2) {
            return;
        }
        // If array of length 2 is rotated 2 times, it'll reach same state.
        // So any rotation can be simplified to 1 to length-1 rotations
        // So k should be between 1 and

        k = k % nums.length;
        if (k == 0) { // No rotations needed
            return;
        }

        /*
        1 2 3 4 5 6 and k=2, out= 5 6 1 2 3 4
        6 5 4 3 2 1, reversing whole array puts k elements and other elements in their place but in reverse order
        6 5 and 4 3 2 1 are in reverse order
        Now reverse them
        5 6 4 3 2 1, reverse 0 to k-1
        5 6 1 2 3 4, reverse k to n-1
         */
        reverse(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
        reverse(nums, 0, k - 1);
        System.out.println(Arrays.toString(nums));
        reverse(nums, k, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    private static void reverse(int[] nums, int start, int end) {
        int mid = start + ((end - start) / 2);
        for (int i = start, j = end; i <= mid; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
