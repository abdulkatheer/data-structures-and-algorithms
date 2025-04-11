package io.abdul.hashing.basic_hashing.problem1;

import java.util.HashMap;
import java.util.Map;

public class HighestOccurringElements {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.mostFrequentElement(new int[]{1, 2, 2, 3, 3, 3}));
        System.out.println(solution.mostFrequentElement(new int[]{4, 4, 5, 5, 6}));
        System.out.println(solution.mostFrequentElement(new int[]{10, 9, 7}));
    }
}

class Solution {
    public int mostFrequentElement(int[] nums) {
        HashMap<Integer, Integer> numCount = new HashMap<>();
        for (int num : nums) {
            numCount.merge(num, 1, Integer::sum);
        }

        int num = Integer.MAX_VALUE;
        int count = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> numCountEntry : numCount.entrySet()) {
            if (numCountEntry.getValue() > count) {
                num = numCountEntry.getKey();
                count = numCountEntry.getValue();
            } else if (numCountEntry.getValue() == count) {
                if (numCountEntry.getKey() < num) {
                    num = numCountEntry.getKey();
                }
            }
        }

        return num;
    }
}
