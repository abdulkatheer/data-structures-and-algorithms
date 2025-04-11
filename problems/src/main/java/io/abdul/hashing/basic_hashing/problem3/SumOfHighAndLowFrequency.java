package io.abdul.hashing.basic_hashing.problem3;

import java.util.HashMap;
import java.util.Map;

public class SumOfHighAndLowFrequency {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.sumHighestAndLowestFrequency(new int[]{1, 2, 2, 3, 3, 3}));
//        System.out.println(solution.sumHighestAndLowestFrequency(new int[]{4, 4, 5, 5, 6}));
//        System.out.println(solution.sumHighestAndLowestFrequency(new int[]{10, 9, 7}));
//        System.out.println(solution.sumHighestAndLowestFrequency(new int[]{10, 9, 7, 7, 8, 8, 8}));
        System.out.println(solution.sumHighestAndLowestFrequency(new int[]{15, 1}));
    }
}

class Solution {
    public int sumHighestAndLowestFrequency(int[] nums) {
        HashMap<Integer, Integer> numCount = new HashMap<>();
        for (int num : nums) {
            numCount.merge(num, 1, Integer::sum);
        }

        int maxNum = Integer.MAX_VALUE;
        int maxCount = Integer.MIN_VALUE;

        int lastMaxNum = Integer.MAX_VALUE;
        int lastMaxCount = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> numCountEntry : numCount.entrySet()) {
            if (numCountEntry.getValue() > maxCount) {
                if (maxCount > 0 && maxCount < lastMaxCount) {
                    lastMaxNum = maxNum;
                    lastMaxCount = maxCount;
                }

                maxNum = numCountEntry.getKey();
                maxCount = numCountEntry.getValue();
            } else if (numCountEntry.getValue() == maxCount) {
                if (numCountEntry.getKey() < maxNum) {
                    maxNum = numCountEntry.getKey();
                }
            } else if (numCountEntry.getValue() < lastMaxCount) {
                lastMaxNum = numCountEntry.getKey();
                lastMaxCount = numCountEntry.getValue();
            } else if (numCountEntry.getValue() == lastMaxCount) {
                if (numCountEntry.getKey() < lastMaxNum) {
                    lastMaxNum = numCountEntry.getKey();
                }
            }
        }

//        System.out.println("First Max=" + maxNum + "; Count=" + maxCount);
//        System.out.println("Last Max=" + lastMaxNum + "; Count=" + lastMaxCount);
        return lastMaxCount == Integer.MAX_VALUE ? maxCount : lastMaxCount + maxCount;
    }
}

