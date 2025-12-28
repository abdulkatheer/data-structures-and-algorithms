package io.abdul.problem27;

/*
https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/
tag:math
 */
public class Solutions {

}

class Solution {

  public int subtractProductAndSum(int n) {
    int product = 1;
    int sum = 0;
    while (n != 0) {
      int digit = n % 10;
      n /= 10;
      product *= digit;
      sum += digit;
    }

    return product - sum;
  }
}