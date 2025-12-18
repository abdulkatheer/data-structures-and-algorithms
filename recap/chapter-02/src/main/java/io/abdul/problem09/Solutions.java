package io.abdul.problem09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;

// https://leetcode.com/problems/maximum-number-that-sum-of-the-prices-is-less-than-or-equal-to-k
// tag:math tag:binary_search tag:binary_search_on_answer
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
    assertEquals(6, solution.findMaximumNumber(9, 1));
    assertEquals(9, solution.findMaximumNumber(7, 2));
    assertEquals(851568447023L, solution.findMaximumNumber(3278539330613L, 5));
  }
}

/*
ERROR - TLE

T - O(10^15 log_x(10^15)
 */
class Solution {

  public long findMaximumNumber(long k, int x) {
    long totalPrice = 0;

    long max = (long) 1e15;
    for (long i = 1; i <= max; i++) {
      int price = getPrice(i, x);
      if (totalPrice + price > k) {
        return i - 1;
      }
      totalPrice += price;
    }

    return k;
  }

  // T - log_x(num)
  private int getPrice(long num, int x) {
    int setBits = 0;
    num = num >> x - 1;

    while (num > 0) {
      if ((num & 1) == 1) {
        setBits++;
      }
      num = num >> x;
    }

    return setBits;
  }
}

class Solution2 {

  public long findMaximumNumber(long k, int x) {
    long low = 1;
    long high = (long) 1e15;

    long result = k;
    while (low <= high) {
      long mid = (low + high) / 2;
      long price = countBits(mid, x);

      if (price <= k) {
        result = mid;
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    return result;
  }

  // T - O(1) - upto 15 iterations
  private long countBits(long num, int x) {
        /* num has num+1 possible bit combinations
        x=1, num = 5, 6 bit combinations
        0 0 0
        0 0 1
        0 1 0
        0 1 1
        1 0 0
        1 0 1

        At pos 1 = 2 1s from complete group, no incomplete group
        At pos 2 = 2 1s from complete group, 0 1s from incomplete group
        At pos 3 = 0 1s from complete group, 2 1s from incomplete group
        */
    long totalNumberOfOnes = 0;
    int bits = countBits(num);
    num++;
    // to avoid overflows and wrong answers thereby, go up to MSB bits
    for (int i = x; i <= bits; i += x) { //  x, 2x, 3x, ... up to the most significant 1 bit for the number
      // We can group num at position i into 2^i groups
      // Remainder will be num % 2^i
      long groupSize = powerOfTwo(i);
      long numberOfGroups = num / groupSize;
      // Each complete group has 2^i-1 1s and 2^i-1 0s
      long numberOfOnesZeroes = powerOfTwo(i - 1);
      long numberOfOnes = numberOfGroups * numberOfOnesZeroes;
      // If last incomplete group exists, we take left outs after considering zeros
      // Let's say i=2, group size is 4, 0s 2 and 1s 2
      // if last group size is 1/2, those 2 are fully 0s
      // if last group size is 3, 3-2=1 is 1s
      // last group can't be 4, otherwise it would have been considered a complete group
      long incompleteGroupSize = num % groupSize;
      if (incompleteGroupSize > numberOfOnesZeroes) {
        long lastIncompleteGroupSize = incompleteGroupSize - numberOfOnesZeroes;
        numberOfOnes = numberOfOnes + lastIncompleteGroupSize;
      }

      totalNumberOfOnes += numberOfOnes;
    }

    return totalNumberOfOnes;
  }

  private long powerOfTwo(int x) {
    return 1L << x;
  }

  private int countBits(long x) {
    int count = 0;
    while (x != 0) {
      count++;
      x >>= 1;
    }

    return count;
  }
}