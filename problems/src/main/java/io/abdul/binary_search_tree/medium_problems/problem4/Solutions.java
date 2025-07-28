package io.abdul.binary_search_tree.medium_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertAll("Validate BST",

        // Valid BST: [5, 3, 6, 2, 4, null, 7]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          assertTrue(solution.isBST(root));
        },

        // Invalid BST: [5, 3, 6, 4, 2, null, 7] - 4 and 2 are in wrong places
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 4, 2, null, 7});
          assertFalse(solution.isBST(root));
        },

        // Valid BST: [2, 1, 3]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{2, 1, 3});
          assertTrue(solution.isBST(root));
        },

        // Single-node tree
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{42});
          assertTrue(solution.isBST(root));
        },

        // Invalid BST with duplicate on right: [2, 1, 2]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{2, 1, 2});
          assertFalse(solution.isBST(root));
        },

        // Invalid BST deep violation: [10, 5, 15, null, null, 6, 20]
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 5, 15, null, null, 6, 20});
          assertFalse(solution.isBST(root));
        },

        // Valid large BST
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{50, 30, 70, 20, 40, 60, 80});
          assertTrue(solution.isBST(root));
        }
    );
  }
}

/*
Optimal - inorder traversal and verifying the increasing order
T - O(n)
S - O(n) - stack
 */
class Solution {

  public boolean isBST(TreeNode root) {
    return inorder(root, new Integer[1]);
  }

  private boolean inorder(TreeNode root, Integer[] prev) {
    if (root == null) {
      return true;
    }

    boolean bst = inorder(root.left, prev);
    if (!bst) {
      return false;
    }

    // prev[0] is null for the first ever element being visited
    if (prev[0] != null && root.data <= prev[0]) {
      return false;
    }

    prev[0] = root.data;

    return inorder(root.right, prev);
  }
}

/*
Optimal - Iterative
T - O(n)
S - O(n)
 */
class Solution2 {

  public boolean isBST(TreeNode root) {
    Integer prev = null;
    TreeNode current = root;

    Stack<TreeNode> stack = new Stack<>();

    while (true) {
      if (current != null) {
        stack.push(current);
        current = current.left;
      } else {
        if (stack.isEmpty()) {
          return true;
        }
        TreeNode r = stack.pop(); // process root
        if (prev != null && r.data <= prev) {
          return false;
        }
        prev = r.data;
        current = r.right;
      }
    }
  }
}