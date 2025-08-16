package io.abdul.tries.problems.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Example 1
    assertEquals("ninja",
        solution.completeString(Arrays.asList("n", "ni", "nin", "ninj", "ninja", "nil")),
        "Should return 'ninja' as it has all prefixes");

    // Example 2
    assertEquals("None",
        solution.completeString(Arrays.asList("ninja", "night", "nil")),
        "No string has all prefixes, should return 'None'");

    // Example 3
    assertEquals("car",
        solution.completeString(Arrays.asList("cat", "car", "cow", "c", "ca", "t", "r", "w")),
        "Should return 'car' as it is lexicographically smallest among max length words");

    // Edge case: only one single-letter word
    assertEquals("a",
        solution.completeString(Arrays.asList("a")),
        "Single letter word should return itself");

    // Edge case: no valid complete string
    assertEquals("None",
        solution.completeString(Arrays.asList("abc", "abd", "abe")),
        "Should return 'None' if no word has all prefixes");

    // Edge case: tie in length but lexicographically smallest wins
    assertEquals("ant",
        solution.completeString(Arrays.asList("a", "an", "ant", "bat", "ba", "ban")),
        "Should return 'ant' as it is lexicographically smaller than 'ban'");

    // Edge case: long valid chain
    assertEquals("abcdefgh",
        solution.completeString(Arrays.asList("a", "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg", "abcdefgh")),
        "Should return the longest valid chain word");

    // Edge case: repeated words
    assertEquals("abc",
        solution.completeString(Arrays.asList("a", "ab", "abc", "abc", "ab")),
        "Duplicates should not affect result");

    // Edge case: multiple valid but shorter word only valid
    assertEquals("abc",
        solution.completeString(Arrays.asList("a", "aa", "ab", "abc")),
        "Should pick shortest valid chain");

    // Edge case: empty list
    assertEquals("None",
        solution.completeString(Collections.emptyList()),
        "Should return 'None' if input is empty");
  }
}

/*
T - O(n log(k)) - n log(k) to build trie; n log(k) to check if each word is complete string or not
S - O(k) - k to store trie; k for stack

k = num of elements

Each char is visited only once, with additional stack space
 */
class Solution {

  public String completeString(List<String> words) {
    Node trie = buildTrie(words);

    String[] result = new String[1];
    findCompleteString(trie, 0, new StringBuilder(), result);
    String res = result[0].substring(1);
    return res.isEmpty() ? "None" : res;
  }

  private void findCompleteString(Node node, int charPos, StringBuilder temp, String[] result) {
    if (node == null || !node.endOfWord) {
      return;
    }

    temp.append((char) (charPos + 'a'));
    if (result[0] == null) {
      result[0] = temp.toString();
    } else {
      if (temp.length() > result[0].length()) {
        result[0] = temp.toString();
      } else if (temp.length() == result[0].length()) {
        if (temp.toString().compareTo(result[0]) < 0) {
          result[0] = temp.toString();
        }
      }
    }

    for (int i = 0; i < 26; i++) {
      findCompleteString(node.nodes[i], i, temp, result);
    }

    temp.deleteCharAt(temp.length() - 1);
  }

  private Node buildTrie(List<String> words) {
    Node root = new Node();
    root.endOfWord = true; // for blank or empty string case
    for (String word : words) {
      insert(root, word);
    }

    return root;
  }

  private void insert(Node node, String word) {
    Node current = node;
    for (int i = 0; i < word.length(); i++) {
      int charPos = word.charAt(i) - 'a';
      if (current.nodes[charPos] == null) {
        current.nodes[charPos] = new Node();
      }
      current = current.nodes[charPos];
    }
    current.endOfWord = true;
  }

  private static class Node {

    private final Node[] nodes = new Node[26];
    private boolean endOfWord;
  }
}

/*
T - O(n log(k)) - n log(k) to build trie; n log(k) to check if each word is complete string or not
S - O(k) - k to store trie

k = num of elements

Each char is visited more than once compared to Solution 1, removing additional stack space
 */
class Solution2 {

  public String completeString(List<String> words) {
    Node trie = buildTrie(words);

    String longest = "";
    for (String word : words) {
      if (isCompleteString(trie, word)) {
        if (word.length() > longest.length()) {
          longest = word;
        } else if (word.length() == longest.length()) {
          if (word.compareTo(longest) < 0) {
            longest = word;
          }
        }
      }
    }

    return longest.isEmpty() ? "None" : longest;
  }

  private boolean isCompleteString(Node node, String word) {
    Node current = node;
    for (int i = 0; i < word.length(); i++) {
      current = current.nodes[word.charAt(i) - 'a'];
      if (current == null || !current.endOfWord) {
        return false;
      }
    }

    return true; // all prefix are having end of word and exists in tree
  }

  private Node buildTrie(List<String> words) {
    Node root = new Node();
    root.endOfWord = true; // for blank or empty string case
    for (String word : words) {
      insert(root, word);
    }

    return root;
  }

  private void insert(Node node, String word) {
    Node current = node;
    for (int i = 0; i < word.length(); i++) {
      int charPos = word.charAt(i) - 'a';
      if (current.nodes[charPos] == null) {
        current.nodes[charPos] = new Node();
      }
      current = current.nodes[charPos];
    }
    current.endOfWord = true;
  }

  private static class Node {

    private final Node[] nodes = new Node[26];
    private boolean endOfWord;
  }
}