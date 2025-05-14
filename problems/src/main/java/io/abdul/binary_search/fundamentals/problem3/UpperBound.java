package io.abdul.binary_search.fundamentals.problem3;

/*
Case 1: at least 1 bigger element exists
Case 2: All are smaller than x
 */
public class UpperBound {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.upperBound(new int[]{1, 2, 2, 3}, 2));
        System.out.println(solution.upperBound(new int[]{3, 5, 8, 15, 19}, 9));
        System.out.println(solution.upperBound(new int[]{3, 5, 8, 15, 19}, 3));
    }
}

/*
[1,2,2,3], x = 2
 */
class Solution {
    public int upperBound(int[] nums, int x) {
        int low = 0;
        int high = nums.length - 1;
        int upperBound = nums.length;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums[mid] > x) {
                upperBound = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return upperBound;
    }
}
