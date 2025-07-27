package io.abdul.binary_search_tree.medium_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution3 solution = new Solution3();

    assertAll("kth Smallest and Largest Element in BST",

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{3, 1, 4, null, 2});
          List<Integer> result = solution.kLargesSmall(root, 1);
          assertEquals(List.of(1, 4), result);
        },

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{3, 1, 4, null, 2});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(2, 3), result);
        },

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, null, null, null, 1});
          List<Integer> result = solution.kLargesSmall(root, 3);
          assertEquals(List.of(3, 3), result);
        },

        // Right-skewed
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, null, 2, null, 3, null, 4});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(2, 3), result);
        },

        // Single-node BST
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{42});
          List<Integer> result = solution.kLargesSmall(root, 1);
          assertEquals(List.of(42, 42), result);
        },

        // Left-skewed
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 8, null, 6, null, 4});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(6, 8), result);
        }
    );
  }
}

/*
Brute-force: Build the sorted data using in-order
T - O(n)
S - O(n)

 */
class Solution {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    ArrayList<Integer> result = new ArrayList<>();
    inorder(root, result);
    return List.of(result.get(k - 1), result.get(result.size() - k));
  }

  private void inorder(TreeNode root, List<Integer> result) {
    if (root == null) {
      return;
    }

    inorder(root.left, result);
    result.add(root.data);
    inorder(root.right, result);
  }
}

/*
Optimal - Counting with inorder and modified inorder
T - O(n)
S - O(1)

 */
class Solution3 {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    List<Integer> result = new ArrayList<>(2);
    inorder(root, 0, k, result);
    reverseInorder(root, 0, k, result);
    return result;
  }

  private int inorder(TreeNode root, int count, int k, List<Integer> result) {
    if (root == null) {
      return count;
    }
    count = inorder(root.left, count, k, result); // process left
    if (count == -1) {
      return -1;
    }

    count++; // process root

    if (count == k) {
      result.add(root.data);
      return -1;
    }

    count = inorder(root.right, count, k, result); // process right
    if (count == -1) {
      return -1;
    }

    return count;
  }

  private int reverseInorder(TreeNode root, int count, int k, List<Integer> result) {
    if (root == null) {
      return count;
    }
    count = reverseInorder(root.right, count, k, result); // process right
    if (count == -1) {
      return -1;
    }

    count++; // process root

    if (count == k) {
      result.add(root.data);
      return -1;
    }

    count = reverseInorder(root.left, count, k, result); // process left
    if (count == -1) {
      return -1;
    }

    return count;
  }
}
