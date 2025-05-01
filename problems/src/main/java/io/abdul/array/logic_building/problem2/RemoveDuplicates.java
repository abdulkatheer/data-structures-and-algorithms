package io.abdul.array.logic_building.problem2;

public class RemoveDuplicates {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.removeDuplicates(new int[]{1}));
        System.out.println(solution.removeDuplicates(new int[]{1, 1}));
        System.out.println(solution.removeDuplicates(new int[]{1, 1, 1}));
        System.out.println(solution.removeDuplicates(new int[]{1, 2, 2, 3, 3, 3}));
        System.out.println(solution.removeDuplicates(new int[]{0, 0, 3, 3, 5, 6}));
        System.out.println(solution.removeDuplicates(new int[]{-2, 2, 4, 4, 4, 4, 5, 5}));
        System.out.println(solution.removeDuplicates(new int[]{-30, -30, 0, 0, 10, 20, 30, 30}));
    }
}

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 1) {
            return 1;
        }

        int pos = 0;
        int count = 0;
        while (pos < nums.length) {
            int k = pos + 1;
            int dup = 0;
            while (k < nums.length) { // find all duplicates
                if (nums[k] != nums[pos]) {
                    break;
                }
                dup++;
                k++;
            }
            for (int i = pos + dup + 1; i < nums.length; i++) { // If there are duplicates, replace them with next set of elements
                nums[i - dup] = nums[i];
            }
            pos++;
            count++;
            if (pos < nums.length && nums[pos] <= nums[pos - 1]) {
                // Base case to stop iteration. After moving,
                // if next element is less than or equals current element, that means that's the already moved element
                // and no further iterations needed
                break;
            }
        }

        return count;
    }
}