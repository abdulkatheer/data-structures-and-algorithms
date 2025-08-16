package io.abdul.tries.problems.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Example 1
    assertEquals(15, solution.findMaximumXOR(new int[]{3, 9, 10, 5, 1}));

    // Example 2
    assertEquals(116, solution.findMaximumXOR(new int[]{26, 49, 30, 15, 69}));

    // Example 3
    assertEquals(7, solution.findMaximumXOR(new int[]{1, 2, 3, 4, 5, 6}));
    // Explanation: 1 ^ 6 = 7 is the maximum

    // Edge case: Single element array
    assertEquals(0, solution.findMaximumXOR(new int[]{5}));
    // Explanation: XOR of same element (5 ^ 5) = 0

    // Edge case: Two equal numbers
    assertEquals(0, solution.findMaximumXOR(new int[]{7, 7}));

    // Edge case: Large numbers
    assertEquals(2147483647, solution.findMaximumXOR(new int[]{0, 2147483647}));
    // Explanation: 0 ^ 2147483647 = 2147483647

    // Edge case: All zeros
    assertEquals(0, solution.findMaximumXOR(new int[]{0, 0, 0, 0}));

    // Mixed small values
    assertEquals(30, solution.findMaximumXOR(new int[]{1, 2, 4, 8, 16, 31}));
    // Explanation: 16 ^ 31 = 15, 31 ^ 0 = 31 -> max = 31
  }
}

/*
Optimal
T - O(n)
S - O(1)

 */
class Solution {

  public int findMaximumXOR(int[] nums) {
    Node root = buildTrie(nums);

    int max = Integer.MIN_VALUE;
    for (int num : nums) {
      max = Math.max(max, maxXor(root, num));
    }

    return max;
  }

  private int maxXor(Node root, int x) {
    Node current = root;
    int max = 0;
    for (int i = 31; i >= 0; i--) { // O(32)
      int bit = (x >> i) & 1;
      int opp = bit ^ 1;

      if (current.nodes[opp] != null) {
        max = max | (1 << i);
        current = current.nodes[opp];
      } else {
        current = current.nodes[bit];
      }
    }

    return max;
  }

  private Node buildTrie(int[] nums) {
    Node root = new Node();

    for (int num : nums) { // O(n)
      Node current = root;
      for (int i = 31; i >= 0; i--) { // O(32)
        int bit = (num >> i) & 1;

        if (current.nodes[bit] == null) {
          current.nodes[bit] = new Node();
        }

        current = current.nodes[bit];
      }
    }

    return root;
  }

  private static class Node {

    private final Node[] nodes = new Node[2];
  }
}

/*
Brute
T - O(n^2)
S - O(1)
 */
class Solution2 {

  public int findMaximumXOR(int[] nums) {
    int max = Integer.MIN_VALUE;

    for (int num1 : nums) {
      for (int num2 : nums) {
        max = Math.max(max, num1 ^ num2);
      }
    }

    return max;
  }
}