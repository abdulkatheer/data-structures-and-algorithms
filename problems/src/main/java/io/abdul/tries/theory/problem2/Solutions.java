package io.abdul.tries.theory.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.tries.theory.problem2.Solution.Trie;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    // Example 1
    Trie trie1 = new Trie();
    trie1.insert("apple");
    assertEquals(1, trie1.countWordsEqualTo("apple"), "apple count should be 1");
    trie1.insert("app");
    assertEquals(2, trie1.countWordsStartingWith("app"), "Prefix 'app' count should be 2");
    trie1.erase("apple");
    assertEquals(1, trie1.countWordsStartingWith("app"),
        "Prefix 'app' count should be 1 after erasing 'apple'");

    // Example 2
    Trie trie2 = new Trie();
    trie2.insert("mango");
    assertEquals(0, trie2.countWordsEqualTo("apple"), "apple should not be found");
    trie2.insert("app");
    trie2.erase("app");
    assertEquals(1, trie2.countWordsStartingWith("mango"), "Prefix 'mango' count should be 1");

    // Example 3
    Trie trie3 = new Trie();
    trie3.insert("abcde");
    trie3.insert("fghij");
    trie3.erase("abcde");
    assertEquals(0, trie3.countWordsEqualTo("abcde"), "abcde count should be 0 after erase");
    trie3.insert("abcde");
    assertEquals(1, trie3.countWordsStartingWith("fgh"), "Prefix 'fgh' count should be 1");

    // Edge case: multiple insertions of same word
    Trie trie4 = new Trie();
    trie4.insert("repeat");
    trie4.insert("repeat");
    trie4.insert("repeat");
    assertEquals(3, trie4.countWordsEqualTo("repeat"),
        "repeat count should be 3 after three insertions");
    trie4.erase("repeat");
    assertEquals(2, trie4.countWordsEqualTo("repeat"), "repeat count should be 2 after one erase");

    // Edge case: erase non-existent word
    Trie trie5 = new Trie();
    trie5.insert("dog");
    trie5.erase("cat"); // no effect expected
    assertEquals(1, trie5.countWordsEqualTo("dog"),
        "dog count should still be 1 after erasing non-existent word");

    // Edge case: countWordsStartingWith for non-existent prefix
    assertEquals(0, trie5.countWordsStartingWith("z"), "Prefix 'z' should not be found");

    // Edge case: empty string handling
    Trie trie6 = new Trie();
    trie6.insert("");
    assertEquals(1, trie6.countWordsEqualTo(""), "Empty string count should be 1 after insertion");
    trie6.erase("");
    assertEquals(0, trie6.countWordsEqualTo(""), "Empty string count should be 0 after erase");

    // Edge case: very long word
    String longWord = "a".repeat(2000);
    Trie trie7 = new Trie();
    trie7.insert(longWord);
    assertEquals(1, trie7.countWordsEqualTo(longWord), "Long word count should be 1");
    assertEquals(1, trie7.countWordsStartingWith("a".repeat(1999)), "Long prefix should match");

  }
}

class Solution {

  static class Trie {

    private final Node root = new Node();

    public Trie() {

    }

    public void insert(String word) {
      Node current = root;

      for (int i = 0; i < word.length(); i++) {
        int charPos = word.charAt(i) - 'a';
        if (current.nodes[charPos] == null) {
          current.nodes[charPos] = new Node();
        }
        Node next = current.nodes[charPos];
        next.usageCount++;
        current = next;
      }

      current.endCount++;
    }

    public int countWordsEqualTo(String word) {
      Node current = root;

      for (int i = 0; i < word.length(); i++) {
        if (current == null) {
          break;
        }
        int charPos = word.charAt(i) - 'a';
        current = current.nodes[charPos];
      }

      return current != null ? current.endCount : 0;
    }

    public int countWordsStartingWith(String prefix) {
      Node current = root;

      for (int i = 0; i < prefix.length(); i++) {
        if (current == null) {
          break;
        }
        int charPos = prefix.charAt(i) - 'a';
        current = current.nodes[charPos];
      }

      return current != null ? current.usageCount : 0;
    }

    /*
    DFS, so need stack to find parents of nodes
    This is needed to remove nodes when no usage found, otherwise we can do this in top-down fashion and just decrease count
     */
    public void erase(String word) {
      Stack<NodeWrapper> stack = new Stack<>();
      stack.push(new NodeWrapper(root, -1));

      Node current = root;
      for (int i = 0; i < word.length(); i++) {

        int charPos = word.charAt(i) - 'a';
        current = current.nodes[charPos];
        if (current == null) {
          break;
        }
        stack.push(new NodeWrapper(current, charPos));
      }

      if (stack.size() != word.length() + 1) { // nodes + root
        return;
      }

      // reduce end count only for last char in the word
      stack.peek().node.endCount--;

      while (stack.size() > 1) { // until non-root node
        NodeWrapper n = stack.pop();
        n.node.usageCount--;
        if (n.node.usageCount == 0) {
          Node p = stack.peek().node;
          p.nodes[n.charPos] = null; // removing current character from Trie as no usage found after erase
        }
      }
    }

    private static class Node {

      private final Node[] nodes = new Node[26];
      private int endCount;
      private int usageCount;
    }

    private record NodeWrapper(Node node, int charPos) {

    }
  }

}
