package io.abdul.array.logic_building.problem5;

import java.util.ArrayList;
import java.util.Arrays;

public class IntersectionOfSortedArrays {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.intersectionArray(new int[]{1, 2, 2, 3, 5}, new int[]{1, 2, 7})));
        System.out.println(Arrays.toString(solution.intersectionArray(new int[]{1, 2, 2, 3}, new int[]{4, 5, 7})));
        System.out.println(Arrays.toString(solution.intersectionArray(new int[]{-45, -45, 0, 0, 2}, new int[]{-50, -45, 0, 0, 5, 7})));
    }
}

class Solution {
    public int[] intersectionArray(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;

        ArrayList<Integer> intersection = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                intersection.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }

        return intersection.stream().mapToInt(t -> t).toArray();
    }
}