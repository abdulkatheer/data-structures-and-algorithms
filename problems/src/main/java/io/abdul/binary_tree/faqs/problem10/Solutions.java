package io.abdul.binary_tree.faqs.problem10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Case 1: root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4], target = 5, k = 2
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4));
    List<Integer> expected1 = List.of(1, 4, 7);
    assertEqualsIgnoringOrder(expected1, solution.distanceK(root1, TreeNode.find(root1, 5), 2));

    // Case 2: root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4], target = 5, k = 3
    List<Integer> expected2 = List.of(0, 8);
    assertEqualsIgnoringOrder(expected2, solution.distanceK(root1, TreeNode.find(root1, 5), 3));

    // Case 3: root = [5, 1, 2, 8, 10, 4, 5, null, 6], target = 10, k = 3
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(5, 1, 2, 8, 10, 4, 5, null, 6));
    List<Integer> expected3 = List.of(2, 6);
    assertEqualsIgnoringOrder(expected3, solution.distanceK(root2, TreeNode.find(root2, 10), 3));

    // Case 4: k = 0 (should return the target node itself)
    List<Integer> expected4 = List.of(10);
    assertEqualsIgnoringOrder(expected4, solution.distanceK(root2, TreeNode.find(root2, 10), 0));

    // Case 5: Single node tree
    TreeNode root3 = TreeNode.buildTree(List.of(42));
    List<Integer> expected5 = List.of();  // No other node at any distance > 0
    assertEqualsIgnoringOrder(expected5, solution.distanceK(root3, TreeNode.find(root3, 42), 1));
    assertEqualsIgnoringOrder(List.of(42), solution.distanceK(root3, TreeNode.find(root3, 42), 0));

    // Case 6: Skewed left
    TreeNode root4 = TreeNode.buildTree(Arrays.asList(1, 2, null, 3, null, 4, null, 5));
    List<Integer> expected6 = List.of(3);
    assertEqualsIgnoringOrder(expected6, solution.distanceK(root4, TreeNode.find(root4, 1), 2));

    // Case 7: Sparse tree with missing children
    TreeNode root5 = TreeNode.buildTree(Arrays.asList(1, 2, 3, null, 5, null, 6));
    List<Integer> expected7 = List.of(3);
    assertEqualsIgnoringOrder(expected7, solution.distanceK(root5, TreeNode.find(root5, 2), 2));
  }

  private static void assertEqualsIgnoringOrder(List<Integer> expected, List<Integer> actual) {
    assertEquals(new HashSet<>(expected), new HashSet<>(actual));
  }
}

/*
Optimal
T - O(n^2) - n to build adjacency list; n to traverse all nodes from target
S - O(n) - n to store adjacency list; n to store visited nodes

From a node, it's easy to find nodes at kth step downwards as we've links to left and right.
But can't find nodes upwards, as there's no link from child to parent.

So we'll first build an adjacency list to find all connections to node - left, right and parent.
Then we'll start from our node and try to navigate through all its connections until k steps are exhausted

As elements in the tree are unique, we can use number itself to refer tree node.
 */
class Solution {

  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    Map<Integer, List<TreeNode>> adjacencyList = new HashMap<>();

    buildAdjacencyList(root, adjacencyList);

    ArrayList<Integer> result = new ArrayList<>();
    find(target.data, adjacencyList, k, new HashSet<>(), result);

    return result;
  }

  private void buildAdjacencyList(TreeNode root, Map<Integer, List<TreeNode>> adjacencyList) {
    if (root == null) {
      return;
    }

    if (root.left != null) {
      List<TreeNode> rootList = adjacencyList.getOrDefault(root.data, new ArrayList<>());
      List<TreeNode> leftList = adjacencyList.getOrDefault(root.left.data, new ArrayList<>());
      rootList.add(root.left);
      leftList.add(root);
      adjacencyList.put(root.data, rootList);
      adjacencyList.put(root.left.data, leftList);

      buildAdjacencyList(root.left, adjacencyList);
    }

    if (root.right != null) {
      List<TreeNode> rootList = adjacencyList.getOrDefault(root.data, new ArrayList<>());
      List<TreeNode> rightList = adjacencyList.getOrDefault(root.right.data, new ArrayList<>());
      rootList.add(root.right);
      rightList.add(root);
      adjacencyList.put(root.data, rootList);
      adjacencyList.put(root.right.data, rightList);

      buildAdjacencyList(root.right, adjacencyList);
    }
  }

  private void find(int data, Map<Integer, List<TreeNode>> adjacencyList,
      int distanceLeft, Set<Integer> visited, List<Integer> result) {
    if (distanceLeft == 0) { // Base case: Reached end
      result.add(data);
      return;
    }

    visited.add(data);

    List<TreeNode> adjacentNodes = adjacencyList.get(data);
    if (adjacentNodes == null) { // Base case: No adjacent nodes to travel
      return;
    }

    for (int i = 0; i < adjacentNodes.size(); i++) {
      int adjData = adjacentNodes.get(i).data;
      if (!visited.contains(adjData)) { // Already visited node, so avoid loop
        find(adjData, adjacencyList, distanceLeft - 1, visited, result);
      }
    }
  }
}