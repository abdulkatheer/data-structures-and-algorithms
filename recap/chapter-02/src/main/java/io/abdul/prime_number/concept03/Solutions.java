package io.abdul.prime_number.concept03;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solutions {

}

/*
Segmented Sieve

When to use?
- Memory-constrained environment
- To find primes between range L-R, but range is too small
- Used when n is large (up to 10^12)
- We can fix the segment size, but primes needed is always sqrt(R)

When this breaks?
1) (int) Math.sqrt(R) - If R is bigger than 4611686014132420609, then sqrt will be bigger than Int.MAX.
sqrt(Long.MAX) -> 3037000499, but Integer.MAX = 2147483647
2) boolean[] segmentComposites = new boolean[(int) (R - L + 1)]; - If R-L > Int.MAX, then we can't create array to hold it

 */
class Solution {

  public List<Long> findPrimes(long L, long R) {
    // To find primes between L and R, we need the primes up to sqrt(n)
    // With those we can sieve the composites between L and R, and we can take remains as primes
    // We can use classic Sieve method to find primes up to sqrt(R)
    List<Integer> primesR = findPrimes((int) Math.sqrt(R));

    // We need to Sieve composites between L and R using primesR
    // let's say we've p1, how do we locate first prime between L and R
    // THIS means R-L should fit in Integer, otherwise we need to segment further
    boolean[] segmentComposites = new boolean[(int) (R - L + 1)];
    for (int p : primesR) {
      // remember, to mark composites, we started from p*p and then +p
      // start = max(p*p, ceil(L/p))
      // ceil(L/p)? we can do + p - 1, it'll complete L to the next possible prime
      // Let's say p=7, L=57, we need 9 as result
      // 57 + 7 - 1 = 63/7 = 9
      // 58 + 7 - 1 = 64/7 = 9
      // 62 + 7 - 1 = 68/7 = 9
      // 63 + 7 - 1 = 69/7 = 9
      long start = Math.max((long) p * p, ((L + p - 1) / p) * p);
      for (long i = start; i <= R; i += p) {
        segmentComposites[(int) (i - L)] = true; // L=0, L+1=1 ....
      }
    }

    List<Long> primes = new ArrayList<>();
    for (int i = 0; i < segmentComposites.length; i++) {
      if (!segmentComposites[i]) {
        long p = i + L;
        if (p > 1) {
          primes.add(p);
        }
      }
    }

    return primes;
  }

  // Classic Sieve of Eratosthenes
  private List<Integer> findPrimes(int n) {
    boolean[] composites = new boolean[n + 1];
    List<Integer> primes = new ArrayList<>();

    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
        markComposites(composites, i);
      }
    }

    return primes;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = (long) x * x; i < composites.length; i += x) {
      composites[(int) i] = true;
    }
  }
}

/*
Multiple-segments

Solves issues in single-segment problem
1) boolean[] segmentComposites = new boolean[(int) (R - L + 1)]; - If R-L > Int.MAX, then we can't create array to hold it
We can limit the segment size to some num less than Int.MAX, let's say 10^9

Issues
1) (int) Math.sqrt(R) - If R is bigger than 4611686014132420609, then sqrt will be bigger than Int.MAX.
 */
class Solution2 {

  public List<Long> findPrimes(long L, long R) {
    int segmentSize = (int) 1e3; // 1_000

    long sqrt = (long) Math.sqrt(R);

    if (sqrt > Integer.MAX_VALUE) {
      throw new IllegalArgumentException(
          "This solution can handle only up to R=4611686014132420609");
    }

    List<Integer> primesR = findPrimes((int) sqrt);// Works only up to some num

    List<Long> primes = new ArrayList<>();
    /*
    Let segmentSize=10, L=25, R=73
    25 - 34
    35 - 44
    45 - 54
    55 - 64
    65 - 73 - min(74, 73)
     */
    for (long segmentL = L; segmentL <= R; segmentL += segmentSize) {
      long segmentR = Math.min(segmentL + segmentSize - 1, R);

      primes.addAll(findPrimesSegment(segmentL, segmentR, primesR));
    }

    return primes;
  }

  // R-L+1 <= Int.MAX
  /*
  25 - 34 -> 34 - 25 + 1 = 10
  35 - 44 -> 44 - 35 + 1 = 10
  45 - 54 ,,
  55 - 64 ,,
  65 - 73 -> 73 - 65 + 1 = 9
   */
  private List<Long> findPrimesSegment(long L, long R, List<Integer> primesR) {
    List<Long> primes = new ArrayList<>();

    boolean[] segmentComposites = new boolean[(int) (R - L + 1)];
    for (Integer p : primesR) {
      long start = Math.max((long) p * p, ((L + p - 1) / p) * p);
      for (long i = start; i <= R; i += p) {
        segmentComposites[(int) (i - L)] = true;
      }
    }

    for (int i = 0; i < segmentComposites.length; i++) {
      long p = i + L;
      if (!segmentComposites[i] && p > 1) {
        primes.add(p);
      }
    }

    return primes;
  }

