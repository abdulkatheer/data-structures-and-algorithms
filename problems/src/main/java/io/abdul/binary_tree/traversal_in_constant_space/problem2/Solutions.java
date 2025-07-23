package io.abdul.binary_tree.traversal_in_constant_space.problem2;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    assertAll("Morris Preorder Traversal",

        // Example 1: [1, 4, null, 4, 2]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, 4, null, 4, 2});
          assertEquals(List.of(1, 4, 4, 2), solution.preorder(root), "Example 1");
        },

        // Example 2: [1]
        () -> {
          TreeNode root = new TreeNode(1);
          assertEquals(List.of(1), solution.preorder(root), "Single Node");
        },

        // Example 3: [1, 4, 2, 9, null, null, 6]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, 4, 2, 9, null, null, 6});
          assertEquals(List.of(1, 4, 9, 2, 6), solution.preorder(root), "Example 3");
        },

        // Skewed Left: [5, 4, null, 3, null, 2]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 4, null, 3, null, 2});
          assertEquals(List.of(5, 4, 3, 2), solution.preorder(root), "Left Skewed");
        },

        // Skewed Right: [1, null, 2, null, 3, null, 4]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, null, 2, null, 3, null, 4});
          assertEquals(List.of(1, 2, 3, 4), solution.preorder(root), "Right Skewed");
        },

        // Empty Tree
        () -> {
          TreeNode root = null;
          assertEquals(List.of(), solution.preorder(root), "Empty Tree");
        }
    );
  }
}

class Solution {

  public List<Integer> preorder(TreeNode root) {
    List<Integer> preorder = new ArrayList<>();

    TreeNode current = root;

    while (current != null) {
      if (current.left == null) {
        /*
        left subtree doesn't exist, so process root
         */
        preorder.add(current.data);
        current = current.right;
      } else {
        /*
        left subtree exists. It might have already been processed or yet to process.
        Find the left subtree's rightmost node
        If it's right is null, first access. So add current link and process current.
        If it's right is current, second access. So remove link and move to current right.
         */
        TreeNode rightMost = current.left;
        while (rightMost.right != null && rightMost.right != current) {
          rightMost = rightMost.right;
        }

        /*
        first access - add link to current only and not to current.right. Bcz we need to know current to remove thread after processing left.
        Process current and move to left.
        second access - root and left subtree is already processed, move to right
         */
        if (rightMost.right == null) {
          rightMost.right = current;
          preorder.add(current.data);
          current = current.left;
        } else {
          rightMost.right = null;
          current = current.right;
        }
      }
    }

    return preorder;
  }
}