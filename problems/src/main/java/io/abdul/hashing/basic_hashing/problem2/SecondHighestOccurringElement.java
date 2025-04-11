package io.abdul.hashing.basic_hashing.problem2;

import java.util.HashMap;
import java.util.Map;

public class SecondHighestOccurringElement {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.secondMostFrequentElement(new int[]{1, 2, 2, 3, 3, 3}));
        System.out.println(solution.secondMostFrequentElement(new int[]{4, 4, 5, 5, 6}));
        System.out.println(solution.secondMostFrequentElement(new int[]{10, 9, 7}));
    }
}

class Solution {
    public int secondMostFrequentElement(int[] nums) {
        HashMap<Integer, Integer> numCount = new HashMap<>();
        for (int num : nums) {
            numCount.merge(num, 1, Integer::sum);
        }

        int maxNum = Integer.MAX_VALUE;
        int maxCount = Integer.MIN_VALUE;

        int secondMaxNum = Integer.MAX_VALUE;
        int secondMaxCount = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> numCountEntry : numCount.entrySet()) {
            /*
            When current is bigger than max, set max and replace secondMax with max
            When current is bigger than secondMax, set secondMax
             */
            if (numCountEntry.getValue() > maxCount) {
                secondMaxNum = maxNum;
                secondMaxCount = maxCount;
                maxNum = numCountEntry.getKey();
                maxCount = numCountEntry.getValue();
            } else if (numCountEntry.getValue() == maxCount) {
                if (numCountEntry.getKey() < maxNum) {
                    maxNum = numCountEntry.getKey();
                }
            } else if (numCountEntry.getValue() > secondMaxCount) {
                secondMaxNum = numCountEntry.getKey();
                secondMaxCount = numCountEntry.getValue();
            } else if (numCountEntry.getValue() == secondMaxCount) {
                if (numCountEntry.getKey() < secondMaxNum) {
                    secondMaxNum = numCountEntry.getKey();
                }
            }
        }

//        System.out.println("First Max=" + maxNum + "; Count=" + maxCount);
//        System.out.println("Second Max=" + secondMaxNum + "; Count=" + secondMaxCount);
        return secondMaxCount < 0 ? -1 : secondMaxNum;
    }
}
