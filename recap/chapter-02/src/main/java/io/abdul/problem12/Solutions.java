package io.abdul.problem12;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://leetcode.com/problems/sum-of-largest-prime-substrings/
// tag:math tag:prime tag:string
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(11, solution.sumOfLargestPrimes("111"));
  }
}

class Solution {

  public long sumOfLargestPrimes(String s) {
    long prime1 = 0;
    long prime2 = 0;
    long prime3 = 0;
    for (int i = 0; i < s.length(); i++) {
      long num = Long.parseLong(s.substring(i));
      while (num != 0) {
        if (isPrime(num)) {
          if (num > prime1) {
            prime3 = prime2;
            prime2 = prime1;
            prime1 = num;
          } else if (num > prime2 && num != prime1) {
            prime3 = prime2;
            prime2 = num;
          } else if (num > prime3 && num != prime2 && num != prime1) {
            prime3 = num;
          }
        }
        num /= 10L;
      }
    }

    return prime1 + prime2 + prime3;
  }

  private boolean isPrime(long num) {
    if (num <= 1) {
      return false;
    }

    if (num <= 3) {
      return true;
    }

    if (num % 2 == 0 || num % 3 == 0) {
      return false;
    }

    long sqrt = (long) Math.sqrt(num);
    for (long i = 5; i <= sqrt; i += 6) {
      if (num % i == 0) {
        return false;
      }
      if (num % (i + 2) == 0) {
        return false;
      }
    }

    return true;
  }
}
