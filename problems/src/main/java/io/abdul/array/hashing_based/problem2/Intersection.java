package io.abdul.array.hashing_based.problem2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// https://leetcode.com/problems/intersection-of-two-arrays/description/
public class Intersection {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.intersection(new int[]{1, 2, 2, 1}, new int[]{2, 2})));
    }
}

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> nums1Set = new HashSet<>();
        for (int n1 : nums1) {
            nums1Set.add(n1);
        }

        HashSet<Integer> result = new HashSet<>();
        for (int n2 : nums2) {
            if (nums1Set.contains(n2)) {
                result.add(n2);
            }
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
