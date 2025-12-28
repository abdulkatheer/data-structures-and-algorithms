package io.abdul.problem21;

public class Solutions {

}

class Solution {
  public String winLose(int n) {
    int k = 0;

    if (n <= 1) {
      return "William";
    }

    while (!isPrime(n)) {
      int mid = n/2;
      int nonPrimeFactor = -1;
      for (int i = 2; i <= mid; i++) {
        if (n % i == 0 && !isPrime(i)) { // Try choosing the first non-prime factor
          nonPrimeFactor = i;
          break;
        }
      }
      k++;
      if (nonPrimeFactor == -1) {
        break;
      }
      n = nonPrimeFactor;
    }

    return k % 2 == 0 ? "William" : "Jax";
  }

  private boolean isPrime(int num) {
    if (num <= 1) {
      return false;
    }

    if (num <= 3) {
      return true;
    }

    if (num % 2 == 0 || num % 3 == 0) {
      return false;
    }

    int sqrt = (int) Math.sqrt(num);
    for (int i = 5; i <= sqrt; i+=6) {
      if (num % i == 0 || num % (i+2) == 0) {
        return false;
      }
    }

    return true;
  }
}
