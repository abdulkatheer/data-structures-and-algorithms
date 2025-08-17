package io.abdul.tries.problems.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Example 1
    int[] nums1 = {4, 9, 2, 5, 0, 1};
    int[][] queries1 = {{3, 0}, {3, 10}, {7, 5}, {7, 9}};
    assertEquals(Arrays.asList(3, 10, 7, 14), solution.maximizeXor(nums1, queries1));

    // Example 2
    int[] nums2 = {0, 1, 2, 3, 4};
    int[][] queries2 = {{3, 1}, {1, 3}, {5, 6}};
    assertEquals(Arrays.asList(3, 3, 7), solution.maximizeXor(nums2, queries2));

    // Example 3
    int[] nums3 = {5, 2, 4, 6, 6, 3};
    int[][] queries3 = {{12, 4}, {8, 1}, {6, 3}};
    assertEquals(Arrays.asList(15, -1, 5), solution.maximizeXor(nums3, queries3));
    // Explanation: For query [8,1] and [6,3], all nums > m so answer is -1.

    // Edge case: All nums are greater than m
    int[] nums4 = {10, 12, 15};
    int[][] queries4 = {{5, 5}};
    assertEquals(Arrays.asList(-1), solution.maximizeXor(nums4, queries4));

    // Edge case: Single element array
    int[] nums5 = {7};
    int[][] queries5 = {{3, 7}, {3, 2}};
    assertEquals(Arrays.asList(4, -1), solution.maximizeXor(nums5, queries5));

    // Edge case: All nums are <= m
    int[] nums6 = {1, 2, 3};
    int[][] queries6 = {{4, 5}};
    assertEquals(Arrays.asList(7), solution.maximizeXor(nums6, queries6));
    // Explanation: Best is 4 XOR 3 = 7

    // Large values
    int[] nums7 = {0, 1000000000};
    int[][] queries7 = {{2147483647, 1000000000}};
    assertEquals(Arrays.asList(2147483647), solution.maximizeXor(nums7, queries7));
  }
}

/*
Optimal
T - O(n log(n)) - n log(n) to sort nums; q log(q) to sort queries; n to build trie; q to find max XOR for each query
S - O(n) - n to copy input nums; q to copy input queries
 */
class Solution {

  public List<Integer> maximizeXor(int[] nums, int[][] queries) {
    int[] numsInput = new int[nums.length];
    int[][] queriesInput = new int[queries.length][3]; // additional space to keep original query position
    System.arraycopy(nums, 0, numsInput, 0, nums.length);
    for (int i = 0; i < queries.length; i++) {
      System.arraycopy(queries[i], 0, queriesInput[i], 0, queries[i].length);
      queriesInput[i][2] = i;
    }

    /*
    Sort the nums and queries by max num
    So that we insert only smaller numbers for query to trie and search
     */
    Arrays.sort(numsInput);
    Arrays.sort(queriesInput, Comparator.comparingInt(o -> o[1]));

    Node trie = new Node();
    int numPos = 0;
    int[] answers = new int[queries.length];
    for (int[] query : queriesInput) {
      int max = query[1];

      while (numPos < numsInput.length && numsInput[numPos] <= max) {
        insert(trie, numsInput[numPos]);
        numPos++;
      }

      if (numPos > 0) { //at least 1 element <= query[1] exists
        answers[query[2]] = findMaxXor(trie, query[0]);
      } else {
        answers[query[2]] = -1;
      }
    }

    return Arrays.stream(answers).boxed().toList();
  }

  private void insert(Node root, int num) {
    Node current = root;

    for (int i = 31; i >= 0; i--) {
      int bit = (num >> i) & 1;

      if (current.nodes[bit] == null) {
        current.nodes[bit] = new Node();
      }
      current = current.nodes[bit];
    }
  }

  private int findMaxXor(Node trie, int x) {
    Node current = trie;

    int maxXor = 0;
    for (int i = 31; i >= 0; i--) {
      int bit = (x >> i) & 1;
      int opp = bit ^ 1;

      if (current.nodes[opp] != null) {
        maxXor = maxXor | (1 << i);
        current = current.nodes[opp];
      } else {
        current = current.nodes[bit];
      }
    }

    return maxXor;
  }

  private static class Node {

    private final Node[] nodes = new Node[32];
  }
}