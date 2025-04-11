package io.abdul.array.searching_and_sorting.problem2;

// https://leetcode.com/problems/search-in-rotated-sorted-array/
public class RotatedArraySearch {
    /*
    6 7 8 9 10 11 12 1 2 3 4 5
    6 7 8 9 10 11 0  1 2 3 4 5
    Find the pivot where split had happened using modified Binary Search
    Funda: If an element is smaller than last most element, then the pivot should be before him. If its higher pivot should be after him
    Elements are distinct, so we don't need to worry about equals case
    At the end, we'll either find out the largest element in the array or the smallest element in the array
    Once we meet the last element, compare with last element and decide whether its smallest or largest

    Now based on the pivot element and position,
    initial mid will be out pivot
    mid = length-1/2 + pivot % length

    Find mid.
    If mid is greater than the last element
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
//        System.out.println(solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
//        System.out.println(solution.search(new int[]{1}, 0));
//        System.out.println(solution.search(new int[]{1, 2, 3, 4, 5, 6}, 7)); // rotated xn times, n is length of the array
//        System.out.println(solution.search(new int[]{1, 2, 3, 4, 5, 6}, 6));
        System.out.println(solution.search(new int[]{7, 1, 2, 3, 4, 5, 6}, 6)); //
//        System.out.println(solution.search(new int[]{7, 1, 2, 3, 4, 5, 6}, 7));
        System.out.println(solution.search(new int[]{7, 1, 2, 3, 4, 5, 6}, 1)); //
        System.out.println(solution.search(new int[]{7, 1, 2, 3, 4, 5, 6}, -1));
    }

    static class Solution {
        public int search(int[] nums, int target) {
            if (nums.length == 0) {
                return -1;
            }

            if (nums.length == 1) {
                return nums[0] == target ? 0 : -1;
            }

            // Find pivot
            int i = 0;
            int j = nums.length - 1;
            int pivot = 0;
            int mid = -1;
            while (i < j) { // Modified binary search to find pivot (either smallest or largest)
                mid = i + ((j - i) / 2);
                if (nums[mid] > nums[nums.length - 1]) { // Pivot might be on the right side
                    i = mid + 1;
                } else { // Pivot might be on the left side
                    j = mid - 1;
                }
            }

            // Now i will be either smallest or largest
            // Splitting the array by pivot for searching the target
            int x1;
            int y1;
            int x2;
            int y2;
            if (nums[i] > nums[nums.length - 1]) { // largest
                if (target > nums[i]) { // No elements can be larger than largest
                    return -1;
                } else {
                    // First lookup
                    x1 = 0;
                    y1 = i;
                    // Second lookup
                    x2 = i + 1;
                    y2 = nums.length - 1;
                }
            } else { // smallest
                if (target < nums[i]) { // No elements can be smaller than largest
                    return -1;
                } else {
                    x1 = i;
                    y1 = nums.length - 1;
                    x2 = 0;
                    y2 = i - 1;
                }
            }

            int result = bSearch(nums, target, x1, y1);
            if (result == -1) {
                result = bSearch(nums, target, x2, y2);
            }

            return result;
        }

        public int bSearch(int[] nums, int target, int start, int end) {
            // Start with i=0 and j=length-1
            // Find mid
            // If mid matches with target, return
            // Else if smaller continue with i=i and j=mid-1
            // Else continue with i=mid+1 and j=j
            // Continue until reach a single element (i and j are same)
//
//            if (end - start == 0) { // single element
//                return nums[start] == target ? start : -1;
//            }

            int x = -1;
            int mid;
            int i = start;
            int j = end;
            while (i < j) { // Stop when reaching the last element in the search
                mid = i + ((j - i) / 2);
                if (nums[mid] == target) {
                    x = mid;
                    break;
                } else if (nums[mid] < target) {
                    i = mid + 1;
                } else {
                    j = mid - 1;
                }
            }

            // Match with the last element if not found already
            if (x == -1) { // if not found until reaching a single element
                if (nums[i] == target) { // trying to match the last element
                    x = i;
                }
            }

            return x;
        }
    }
}

