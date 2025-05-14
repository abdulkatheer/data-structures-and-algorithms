package io.abdul.binary_search.logic_building.problem2;

import java.util.Arrays;

/*
Case 1: No element exists, largest smaller element and smallest larger element
Case 2: Element exists, the element itself
Case 3: All are smaller, ceil will be -1
Case 4: All are larger, floor will be -1
 */
public class FloorAndCeil {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.getFloorAndCeil(new int[]{3, 4, 4, 7, 8, 10}, 5)));
        System.out.println(Arrays.toString(solution.getFloorAndCeil(new int[]{3, 4, 4, 7, 8, 10}, 8)));
        System.out.println(Arrays.toString(solution.getFloorAndCeil(new int[]{2, 4, 6, 8, 10, 12, 14}, 1)));
        System.out.println(Arrays.toString(solution.getFloorAndCeil(new int[]{}, 1)));
    }
}

class Solution {
    public int[] getFloorAndCeil(int[] nums, int x) {
        int floor = getFloor(nums, x);
        int ceil = getCeil(nums, x);

        return new int[]{floor, ceil};
    }

    private static int getCeil(int[] nums, int x) {
        int low = 0, high = nums.length - 1;
        int ceil = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= x) {
                ceil = nums[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ceil;
    }

    private static int getFloor(int[] nums, int x) {
        int low = 0, high = nums.length - 1;

        int floor = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] <= x) {
                floor = nums[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return floor;
    }
}