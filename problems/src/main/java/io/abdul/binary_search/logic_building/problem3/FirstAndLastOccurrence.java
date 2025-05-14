package io.abdul.binary_search.logic_building.problem3;

import java.util.Arrays;

/*

 */
public class FirstAndLastOccurrence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
        System.out.println(Arrays.toString(solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 5)));
    }
}

/*
Optimal - Lower-bound and Upper-bound
T - O(log n)
S - O(1)
 */
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int firstOccurrence = lowerBound(nums, target);

        if (firstOccurrence == nums.length || nums[firstOccurrence] != target) { // element not found
            return new int[]{-1, -1};
        }
        int lastOccurrence = upperBound(nums, target);
        return new int[]{firstOccurrence, lastOccurrence - 1};
    }

    // Will return first occurrence or first larger element or nums.length
    private int lowerBound(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        int lowerBound = nums.length;
        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums[mid] >= target) {
                lowerBound = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return lowerBound;
    }

    // Will return first larger element or nums.length
    private int upperBound(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        int upperBound = nums.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] > target) {
                upperBound = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return upperBound;
    }
}

/*
Optimal - Modified binary search
T - O(log n)
S - O(1)
 */
class Solution2 {
    public int[] searchRange(int[] nums, int target) {
        int firstOccurrence = firstOccurrence(nums, target);

        if (firstOccurrence != -1) {
            return new int[]{firstOccurrence, lastOccurrence(nums, target)};
        }
        return new int[]{-1, -1};
    }

    private int firstOccurrence(int[] nums, int target) {
        int low = 0, high = nums.length - 1, firstOccurrence = -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                firstOccurrence = mid;
            }

            if (nums[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return firstOccurrence;
    }

    private int lastOccurrence(int[] nums, int target) {
        int low = 0, high = nums.length - 1, lastOccurrence = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums[mid] <= target) {
                lastOccurrence = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return lastOccurrence;
    }
}

class Solution3 {
    public int[] searchRange(int[] nums, int target) {
        return new int[]{smallestElementLargerThanOrEquals(nums, target), largestElementSmallerThanOrEquals(nums, target)};
    }

    private int largestElementSmallerThanOrEquals(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int pos = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] <= target) {
                pos = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

//        return pos;
        return pos == -1 ? pos : nums[pos] != target ? -1 : pos;
    }

    private int smallestElementLargerThanOrEquals(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int pos = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= target) {
                pos = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

//        return pos;
        return pos == -1 ? pos : nums[pos] != target ? -1 : pos;
    }
}