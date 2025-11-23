package io.abdul.strings_advanced_algo.advanced_problems.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Given examples
    assertEquals("abab", sol.lps("ababab"));
    assertEquals("aaa", sol.lps("aaaa"));
    assertEquals("", sol.lps("abc"));

    // Single character → no proper prefix/suffix
    assertEquals("", sol.lps("a"));

    // No matching prefix-suffix
    assertEquals("", sol.lps("abcdabcx"));

    // Entire string repeated
    assertEquals("abcabc", sol.lps("abcabcabc"));

    // Overlapping case
    assertEquals("aba", sol.lps("ababa"));

    // Mixed characters
    assertEquals("xyzxyz", sol.lps("xyzxyzAxyzxyz"));

    // All same characters
    assertEquals("bbbb", sol.lps("bbbbb"));

    // Special characters
    assertEquals("@@@", sol.lps("@@@@"));

    // Numeric characters
    assertEquals("1212", sol.lps("121212"));

    // Case sensitivity
    assertEquals("AaA", sol.lps("AaAaA"));

    // Pattern in the middle but not valid prefix/suffix
    assertEquals("abc", sol.lps("abcxxxabc"));

    // Long prefix but not the whole string
    assertEquals("abcab", sol.lps("abcababcab"));

    // Empty string
    assertEquals("", sol.lps(""));

    // Random test
    assertEquals("ab", sol.lps("abxxab"));

    assertEquals("", sol.lps("ouwuosjikfbuvzctjdsv"));
  }
}

/*
Longest Happy String = Longest of LPS
 */

/*
Brute-force - LPS
T - O(n^3)
S - O(n)
 */
class Solution {

  public String lps(String s) {
    if (s.isEmpty()) {
      return "";
    }

    int[] lps = computeLps(s);

    return s.substring(0, lps[s.length() - 1]);
  }

  private int[] computeLps(String text) {
    int[] lps = new int[text.length()];

    for (int i = 1; i < text.length(); i++) { // for each char in string excluding 1st one
      for (int j = 1; j <= i; j++) { // length of prefix string for each string ending at i
        String prefix = text.substring(0, j);
        String suffix = text.substring(i - j + 1, i + 1);
        if (prefix.equals(suffix)) {
          lps[i] = j;
        }
      }
    }

    return lps;
  }
}

class Solution2 {

  public String lps(String s) {
    if (s.isEmpty()) {
      return "";
    }

    int[] lps = computeLps(s);

    return s.substring(0, lps[s.length() - 1]);
  }

  private int[] computeLps(String text) {
    int n = text.length();
    int[] lps = new int[n];

    int i = 1; // for each character in text except 1st
    int j = 0; // longest prefix to char to compare
    while (i < n) {
      while (j > 0 && text.charAt(i) != text.charAt(j)) {
        j = lps[j - 1]; // move back to the known longest prefix
      }

      if (text.charAt(i) == text.charAt(j)) { // Otherwise, no match, j starts over from 0
        lps[i] = j + 1; // j is at the longest prefix for i, so add +1 for count
        j++;
      }

      i++;
    }

    return lps;
  }
}

