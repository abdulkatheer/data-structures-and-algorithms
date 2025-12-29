package io.abdul.problem28;

// https://leetcode.com/problems/powx-n/description/
// tag:math
public class Solutions {

}

/*
Recursive
T - O(log n)
S - O(log n) - stack
 */
class Solution {

  public double myPow(double x, int n) {
    if (n >= 0) {
      return power(x, n);
    } else {
      return 1 / power(x, (long) n * (-1));
    }
  }

  private double power(double x, long n) {
    if (n == 0) {
      return 1;
    }

    if ((n & 1) == 1) { // odd
      return x * power(x, n - 1);
    } else { // even
      return power(x * x, n / 2);
    }
  }
}

/*
Iterative
T - O(log n)
S - O(1)
 */
class Solution2 {

  public double myPow(double x, int n) {
    long p = n;
    if (p < 0) {
      p = -p;
    }

    double power = 1;
    while (p != 0) {
      if ((p & 1) == 1) { // odd
        power *= x;
        p--;
      } else { // even
        x *= x;
        p >>= 1; // n/2
      }
    }

    return n < 0 ? (1 / power) : power;
  }
}