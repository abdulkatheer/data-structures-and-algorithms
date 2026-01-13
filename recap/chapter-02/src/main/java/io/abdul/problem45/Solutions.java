package io.abdul.problem45;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

// https://leetcode.com/problems/largest-component-size-by-common-factor
// tag:math tag:smallest_prime_factor tag:disjoint_set tag:union_find
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    solution.largestComponentSize(new int[] {4,6,15,35});
    Assertions.assertEquals(8,
        solution.largestComponentSize(new int[]{99, 100, 69, 39, 14, 56, 91, 60}));
  }
}

/*
T - O(n^2 log num)
S - O(n)

ERROR: TLE
 */
class Solution {

  public int largestComponentSize(int[] nums) {
    DisjointSet ds = new DisjointSet(nums.length);

    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (gcd(nums[i], nums[j]) > 1) {
          ds.union(i, j);
        }
      }
    }

    return ds.largestComponentSize();
  }

  private int gcd(int a, int b) {
    if (b > a) {
      int temp = a;
      a = b;
      b = temp;
    }

    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}

class DisjointSet {

  private final int[] parents;
  private final int[] sizes;

  DisjointSet(int n) {
    parents = new int[n];
    sizes = new int[n];

    Arrays.fill(sizes, 1);

    for (int i = 0; i < n; i++) {
      parents[i] = i;
    }
  }

  void union(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    if (uUltimateParent == vUltimateParent) {
      return;
    }

    if (sizes[uUltimateParent] > sizes[vUltimateParent]) {
      parents[vUltimateParent] = uUltimateParent;
      sizes[uUltimateParent] += sizes[vUltimateParent];
    } else if (sizes[vUltimateParent] > sizes[uUltimateParent]) {
      parents[uUltimateParent] = vUltimateParent;
      sizes[vUltimateParent] += sizes[uUltimateParent];
    } else {
      parents[uUltimateParent] = vUltimateParent;
      sizes[vUltimateParent] += sizes[uUltimateParent];
    }
  }

  int findUltimateParent(int x) {
    while (parents[x] != x) {
      x = parents[x];
    }

    return x;
  }

  int largestComponentSize() {
    int max = Integer.MIN_VALUE;
    for (int size : sizes) {
      max = Math.max(max, size);
    }

    return max;
  }
}

/*
T - O(n * log m) less time
S - O(n), more memory usage

If we can get the prime factors of the numbers and let's say there're x unique prime factors for all nums in total
we just need to find if the same prime factor is shared across any other node and union them
*/
class Solution2 {

  public int largestComponentSize(int[] nums) {
    DisjointSet ds = new DisjointSet(nums.length);

    Map<Integer, Integer> pfMap = new HashMap<>();

    int maxNum = Integer.MIN_VALUE;
    for (int num : nums) {
      maxNum = Math.max(maxNum, num);
    }
    int[] spf = smallestPrimeFactors(maxNum);

    for (int i = 0; i < nums.length; i++) {
      for (int p : findPrimeFactors(nums[i], spf)) {
        if (p == 1) {
          continue; // we want share to be > 1
        }
        if (pfMap.containsKey(p)) { // some other element in the past share this
          ds.union(i, pfMap.get(p));
        } else {
          pfMap.put(p, i); // storing first index who has p as prime factor
        }
      }
    }

    return ds.largestComponentSize();
  }

  private int[] smallestPrimeFactors(int n) {
    int[] spf = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      spf[i] = i;
    }

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (spf[i] == i) { // prime
        for (long j = (long) i * i; j <= n; j += i) {
          if (spf[(int) j] == j) { // no spf set yet, set the first prime as spf
            spf[(int) j] = i;
          }
        }
      }
    }

    return spf;
  }

  private List<Integer> findPrimeFactors(int num, int[] spf) {
    List<Integer> pf = new ArrayList<>();
    while (num != 1) {
      int p = spf[num];
      pf.add(p);

      while (num % p == 0) {
        num /= p;
      }
    }

    return pf;
  }
}

/*
Optimal
T - O(n * sqrt(n)), little more time than Solution2 as we try nums upto sqrt(num) instead of just using spf[num]
S - O(n), less space as no pre-computation (spf) done

Instead of find SPF separately, we merge it into the loop
 */
class Solution3 {

  public int largestComponentSize(int[] nums) {
    int n = nums.length;
    DisjointSet dsu = new DisjointSet(n);

    int maxNum = 0;
    for (int num : nums) {
      maxNum = Math.max(maxNum, num);
    }

    int[] factorFirstIndex = new int[maxNum + 1];
    Arrays.fill(factorFirstIndex, -1);

    for (int i = 0; i < n; i++) {
      int num = nums[i];

      for (int f = 2; f * f <= num; f++) { // for each factor of the number
        if (num % f != 0) {
          continue;
        }

        if (factorFirstIndex[f] != -1) { // if someone in the past shared this factor
          dsu.union(factorFirstIndex[f], i);
        } else { // otherwise mark the first num having this factor
          factorFirstIndex[f] = i;
        }

        while (num % f == 0) {
          num /= f;
        }
      }

      // for the last factor
      if (num > 1) {
        if (factorFirstIndex[num] != -1) {
          dsu.union(factorFirstIndex[num], i);
        } else {
          factorFirstIndex[num] = i;
        }
      }
    }

    return dsu.largestComponentSize();
  }
}