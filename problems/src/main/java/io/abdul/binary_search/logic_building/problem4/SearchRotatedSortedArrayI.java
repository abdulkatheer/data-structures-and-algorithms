package io.abdul.binary_search.logic_building.problem4;

public class SearchRotatedSortedArrayI {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 5));
        System.out.println(solution.search(new int[]{2, 1}, 1));
    }
}

/*
Optimal - Modified Binary Search
T - O(log n)
S - O(1)

Idea here is, after splitting, at least one half would be sorted. So we try to check if target lies in that half or not and skip if not.
 */
class Solution {
    public int search(int[] nums, int k) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (k == nums[mid]) {
                return mid;
            }

            if (nums[low] <= nums[mid]) { // left half is sorted
                if (k >= nums[low] && k <= nums[mid]) { // target lies in left half
                    high = mid - 1;
                } else { // target lies in right half
                    low = mid + 1;
                }
            } else { // Right half must be sorted
                if (k >= nums[mid] && k <= nums[high]) { // target lies in right half
                    low = mid + 1;
                } else {
                    high = mid - 1; // target lies in left half
                }
            }
        }

        return -1;
    }
}
