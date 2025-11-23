package io.abdul.maths.sieve_of_eratosthenes.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    ArrayList<int[]> q1 = new ArrayList<>();
    q1.add(new int[]{2, 5});
    q1.add(new int[]{4, 7});
    assertEquals(Arrays.asList(3, 2), sol.primesInRange(q1));

    // Example 2
    ArrayList<int[]> q2 = new ArrayList<>();
    q2.add(new int[]{1, 7});
    q2.add(new int[]{3, 7});
    assertEquals(Arrays.asList(4, 3), sol.primesInRange(q2));

    // Example 3
    ArrayList<int[]> q3 = new ArrayList<>();
    q3.add(new int[]{1, 10});
    q3.add(new int[]{10, 20});
    assertEquals(Arrays.asList(4, 4), sol.primesInRange(q3));

    // Single point range
    ArrayList<int[]> q4 = new ArrayList<>();
    q4.add(new int[]{5, 5}); // prime
    q4.add(new int[]{6, 6}); // not prime
    assertEquals(Arrays.asList(1, 0), sol.primesInRange(q4));

    // Range containing no primes
    ArrayList<int[]> q5 = new ArrayList<>();
    q5.add(new int[]{8, 10});  // only 11 is prime, but out of range
    assertEquals(Arrays.asList(0), sol.primesInRange(q5));

    // Large range but small count
    ArrayList<int[]> q6 = new ArrayList<>();
    q6.add(new int[]{20, 30});  // primes = 23,29 → 2
    assertEquals(Arrays.asList(2), sol.primesInRange(q6));

    // All primes range
    ArrayList<int[]> q7 = new ArrayList<>();
    q7.add(new int[]{2, 13}); // primes = 2,3,5,7,11,13 → 6
    assertEquals(Arrays.asList(6), sol.primesInRange(q7));

    // Reverse ranges (invalid, assume 0)
    ArrayList<int[]> q8 = new ArrayList<>();
    q8.add(new int[]{10, 1});
    assertEquals(Arrays.asList(0), sol.primesInRange(q8));

    // Negative ranges
    ArrayList<int[]> q9 = new ArrayList<>();
    q9.add(new int[]{-10, 5}); // primes = 2,3,5 → 3
    assertEquals(Arrays.asList(3), sol.primesInRange(q9));

    // Zero and one in range
    ArrayList<int[]> q10 = new ArrayList<>();
    q10.add(new int[]{0, 1}); // no primes
    assertEquals(Arrays.asList(0), sol.primesInRange(q10));

    // Mixed multiple queries
    ArrayList<int[]> q11 = new ArrayList<>();
    q11.add(new int[]{2, 2}); // 1
    q11.add(new int[]{2, 3}); // 2
    q11.add(new int[]{14, 16}); // 0
    q11.add(new int[]{17, 19}); // 3
    assertEquals(Arrays.asList(1, 2, 0, 2), sol.primesInRange(q11));
  }
}

/*
Brute-force
T - O(n * (R-L) * sqrt(R))
S - O(1)
where n is number of queries, L is the left in range, R is the right in range
 */
class Solution {

  public ArrayList<Integer> primesInRange(ArrayList<int[]> queries) {
    ArrayList<Integer> result = new ArrayList<>();
    for (int[] query : queries) {
      result.add(primes(query));
    }

    return result;
  }

  // T - O(R-L * sqrt(R))
  private int primes(int[] query) {
    int from = query[0];
    int to = query[1];

    int count = 0;
    for (int i = from; i <= to; i++) {
      if (isPrime(i)) {
        count++;
      }
    }

    return count;
  }

  // T - O(sqrt(num))
  private boolean isPrime(int num) {
    if (num < 2) {
      return false;
    }
    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }
}

class Solution2 {

  public ArrayList<Integer> primesInRange(ArrayList<int[]> queries) {
    ArrayList<Integer> result = new ArrayList<>();
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int[] query : queries) {
      min = Math.min(min, query[0]);
      max = Math.max(max, query[1]);
    }

    List<Integer> primes = primeNumbers(max);

    for (int[] query : queries) {
      int count = 0;
      int i = 0;
      while (i < primes.size() && primes.get(i) <= query[1]) {
        if (primes.get(i) >= query[0]) {
          count++;
        }
        i++;
      }
      result.add(count);
    }

    return result;
  }

  private List<Integer> primeNumbers(int end) {
    List<Integer> primes = new ArrayList<>();
    boolean[] prime = new boolean[end + 1];
    Arrays.fill(prime, true);

    for (int i = 2; i <= end; i++) {
      if (prime[i]) {
        primes.add(i);
        for (long j = (long) i * i; j <= end; j += i) {
          prime[(int) j] = false;
        }
      }
    }
    return primes;
  }
}