  private List<Integer> findPrimes(int n) {
    List<Integer> primes = new ArrayList<>();
    boolean[] composites = new boolean[n + 1];

    for (int i = 2; i <= n; i++) {
      if (!composites[i]) {
        primes.add(i);
        markComposites(composites, i);
      }
    }

    return primes;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = (long) x * x; i < composites.length; i += x) {
      composites[(int) i] = true;
    }
  }
}

/*
PROOF : Why segmented sieve may not work beyond 4611686014132420609
We tried making Classic Sieve to accept more than that. Failed!

Multiple-segments - Incorrect Solution

Solves issues in single-segment problem
1) boolean[] segmentComposites = new boolean[(int) (R - L + 1)]; - If R-L > Int.MAX, then we can't create array to hold it
We can limit the segment size to some num less than Int.MAX, let's say 10^9
2) (int) Math.sqrt(R) -> We can use Multiple fixed boolean[] to Sieve
 */
class Solution3 {

  public static void main(String[] args) {
    Solution3 solution = new Solution3();
    System.out.println(solution.findPrimes(0L, 798L).size());
    System.out.println(solution.findPrimes(Long.MAX_VALUE - 1000, Long.MAX_VALUE).size());
  }

  public List<Long> findPrimes(long L, long R) {
    int segmentSize = (int) 1e3; // 1_000

    long sqrt = (long) Math.sqrt(R);

    List<Long> primesR = findPrimes(sqrt);// Works only up to some num

    List<Long> primes = new ArrayList<>();
    /*
    Let segmentSize=10, L=25, R=73
    25 - 34
    35 - 44
    45 - 54
    55 - 64
    65 - 73 - min(74, 73)
     */
    for (long segmentL = L; segmentL <= R; segmentL += segmentSize) {
      long segmentR = Math.min(segmentL + segmentSize - 1, R);

      primes.addAll(findPrimesSegment(segmentL, segmentR, primesR));
    }

    return primes;
  }

  // R-L+1 <= Int.MAX
  /*
  25 - 34 -> 34 - 25 + 1 = 10
  35 - 44 -> 44 - 35 + 1 = 10
  45 - 54 ,,
  55 - 64 ,,
  65 - 73 -> 73 - 65 + 1 = 9
   */
  private List<Long> findPrimesSegment(long L, long R, List<Long> primesR) {
    List<Long> primes = new ArrayList<>();

    boolean[] segmentComposites = new boolean[(int) (R - L + 1)];
    for (Long p : primesR) {
      long start = Math.max(p * p, ((L + p - 1) / p) * p); // p * p may overflow, p itself may be greater than sqrt(Long.MAX_VALUE)
      for (long i = start; i <= R; i += p) {
        segmentComposites[(int) (i - L)] = true;
      }
    }

    for (int i = 0; i < segmentComposites.length; i++) {
      long p = i + L;
      if (!segmentComposites[i] && p > 1) {
        primes.add(p);
      }
    }

    return primes;
  }

  private List<Long> findPrimes(long n) {
    n = n + 1;
    List<Long> primes = new LinkedList<>(); // ArrayList might fail as it can't find GB of consecutive space, but it's virtual memory right?
    int segmentSize = (int) 1e5; // Bigger to keep numberOfSegments <= Int.MAX
    int numOfSegments = (int) ((n + segmentSize - 1) / segmentSize);
    int lastSegmentSize = (int) (n % segmentSize) + 1;
    boolean[][] composites = new boolean[numOfSegments][];

    for (int i = 0; i < numOfSegments - 1; i++) {
      composites[i] = new boolean[segmentSize];
    }
    composites[numOfSegments - 1] = new boolean[lastSegmentSize];

    /*
    segmentSize = 10
    n = 38, so n = 39
    numOfSegments = (39 + 10 - 1) / 10 = 48 / 10 = 4
    lastSegmentSize = 39 % 10 = 9

    0 to 9 = 1
    10 to 19 = 2
    20 to 29 = 3
    30 to 39 = 4

    i = 7
    segment = 7 / 10 = 0
    segmentPos = 7 - (0 * 10) = 7

    i = 13
    segment = 13 / 10 = 1
    segmentPos = 13 - (1 * 10) = 3
     */
    for (long i = 2; i <= n; i++) {
      int segment = (int) (i / segmentSize);
      int segmentPos = (int) (i - (segment * segmentSize));
      if (!composites[segment][segmentPos]) {
        primes.add(i);
        markComposites(composites, n, i, segmentSize);
      }
    }

    return primes;
  }

  private void markComposites(boolean[][] composites, long n, long x, int segmentSize) {
    for (long i = x * x; i <= n; i += x) { // x * x may overflow as x may be > sqrt(Long.MAX_VALUE)
      int segment = (int) (i / segmentSize);
      int segmentPos = (int) (i - (segment * segmentSize));
      composites[segment][segmentPos] = true;
    }
  }
}