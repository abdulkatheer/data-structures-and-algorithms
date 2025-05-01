package io.abdul.sorting.problem6;

import java.util.Arrays;

/*
Optimization to Insertion Sort
 */
public class ShellSort {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        int[] nums;

        nums = new int[]{23, 29, 15, 19, 31, 7, 9, 5, 2};
        solution.shellSort(nums);
        System.out.println(Arrays.toString(nums));

//        InsertionSortSolution insertionSortSolution = new InsertionSortSolution();
//        nums = new int[]{23, 29, 15, 19, 31, 7, 9, 5, 2};
//        insertionSortSolution.insertionSort(nums);
//        System.out.println(Arrays.toString(nums));
    }
}

// Not similar to Insertion sort
class Solution {
    public void shellSort(int[] nums) {
        int gap = (nums.length + 1) / 2; // +1 for ceil
        while (gap > 0) {
            int i = 0;
            int j = gap;

            while (j < nums.length) {
                if (nums[i] > nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
                i++;
                j++;
            }

            System.out.println(Arrays.toString(nums));
            if (gap == 1) {
                break;
            }
            gap = (gap + 1) / 2;
        }
    }
}

class InsertionSortSolution {
    public void insertionSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int j = i;
            while (j > 0 && nums[j] < nums[j - 1]) { // Swap until nums[i] sits in correct position to its left side array
                int temp = nums[j];
                nums[j] = nums[j - 1];
                nums[j - 1] = temp;
                j--;
            }
        }
    }
}

/* Shell sort similar to Insertion sort

Inserting elements at the gap using Insertion sort and reduce gap every iteration till it reaches 1

23, 29, 15, 19, 31, 7, 9, 5, 2

Gap=4 | 23, 29, 15, 19, 31, 7, 9, 5, 2
Itr 1
31,23 checked
Itr 2
7,29 checked and swapped
23, 7, 15, 19, 31, 29, 9, 5, 2
Itr 3
9,15 checked and swapped
23, 7, 9, 19, 31, 29, 15, 5, 2
Itr 4
5,19 checked and swapped
23, 7, 9, 5, 31, 29, 15, 19, 2
Itr 5
2,31 checked and swapped
23, 7, 9, 5, 2, 29, 15, 19, 31
2,23 checked and swapped
2, 7, 9, 5, 23, 29, 15, 19, 31
End

Gap=2 | 2, 7, 9, 5, 23, 29, 15, 19, 31
Itr 1
9,2 checked
Itr 2
5,7 checked and swapped
2, 5, 9, 7, 23, 29, 15, 19, 31
Itr 3
23, 9 checked
9, 2 checked
Itr 4
29, 7 checked
7, 5 checked
Itr 5
15, 23 checked and swapped
2, 5, 9, 7, 15, 29, 23, 19, 31
15, 9 checked
9, 2 checked
Itr 6
19, 29 checked and swapped
2, 5, 9, 7, 15, 19, 23, 29, 31
19, 7 checked
7, 5 checked
Itr 7
31, 23 checked
23, 15 checked
15, 9 checked
9, 2 checked

Gap = 1 | 2, 5, 9, 7, 15, 19, 23, 29, 31 (Actual Insertion sort happens here, observe not more than 1 swap done in any iteration)
Itr 1
5,2 checked
Itr 2
9,5 checked
5,2 checked
Itr 3
7,9 checked and swapped
2, 5, 7, 9, 15, 19, 23, 29, 31
7, 5 checked
5, 2 checked
Itr 4
15, 9 checked
9, 7 checked
7, 5 checked
5, 2 checked
Itr 5
19, 15 checked
15, 9 checked
9, 7 checked
7, 5 checked
5, 2 checked
Itr 6
23, 19 checked
19, 15 checked
15, 9 checked
9, 7 checked
7, 5 checked
5, 2 checked
Itr 7
29, 23 checked
23, 19 checked
19, 15 checked
15, 9 checked
9, 7 checked
7, 5 checked
5, 2 checked
Itr 8
31, 29 checked
29, 23 checked
23, 19 checked
19, 15 checked
15, 9 checked
9, 7 checked
7, 5 checked
5, 2 checked
 */
class Solution2 {
    public void shellSort(int[] nums) {
        for (int gap = nums.length / 2; gap >= 1; gap = gap / 2) { // for an array of size 8, gaps are 4,2,1
            for (int i = gap; i < nums.length; i++) {
                int j = i;
                while (j >= gap && nums[j] < nums[j - gap]) {
                    int temp = nums[j];
                    nums[j] = nums[j - gap];
                    nums[j - gap] = temp;
                    j -= gap;
                }
            }
            System.out.println(Arrays.toString(nums));
        }
    }
}
