package io.abdul.array.practice.problem7;

import java.util.Arrays;
import java.util.HashSet;

// #tag_array
public class ContainsDuplicate {
    // Time: O(n^2)
    // Space: O(1)
    private static boolean containsDuplicateV1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // With additional space
    // Time: O(n)
    // Space: O(n)
    private static boolean containsDuplicateV2(int[] nums) {
        HashSet<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            numSet.add(nums[i]);
        }
        return numSet.size() != nums.length;
    }

    // With sorting
    // Time: O(nlogn) The minimum most for sorting in n.logn only!
    // Space: O(1)
    private static boolean containsDuplicateV3(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i != nums.length - 1) {
                if (nums[i] == nums[i + 1]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] set1 = new int[]{1, 2, 3, 1};
        int[] set2 = new int[]{1, 2, 3, 4};
        int[] set3 = new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2};

        System.out.println("--V1--");
        System.out.println(containsDuplicateV1(set1));
        System.out.println(containsDuplicateV1(set2));
        System.out.println(containsDuplicateV1(set3));

        System.out.println("--V2--");
        System.out.println(containsDuplicateV2(set1));
        System.out.println(containsDuplicateV2(set2));
        System.out.println(containsDuplicateV2(set3));

        System.out.println("--V3--");
        System.out.println(containsDuplicateV3(set1));
        System.out.println(containsDuplicateV3(set2));
        System.out.println(containsDuplicateV3(set3)); // mutates input
    }
}
