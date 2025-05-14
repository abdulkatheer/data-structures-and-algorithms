package io.abdul.binary_search.fundamentals.problem2;

/*
Case 1: If x doesn't exist, position of first bigger element than x
Case 2: If x exists only once, x's position
Case 3: If x exists multiple times, position of first occurrence
Case 4: All are smaller than x
 */
public class LowerBound {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.lowerBound(new int[]{1, 2, 2, 3}, 2));
        System.out.println(solution.lowerBound(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, 2));
        System.out.println(solution.lowerBound(new int[]{3, 5, 8, 15, 19}, 9));
        System.out.println(solution.lowerBound(new int[]{3, 5, 8, 15, 19}, 3));
        System.out.println(solution.lowerBound(new int[]{-49049, 12215, 14963, 74220, 91021}, 89353));
    }
}

class Solution {
    public int lowerBound(int[] nums, int x) {
        int low = 0;
        int high = nums.length - 1;
        int lowerBound = nums.length;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums[mid] >= x) {
                lowerBound = mid; // Always goes left, so new pos will always be lesser
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return lowerBound;
    }
}

class Solution2 {
    public int lowerBound(int[] nums, int x) {
        int low = 0, high = nums.length - 1;

        int answer = nums.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= x) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return answer;
    }
}
