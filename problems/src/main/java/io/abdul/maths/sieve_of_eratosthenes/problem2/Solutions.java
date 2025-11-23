package io.abdul.maths.sieve_of_eratosthenes.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Given examples
    assertEquals(
        Arrays.asList(
            Arrays.asList(2),
            Arrays.asList(3),
            Arrays.asList(2, 2),
            Arrays.asList(5),
            Arrays.asList(2, 3)
        ),
        sol.primeFactors(new int[]{2, 3, 4, 5, 6})
    );

    assertEquals(
        Arrays.asList(
            Arrays.asList(7),
            Arrays.asList(2, 2, 3),
            Arrays.asList(2, 3, 3)
        ),
        sol.primeFactors(new int[]{7, 12, 18})
    );

    assertEquals(
        Arrays.asList(
            Arrays.asList(3, 5),
            Arrays.asList(2, 2, 5)
        ),
        sol.primeFactors(new int[]{15, 20})
    );

    // Single number
    assertEquals(
        Arrays.asList(Arrays.asList(11)),
        sol.primeFactors(new int[]{11})
    );

    // Number = 1 (no prime factors)
    assertEquals(
        Arrays.asList(Collections.emptyList()),
        sol.primeFactors(new int[]{1})
    );

    // Multiple values including primes and composites
    assertEquals(
        Arrays.asList(
            Arrays.asList(2, 2, 2),
            Arrays.asList(3),
            Arrays.asList(2, 5),
            Arrays.asList(11),
            Arrays.asList(2, 3, 3, 7)
        ),
        sol.primeFactors(new int[]{8, 3, 10, 11, 126})
    );

    // Large composite number
    assertEquals(
        Arrays.asList(
            Arrays.asList(2, 2, 2, 3, 3, 5, 7)
        ),
        sol.primeFactors(new int[]{2520})
    );

    // Mixed order
    assertEquals(
        Arrays.asList(
            Arrays.asList(2, 3),
            Arrays.asList(2, 2, 2, 2),
            Arrays.asList(13)
        ),
        sol.primeFactors(new int[]{6, 16, 13})
    );

    // Edge case: negative numbers → usually invalid, return empty lists
    assertEquals(
        Arrays.asList(
            Collections.emptyList(),
            Collections.emptyList()
        ),
        sol.primeFactors(new int[]{-5, -10})
    );

    // Zero → no prime factors
    assertEquals(
        Arrays.asList(Collections.emptyList()),
        sol.primeFactors(new int[]{0})
    );

    // Duplicate values
    assertEquals(
        Arrays.asList(
            Arrays.asList(2, 2, 3),
            Arrays.asList(2, 2, 3)
        ),
        sol.primeFactors(new int[]{12, 12})
    );
  }
}

/*
Brute-force
T - O(n * sqrt(k))
S - O(1)
where n is number of queries, k is the query itself
 */
class Solution {

  public List<List<Integer>> primeFactors(int[] queries) {
    List<List<Integer>> primeFactors = new ArrayList<>();
    for (int query : queries) {
      primeFactors.add(primeFactor(query));
    }

    return primeFactors;
  }

  private List<Integer> primeFactor(int num) {
    if (num < 2) {
      return Collections.emptyList();
    }

    List<Integer> primeFactor = new ArrayList<>();

    int sqrtNum = (int) Math.sqrt(num);
    for (int i = 2; i <= sqrtNum; i++) {
      while (num % i == 0) { // divide with i until you can
        primeFactor.add(i);
        num /= i;
      }
    }

    if (num > 1) { // the last bit is also a prime
      primeFactor.add(num);
    }

    return primeFactor;
  }
}

/*
T - O(max_K log(log max_K)) + O(N log(K))
 */
class Solution2 {

  public List<List<Integer>> primeFactors(int[] queries) {
    List<List<Integer>> primeFactors = new ArrayList<>();
    int max = 0;
    for (int query : queries) {
      max = Math.max(max, query);
    }

    List<Integer> primes = primeNumbers(max);

    for (int query : queries) {
      primeFactors.add(primeFactor(query, primes));
    }

    return primeFactors;
  }

  // Sieve of Eratosthenes - O(max_K log(log max_K))
  private List<Integer> primeNumbers(int max) {
    boolean[] prime = new boolean[max + 1];
    Arrays.fill(prime, true);
    List<Integer> result = new ArrayList<>();

    for (int i = 2; i <= max; i++) {
      if (prime[i]) { // mark as multiples as composite
        result.add(i);
        for (int j = i * i; j <= max; j += i) {
          prime[j] = false;
        }
      }
    }

    return result;
  }

  private List<Integer> primeFactor(int num, List<Integer> primes) {
    List<Integer> primeFactor = new ArrayList<>();
    int i = 0;
    while (i < primes.size() && num > 1) {
      int p = primes.get(i);
      while (num % p == 0) {
        primeFactor.add(p);
        num /= p;
      }
      i++;
    }

    return primeFactor;
  }
}