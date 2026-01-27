package io.abdul.problem58;

// https://leetcode.com/problems/single-number/
// tag:math tag:bit_manipulation
public class Solutions {

}

class Solution {
  /*
  XOR properties used
  Identity a ^ 0 = a
  Self-inverse a ^ a = 0
  a ^ b ^ a = b
  Commutative a ^ b = b ^ a, order doesn't matter
  Associative (a ^ b) ^ c = a ^ (b ^ c), grouping doesn't matter
  */
  public int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
      result ^= num;
    }

    return result;
  }
}
