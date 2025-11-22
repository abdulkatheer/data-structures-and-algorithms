package io.abdul.strings_advanced_algo.advanced_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example cases
    assertEquals("aaacecaaa", sol.shortestPalindrome("aacecaaa"));
    assertEquals("ecarace", sol.shortestPalindrome("race"));
    assertEquals("dcbabcd", sol.shortestPalindrome("abcd"));

    // Palindrome already
    assertEquals("aba", sol.shortestPalindrome("aba"));
    assertEquals("abba", sol.shortestPalindrome("abba"));

    // Edge cases
    assertEquals("z", sol.shortestPalindrome("z"));
    assertEquals("", sol.shortestPalindrome(""));
    assertEquals("aaaaa", sol.shortestPalindrome("aaaaa"));

    // No palindrome prefix
    assertEquals("gfedcbabcdefg", sol.shortestPalindrome("abcdefg"));

    // Partial palindrome prefix
    assertEquals("cbcbbcbabcbbcbc", sol.shortestPalindrome("abcbbcbc"));

    // Case sensitivity
    assertEquals("AaA", sol.shortestPalindrome("aA"));

    // Numbers
    assertEquals("32123", sol.shortestPalindrome("123"));
  }
}

/*
Brute-force
T - O(n^3)
S - O(n)

LPS array
Longest prefix which is also having same in suffix ending at i

If we reverse the string and append with a some delimiter.
from LPS[n] to LPS[2n -1] will only check from 0 to n-2 just before the special character

LPS[2n - 1] tells the longest palindrome that can be formed from given string

Let's say n = 10,
LPS[2n - 1] = 8
10-2 chars to be added to make it full palindrome
rev.substring(0, 2) are the chars
 */
class Solution {

  public String shortestPalindrome(String s) {
    String rev = new StringBuilder(s).reverse().toString();

    String txt = s + "#" + rev;
    int[] lps = computeLps(txt);

    int r = s.length() - lps[lps.length - 1];

    return rev.substring(0, r) + s;
  }

  private int[] computeLps(String text) {
    int n = text.length();
    int[] lps = new int[n];

    for (int i = 1; i < n; i++) {
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

class Solution2 {

  public String shortestPalindrome(String s) {
    String rev = new StringBuilder(s).reverse().toString();

    String txt = s + "#" + rev;
    int[] lps = computeLps(txt);

    int r = s.length() - lps[lps.length - 1];

    return rev.substring(0, r) + s;
  }

  private int[] computeLps(String text) {
    int n = text.length();
    int[] lps = new int[n];

    int i = 1;
    int j = 0;
    while (i < text.length()) {
      while (j > 0 && text.charAt(j) != text.charAt(i)) {
        j = lps[j - 1];
      }

      if (text.charAt(j) == text.charAt(i)) {
        lps[i] = j + 1;
        j++;
      }
      i++;
    }

    return lps;
  }
}
