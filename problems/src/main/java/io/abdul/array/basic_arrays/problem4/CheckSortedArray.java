package io.abdul.array.basic_arrays.problem4;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-arrays/check-if-the-array-is-sorted
public class CheckSortedArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.arraySortedOrNot(new int[]{1, 2, 3, 4, 5}, 5));
        System.out.println(solution.arraySortedOrNot(new int[]{1, 2, 3, 5, 4}, 5));
    }
}

class Solution {
    boolean arraySortedOrNot(int[] arr, int n) {
        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
