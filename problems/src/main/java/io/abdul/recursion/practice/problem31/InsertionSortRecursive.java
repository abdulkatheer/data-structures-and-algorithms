package io.abdul.recursion.practice.problem31;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InsertionSortRecursive {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: Empty array
        int[] nums1 = {};
        solution.sort(nums1);
        assertArrayEquals(new int[]{}, nums1, "Empty array should remain empty");

        // Test Case 2: Single element array
        int[] nums2 = {5};
        solution.sort(nums2);
        assertArrayEquals(new int[]{5}, nums2, "Single element array should remain unchanged");

        // Test Case 3: Already sorted array
        int[] nums3 = {1, 2, 3, 4, 5};
        solution.sort(nums3);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums3, "Already sorted array should remain unchanged");

        // Test Case 4: Reverse sorted array
        int[] nums4 = {5, 4, 3, 2, 1};
        solution.sort(nums4);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums4, "Reverse sorted array should be sorted in ascending order");

        // Test Case 5: Unsorted array
        int[] nums5 = {3, 1, 4, 1, 5, 9, 2, 6};
        solution.sort(nums5);
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 5, 6, 9}, nums5, "Unsorted array should be sorted in ascending order");

        // Test Case 6: Array with duplicates
        int[] nums6 = {4, 2, 4, 3, 1, 2};
        solution.sort(nums6);
        assertArrayEquals(new int[]{1, 2, 2, 3, 4, 4}, nums6, "Array with duplicates should be sorted correctly");

        // Test Case 7: Large array
        int[] nums7 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        solution.sort(nums7);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, nums7, "Large array should be sorted in ascending order");

    }
}

/*
Recurrence => T(n) = 1 where n = 0 or n =1; T(n-1) + n where n > 1
T - O(n^2)
S - O(n) - Stack size, recursion happens for all elements of the array
 */
class Solution {
    public void sort(int[] nums) {
        if (nums.length == 0) {
            return;
        }
        sortInternal(nums, nums.length - 1);
    }

    private void sortInternal(int[] nums, int pos) {
        if (pos == 0) { // last single element is sorted
            return;
        }
        sortInternal(nums, pos - 1);
        insert(nums, pos);
    }

    /*
    Insert element at pos in array 0 to pos-1

    1,2,4,5,6,3,7,8,9,10 5
    1 - 1 2 4 5 6 3 7 8 9 10, pos=5
    2 - 1 2 4 5 3 6 7 8 9 10, pos=4
    2 - 1 2 4 3 5 6 7 8 9 10, pos=3
    2 - 1 2 3 4 5 6 7 8 9 10, pos=3
     */
    private void insert(int[] nums, int pos) {
        while (pos > 0 && nums[pos] < nums[pos - 1]) {
            int temp = nums[pos - 1];
            nums[pos - 1] = nums[pos];
            nums[pos] = temp;
            pos--;
        }
    }
}
