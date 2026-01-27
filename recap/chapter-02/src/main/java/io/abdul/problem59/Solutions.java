package io.abdul.problem59;

// https://leetcode.com/problems/single-number-ii/
// tag:math tag:bit_manipulation
// refer io.abdul.basics.problem15.SingleNumberII
public class Solutions {

}

class Solution {
  /*
  2-bit finite state machine
  00 - Not appeared
  10 - Appeared once
  01 - Appeared twice
  00 - Appeared thrice and Reset
  */
  public int singleNumber(int[] nums) {
    int once = 0;
    int twice = 0;

    for (int num : nums) {
      once = (once ^ num) & (~twice); // add to once bit if not exists in twice
      twice = (twice ^ num) & (~once); // add to twice bit if not exists in once
    }

    return once; // num appeared only once is added to once only
  }
}
