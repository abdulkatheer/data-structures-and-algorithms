package io.abdul.problem19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    System.out.println(solution.primeFactors(new int[]{2, 3, 4, 5, 6}));
  }
}

class Solution {

  public List<List<Integer>> primeFactors(int[] queries) {
    int max = 0;
    for (int query : queries) {
      max = query > max ? query : max;
    }

    List<Integer> primes = primes(max);

    List<List<Integer>> result = new ArrayList<>();
    for (int query : queries) {
      if (query <= 1) {
        result.add(List.of(query));
        continue;
      }
      List<Integer> pf = new ArrayList<>();
      int sqrt = (int) Math.sqrt(query);
      for (int i = 0; i < primes.size(); i++) {
        int prime = primes.get(i);
        if (query == 1 || prime > sqrt) {
          break;
        }
        if (query % prime == 0) {
          pf.add(prime);
          query /= prime;
        }
      }

      if (query > 1) {
        pf.add(query);
      }

      result.add(pf);
    }

    return result;
  }

  // Sieve of Eratosthenes
  private List<Integer> primes(int n) {
    boolean[] primes = new boolean[n + 1];
    List<Integer> result = new ArrayList<>();
    Arrays.fill(primes, true);

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (primes[i]) {
        result.add(i);
        for (long j = (long) i * i; j <= n; j += i) {
          primes[(int) j] = false;
        }
      }
    }
    return result;
  }
}

