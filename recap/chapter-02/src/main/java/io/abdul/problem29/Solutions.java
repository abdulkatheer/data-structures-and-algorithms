package io.abdul.problem29;

// https://leetcode.com/problems/sqrtx/
// tag:math tag:binary_search tag:binary_search_on_answer
public class Solutions {

}

class Solution {

  public int mySqrt(int x) {
    long i = 1;
    while (i * i <= x) {
      i++;
    }

    return (int) (i - 1);
  }
}

class Solution2 {

  public int mySqrt(int x) {
    long low = 1;
    long high = x;
    long sqrt = 0;
    while (low <= high) {
      long mid = (low + high) / 2;
      long square = mid * mid;
      if (square <= x) {
        sqrt = mid;
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    return (int) sqrt;
  }
}