package io.abdul.problem25;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
https://leetcode.com/problems/perfect-number/
tag:math tag:prime tag:mersenne_prime
 */
public class Solutions {

  public static void main(String[] args) {
    Solution1 solution = new Solution1();
    assertTrue(solution.checkPerfectNumber(28));
  }
}

/*
Brute - O(n)
 */
class Solution {

  public boolean checkPerfectNumber(int num) {
    if (num <= 1) {
      return false; // 1 has only one divisor which is itself, but we should not count it.
    }
    int mid = num / 2;
    int sum = 1; // for divisor 1
    for (int i = 2; i <= mid; i++) {
      if (num % i == 0) {
        sum += i;
      }
      if (sum > num) {
        return false;
      }
    }

    return sum == num;
  }
}

/*
Better - O(sqrt(n))

If n is divisible by i, n/i=j
then it'll be divisible by j as well -> n/j=i

Max is, sqrt itself.
sqrt(n) * sqrt(n) = n
So to count distinct ones, we'll have to skip sqrt(n).
 */
class Solution1 {

  public boolean checkPerfectNumber(int num) {
    if (num <= 1) {
      return false; // 1 has only one divisor which is itself, but we should not count it.
    }
    int sqrt = (int) Math.sqrt(num);
    int sum = 1; // divisor 1 is counted
    for (int i = 2; i <= sqrt; i++) {
      if (num % i == 0) {
        sum += i;
        if (i * i != num) { // i is not the sqrt(num)
          sum += num / i;
        }
      }
    }

    return sum == num;
  }
}

/*
Optimal - O(1)
Euclid-Euler Theorem
Mersenne Prime
Even Perfect Number, No odd perfect number found yet
 */
class Solution3 {

  // Euclid-Euler theorem
  public boolean checkPerfectNumber(int num) {
    if (num <= 1) {
      return false; // 1 has only one divisor which is itself, but we should not count it.
    }

    int[] mersennePrimes = {2, 3, 5, 7, 13, 17};
    for (int p : mersennePrimes) {
      if (num == perfectNumber(p)) {
        return true;
      }
    }

    return false;
  }

  private int perfectNumber(int p) {
    return (1 << (p - 1)) * ((1 << p) - 1); // 2^(p-1) * 2^p - 1
  }
}