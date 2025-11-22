package io.abdul.strings_advanced_algo.advanced_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// KMP algorithm - Knuth-Morris-Pratt algorithm
// Longest Prefix Suffix LPS
public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertArrayEquals(new int[]{0, 0, 1, 1, 2, 3, 4, 5, 6, 2, 3, 4, 5},
        solution.computeLps("abaabaababaab"));
  }
}

/*
Brute-force
T - O(n^3)
S - O(n)
 */
class Solution {

  public List<Integer> search(String pat, String txt) {
    int n = txt.length();
    int p = pat.length();

    if (n == 0 || p == 0 || n < p) {
      return Collections.emptyList();
    }

    int[] lps = computeLps(pat + "$" + txt);

    List<Integer> result = new ArrayList<>();
    for (int i = p + 1; i < lps.length; i++) {
      if (lps[i] == p) {
        /*
        p=3
        the first match can be at 3 + 1 + 3.
        3 for pat
        1 for $
        then 3 to match pat
        */
        result.add(i - (2 * p));
      }
    }

    return result;
  }

  // T - O(n^3)
  int[] computeLps(String text) {
    int[] lps = new int[text.length()];

    for (int i = 1; i < lps.length; i++) {
      for (int j = 1; j <= i; j++) {
        String prefix = text.substring(0, j);
        String suffix = text.substring(i + 1 - j, i + 1);
        if (prefix.equals(suffix)) {
          lps[i] = j;
        }
      }
    }

    return lps;
  }
}

/*
Optimal - Using precomputed values to eliminate redundancies
T - O(n)
S - O(n)
 */
class Solution2 {

  public List<Integer> search(String pat, String txt) {
    int n = txt.length();
    int p = pat.length();

    if (n == 0 || p == 0 || n < p) {
      return Collections.emptyList();
    }

    int[] lps = computeLps(pat + "$" + txt);

    List<Integer> result = new ArrayList<>();
    for (int i = p + 1; i < lps.length; i++) {
      if (lps[i] == p) {
        /*
        p=3
        the first match can be at 3 + 1 + 3.
        3 for pat
        1 for $
        then 3 to match pat
        */
        result.add(i - (2 * p));
      }
    }

    return result;
  }

  /*
  a b a a b a a b a b a a b

  j=0, i=1, no match and j is already at 0, so set 0 and move i
  a b a a b a a b a b a a b
  0 0

  j=0, i=2, match move i and j
  a b a a b a a b a b a a b
  0 0 1

  j=1, i=3, no match, move j back to 0
  a b a a b a a b a b a a b
  0 0 1

  j=0, i=3, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1

  j=1, i=4, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2

  j=2, i=5, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3

  j=3, i=6, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4

  j=4, i=7, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5

  j=5, i=8, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6

  j=6, i=9, no match, move j to 0
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6

  j=0, i=9, no match, move j to 0
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6
   */

  /*
  a b a a b a a b a b a a b

  j=0, i=1, no match
  a b a a b a a b a b a a b
  0 0

  j=0, i=2, match move i and j
  a b a a b a a b a b a a b
  0 0 1

  j=1, i=3, no match, move j back to find match. j=lps[j-1]; j=0
  a b a a b a a b a b a a b
  0 0 1

  j=0, i=3, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1

  j=1, i=4, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2

  j=2, i=5, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3

  j=3, i=6, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4

  j=4, i=7, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5

  j=5, i=8, match move i and j
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6

  j=6, i=9, no match, move j back until match found or j underflows
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6

  The prefix j character is not matching with suffix i character
  So we need to find all prefix strings ending with the suffix i character.
  So we move j backwards to known matching positions.
  lsp[j-1] = lps[5] = 3
  meaning
  a b a a b a = first 3 and last 3 are matching
  unmatched character is 4th character which is at index 3

  if that 4th character is matching ith character, we can take that as the max length. Otherwise move backwards and repeat.

  How can we take that as full match?
  j=6 i=9
  a b a a b a a b a b a a b
  0 0 1 1 2 3 4 5 6

  lps[8] = 6, first and last 6 are matching -> a b a a b a a b a + b
  7th prefix = a
  7th suffix = b
  not matching

  Last match at j=5, i=8
  lps[8] = 6, first and last 6 of 9 characters are matching
  a b a a b a a b a

  a b a a b a
        a b a a b a

  Hence, first 3 and last 3 are matching.
  a b a x x x
        x x x a b a

  a b a x x x == x x x a b a, known
  a b a x x ==? x x a b a, unknown
  a b a x ==? x a b a, unknown
  a b a == a b a, known

  So go back to a known position where it has the longest match and try matching the next char with ith char

  lps[5] = 3, first and last 3 of 6 are matching -> a b a a b a
  it also means, first and last 3 of 9 are matching -> a b a x x x a b a
  So we cam start checking from 4th character (3rd index)

  If there are no overlaps, lps[j-1] will be 0 only
  If there are x overlaps, lps[i-1] will be j+1-x
  if there are full overlaps, lps[i-1]

   */

  /*
  No overlap case:

  Last match at j=5, i=11
  lps[11] = 6, first and last 6 of 9 characters are matching
  a b a b a f a b a b a f x
  0 0 1 2 3 0 1 2 3 4 5 6
  No overlap here
  a b a b a f
              a b a b a f
  We're not sure which are having same prefix/suffix. So we have to start from 0
  lps[4] = 0 only
   */

  /*
  X overlaps case:

  Last match at j=5, i=7
  a b a b a b a b x

  a b a b a b
      a b a b a b
  4 overlaps
  lps[5] = 6
  6-4 = 2, first 2 and last 2 are known match
  a b x x x x a b
   */

  /*
  Full overlap case:
  When full match, we don't know which prefix and suffix are matching. It's un predictable
   */

  int[] computeLps(String text) {
    int[] lps = new int[text.length()];

    int i = 1;
    int j = 0;
    while (i < lps.length) {
      while (j > 0 && text.charAt(j) != text.charAt(i)) {
        j = lps[j - 1];
      }

      if (text.charAt(j) == text.charAt(i)) { // match or otherwise j will be at start (0)
        lps[i] = j + 1;
        j++;
      }
      i++;
    }

    return lps;
  }
}
