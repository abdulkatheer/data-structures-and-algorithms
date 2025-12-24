package io.abdul.problem16;

// https://leetcode.com/problems/complete-prime-number/
// tag:math tag:prime
public class Solutions {

}

class Solution {
  /*
  k digits
  How many prefixes?
  k-1 prefixes
  k-1 suffixes

  O (k sqrt(k))

  1234
  1
  12
  123
  4
  34
  234

  1234 % 10 = 123, 10
  123 % 10 = 12, 100
  12 % 10 = 1, 1000
  1 % 10 = 0, 10000

  1234 % 10000
  1234 % 1000 = 234
  234 % 100 = 34
  34 % 10 = 4
  4 % 1 = 1
  */
  public boolean completePrime(int num) {
    if (!isPrime(num)) {
      return false;
    }

    int digitsPower = 1;
    int x = num;
    while (x != 0) {
      x /= 10;
      digitsPower *= 10;
      if (x != 0 && !isPrime(x)) {
        return false;
      }
    }

    x = num;
    while (x != 0) {
      x %= digitsPower;
      digitsPower /= 10;
      if (x != 0 && !isPrime(x)) {
        return false;
      }
    }

    return true;
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
    for (int i = 5; i <= sqrt; i+=6) {
      if (n % i == 0) {
        return false;
      }
      if (n % (i+2) == 0) {
        return false;
      }
    }

    return true;
  }
}
