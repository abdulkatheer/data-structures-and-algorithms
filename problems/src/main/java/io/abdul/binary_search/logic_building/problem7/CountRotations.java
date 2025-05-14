package io.abdul.binary_search.logic_building.problem7;

import java.util.ArrayList;
import java.util.Arrays;

public class CountRotations {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findKRotation(new ArrayList<>(Arrays.asList(4, 5, 6, 7, 0, 1, 2, 3))));
        System.out.println(solution.findKRotation(new ArrayList<>(Arrays.asList(3, 4, 5, 1, 2))));
        System.out.println(solution.findKRotation(new ArrayList<>(Arrays.asList(4, 5, 1, 2))));
        System.out.println(solution.findKRotation(new ArrayList<>(Arrays.asList(1, 2, 4, 5))));
    }
}

/*
Optimal - Find the min position and calculate how many times it's rotated
T - O(log n)
S - O(1)
 */
class Solution {
    public int findKRotation(ArrayList<Integer> nums) {
        int low = 0, high = nums.size() - 1, min = Integer.MAX_VALUE, minPos = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums.get(low) <= nums.get(mid)) { // left is sorted
                if (nums.get(low) < min) {
                    minPos = low;
                    min = nums.get(low);
                }
                low = mid + 1;
            } else { // right is sorted
                if (nums.get(mid) < min) {
                    minPos = mid;
                    min = nums.get(mid);
                }
                high = mid - 1;
            }
        }

        return minPos;
    }
}
