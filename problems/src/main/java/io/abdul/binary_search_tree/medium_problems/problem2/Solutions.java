package io.abdul.binary_search_tree.medium_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertAll("BST Deletion Correctness Check",

        // Case 1: Delete node with two children
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode updated = solution.deleteNode(root, 3);
          assertTrue(TreeNode.isValidBST(updated), "Tree must remain a valid BST");
          assertTrue(TreeNode.containsAllValues(updated, List.of(2, 4, 5, 6, 7)),
              "Tree must contain all expected values after deleting 3");
        },

        // Case 2: Delete root node
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode updated = solution.deleteNode(root, 5);
          assertTrue(TreeNode.isValidBST(updated), "Tree must remain a valid BST");
          assertTrue(TreeNode.containsAllValues(updated, List.of(2, 3, 4, 6, 7)),
              "Tree must contain all expected values after deleting root 5");
        },

        // Case 3: Delete leaf node
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode updated = solution.deleteNode(root, 7);
          assertTrue(TreeNode.isValidBST(updated), "Tree must remain a valid BST");
          assertTrue(TreeNode.containsAllValues(updated, List.of(2, 3, 4, 5, 6)),
              "Tree must contain all expected values after deleting 7");
        },

        // Case 4: Delete node that doesn't exist
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, 4, null, 7});
          TreeNode updated = solution.deleteNode(root, 0);
          assertTrue(TreeNode.isValidBST(updated), "Tree must remain a valid BST");
          assertTrue(TreeNode.containsAllValues(updated, List.of(2, 3, 4, 5, 6, 7)),
              "Tree must remain unchanged when deleting non-existent 0");
        },

        // Case 5: Delete node with one child
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 5, 15, null, null, 12});
          TreeNode updated = solution.deleteNode(root, 15);
          assertTrue(TreeNode.isValidBST(updated), "Tree must remain a valid BST");
          assertTrue(TreeNode.containsAllValues(updated, List.of(5, 10, 12)),
              "Tree must contain all expected values after deleting 15");
        },

        // Case 6: Delete only node in tree
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{42});
          TreeNode updated = solution.deleteNode(root, 42);
          assertNull(updated, "Tree must be null after deleting the only node");
        }
    );
  }
}

/*
Standard BST removal - Recursive
 */
class Solution {

  public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) { // Not found
      return null;
    }

    if (root.data == key) {
      if (root.left == null && root.right == null) { // leaf node
        return null;
      } else if (root.left == null || root.right == null) { // one child
        return root.left != null ? root.left : root.right;
      } else { // two children
        // will be non-null, as right children exist
        TreeNode successor = findSuccessor(root.right, key);
        root.data = successor.data; // copy successor data to root and delete successor
        // might be the right node itself or left-most element in the right subtree
        root.right = deleteNode(root.right, successor.data);
        return root;
      }
    } else if (key < root.data) {
      root.left = deleteNode(root.left, key);
      return root;
    } else {
      root.right = deleteNode(root.right, key);
      return root;
    }
  }

  // the smallest element larger than key will on the left most side possible
  private TreeNode findSuccessor(TreeNode root, int key) {
    if (root.left == null) { // leaf node, root can't be null
      return root;
    }

    return findSuccessor(root.left, key);
  }
}

/*
Standard BST removal - Iterative

In recursive method, the parent of the node being deleted will be in the stack. So if we just return the nodes without deleteNode, it'll just work fine.
It works well for leaf, root, and any node in the tree.

But in iterative method, we can reproduce this if we can Stack DS and mimic a recursive callstack.
Otherwise, we need to think differently.

We'll think differently!

1) if root node going to be deleted, no parent exists for it and also the root will change after deletion
2) otherwise we can keep track of the parent and whether it's a left or right child of parent, and after deletion we'll update that side alone.
 */
class Solution2 {

  public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) {
      return null;
    }

    if (root.data == key) {
      return deleteRootNode(root);
    } else {
      return deleteNonRootNode(root, key);
    }
  }

  private TreeNode deleteNonRootNode(TreeNode root, int key) {
    TreeNode parent = root;
    TreeNode current;
    boolean childOnLeft;
    if (key < root.data) {
      current = root.left;
      childOnLeft = true;
    } else {
      current = root.right;
      childOnLeft = false;
    }

    while (current != null) {
      if (current.data == key) {
        if (current.left == null && current.right == null) { // leaf node
          if (childOnLeft) {
            parent.left = null;
            break;
          } else {
            parent.right = null;
            break;
          }
        } else if (current.left == null || current.right == null) { // one child
          if (childOnLeft) {
            parent.left = current.left != null ? current.left : current.right;
            break;
          } else {
            parent.right = current.left != null ? current.left : current.right;
            break;
          }
        } else { // two children
          TreeNode successor = findSuccessor(current.right);
          current.data = successor.data;
          // may be a root node or non-root node
          current.right = deleteNode(current.right, successor.data);
          break;
        }
      } else if (key < current.data) {
        parent = current;
        current = current.left;
        childOnLeft = true;
      } else {
        parent = current;
        current = current.right;
        childOnLeft = false;
      }
    }

    return root;
  }

  private TreeNode deleteRootNode(TreeNode root) {
    if (root.left == null && root.right == null) { // root is a leaf
      return null;
    } else if (root.left == null || root.right == null) { // root has one child
      return root.left != null ? root.left : root.right;
    } else { // root has two children
      TreeNode successor = findSuccessor(root.right);
      root.data = successor.data;
      // root.right itself might be the successor or the left-most element in the right subtree
      root.right = deleteNode(root.right, successor.data);
      return root;
    }
  }

  private TreeNode findSuccessor(TreeNode root) {
    TreeNode current = root;

    while (current.left != null) {
      current = current.left;
    }

    return current;
  }
}