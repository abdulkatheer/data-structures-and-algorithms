package io.abdul.problem60;

// https://leetcode.com/problems/single-number-iii/
// tag:math tag:bit_manipulation
// refer io.abdul.basics.problem16.SingleNumberIII
public class Solutions {

}

class Solution {
  /*
  if we xor all nums, the result will be xor of two nums that we want.
  But how do we find two nums out of it?

  the two nums must differ by at least 1 bit.
  otherwise they should be called as same numbers.

  Every set bit in the xor, is actually the differing bits between a and b
  1 ^ 1 = 0
  0 ^ 0 = 0
  1 ^ 0 = 1
  0 ^ 1 = 1

  Find the rightmost (any is fine) differing bit and group items based on it
  */
  public int[] singleNumber(int[] nums) {
    int a_xor_b = 0;
    for (int num : nums) {
      a_xor_b ^= num;
    }

    // isolating the rightmost set bit
    int rightMostSetBit = a_xor_b & (-a_xor_b);
    // int rightMostSetBit = (a_xor_b & (a_xor_b-1)) ^ a_xor_b;

    int groupA = 0;
    int groupB = 0;
    for (int num : nums) {
      if ((num & rightMostSetBit) != 0) {
        groupA ^= num;
      } else {
        groupB ^= num;
      }
    }

    return new int[] {groupA, groupB};
  }
}
