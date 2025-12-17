package io.abdul.problem02;

// https://leetcode.com/problems/prime-palindrome/
// tag:math tag:math_trick
public class Solutions {

}

// ERROR: TLE
class Solution {

  /*
  T - sqrt(n+1) + log(n+1), sqrt(n+2) + log(n+2), sqrt(n+3) + log(n+3), sqrt(n+4) + log(n+4), ...
  T(i) = sqrt(i) + logi
  Up to a constant 2*10^8
  T - O(m^3/2 - n^3/2)
  If we skip constant
  T - O(n^3/2)
  S - O(1)
  */
  public int primePalindrome(int n) {
    int max = 2 * (int) 10e8;

    for (int i = n; i <= max; i++) {
      if (i == reverse(i) && isPrime(i)) {
        return i;
      }
    }

    return -1;
  }

  /*
  T - O(log n)
  S - O(1)
  */
  private int reverse(int n) {
    int rev = 0;
    while (n > 0) {
      rev = (10 * rev) + (n % 10);
      n /= 10;
    }

    return rev;
  }

  /*
  T - O(sqrt(n))
  S - O(1)
  */
  private boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }
}

// ERROR: MLE
class Solution2 {

  /*
  T - O(n log log n)
  T - O(max)
  */
  public int primePalindrome(int n) {
    int max = 2 * (int) 10e8;

    if (n < 2) {
      return 2;
    }

    // find all primes
    // T - O(n log log n)
    // S - O(max) - O(1), but huge
    boolean[] composites = new boolean[max + 1];
    for (int i = 2; i <= max; i++) {
      if (!composites[i]) {
        markComposites(composites, i);
      }
    }

    for (int i = n; i <= max; i++) {
      if (!composites[i] && i == reverse(i)) {
        return i;
      }
    }

    return -1;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }

  private int reverse(int n) {
    int rev = 0;
    while (n > 0) {
      rev = (10 * rev) + (n % 10);
      n /= 10;
    }

    return rev;
  }
}

/*
Optimal

Math trick: All even digit palindromes are composites

start = 1; end = 200000000

10 - 100 (except 11), we can also skip 10 as its not prime.
1000 - 10000
100000 - 1000000
10000000 - 100000000
1000000000 - Inf
 */
class Solution3 {

  // T - O(Sqrt(N))
  public int primePalindrome(int n) {
    int max = 2 * (int) 10e8;

    int i = n;
    while (i <= max) {
      if (i == reverse(i) && isPrime(i)) {
        return i;
      }
      i++;

      // Skip even digit numbers
      if (i >= 12 && i < 100) {
        i = 100;
      }
      if (i >= 1000 && i < 10000) {
        i = 10000;
      }
      if (i >= 100000 && i < 1000000) {
        i = 1000000;
      }
      if (i >= 10000000 && i < 100000000) {
        i = 100000000;
      }
      if (i >= 1000000000) {
        i = max;
      }
    }

    return -1;
  }

  /*
  T - O(log n)
  S - O(1)
  */
  private int reverse(int n) {
    int rev = 0;
    while (n > 0) {
      rev = (10 * rev) + (n % 10);
      n /= 10;
    }

    return rev;
  }

  /*
  T - O(sqrt(n))
  S - O(1)
  */
  private boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }
}