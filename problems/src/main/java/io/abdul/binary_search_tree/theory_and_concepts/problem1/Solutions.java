package io.abdul.binary_search_tree.theory_and_concepts.problem1;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertAll("Search in BST",

        // Case 1: Match found (val = 2)
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{4, 2, 7, 1, 3});
          TreeNode result = solution.searchBST(root, 2);
          assertTrue(TreeNode.areEqual(result, TreeNode.buildTree(new Integer[]{2, 1, 3})), "Match found for 2");
        },

        // Case 2: No match found (val = 5)
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{4, 2, 7, 1, 3});
          TreeNode result = solution.searchBST(root, 5);
          assertNull(result, "No match found for 5");
        },

        // Case 3: Match found with nested subtree (val = 2)
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 2, 12, 1, 4, null, null, null, null, 3});
          TreeNode result = solution.searchBST(root, 2);
          assertTrue(TreeNode.areEqual(result, TreeNode.buildTree(new Integer[]{2, 1, 4, null, null, 3})), "Nested subtree for 2");
        },

        // Case 4: Match is root
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{7, 3, 9});
          TreeNode result = solution.searchBST(root, 7);
          assertTrue(TreeNode.areEqual(result, root), "Root node matched");
        },

        // Case 5: Leaf node search
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{8, 3, 10, 1, 6, null, 14});
          TreeNode result = solution.searchBST(root, 14);
          assertTrue(TreeNode.areEqual(result, TreeNode.buildTree(new Integer[]{14})), "Leaf node matched");
        },

        // Case 6: Single-node tree (match)
        () -> {
          TreeNode root = new TreeNode(5);
          TreeNode result = solution.searchBST(root, 5);
          assertEquals(5, result.data, "Single node matched");
        },

        // Case 7: Single-node tree (no match)
        () -> {
          TreeNode root = new TreeNode(5);
          TreeNode result = solution.searchBST(root, 6);
          assertNull(result, "Single node no match");
        },

        // Case 8: Empty tree
        () -> {
          TreeNode result = solution.searchBST(null, 1);
          assertNull(result, "Empty tree");
        }
    );
  }
}

class Solution {

  public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
      return null;
    }

    if (root.data == val) {
      return root;
    } else if (val < root.data) {
      return searchBST(root.left, val);
    } else {
      return searchBST(root.right, val);
    }
  }
}

class Solution2 {

  public TreeNode searchBST(TreeNode root, int val) {
    TreeNode current = root;

    while (current != null) {
      if (current.data == val) {
        return current;
      } else if (val < current.data) {
        current = current.left;
      } else {
        current = current.right;
      }
    }

    return null;
  }
}