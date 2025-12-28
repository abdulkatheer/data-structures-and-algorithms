package io.abdul.problem01;

import java.util.ArrayList;

// https://takeuforward.org/plus/dsa/problems/print-all-primes-till-n?category=maths&subcategory=sieve-of-eratosthenes
// tag:math tag:algorithm
public class Solutions {

}

// Brute-force
class Solution {

  /*
   * T - O(n^(3/2)) -> sqrt(1) + sqrt(2) + sqrt(3) + ... + sqrt(n) -> solved using Integral calculus
   * S - O(1)
   */
  public ArrayList<Integer> primeTillN(int n) {
    ArrayList<Integer> primes = new ArrayList<>();

    for (int i = 2; i <= n; i++) {
      if (isPrime(i)) {
        primes.add(i);
      }
    }

    return primes;
  }

  // T - O(sqrt(n))
  // S - O(1)
  private boolean isPrime(int n) {
    int sqrt = (int) Math.sqrt(n);

    for (int i = 2; i <= sqrt; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }
}

// Better - Sieve of Eratosthenes
class Solution2 {

  /*
   * T - O(n * log n) -> n/1 + n/2 + n/3 + ... + n/n
   * Harmonic series Hn = n/1 + n/2 + n/3 + ... + n/n = logn + Y, where Y is a constant
   * S - O(n)
   */
  public ArrayList<Integer> primeTillN(int n) {
    boolean[] composites = new boolean[n + 1];

    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        markComposites(composites, i);
      }
    }

    ArrayList<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
      }
    }

    return primes;
  }

  // T - O(n/x)
  private void markComposites(boolean[] composites, int x) {
    for (int i = 2; i * x < composites.length; i++) {
      composites[i * x] = true;
    }
  }
}


// Optimal - Sieve of Eratosthenes
class Solution3 {

  /*
   * T - O(n log logn) - As per Mertens theorem
   * S - O(n)
   */
  public ArrayList<Integer> primeTillN(int n) {
    boolean[] composites = new boolean[n + 1];
    ArrayList<Integer> primes = new ArrayList<>();

    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
        markComposites(composites, i);
      }
    }

    return primes;
  }

  // T - O(n/x)
  private void markComposites(boolean[] composites, int x) {
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }
}

