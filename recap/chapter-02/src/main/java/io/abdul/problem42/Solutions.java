package io.abdul.problem42;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://leetcode.com/problems/simplified-fractions/
// tag:math
public class Solutions {

}

/*
Brute
T - O(n^2)
S - O(n^2)
*/
class Solution {
  public List<String> simplifiedFractions(int n) {
    Set<Double> fractions = new HashSet<>();
    List<String> result = new ArrayList<>();
    for (int i = 1; i < n; i++) { // numerator
      for (int j = i + 1; j <= n; j++) { // denominator
        double f = (double) i / j;
        if (!fractions.contains(f)) {
          fractions.add(f);
          result.add(i + "/" + j);
        }
      }
    }

    return result;
  }
}

/*
Optimal
T - O(n^2 log n)
S - O(1)
*/
class Solution2 {
  public List<String> simplifiedFractions(int n) {
    List<String> result = new ArrayList<>();
    for (int i = 1; i < n; i++) { // numerator
      for (int j = i + 1; j <= n; j++) { // denominator
        if (gcd(j, i) == 1) { // if not, this can be simplified to i/gcd / j/gcd
          result.add(i + "/" + j);
        }
      }
    }

    return result;
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}
