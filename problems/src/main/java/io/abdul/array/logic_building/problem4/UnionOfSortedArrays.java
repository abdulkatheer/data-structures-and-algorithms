package io.abdul.array.logic_building.problem4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnionOfSortedArrays {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.unionArray(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 7})));
        System.out.println(Arrays.toString(solution.unionArray(new int[]{3, 4, 6, 7, 9, 9}, new int[]{1, 5, 7, 8, 8})));
        System.out.println(Arrays.toString(solution.unionArray(new int[]{3, 4, 4, 4}, new int[]{6, 7, 7})));
    }
}

class Solution {
    public int[] unionArray(int[] nums1, int[] nums2) {
        List<Integer> union = new ArrayList<>();

        int left = 0;
        int right = 0;

        while (left < nums1.length && right < nums2.length) {
            if (!union.isEmpty() && nums1[left] == union.get(union.size() - 1)) {
                left++;
                continue;
            }
            if (!union.isEmpty() && nums2[right] == union.get(union.size() - 1)) {
                right++;
                continue;
            }
            if (nums1[left] < nums2[right]) {
                union.add(nums1[left]);
                left++;
            } else {
                union.add(nums2[right]);
                right++;
            }
        }

        while (left < nums1.length) {
            if (!union.isEmpty() && nums1[left] == union.get(union.size() - 1)) {
                left++;
                continue;
            }
            union.add(nums1[left]);
            left++;
        }

        while (right < nums2.length) {
            if (!union.isEmpty() && nums2[right] == union.get(union.size() - 1)) {
                right++;
                continue;
            }
            union.add(nums2[right]);
            right++;
        }

        return union.stream().mapToInt(value -> value).toArray();
    }
}