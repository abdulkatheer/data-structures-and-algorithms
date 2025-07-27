package io.abdul.binary_search_tree.medium_problems.problem1;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    assertAll("BST Insert Correctness Check",

        // Case 1
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{4, 2, 7, 1, 3});
          TreeNode updated = solution.insertIntoBST(root, 5);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(1, 2, 3, 4, 5, 7)),
              "BST must contain all expected values");
        },

        // Case 2
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{40, 20, 60, 10, 30, 50, 70});
          TreeNode updated = solution.insertIntoBST(root, 25);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(10, 20, 25, 30, 40, 50, 60, 70)),
              "BST must contain all expected values");
        },

        // Case 3
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{4, 2, 7, 1, null, 6});
          TreeNode updated = solution.insertIntoBST(root, 3);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(1, 2, 3, 4, 6, 7)),
              "BST must contain all expected values");
        },

        // Case 4: Insert into empty tree
        () -> {
          TreeNode updated = solution.insertIntoBST(null, 42);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(42)),
              "BST must contain only the inserted value");
        },

        // Case 5: Insert into rightmost
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 5, 15});
          TreeNode updated = solution.insertIntoBST(root, 20);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(5, 10, 15, 20)),
              "BST must contain all expected values");
        },

        // Case 6: Insert into leftmost
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 5, 15});
          TreeNode updated = solution.insertIntoBST(root, 1);
          assertTrue(TreeNode.isValidBST(updated), "Tree must satisfy BST properties");
          assertTrue(TreeNode.containsAllValues(updated, List.of(1, 5, 10, 15)),
              "BST must contain all expected values");
        }
    );
  }
}

/*
Standard BST Insertion - Recursive
 */
class Solution {

  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) {
      return new TreeNode(val);
    }

    if (val < root.data) {
      root.left = insertIntoBST(root.left, val);
    } else {
      root.right = insertIntoBST(root.right, val);
    }

    return root;
  }
}

/*
AVL tree Insertion
 */
class Solution2 {

  public TreeNode insertIntoBST(TreeNode root, int val) {
    return null; // TODO implement
  }
}

/*
Red Black tree Insertion
 */
class Solution3 {

  public TreeNode insertIntoBST(TreeNode root, int val) {
    return null; // TODO implement
  }
}

/*
Standard BST Insertion - Iterative
 */
class Solution4 {

  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) {
      return new TreeNode(val);
    }

    TreeNode current = root;

    while (true) {
      if (val < current.data) {
        if (current.left == null) {
          current.left = new TreeNode(val);
          break;
        } else {
          current = current.left;
        }
      } else {
        if (current.right == null) {
          current.right = new TreeNode(val);
          break;
        } else {
          current = current.right;
        }
      }
    }

    return root;
  }
}