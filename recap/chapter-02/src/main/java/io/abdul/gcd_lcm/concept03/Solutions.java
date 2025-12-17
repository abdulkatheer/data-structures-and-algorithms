package io.abdul.gcd_lcm.concept03;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// LCM
public class Solutions {

}

// Brute-force
class Solution {

  public int lcm(int a, int b) {
    int start = Math.max(a, b);
    int end = a * b;

    for (int i = start; i <= end; i += start) {
      if (i % a == 0 && i % b == 0) {
        return i;
      }
    }

    return -1; // not possible, result will be at least a*b
  }
}

// Prime Factorisation method
class Solution2 {

  public int lcm(int a, int b) {
    Map<Integer, Integer> pfA = primeFactors(a);
    Map<Integer, Integer> pfB = primeFactors(b);
    Map<Integer, Integer> unionMax = new HashMap<>(pfA);
    for (Entry<Integer, Integer> pfBEntry : pfB.entrySet()) {
      Integer prime = pfBEntry.getKey();
      Integer bPow = pfBEntry.getValue();
      Integer aPow = unionMax.getOrDefault(prime, 0);
      unionMax.put(prime, Math.max(bPow, aPow));
    }

    int result = 0;
    for (Entry<Integer, Integer> maxPowPrimeEntry : unionMax.entrySet()) {
      int base = maxPowPrimeEntry.getKey();
      int exponent = maxPowPrimeEntry.getValue();
      result = result + (int) Math.pow(base, exponent);
    }

    return result;
  }

  private Map<Integer, Integer> primeFactors(int num) {
    int sqrt = (int) Math.sqrt(num);

    Map<Integer, Integer> primeFactors = new HashMap<>();
    for (int i = 2; i <= sqrt; i++) {
      while (num % i == 0) {
        primeFactors.put(i, primeFactors.getOrDefault(i, 0) + 1);
        num /= i;
      }
    }

    if (num > 1) {
      primeFactors.put(num, 1);
    }

    return primeFactors;
  }
}

/*
Optimal - Using GCD - Euclidean Algorithm
gcd(a,b) * lcm(a,b) = a*b
lcm(a,b) = a*b / gcd(a,b)
 */
class Solution3 {

  public int lcm(int a, int b) {
    Euclidean euclidean = new Euclidean();
    int gcd = euclidean.gcd(a, b);

    return (a / gcd) * b;
  }
}

class Euclidean {

  public int gcd(int a, int b) {
    if (a > b) {
      return gcdItr(a, b);
    } else if (b > a) {
      return gcdItr(b, a);
    } else {
      return a;
    }
  }

  private int gcdItr(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}