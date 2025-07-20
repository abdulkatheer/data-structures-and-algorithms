package io.abdul.binary_tree.faqs.problem11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    assertBurnTime(new Integer[]{1, 2, 3, 4, null, 5, 6, null, 7}, 1, 3, solution);
    assertBurnTime(new Integer[]{1, 2, 3, null, 5, null, 4}, 4, 4, solution);
    assertBurnTime(new Integer[]{1, 2, 3, 6, 5, 8, 4}, 4, 4, solution);

    // Additional edge cases

    // Skewed left
    assertBurnTime(new Integer[]{1, 2, null, 3, null, 4}, 4, 3, solution);  // 4 -> 3 -> 2 -> 1

    // Skewed right
    assertBurnTime(new Integer[]{1, null, 2, null, 3, null, 4}, 1, 3, solution); // 1 -> 2 -> 3 -> 4

    // Sparse tree
    assertBurnTime(new Integer[]{10, 20, 30, null, 25, null, 40, null, null, null, 35}, 25, 5,
        solution);

    // Single node
    assertBurnTime(new Integer[]{7}, 7, 0, solution);

    // Two level tree
    assertBurnTime(new Integer[]{1, 2, 3}, 2, 2, solution);
  }

  private static void assertBurnTime(Integer[] arr, int target, int expected, Solution solution) {
    TreeNode root = TreeNode.buildTree(arr);
    int result = solution.timeToBurnTree(root, target);
    assertEquals(expected, result);
  }
}

/*
Optimal
T - O(n)
S - O(n)

We need to find the longest connected node from target
Similar to "Print nodes at distance k", but we need to stop only when root is null

 */

class Solution {

  public int timeToBurnTree(TreeNode root, int start) {

    HashMap<Integer, List<Integer>> adjacencyList = new HashMap<>();
    buildAdjacencyList(root, adjacencyList);

    return timeToBurn(start, 0, new HashSet<>(), adjacencyList);
  }

  private void buildAdjacencyList(TreeNode root, Map<Integer, List<Integer>> adjacencyList) {
    if (root == null) {
      return;
    }

    if (root.left != null) {
      List<Integer> leftList = adjacencyList.getOrDefault(root.left.data, new ArrayList<>());
      List<Integer> rootList = adjacencyList.getOrDefault(root.data, new ArrayList<>());

      rootList.add(root.left.data);
      leftList.add(root.data);

      adjacencyList.put(root.data, rootList);
      adjacencyList.put(root.left.data, leftList);

      buildAdjacencyList(root.left, adjacencyList);
    }

    if (root.right != null) {
      List<Integer> rightList = adjacencyList.getOrDefault(root.right.data, new ArrayList<>());
      List<Integer> rootList = adjacencyList.getOrDefault(root.data, new ArrayList<>());

      rootList.add(root.right.data);
      rightList.add(root.data);

      adjacencyList.put(root.data, rootList);
      adjacencyList.put(root.right.data, rightList);

      buildAdjacencyList(root.right, adjacencyList);
    }
  }

  private int timeToBurn(Integer nodeData, int distance, Set<Integer> visited,
      Map<Integer, List<Integer>> adjacencyList) {
    if (nodeData == null) {
      return distance;
    }

    visited.add(nodeData);

    List<Integer> adjNodes = adjacencyList.get(nodeData);

    if (adjNodes == null) {
      return distance;
    }

    int maxDistance = distance;
    for (Integer adjNode : adjNodes) {
      if (!visited.contains(adjNode)) {
        maxDistance = Math.max(maxDistance,
            timeToBurn(adjNode, distance + 1, visited, adjacencyList));
      }
    }

    return maxDistance;
  }
}