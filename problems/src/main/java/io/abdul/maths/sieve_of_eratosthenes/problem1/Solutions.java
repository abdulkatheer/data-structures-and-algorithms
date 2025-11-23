package io.abdul.maths.sieve_of_eratosthenes.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
//    Solution2 sol = new Solution2();
//    Solution3 sol = new Solution3();
    Solution4 sol = new Solution4();

    // Given examples
    assertEquals(Arrays.asList(2, 3, 5, 7), sol.primeTillN(7));
    assertEquals(Arrays.asList(2), sol.primeTillN(2));
    assertEquals(Arrays.asList(2, 3, 5, 7), sol.primeTillN(10));

    // Edge cases
    assertEquals(new ArrayList<>(), sol.primeTillN(0));
    assertEquals(new ArrayList<>(), sol.primeTillN(1));
    assertEquals(Arrays.asList(2), sol.primeTillN(2));

    // Small ranges
    assertEquals(Arrays.asList(2, 3), sol.primeTillN(3));
    assertEquals(Arrays.asList(2, 3, 5), sol.primeTillN(5));

    // Larger values
    assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13), sol.primeTillN(15));
    assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19), sol.primeTillN(20));

    // Medium random test
    assertEquals(
        Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29),
        sol.primeTillN(30)
    );

    // Value where last number is not prime
    assertEquals(Arrays.asList(2, 3, 5, 7, 11), sol.primeTillN(12));

    // Case with a large prime at the end
    assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31), sol.primeTillN(31));
  }
}

/*
Brute-force
T - O(n^2)
S - O(1)
 */
class Solution {

  public ArrayList<Integer> primeTillN(int n) {
    ArrayList<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
      if (isPrime(i)) {
        primes.add(i);
      }
    }

    return primes;
  }

  // T - O(n)
  private boolean isPrime(int num) {
    for (int i = 2; i < num; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}

/*
Better
T - O(n * sqrt(n))
S - O(1)
 */
class Solution2 {

  public ArrayList<Integer> primeTillN(int n) {
    ArrayList<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
      if (isPrime(i)) {
        primes.add(i);
      }
    }

    return primes;
  }

  // T - O(n)
  private boolean isPrime(int num) {
    int sqrt = (int) Math.sqrt(num);
    for (int i = 2; i <= sqrt; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}

/*
Optimal - Sieve of Eratosthenes
T - O(n log(log n))
S - O(n)
 */
class Solution3 {

  public ArrayList<Integer> primeTillN(int n) {
    ArrayList<Integer> primes = new ArrayList<>();
    boolean[] sor = new boolean[n + 1];
    Arrays.fill(sor, true);

    int i = 2; //
    while (i <= n) {
      int k = 2;
      while (k * i <= n) { // mark all multiples of prime as composite
        sor[k * i] = false; // non-prime
        k++;
      }
      do {  // move to next prime
        i++;
      }
      while (i <= n && !sor[i]);
    }

    for (int j = 2; j <= n; j++) {
      if (sor[j]) {
        primes.add(j);
      }
    }

    return primes;
  }
}

/*
Further optimization
1. Instead of starting from i+i, we can start from i*i as earlier numbers would have been already covered
 */
class Solution4 {

  public ArrayList<Integer> primeTillN(int n) {
    ArrayList<Integer> primes = new ArrayList<>();
    boolean[] prime = new boolean[n + 1];
    Arrays.fill(prime, true);

    for (int i = 2; i <= n; i++) {
      if (prime[i]) {
        primes.add(i);

        // mark all its multiples composite
        for (int j = i * i; j <= n; j += i) { // 4,6,8,10 ..., 9,12,15..., 25,30,35...
          prime[j] = false;
        }
      }
    }

    return primes;
  }
}