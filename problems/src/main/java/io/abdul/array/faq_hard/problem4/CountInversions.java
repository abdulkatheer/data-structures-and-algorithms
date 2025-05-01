package io.abdul.array.faq_hard.problem4;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/count-inversions
public class CountInversions {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.numberOfInversions(new int[]{2, 3, 7, 1, 3, 5}));
        System.out.println(solution.numberOfInversions(new int[]{-10, -5, 6, 11, 15, 17}));
        System.out.println(solution.numberOfInversions(new int[]{9, 5, 4, 2}));
        System.out.println(solution.numberOfInversions(new int[]{}));
        System.out.println(solution.numberOfInversions(new int[]{}));
    }
}

/*
Brute
T - O(n^2)
S - O(1)
 */
class Solution {
    public long numberOfInversions(int[] nums) {
        long numberOfInversions = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] < nums[j]) {
                    numberOfInversions++;
                }
            }
        }

        return numberOfInversions;
    }
}

/*
Optimal - Modified merge sort approach (Recursion)
T - O(n logn) for merge sort
S - O(n) for merge sort
 */
class Solution2 {
    public long numberOfInversions(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {
            return 0;
        }

        int inversions = 0;
        int mid = start + (end - start) / 2;
        inversions += mergeSort(nums, start, mid); // Inversions to sort left
        inversions += mergeSort(nums, mid + 1, end); // Inversions to sort right
        inversions += merge(nums, start, mid, end); // Inversion to merge both
        return inversions;
    }

    private int merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int inversions = 0;

        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (nums[i] <= nums[j]) {
                temp[k] = nums[i];
                i++;
            } else {
                temp[k] = nums[j];
                j++;
                inversions = inversions + (mid - i + 1);
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = nums[i];
            i++;
            k++;
        }

        while (j <= end) {
            temp[k] = nums[j];
            j++;
            k++;
        }

        System.arraycopy(temp, 0, nums, start, temp.length);

        return inversions;
    }
}