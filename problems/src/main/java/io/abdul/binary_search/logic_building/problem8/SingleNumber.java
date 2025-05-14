package io.abdul.binary_search.logic_building.problem8;

public class SingleNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6}));
        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 3, 5, 5}));
        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7}));
    }
}

/*
Optimal - Modified Binary Search
T - O(log n)
S - O(n)

Intuition: Sorted array, so Binary Search would fit here.
Property of the single element to look into left half or right half.
Elements before that single element has position as (even,odd) and elements after that single element has position as (odd,even).
 */
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (mid - 1 >= 0 && nums[mid] == nums[mid - 1]) {
                if (mid % 2 == 0) { // Left to it (Odd,Even)
                    high = mid - 1;
                } else { // Right to it (Even,Odd)
                    low = mid + 1;
                }
            } else if (mid + 1 < nums.length && nums[mid] == nums[mid + 1]) {
                if (mid % 2 == 0) { // Right to it (Even,Odd)
                    low = mid + 1;
                } else { //  Left to it (Odd,Even)
                    high = mid - 1;
                }
            } else { // This is the Single element
                return nums[mid];
            }
        }

        return -1;
    }
}