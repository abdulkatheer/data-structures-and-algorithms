package io.abdul.binary_search.logic_building.problem6;

import java.util.ArrayList;
import java.util.Arrays;

public class FindMinimumRotatedSortedArray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findMin(new ArrayList<>(Arrays.asList(4, 5, 6, 7, 0, 1, 2, 3))));
        System.out.println(solution.findMin(new ArrayList<>(Arrays.asList(3, 4, 5, 1, 2))));
        System.out.println(solution.findMin(new ArrayList<>(Arrays.asList(4, 5, 6, 7, -7, 1, 2, 3))));
        System.out.println(solution.findMin(new ArrayList<>(Arrays.asList(90, -87, -78, -65, -49, -29, -28, -23, -2, 7, 12, 14, 24, 40, 46, 55, 76, 77, 80, 83))));
    }
}

/*
Optimal - Modified Binary Search
T - O(log n)
S - O(1)

Idea is that, at any split, at least one part of the array is sorted. If it's sorted, nums[low] could be the candidate for min.
For other part, we need to repeat this process until we get to single element
 */
class Solution {
    public int findMin(ArrayList<Integer> nums) {
        int low = 0, high = nums.size() - 1, min = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums.get(low) <= nums.get(mid)) { // Left is sorted
                min = Math.min(min, nums.get(low));
                low = mid + 1;
            } else { // Right is sorted
                min = Math.min(min, nums.get(mid));
                high = mid - 1;
            }
        }

        return min;
    }
}

