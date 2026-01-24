package io.abdul.problem53;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/count-ways-to-make-array-with-product/
// tag:math tag:dynamic_programming tag:top_1p
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertArrayEquals(new int[]{4, 1, 50734910},
        solution.waysToFillArray(new int[][]{{2, 6}, {5, 1}, {73, 660}}));
  }
}

/*
Stars and Bars pattern
stars = 5 bars = 2 (or 3 boxes)
* * * * * | |
In how many ways we can arrange this? or how many ways we can put them in boxes?

How many ways we can position bars?
C(bars) = (5+2  2)
C(stars) = (5+2  5)

C(bars) = n!/(n-r)! r! = 7! / 5! * 2!
C(stars) = 7! / 2! * 5!
---
Now, to get products, using multiplication is difficult.
So if we fine prime factors, we can convert this to addition.
36 = 2^2 * 3^2
n = 4

We need to arrange 2 2s and 2 3s in 4 boxes.
Primes are independent, so we can find this for each prime and
all their results together forms total possibilities

1) How many ways we can arrange 2 2s in 4 slots
10 possible ways
2 2 | | | = 4 _ _ _
2 | 2 | | = 2 2 _ _
2 | | 2 | = 2 _ 2 _
2 | | | 2 = 2 _ _ 2

| 2 2 | | = _ 4 _ _
| 2 | 2 | = _ 2 2 _
| 2 | | 2 = _ 2 _ 2

| | 2 2 | = _ _ 4 _
| | 2 | 2 = _ _ 2 2

| | | 2 2 = _ _ _ 4

2) How many ways we can arrange 2 3s in 4 slots
10 possible ways
3 3 | | | = 9 _ _ _
3 | 3 | | = 3 3 _ _
3 | | 3 | = 3 _ 3 _
3 | | | 3 = 3 _ _ 3

| 3 3 | | = _ 9 _ _
| 3 | 3 | = _ 3 3 _
| 3 | | 3 = _ 3 _ 3

| | 3 3 | = _ _ 9 _
| | 3 | 3 = _ _ 3 3

| | | 2 2 = _ _ _ 9

So 10 possibilities from 2 and 10 from 3 are forming 10*10 possible arrangements.

Ex: 4 _ _ _ can go with all 10 combinations of 3

What about empty slots?
4 9 _ _ ?
we can fill it with 1s

What about 36 itself?
it'll be taken care in above combinations
4 _ _ _ and 9 _ _ _ -> 36 _ _ _

*/
class Solution {

  private static final int MOD = ((int) 1e9) + 7;

  public int[] waysToFillArray(int[][] queries) {
    int max = -1;
    for (int[] query : queries) {
      max = Math.max(max, query[1]);
    }

    int[] spf = smallestPrimeFactor(max);

    int[][] nCrMod = nCrMod();

    int[] result = new int[queries.length];
    for (int i = 0; i < queries.length; i++) {
      int[] query = queries[i];
      int n = query[0];
      int k = query[1];

      if (k == 1) {
        result[i] = 1;
        continue;
      }

      Map<Integer, Integer> primeFactors = primeFactors(k, spf);

      long totalCombinations = 1;
      int b = n - 1;
      for (Map.Entry<Integer, Integer> pf : primeFactors.entrySet()) {
        // stars - number of primes
        // boxes - n, bars - n-1
        int s = pf.getValue();
        int combinations = nCrMod[s + b][s];
        totalCombinations = (totalCombinations * combinations) % MOD;
      }

      result[i] = (int) totalCombinations;
    }

    return result;
  }

  private int[] smallestPrimeFactor(int n) {
    int[] spf = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      spf[i] = i;
    }

    for (int i = 2; i <= n; i++) {
      if (spf[i] == i) { // prime
        for (long j = (long) i * i; j <= n; j += i) {
          if (spf[(int) j] == j) { // not updated yet
            spf[(int) j] = i;
          }
        }
      }
    }

    return spf;
  }

  /*
  nCr = n! / (n-r)! * r!
  Mod in division requires inverse;

  As mod is prime, we can apply Fermat's theorem
  (n-r)^-1 = (n-r)^m-2

  Instead of doing multiplication and division, we can precompute the combinations for all n and r
  As per problem n <= 10^4 and k<=10^4
  If k<=10^4, the max number of any factor for 10^4 is 33 (check it out)
  So 10000 + 33 = 10033
  Roughly we'll take 10049 as n and 32 as r
  1
  1 1
  1 2 1
  1 3 3 1
  1 4 6 4 1
  ....
  */
  private int[][] nCrMod() {
    int[][] nCr = new int[10050][33];

    // handling 0-choose separately to avoid out of bounds check
    for (int i = 0; i < 10050; i++) {
      nCr[i][0] = 1;
    }

    for (int i = 1; i < 10050; i++) {
      for (int j = 1; j < 33; j++) {
        nCr[i][j] = (nCr[i - 1][j - 1] + nCr[i - 1][j]) % MOD;
      }
    }

    return nCr;
  }

  private Map<Integer, Integer> primeFactors(int num, int[] spf) {
    Map<Integer, Integer> result = new HashMap<>();
    while (num != 1) {
      result.put(spf[num], result.getOrDefault(spf[num], 0) + 1);
      num /= spf[num];
    }

    return result;
  }
}

// Memory Efficient
class Solution2 {

  private static final int MOD = ((int) 1e9) + 7;

  public int[] waysToFillArray(int[][] queries) {
    int max = -1;
    for (int[] query : queries) {
      max = Math.max(max, query[1]);
    }

    int[][] nCrMod = nCrMod();
    int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
        53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

    int[] result = new int[queries.length];
    for (int i = 0; i < queries.length; i++) {
      int[] query = queries[i];
      int n = query[0];
      int k = query[1];

      if (k == 1) {
        result[i] = 1;
        continue;
      }

      long totalCombinations = 1;
      int b = n - 1;

      for (int prime : primes) {
        int s = 0;
        while (k % prime == 0) {
          s++;
          k /= prime;
        }

        // if s = 0, it's fine, as nC0 is always 1
        // stars - number of primes
        // boxes - n, bars - n-1
        int combinations = nCrMod[s + b][s];
        totalCombinations = (totalCombinations * combinations) % MOD;
      }

      if (k != 1) { // the left out is prime too and only one factor
        // nC1 is always n or you can pick from nCrMod
        totalCombinations = (totalCombinations * nCrMod[1 + b][1]) % MOD;
      }

      result[i] = (int) totalCombinations;
    }

    return result;
  }

  /*
  nCr = n! / (n-r)! * r!
  Mod in division requires inverse;

  As mod is prime, we can apply Fermat's theorem
  (n-r)^-1 = (n-r)^m-2

  Instead of doing multiplication and division, we can precompute the combinations for all n and r
  As per problem n <= 10^4 and k<=10^4
  If k<=10^4, the max number of any factor for 10^4 is 13 (check it out)
  So 10000 + 13 - 1 = 10012
  Roughly we'll take 10012 as n and 13 as r
  1
  1 1
  1 2 1
  1 3 3 1
  1 4 6 4 1
  ....
  */
  private int[][] nCrMod() {
    int n = 10012;
    int r = 13;
    int[][] nCr = new int[n + 1][r + 1];

    // handling 0-choose separately to avoid out of bounds check
    for (int i = 0; i <= n; i++) {
      nCr[i][0] = 1;
    }

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= r; j++) {
        nCr[i][j] = (nCr[i - 1][j - 1] + nCr[i - 1][j]) % MOD;
      }
    }

    return nCr;
  }
}