package io.abdul.binary_search_tree.medium_problems.problem5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    assertAll("LCA in BST by value",

        // Test 1: LCA is 3
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode result = solution.lca(root, 2, 4);
          assertNotNull(result);
          assertEquals(3, result.data);
        },

        // Test 2: LCA is 5
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode result = solution.lca(root, 2, 7);
          assertNotNull(result);
          assertEquals(5, result.data);
        },

        // Test 3: LCA is 2
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{2, 1, 4, null, null, 3, 6});
          TreeNode result = solution.lca(root, 1, 6);
          assertNotNull(result);
          assertEquals(2, result.data);
        },

        // Test 4: Same node values — LCA is the node itself
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 2, 8});
          TreeNode result = solution.lca(root, 2, 2);
          assertNotNull(result);
          assertEquals(2, result.data);
        },

        // Test 5: LCA is root
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{6, 2, 8, 0, 4, 7, 9});
          TreeNode result = solution.lca(root, 2, 8);
          assertNotNull(result);
          assertEquals(6, result.data);
        }
    );
  }
}

/*
Better
T - O(n)
S - O(h) - stack
where is the height of the tree

Similar to LCA in a binary tree, but we can avoid left/right traversal if we know p/q is on left/right due to sorted nature
 */
class Solution {

  public TreeNode lca(TreeNode root, int p, int q) {
    if (root == null) {
      return null;
    }

    // In case where root is matching and root is the LCA for another element as well, answer will still work
    if (root.data == p || root.data == q) {
      return root;
    }

    if (p < root.data && q < root.data) { // both on left
      return lca(root.left, p, q);
    } else if (p > root.data && q > root.data) { // both on right
      return lca(root.right, p, q);
    } else {
      TreeNode found1 = lca(root.left, p, q);
      TreeNode found2 = lca(root.right, p, q);
      // If both non null, both and p and q found under root
      if (found1 != null && found2 != null) {
        return root;
      }
      // Either p/q is found or none if found. Return the found one if possible
      return found1 != null ? found1 : found2;
    }
  }
}

/*
Optimal
T - O(h)
S - O(h)
where is the height of the tree

 */
class Solution2 {

  public TreeNode lca(TreeNode root, int p, int q) {
    if (root == null) {
      return null;
    }

    // In case where root is matching and root is the LCA for another element as well, answer will still work
    if (root.data == p || root.data == q) {
      return root;
    }

    if (p < root.data && q < root.data) { // both on left
      return lca(root.left, p, q);
    } else if (p > root.data && q > root.data) { // both on right
      return lca(root.right, p, q);
    } else {
      /*
      2 possibilities here, p on left and q on right OR p on right and q on left
      In either case, no other than root can be the common ancestor. At this point both part ways!
       */
      return root;
    }
  }
}

/*
Optimal - Iterative
T - O(h
 */
class Solution3 {

  public TreeNode lca(TreeNode root, int p, int q) {
    TreeNode current = root;

    while (current != null) {
      if (p < current.data && q < current.data) {
        current = current.left;
      } else if (p > current.data && q > current.data) {
        current = current.right;
      } else {
        return current;
      }
    }

    return null; // impossible case given that p and q exist for sure
  }
}