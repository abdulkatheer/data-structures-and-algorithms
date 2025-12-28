package io.abdul.problem22;

// https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/description/
// tag:math
public class Solutions {

}

class Solution {

  public int countOdds(int low, int high) {
    // Using modulo operator to check if low or high is odd
    return ((low % 2) == 1 || (high % 2) == 1) ? ((high - low) / 2) + 1 : (high - low) / 2;
  }
}

class Solution2 {

  public int countOdds(int low, int high) {
    // Using binary operator to check if low or high is odd
    return ((low & 1) == 1 || (high & 1) == 1) ? ((high - low) / 2) + 1 : (high - low) / 2;
  }
}