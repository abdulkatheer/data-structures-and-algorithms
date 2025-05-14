package io.abdul.binary_search.logic_building.problem5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SearchRotatedSortedArrayII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.searchInARotatedSortedArrayII(new ArrayList<>(Arrays.asList(7, 8, 1, 2, 3, 3, 3, 4, 5, 6)), 3));
        System.out.println(solution.searchInARotatedSortedArrayII(new ArrayList<>(Arrays.asList(7, 8, 1, 2, 3, 3, 3, 4, 5, 6)), 10));
        System.out.println(solution.searchInARotatedSortedArrayII(new ArrayList<>(Arrays.asList(7, 8, 1, 2, 3, 3, 3, 4, 5, 6)), 7));
    }
}

/*
Optimal - Modified Binary Search

Problem with duplicates is, when low,mid and high has same number, it's impossible to find out the sorted part of the array to go further.
So when we meet that condition, we shrink the search space to get out of the condition.
We check if that's the target or not beforehand.
 */
class Solution {
    public boolean searchInARotatedSortedArrayII(ArrayList<Integer> nums, int k) {
        int low = 0, high = nums.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums.get(mid) == k) {
                return true;
            }

            if (Objects.equals(nums.get(low), nums.get(mid)) && Objects.equals(nums.get(mid), nums.get(high))) {
                // Will occur if we reach single element or 3 different elements with same value
                low++;
                high--;
                continue; // repeat until we escape this condition
            }

            if (nums.get(low) <= nums.get(mid)) { // left part is sorted
                if (k >= nums.get(low) && k <= nums.get(mid)) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else { // right part is sorted
                if (k >= nums.get(mid) && k <= nums.get(high)) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return false;
    }
}