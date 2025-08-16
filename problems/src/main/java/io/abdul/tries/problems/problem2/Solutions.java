package io.abdul.tries.problems.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Example 1
    assertEquals(6, solution.countDistinctSubstring("aba"),
        "Distinct substrings should be [\"\", \"a\", \"b\", \"ab\", \"ba\", \"aba\"]");

    // Example 2
    assertEquals(7, solution.countDistinctSubstring("abc"),
        "Distinct substrings should be [\"\", \"a\", \"b\", \"c\", \"ab\", \"bc\", \"abc\"]");

    // Example 3 (not fully specified in prompt, but testable)
    assertEquals(13, solution.countDistinctSubstring("aaabc"),
        "Verify substrings with repeated characters");

    // Edge case: single character
    assertEquals(2, solution.countDistinctSubstring("a"),
        "Substrings: [\"\", \"a\"]");

    // Edge case: two identical characters
    assertEquals(3, solution.countDistinctSubstring("aa"),
        "Substrings: [\"\", \"a\", \"aa\"]");

    // Edge case: two distinct characters
    assertEquals(4, solution.countDistinctSubstring("ab"),
        "Substrings: [\"\", \"a\", \"b\", \"ab\"]");

    // Edge case: palindrome
    assertEquals(6, solution.countDistinctSubstring("aba"),
        "Palindrome case re-check");

    // Edge case: all identical characters
    assertEquals(4, solution.countDistinctSubstring("aaa"),
        "Substrings: [\"\", \"a\", \"aa\", \"aaa\"]");

    // Edge case: empty string (if allowed, but constraint says len>=1)
    assertEquals(1, solution.countDistinctSubstring(""),
        "Only empty substring exists");

    // Larger string (performance sanity check, not exact)
    String longString = "abcd";
    assertEquals(11, solution.countDistinctSubstring(longString),
        "Expected substrings: [\"\", \"a\", \"b\", \"c\", \"d\", \"ab\", \"bc\", \"cd\", \"abc\", \"bcd\", \"abcd\"]");
  }
}

class Solution {

  public int countDistinctSubstring(String s) {
    return buildTrie(s) + 1; // +1 for blank
  }

  /*
  T - O(n^2 log(k)
   */
  private int buildTrie(String s) {
    int count = 0;

    /*
    At every i, current starts from root to last node for every substring
    At every j, jth char is inserted at j-i th level

    For ex: test
    i=0
    t at level 0 - connected to root
    e at level 1 - connected to t
    s at level 2 - connected to e
    t at level 3 - connected to s

    i = 1
    e at level 0 - connected to root
    s at level 1 - connected to e
    t at level 2 - connected to s

    i = 2
    s at level 0 - connected to root
    t at level 1 - connected to s

    i = 3
    t at level 1 - connected to root
     */
    Node root = new Node();
    for (int i = 0; i < s.length(); i++) {
      Node current = root;
      for (int j = i; j < s.length(); j++) { // each char ending at j is forming a substring
        // when a end of substring already exists, it's counted already
        int charPos = s.charAt(j) - 'a';
        if (current.nodes[charPos] == null) {
          current.nodes[charPos] = new Node();
          count++;
        }
        current = current.nodes[charPos];
      }
    }

    return count;
  }

  private static class Node {

    private final Node[] nodes = new Node[26];
    private boolean endOfWord;
  }
}