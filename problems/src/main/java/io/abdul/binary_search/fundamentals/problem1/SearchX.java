package io.abdul.binary_search.fundamentals.problem1;

public class SearchX {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, -1));
    }
}

class Solution {
    public int search(int[] nums, int target) {
        return binarySearch(target, nums, 0, nums.length - 1);
    }

    private int binarySearch(int target, int[] nums, int start, int end) {
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;
        if (target == nums[mid]) {
            return mid;
        } else if (target < nums[mid]) {
            return binarySearch(target, nums, start, mid - 1);
        } else {
            return binarySearch(target, nums, mid + 1, end);
        }
    }
}

class Solution2 {
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }
}