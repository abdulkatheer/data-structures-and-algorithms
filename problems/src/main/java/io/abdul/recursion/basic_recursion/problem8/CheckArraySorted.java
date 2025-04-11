package io.abdul.recursion.basic_recursion.problem8;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-recursion/check-if-the-array-is-sorted
public class CheckArraySorted {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isSorted(new ArrayList<>(List.of(1))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(1, 2))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(2, 1))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(1, 2, 3))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(1, 3, 2))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(1, 2, 2, 3, 3, 5, 4))));
        System.out.println(solution.isSorted(new ArrayList<>(List.of(4, 1, 2, 3))));
    }
}

class Solution {
    public boolean isSorted(ArrayList<Integer> nums) {
        return isSorted(nums, nums.size() - 1);
    }

    public boolean isSorted(ArrayList<Integer> nums, int i) {
        if (i <= 0) { // If able to come till last, means no element was not in unsorted order
            return true;
        }
        if (nums.get(i - 1) > nums.get(i)) {
            return false;
        }
        return isSorted(nums, i - 1);
    }
}
