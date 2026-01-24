package io.abdul.problem54;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://leetcode.com/problems/minimum-number-of-operations-to-make-string-sorted/
// tag:math tag:string tag:top_1p
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
    assertEquals(5, solution.makeStringSorted("cba"));
    assertEquals(2, solution.makeStringSorted("aabaa"));
    assertEquals(1099, solution.makeStringSorted("cabbcdba"));
    assertEquals(982157772, solution.makeStringSorted("leetcodeleetcodeleetcode"));
  }
}

// Bruteforce
class Solution {

  public int makeStringSorted(String s) {
    char[] ss = s.toCharArray();

    int count = 0;
    int i = findI(ss);
    while (i != -1) {
      int j = findJ(ss, i);
      count++;
      swap(ss, i - 1, j);
      reverse(ss, i);

      i = findI(ss);
    }

    return count;
  }

  private int findI(char[] ss) {
    int r = -1;
    for (int i = 1; i < ss.length; i++) {
      if (ss[i] < ss[i - 1]) {
        r = i;
      }
    }

    return r;
  }

  private int findJ(char[] ss, int i) {
    int j = i;
    while (j < ss.length && ss[j] < ss[i - 1]) {
      j++;
    }

    return j - 1;
  }

  /**
   * 0 1 2 3 4 5 6
   * <p>
   * i = 2 2-6 0 3-5 1
   * <p>
   * mid = 7-2 / 2 = 2 0+2 7-1-0 1+2 7-1-1
   * <p>
   * i = 1 1-6 0 2-5 1 3-4 2
   * <p>
   * mid = 7-1 / 2 = 3 0+1 7-1-0 1+1 7-1-1 1+2 7-1-2
   * <p>
   * i = 3 3-6 0 4-5 1
   */
  private void reverse(char[] ss, int i) {
    int mid = (ss.length - i + 1) / 2;
    for (int k = 0; k < mid; k++) {
      int src = k + i;
      int dest = ss.length - 1 - k;
      swap(ss, src, dest);
    }
  }

  private void swap(char[] ss, int src, int dest) {
    char temp = ss[src];
    ss[src] = ss[dest];
    ss[dest] = temp;
  }
}

/*
All permutations of a string s!
What we're looking for?
First permutation is where all characters are in ascending order
Last permutation is where all are in descending order, meaning sorted
Bcz of duplicates, we may end up finding ascending order sooner than last permutation

We're somewhere in between first and last and we need to go to first
The 4 step process is actually converting s to the next greater permutation
We move step by step to find the fully sorted permutation

So we need to count number of lexicographically smaller permutations than a

abc - 0 -> smallest
acb - 1
bac - 2
bca - 3
cab - 4
cba - 5 -> largest

if we start at acb,
we go to acb -> abc
1 step

if cba
cba -> cab -> bca -> bac -> acb -> abc
5 steps

So to rephrase the problem,
given a string s, how many smaller permutations exist to reach the smallest (sorted)
Remeber, permutations are arrangements. Duplicate elements are allowed like aabcc, but duplicate arrangements are not counted like 2 aabcc. We need to remove duplicates as well.

Ex: cabbcdba ---> abbbccd
1) s=c*******
How many chars are smaller than c?
if we replace c with them, all of them will be smaller than s.
a b b b a are smaller than c
a has 7! permutations
b has 7! permutations
similarly b b and a also have 7! permutations individually.

So total permutations 5 * 7!
Duplicates - a - 2, b - 3, c - 2
so divide by 2!, 3!, 2!

sum += (5 * 7!) / (2! * 3! * 2!)

2) s=ca******
How many are smaller than a? - None

3) s=cab*****
How many are smaller than b? - a
a has 5! permutations at position 3

So total permutations 5!
Duplicates - b - 3

sum += 5! / 3!

4) s=cabb****
How many are smaller than b? - a
a has 4! permutations at position 4

So total permutations 5!
Duplicates - b - 2

sum += 4! / 2!

5) s=cabbc***
How many are smaller than c? b and a
b has 3! permutations at position 5
a has the same

total permutations - 2 * 3!
Duplicates - None

sum += 2 * 3!

6) s=cabbcd**
How many are smaller than d? b and a
b has 2! permutations at postion 6
a has same

total permutations - 2 * 2!
Duplicates - None

sum += 2 * 2!

7) s=cabbcdb*
How many are smaller than b? - a
a has 1! permutations at position 7

total permutations - 1!
Duplicates - None

sum += 1!
*/
class Solution2 {
  private static final int MOD = ((int) 1e9) + 7;

  public int makeStringSorted(String s) {
        /*
        at each position we need duplicates to the right and number of smaller chars to the right
        */
    char[] ss = s.toCharArray();
    int[] charFreq = new int[26];
    int[] factorial = buildFactorial(ss.length);

    long total = 0;
    for (int i = ss.length-1; i >= 0; i--) {
      int pos = ss[i] - 'a';
      charFreq[pos]++;

      int smaller = 0;
      for (int j = 0; j < pos; j++) {
        smaller += charFreq[j];
      }

      long permutationsAtI = ((long) smaller * factorial[ss.length-i-1]) % MOD;

      // removing duplicates
      for (int j = 0; j < 26; j++) {
        if (charFreq[j] > 1) {
          permutationsAtI = (permutationsAtI * modularInverse(factorial[charFreq[j]])) % MOD;
        }
      }

      total = (total + permutationsAtI) % MOD;
    }

    return (int) total;
  }

  int[] buildFactorial(int n) {
    int[] fact = new int[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; i++) {
      fact[i] = (int) (((long) fact[i - 1] * i) % MOD);
    }

    return fact;
  }

  private int pow(int b, int e) {
    long result = 1;
    long base = b;
    long exponent = e;
    while (exponent > 0) {
      if ((exponent & 1) == 1) { // Odd
        result = (result * base) % MOD;
      }
      base = (base * base) % MOD;
      exponent >>= 1; // exponent / 2
    }

    return (int) result;
  }

  private int modularInverse(int num) {
    // as mod is prime, we can use Fermat's little theorem
    return pow(num, MOD - 2);
  }
}

class Solution3 {
  static final long MOD = 1_000_000_007L;

  long[] fact;   // factorial cache

  // Precompute factorials up to n
  void buildFactorial(int n) {
    fact = new long[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; i++) {
      fact[i] = (fact[i - 1] * i) % MOD;
    }
  }

  // Fast power: (base^exp) % MOD
  long modPow(long base, long exp) {
    long res = 1;
    base %= MOD;
    while (exp > 0) {
      if ((exp & 1) == 1) res = (res * base) % MOD;
      base = (base * base) % MOD;
      exp >>= 1;
    }
    return res;
  }

  public int makeStringSorted(String s) {
    int n = s.length();
    buildFactorial(n);

    int[] cnt = new int[26];
    long out = 0;

    // traverse from right to left
    for (int i = n - 1; i >= 0; i--) {
      int ind = s.charAt(i) - 'a';
      cnt[ind]++;

      // count smaller characters already seen
      long smaller = 0;
      for (int j = 0; j < ind; j++) {
        smaller += cnt[j];
      }

      long ans = (smaller * fact[n - i - 1]) % MOD;

      // divide by factorial of frequencies
      for (int j = 0; j < 26; j++) {
        if (cnt[j] > 1) {
          ans = (ans * modPow(fact[cnt[j]], MOD - 2)) % MOD;
        }
      }

      out = (out + ans) % MOD;
    }
    return (int) out;
  }
}
