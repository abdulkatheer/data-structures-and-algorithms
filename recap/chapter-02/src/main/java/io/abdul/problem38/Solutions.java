package io.abdul.problem38;

// https://leetcode.com/problems/find-greatest-common-divisor-of-array/
// tag:math
public class Solutions {

}

/*
Euclidean Algorithm
*/
class Solution {

  public int findGCD(int[] nums) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int num : nums) {
      min = Math.min(min, num);
      max = Math.max(max, num);
    }

    return gcd(max, min);
  }

  private int gcd(int a, int b) {
    if (b == 0) {
      return a;
    }

    return gcd(b, a % b);
  }
}

/*
Euclidean Algorithm
*/
class Solution2 {

  public int findGCD(int[] nums) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int num : nums) {
      min = Math.min(min, num);
      max = Math.max(max, num);
    }

    return gcd(max, min);
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = a % b;
      a = b;
      b = temp;
    }

    return a;
  }
}
