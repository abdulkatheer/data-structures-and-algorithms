package io.abdul.array.faq_hard.problem7;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/merge-two-sorted-arrays-without-extra-space
public class MergeSortedArrays {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        int[] nums1;
        int[] nums2;

        nums1 = new int[]{-5, -2, 4, 5, 0, 0, 0};
        nums2 = new int[]{-3, 1, 8};
        solution.merge(nums1, nums1.length - nums2.length, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1) + "   " + Arrays.toString(nums2));

        nums1 = new int[]{0, 2, 7, 8, 0, 0, 0};
        nums2 = new int[]{-7, -3, -1};
        solution.merge(nums1, nums1.length - nums2.length, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1) + "   " + Arrays.toString(nums2));

        nums1 = new int[]{1, 3, 5, 0, 0, 0, 0};
        nums2 = new int[]{2, 4, 6, 7};
        solution.merge(nums1, nums1.length - nums2.length, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1) + "   " + Arrays.toString(nums2));
    }
}

/*
Brute - Insert and shift elements to the right | Insertion sort
T - O(m*n)
S - O(1)
 */
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int mCount = 0;
        int left = 0;
        int right = 0;
        int k = 0;
        while (mCount < m && right < n) { // O(m+n)
            if (nums1[left] <= nums2[right]) {
                left++; // already in right place
                mCount++;
            } else {
                free(nums1, k); // O(m)
                nums1[k] = nums2[right];
                left++;
                right++;
            }
            k++;
        }

        while (right < n) {
            nums1[k] = nums2[right];
            right++;
            k++;
        }
    }

    private void free(int[] nums, int pos) {
        if (pos >= nums.length - 1) { // even last element can't be freed as no more space available
            return;
        }

        int prevElement = nums[pos];
        for (int i = pos + 1; i < nums.length; i++) {
            int temp = nums[i];
            nums[i] = prevElement;
            prevElement = temp;
        }
    }
}

/*
Optimal - Gap Method 1 (Shell sort) - Optimized insertion sort
T - O(n log_2 n) As gas reduces by half every iteration
S - O(1)
 */
class Solution2 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < nums2.length; i++) { // Copy to same array
            nums1[i + m] = nums2[i];
        }

        int gap = (nums1.length + 1) / 2;

        while (gap > 0) {
            int left = 0;
            int right = left + gap;

            while (right < nums1.length) {
                if (nums1[left] > nums1[right]) {
                    int temp = nums1[left];
                    nums1[left] = nums1[right];
                    nums1[right] = temp;
                }
                left++;
                right++;
            }

            gap = gap == 1 ? 0 : (gap + 1) / 2;
        }
    }
}

/*
Optimal - Gap Method 2 (Shell sort) - Optimized insertion sort
T - O(n log_2 n) As gas reduces by half every iteration
S - O(1)
 */
class Solution3 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < nums2.length; i++) { // Copy to same array
            nums1[i + m] = nums2[i];
        }

        for (int gap = nums1.length / 2; gap >= 1; gap = gap / 2) {
            for (int i = gap; i < nums1.length; i++) {
                while (i >= gap && nums1[i] < nums1[i - gap]) {
                    int temp = nums1[i];
                    nums1[i] = nums1[i - gap];
                    nums1[i - gap] = temp;
                    i -= gap;
                }
            }
        }
    }
}