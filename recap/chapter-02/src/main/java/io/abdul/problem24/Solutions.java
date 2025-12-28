package io.abdul.problem24;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
https://leetcode.com/problems/three-divisors
tag:math tag:prime tag:binary_search tag:binary_search_on_answer
 */
public class Solutions {

  public static void main(String[] args) {
    Solution2 solution = new Solution2();
    assertTrue(solution.isThree(25));
  }
}

/*
Optimal
T - O(log n)
S - O(1)
 */
class Solution2 {

  // prime^2 always has 3 divisors = 1, p and p^2
  // So if n is a perfect square and sqrt(n) is a prime, then it has only 3 divisors
  public boolean isThree(int n) {
    int sqrt = sqrtForPerfectSquare(n);

    if (sqrt <= 1) { // sqrt(1) is 1 AND sqrt(<1) is -1
      return false;
    }

    return isPrime(sqrt);
  }

  private int sqrtForPerfectSquare(int n) {
    if (n < 1) {
      return -1;
    }

    int low = 1;
    int high = n;
    while (low <= high) {
      int mid = (low + high) / 2;
      if (mid == n / mid) {
        if (mid * mid == n) {
          return mid;
        } else {
          return -1; // not a perfect square
        }
      } else if (mid < n / mid) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    return -1;
  }

  private boolean isPrime(int n) {
    if (n <= 1) {
      return false;
    }

    if (n <= 3) {
      return true;
    }

    if (n % 2 == 0 || n % 3 == 0) {
      return false;
    }

    int sqrt = (int) Math.sqrt(n);
    for (int i = 5; i <= sqrt; i++) {
      if (n % i == 0 || n % (i + 2) == 0) {
        return false;
      }
    }

    return true;
  }
}
