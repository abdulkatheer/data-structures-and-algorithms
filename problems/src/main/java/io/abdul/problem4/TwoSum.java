package io.abdul.problem4;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {
    // O(n^2)
    private static int[] findTwoSumPairV1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == j) {
                    continue;
                }
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("No pair found");
    }

    // O(n) using HashMap, Fast runtime, but more memory
    private static int[] findTwoSumPairV2(int[] nums, int target) {
        HashMap<Integer, Integer> complements = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int comp = target - nums[i];
            if (complements.containsKey(comp)) {
                return new int[]{complements.get(comp), i};
            } else {
                complements.put(nums[i], i);
            }
        }
        throw new RuntimeException("No pair found");
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findTwoSumPairV1(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(findTwoSumPairV1(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(findTwoSumPairV1(new int[]{3, 3}, 6)));

        System.out.println(Arrays.toString(findTwoSumPairV2(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(findTwoSumPairV2(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(findTwoSumPairV2(new int[]{3, 3}, 6)));
    }
}
