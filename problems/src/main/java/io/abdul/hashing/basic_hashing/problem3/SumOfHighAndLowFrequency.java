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
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    HashMap<Integer, Integer> countByNum = new HashMap<>();
    for (int n : nums) {
      countByNum.put(n, countByNum.getOrDefault(n, 0) + 1);
    }

    for (Map.Entry<Integer, Integer> countEntry : countByNum.entrySet()) {
      if (countEntry.getValue() > max) {
        max = countEntry.getValue();
      }

      if (countEntry.getValue() < min) {
        min = countEntry.getValue();
      }
    }

    return max + min;
  }
}

