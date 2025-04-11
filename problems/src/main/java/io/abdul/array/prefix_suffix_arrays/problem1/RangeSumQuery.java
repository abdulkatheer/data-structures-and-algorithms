package io.abdul.array.prefix_suffix_arrays.problem1;

import java.util.HashMap;

// https://leetcode.com/problems/range-sum-query-immutable/
public class RangeSumQuery {
    public static void main(String[] args) {
//        NumArray numArray = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        NumArray2 numArray = new NumArray2(new int[]{-2, 0, 3, -5, 2, -1});
        System.out.println(numArray.sumRange(0, 2));
        System.out.println(numArray.sumRange(2, 5));
        System.out.println(numArray.sumRange(0, 5));
        System.out.println(numArray.sumRange(0, 0));
        System.out.println(numArray.sumRange(5, 5));
    }
}

/*
Better Solution
Using HashMap
Time - O(1)
Space - O(1) As we use same space size as input
 */
class NumArray {
    private final HashMap<Integer, Integer> sumByIndex;

    public NumArray(int[] nums) {
        sumByIndex = new HashMap<>(nums.length);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sumByIndex.put(i, sum);
        }
    }

    public int sumRange(int left, int right) {
        Integer sum = sumByIndex.get(right);
        if (left > 0) {
            Integer sumBeforeLeft = sumByIndex.get(left - 1);
            sum -= sumBeforeLeft;
        }
        return sum;
    }
}

/*
Optimal Solution
Using Array
Time - O(1)
Space - O(1) As we use same space size as input
 */
class NumArray2 {
    private final int[] sumByIndex;

    public NumArray2(int[] nums) {
        sumByIndex = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sumByIndex[i] = sum;
        }
    }

    public int sumRange(int left, int right) {
        int sum = sumByIndex[right];
        if (left > 0) {
            int sumBeforeLeft = sumByIndex[left - 1];
            sum -= sumBeforeLeft;
        }
        return sum;
    }
}