package io.abdul.tries.problems.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Que - Given an array of numbers and a number x, find the max value of arr[i] ^ x
 */
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
//    Solution2 solution = new Solution2();

    // Basic case
    assertEquals(5 ^ 2, solution.findMaximumXOR(new int[]{1, 2, 3, 4, 5}, 2),
        "Best match is 5 ^ 2 = 7");

    // Case with negatives
    assertEquals(-3 ^ 3, solution.findMaximumXOR(new int[]{-1, -2, -3}, 3),
        "Check handling of negative numbers");

    // Case where x = 0 → result is max element itself
    assertEquals(10 ^ 0, solution.findMaximumXOR(new int[]{2, 7, 10}, 0),
        "XOR with 0 returns the number itself, so max is 10");

    // Case where array has only one element
    assertEquals(3 ^ 5, solution.findMaximumXOR(new int[]{3}, 5),
        "Only element: 3 ^ 5 = 6");

    // Case with large numbers
    assertEquals(1023 ^ 0, solution.findMaximumXOR(new int[]{1023, 512, 256}, 0),
        "Max value should be 1023 when x = 0");

    // Case with repeated numbers
    assertEquals(7 ^ 8, solution.findMaximumXOR(new int[]{7, 7, 7}, 8),
        "7 ^ 8 = 15 always, duplicates should not affect result");

    // Case where x is larger than all elements
    assertEquals(20 ^ 100, solution.findMaximumXOR(new int[]{1, 5, 20}, 100),
        "Check when x is much larger than array elements");

    // Edge case: nums contains both small and large values
    assertEquals(1024 ^ 7, solution.findMaximumXOR(new int[]{1, 50, 1024}, 7),
        "Check correct max among mix of numbers");
  }
}

/*

Simple linear approach exists, but we solve using trie to understand problem 4
 */
class Solution {

  public int findMaximumXOR(int[] nums, int x) {
    Node root = buildTrie(nums);

    int xor = 0;
    Node current = root;
    for (int i = 31; i >= 0; i--) {
      int bit = (x >> i) & 1; // x's 32nd bit will be at 0
      int opp = bit ^ 1; // we prefer the opposite bit
      if (current.nodes[opp] != null) {
        xor = xor | 1 << i; // 1 << i appends the set bit to result
        current = current.nodes[opp];
      } else {
        current = current.nodes[bit];
      }
    }

    return xor;
  }

  private Node buildTrie(int[] nums) {
    Node root = new Node();
    for (int num : nums) {
      Node current = root;
      for (int i = 31; i >= 0; i--) {
        int bit = (num >> i) & 1; // num's 32nd bit will be at 0th position for num >> 31
        // bit is either 0 or 1; set or unset

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
    private boolean endOfDigit;
  }
}

class Solution2 {

  public int findMaximumXOR(int[] nums, int x) {
    int max = Integer.MIN_VALUE;

    for (int num : nums) {
      max = Math.max(max, num ^ x);
    }

    return max;
  }
}