package io.abdul.gcd_lcm.concept01;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// Finding gcd(a,b)
public class Solutions {

}

/*
Brute-force
T - O(min(a,b))
S - O(1)

Try all nums from 1 to min(a,b) and find the largest divisor
 */
class Solution {

  public int gcd(int a, int b) {
    int min = Math.min(a, b);
    int gcd = 1;
    for (int i = 1; i <= min; i++) {
      if (a % i == 0 && b % i == 0) {
        gcd = i;
      }
    }

    return gcd;
  }
}

/*
Prime Factorisation method

Find prime factors for a and b
Find common primes and the min power of them
Multiply it to get gcd
 */
class Solution2 {

  public int gcd(int a, int b) {
    Map<Integer, Integer> primeFactorsA = primeFactors(a);
    Map<Integer, Integer> primeFactorsB = primeFactors(b);

    int gcd = 1;
    for (Entry<Integer, Integer> primeFactorA : primeFactorsA.entrySet()) {
      Integer pf = primeFactorA.getKey();
      if (primeFactorsB.containsKey(pf)) {
        gcd = gcd * (int) Math.pow(pf, Math.min(primeFactorA.getValue(), primeFactorsB.get(pf)));
      }
    }

    return gcd;
  }

  // T - O(sqrt(n))
  private Map<Integer, Integer> primeFactors(int num) {
    HashMap<Integer, Integer> primeFactors = new HashMap<>();
    int i = 2;
    int sqrt = (int) Math.sqrt(num);
    while (i <= sqrt) {
      while (num % i == 0) {
        num /= i;
        primeFactors.put(i, primeFactors.getOrDefault(i, 0) + 1);
      }
      i++;
    }
    if (num > 1) {
      primeFactors.put(num, 1);
    }
    return primeFactors;
  }
}

/*
Optimal - Euclidean Algorithm

gcd(a,b) = gcd(b, b%a), given a>b -> GCD Modular Property
 */
class Solution3 {

  public int gcd(int a, int b) {
    if (a > b) {
      return gcdRec(a, b);
    } else if (b > a) {
      return gcdRec(b, a);
    } else {
      return a;
    }
  }

  private int gcdRec(int a, int b) {
    if (b == 0) {
      return a;
    }

    return gcdRec(b, a % b);
  }
}

/*
Optimal - Euclidean Algorithm (Iterative)

gcd(a,b) = gcd(b, b%a), given a>b -> GCD Modular Property
 */
class Solution4 {

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

class Simplification {

  public int[] simplify(int numerator, int denominator) {
    Solution4 solution = new Solution4();
    int gcd = solution.gcd(numerator, denominator);

    return new int[]{numerator / gcd, denominator / gcd};
  }
}