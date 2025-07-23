package io.abdul.binary_tree.traversal_in_constant_space.problem1;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    assertAll("Morris Inorder Traversal",

        // Example 1: [1, 4, null, 4, 2]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, 4, null, 4, 2});
          assertEquals(List.of(4, 4, 2, 1), solution.getInorder(root), "Example 1");
        },

        // Example 2: [1, null, 2, 3]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, null, 2, 3});
          assertEquals(List.of(1, 3, 2), solution.getInorder(root), "Example 2");
        },

        // Example 3: [5, 1, 2, 8, null, 4, 5, null, 6]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 1, 2, 8, null, 4, 5, null, 6});
          assertEquals(List.of(8, 6, 1, 5, 4, 2, 5), solution.getInorder(root), "Example 3");
        },

        // Skewed Left: [5, 4, null, 3, null, 2]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 4, null, 3, null, 2});
          assertEquals(List.of(2, 3, 4, 5), solution.getInorder(root), "Left Skewed");
        },

        // Skewed Right: [1, null, 2, null, 3, null, 4]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, null, 2, null, 3, null, 4});
          assertEquals(List.of(1, 2, 3, 4), solution.getInorder(root), "Right Skewed");
        },

        // Single Node
        () -> {
          TreeNode root = new TreeNode(10);
          assertEquals(List.of(10), solution.getInorder(root), "Single Node");
        },

        // Empty Tree
        () -> {
          TreeNode root = null;
          assertEquals(List.of(), solution.getInorder(root), "Empty Tree");
        }
    );
  }
}

class Solution {

  public List<Integer> getInorder(TreeNode root) {
    List<Integer> inorder = new ArrayList<>();

    TreeNode current = root;

    while (current != null) {
      // if left subtree doesn't exist, process root and move to left
      if (current.left == null) {
        inorder.add(current.data);
        current = current.right;
      } else {
        /*
        If left exists, current can be processed only after processing entire left subtree
        Precisely, after processing the left subtree's rightmost node. So find that node and add a link to current
         */
        TreeNode rightMost = current.left;
        // rightmost right will be null for first time access
        // or will point to current
        // so traverse until node has right child and it is not the current (second access) OR node doesn't have right child (first access)
        while (rightMost.right != null && rightMost.right != current) {
          rightMost = rightMost.right;
        }

        if (rightMost.right == null) { // first access, establish thread to current to traverse
          rightMost.right = current;
          current = current.left;
        } else { // second access. Means entire left subtree of current is already processed, remove thread and process root.
          rightMost.right = null;
          inorder.add(current.data);
          current = current.right;
        }
      }
    }

    return inorder;
  }
}