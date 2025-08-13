package io.abdul.tries.theory.problem1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.tries.theory.problem1.Solution.Trie;

public class Solutions {

  public static void main(String[] args) {
    Trie trie = new Trie();

    // Example 1
    trie.insert("apple");
    assertTrue(trie.search("apple"), "apple should be found after insertion");
    assertFalse(trie.search("app"), "app should not be found before explicit insertion");
    assertTrue(trie.startsWith("app"), "prefix 'app' should be found");
    trie.insert("app");
    assertTrue(trie.search("app"), "app should be found after insertion");

    // Example 2
    Trie trie2 = new Trie();
    trie2.insert("takeu");
    trie2.insert("banana");
    assertTrue(trie2.startsWith("bana"), "prefix 'bana' should be found");
    assertTrue(trie2.search("takeu"), "takeu should be found");

    // Example 3
    Trie trie3 = new Trie();
    trie3.insert("caterpiller");
    trie3.insert("cat");
    assertTrue(trie3.startsWith("cat"), "prefix 'cat' should be found");
    assertTrue(trie3.search("cat"), "cat should be found");

    // Additional edge cases

    // Empty string insertion & search
    Trie trie4 = new Trie();
    trie4.insert("");
    assertTrue(trie4.search(""), "Empty string should be found after insertion");
    assertTrue(trie4.startsWith(""), "Empty prefix should always match");

    // No match cases
    Trie trie5 = new Trie();
    trie5.insert("dog");
    assertFalse(trie5.search("do"), "Partial word 'do' should not be found unless inserted");
    assertTrue(trie5.startsWith("do"), "Prefix 'do' should be found");

    // Overlapping words
    Trie trie6 = new Trie();
    trie6.insert("car");
    trie6.insert("cart");
    trie6.insert("carbon");
    assertTrue(trie6.search("cart"), "'cart' should be found");
    assertTrue(trie6.startsWith("carb"), "Prefix 'carb' should be found");
    assertFalse(trie6.search("cars"), "'cars' should not be found");

    // Long word and prefix
    String longWord = "a".repeat(2000);
    Trie trie7 = new Trie();
    trie7.insert(longWord);
    assertTrue(trie7.search(longWord), "Longest possible word should be found");
    assertTrue(trie7.startsWith("a".repeat(1999)), "Longest possible prefix should be found");
  }
}

class Solution {

  static class Trie {

    private final Node root = new Node();

    Trie() {
    }

    void insert(String word) {
      Node current = root;
      for (int i = 0; i < word.length(); i++) {
        int charPos = word.charAt(i) - 'a';
        if (current.nodes[charPos] == null) {
          current.nodes[charPos] = new Node();
        }

        current = current.nodes[charPos];
      }
      // Now current is the node of last character
      current.endOfWord = true;
    }

    boolean search(String word) {
      // trying to go the node of last character in the word, if not current will be null
      Node current = root;
      for (int i = 0; i < word.length(); i++) {
        if (current == null) {
          break;
        }
        int charPos = word.charAt(i) - 'a';
        current = current.nodes[charPos];
      }

      return current != null && current.endOfWord;
    }

    boolean startsWith(String prefix) {
      // trying to go the node of last character in the prefix, if not current will be null
      Node current = root;
      for (int i = 0; i < prefix.length(); i++) {
        if (current == null) {
          break;
        }
        int charPos = prefix.charAt(i) - 'a';
        current = current.nodes[charPos];
      }

      return current != null; // it doesn't have to be end of word
    }
  }

  private static class Node {

    private final Node[] nodes = new Node[26];
    private boolean endOfWord = false;
  }
}