package io.abdul.problem06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.com/problems/closest-prime-numbers-in-range/
// tag:math tag:prime
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(Arrays.toString(solution.closestPrimes(19, 31)));
  }
}

/*
More memory - More time
 */
class Solution {
  public int[] closestPrimes(int left, int right) {
    List<Integer> primes = new ArrayList<>();
    for (int i = left; i <= right; i++) {
      if (isPrime(i)) {
        primes.add(i);
      }
    }

    if (primes.size() < 2) {
      return new int[] {-1, -1};
    }

    if (primes.size() == 2) {
      return new int[] {primes.get(0), primes.get(1)};
    }

    int p1 = primes.get(0);
    int p2 = primes.get(1);
    for (int i = 2; i < primes.size(); i++) {
      int newP1 = primes.get(i-1);
      int newP2 = primes.get(i);
      if ((newP2 - newP1) < (p2-p1)) {
        p1 = newP1;
        p2 = newP2;
      }
    }

    return new int[] {p1, p2};
  }

  private boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }

    int sqrt = (int) Math.sqrt(n);

    for (int i = 2; i <= sqrt; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }
}

/*
More memory - Less time
 */
class Solution2 {
  public int[] closestPrimes(int left, int right) {
    List<Integer> primes = new ArrayList<>();
    boolean[] composites = new boolean[right + 1];

    for (int i = 2; i <= right; i++) {
      if (!composites[i]) {
        if (i >= left && i <= right) {
          primes.add(i);
        }
        markComposites(composites, i);
      }
    }

    if (primes.size() < 2) {
      return new int[] { -1, -1 };
    }

    if (primes.size() == 2) {
      return new int[] { primes.get(0), primes.get(1) };
    }

    int p1 = primes.get(0);
    int p2 = primes.get(1);
    for (int i = 2; i < primes.size(); i++) {
      int newP1 = primes.get(i - 1);
      int newP2 = primes.get(i);
      if ((newP2 - newP1) < (p2 - p1)) {
        p1 = newP1;
        p2 = newP2;
      }
    }

    return new int[] { p1, p2 };
  }

  private boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }

    int sqrt = (int) Math.sqrt(n);

    for (int i = 2; i <= sqrt; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }

  private void markComposites(boolean[] composites, int x) {
    for (long i = x; i * x < composites.length; i++) {
      composites[(int) (i * x)] = true;
    }
  }
}

/*
Less memory - More time
 */
class Solution3 {
  public int[] closestPrimes(int left, int right) {
    int p1 = -1;
    int p2 = -1;
    int prevPrime = -1;

    for (int i = left; i <= right; i++) {
      if (isPrime(i)) {
        if (p1 == -1) { // no prime found yet
          p1 = i;
        } else if (p2 == -1) { // only one prime found so far
          p2 = i;
        } else if (prevPrime == -1) { // we've two best prime, now check immediate next prime with p2
          int newPrime = i;

          if ((newPrime - p2) < (p2 - p1)) { // if p2 and immediate next prime is good, we replace the best
            p1 = p2;
            p2 = newPrime;
          } else {
            prevPrime = newPrime;
          }
        } else { // we've two best primes, and the immediate next prime to it
          int newPrime = i;

          if ((newPrime - prevPrime) < (p2 - p1)) {
            p1 = prevPrime;
            p2 = newPrime;
            prevPrime = -1;
          } else {
            prevPrime = newPrime;
          }
        }
      }
    }

    if (p1 != -1 && p2 != -1) {
      return new int[] { p1, p2 };
    } else {
      return new int[] { -1, -1 };
    }

  }

  private boolean isPrime(int num) {
    if (num < 2) {
      return false;
    }
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
Optimal
Less memory - Less time
 */
class Solution4 {

  public int[] closestPrimes(int left, int right) {
    // Step 1: Handle special (2, 3) case
    if (left <= 2 && right >= 3)
      return new int[] { 2, 3 };

    int prevPrime = -1, closestA = -1, closestB = -1;
    int minDifference = (int) 1e6;

    // Step 2: Iterate and find primes
    for (int candidate = left; candidate <= right; candidate++) {
      if (isPrime(candidate)) {
        if (prevPrime != -1) {
          int difference = candidate - prevPrime;
          if (difference < minDifference) {
            minDifference = difference;
            closestA = prevPrime;
            closestB = candidate;
          }
          // Twin prime optimization
          if (difference == 2)
            return new int[] { prevPrime, candidate };
        }
        prevPrime = candidate;
      }
    }

    // Step 3: Return result
    return new int[] { closestA, closestB };
  }

  private boolean isPrime(int number) {
    if (number < 2)
      return false;
    if (number == 2 || number == 3)
      return true;
    if (number % 2 == 0)
      return false;
    for (int divisor = 3; divisor * divisor <= number; divisor += 2) {
      if (number % divisor == 0)
        return false;
    }
    return true;
  }
}