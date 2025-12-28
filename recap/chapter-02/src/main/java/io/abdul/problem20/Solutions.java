package io.abdul.problem20;

import java.util.ArrayList;
import java.util.Arrays;

public class Solutions {

}

class Solution {
  public ArrayList<Integer> primesInRange(ArrayList<int[]> queries) {
    int max = 0;
    for (int[] query : queries) {
      max = query[1] > max ? query[1] : max;
    }

    int[] primePrefixSum = primePrefixSum(max);
    ArrayList<Integer> result = new ArrayList<>(queries.size());

    for (int[] query : queries) {
      result.add(primePrefixSum[query[1]] - primePrefixSum[query[0] - 1]);
    }

    return result;
  }

  private int[] primePrefixSum(int n) {
    boolean[] primes = new boolean[n + 1];
    int[] result = new int[n + 1];
    Arrays.fill(primes, true);

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= n; i++) {
      if (primes[i]) {
        result[i] = result[i - 1] + 1;
        if (i <= sqrt) {
          for (long j = (long) i * i; j <= n; j += i) {
            primes[(int) j] = false;
          }
        }
      } else {
        result[i] = result[i - 1];
      }
    }

    return result;
  }
}

