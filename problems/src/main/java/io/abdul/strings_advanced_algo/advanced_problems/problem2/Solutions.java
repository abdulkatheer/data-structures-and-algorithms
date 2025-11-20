package io.abdul.strings_advanced_algo.advanced_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
//    Solution3 solution = new Solution3();

    assertArrayEquals(new int[]{0, 1, 0, 0, 4, 1, 0, 0, 0, 8, 1, 0, 0, 5, 1, 0, 0, 1, 0},
        solution.computeZArray("ttfxttfxzttfxttfxtz"));
  }
}

/*
Brute-force
T - O(n^2)
S - O(n)
 */
class Solution {

  public List<Integer> search(String text, String pattern) {
    int n = text.length();
    int p = pattern.length();

    if (n == 0 || p == 0 || n < p) {
      return Collections.emptyList();
    }

    int[] z = computeZArray(pattern + "#" + text);

    List<Integer> result = new ArrayList<>();
    for (int i = p + 1; i < z.length; i++) {
      if (z[i] == p) {
        result.add(i - p - 1);
      }
    }

    return result;
  }

  // T - O(n^2)
  int[] computeZArray(String text) {
    int[] z = new int[text.length()];
    for (int i = 1; i < text.length(); i++) {
      /*
      if i is at 2, we need to check 2,3,...n-1 with 0,1,..n-1-2
      if i is at 5, we need to check 5,6,...n-1 with 0,1,..n-1-5
      */
      while (i + z[i] < text.length() && text.charAt(i + z[i]) == text.charAt(z[i])) {
        z[i]++;
      }
    }

    return z;
  }
}

/*
Optimal - Using pre-computed z[] values to eliminate redundant checks
T - O(n)
S - O(n)
 */
class Solution2 {

  public List<Integer> search(String text, String pattern) {
    int n = text.length();
    int p = pattern.length();

    if (n == 0 || p == 0 || n < p) {
      return Collections.emptyList();
    }

    int[] z = computeZArray(pattern + "#" + text);

    List<Integer> result = new ArrayList<>();
    for (int i = p + 1; i < z.length; i++) {
      if (z[i] == p) {
        result.add(i - p - 1);
      }
    }

    return result;
  }

  // T - O(n)
  /*
  Two conditions:
  1) If you find repeating string, the values can be copied as they're the same characters
  2) One exception to this is, if the value is >= your boundary size, then we'll have to start the copy job and start over new window

  t t f x t t f x z t t f x t t f x t z
  0 1

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4
  Now it's 4, copy values
  t == t and value 1 < 4-1, copy value
  f == f and value 0 < 4-2, copy value
  x == x and value 0 < 4-3, copy value
  z != t, stop, reset window

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0 8
  Now it's 8, copy values
  t == t and value 1 < 8-1, copy value
  f == f and value 0 < 8-2, copy value
  x == x and value 0 < 8-3, copy value
  t == t and value 4 == 8-4, stop

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0 8 1 0 0

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0 8 1 0 0 5
  Now it's 5, copy values
  t == t and value 1 < 5-1, copy value
  f == f and value 0 < 5-2, copy value
  x == x and value 0 < 5-3, copy value
  t == t and value 4 > 5-4, stop

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0 8 1 0 0 5 1 0 0

  t t f x t t f x z t t f x t t f x t z
  0 1 0 0 4 1 0 0 0 8 1 0 0 5 1 0 0 1 0
   */
  int[] computeZArray(String text) {
    int[] z = new int[text.length()];

    int i = 1;
    while (i < z.length) {
      while (i + z[i] < z.length && text.charAt(i + z[i]) == text.charAt(z[i])) {
        z[i]++;
      }

      if (z[i] > 1) {
        int currentValue = z[i];

        int j = 1;
        while (j < currentValue && z[j] < currentValue - j) {
          i++;
          z[i] = z[j];
          j++;
        }
      }
      i++;
    }

    return z;
  }
}

class Solution3 {

  // Compute the Z array for the combined string
  int[] computeZArray(String s) {
    int n = s.length(); // size of string

    int[] Z = new int[n]; // Z-array

    // Pointers to mark the window
    int left = 0, right = 0;

    // For every character
    for (int i = 1; i < n; i++) {

      // Out of window
      if (i > right) {
        while (i + Z[i] < n && s.charAt(i + Z[i]) == s.charAt(Z[i])) {
          Z[i]++;
        }
      }

      // Else (Inside the window)
      else {
        // Check for inside
        if (i + Z[i - left] <= right) {
          Z[i] = Z[i - left];
        }

        // Else compute again using brute force method
        else {
          Z[i] = right - i + 1; // Take the answer till boundary

          // Start matching beyond boundary using brute force
          while (i + Z[i] < n && s.charAt(i + Z[i]) == s.charAt(Z[i])) {
            Z[i]++;
          }
        }
      }

      // Update the window only if it extends beyond current boundary
      if (i + Z[i] - 1 > right) {
        left = i;
        right = i + Z[i] - 1;
      }
    }

    return Z; // Return the computed Z-array
  }

  // Function to find all indices of pattern in text
  public List<Integer> search(String text, String pattern) {
    String s = pattern + '$' + text; // Combined string

    // Function call to find the Z array for the combined string
    int[] Z = computeZArray(s);

    // Length of pattern and text
    int n = text.length(), m = pattern.length();

    // To store the result
    List<Integer> ans = new ArrayList<>();

    // Iterate on the combined string after the delimiter
    for (int i = m + 1; i < s.length(); i++) {
      if (Z[i] == m) {
        ans.add(i - (m + 1));
      }
    }

    return ans;
  }
}