package io.abdul.array.searching_and_sorting.problem1;

// https://leetcode.com/problems/binary-search/
public class BinarySearch {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
//        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 2));
//        System.out.println(solution.search(new int[]{}, 0));
//        System.out.println(solution.search(new int[]{1}, 2));
//        System.out.println(solution.search(new int[]{1}, 1));
//        System.out.println(solution.search(new int[]{1, 3}, 2));
//        System.out.println(solution.search(new int[]{1, 3}, 1));
//        System.out.println(solution.search(new int[]{2, 5}, 0));
        System.out.println(solution.search(new int[]{-1, 0, 3, 5, 9, 12}, 13));
    }

    static class Solution {
        public int search(int[] nums, int target) {
            // Start with i=0 and j=length-1
            // Find mid
            // If mid matches with target, return
            // Else if smaller continue with i=i and j=mid-1
            // Else continue with i=mid+1 and j=j
            // Continue until reach a single element (i and j are same)
            if (nums.length == 0) {
                return -1;
            }

            if (nums.length == 1) {
                return nums[0] == target ? 0 : -1;
            }

            int x = -1;
            int mid;
            int i = 0;
            int j = nums.length - 1;
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


