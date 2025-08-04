package io.abdul.ds.problem24;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    isValidBST();
    isValidBSTTestTwo();
  }

  static void isValidBST() {
    Solution4 os = new Solution4();
    TreeNode n1 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
    assertTrue(os.isValidBST(n1));
  }

  static void isValidBSTTestTwo() {
    TreeNode n5 = new TreeNode(5);
    TreeNode n1 = new TreeNode(1);
    TreeNode n4 = new TreeNode(4);
    TreeNode n3 = new TreeNode(3);
    TreeNode n6 = new TreeNode(6);

    n5.left = n1;
    n5.right = n4;
    n4.left = n3;
    n4.right = n6;

    Solution4 os = new Solution4();
    assertFalse(os.isValidBST(n5));
  }
}

/**
 * Time Complexity - O(n) Space Complexity - O(n)
 */
class Solution {

  public boolean isValidBST(TreeNode root) {
    Stack<TreeNode> s = new Stack<>();
    TreeNode current = root;
    Integer lastElement = null;

    while (current != null || !s.isEmpty()) { // Iterate until current exists OR stack has elements
      while (current != null) { // Traverse to the left of current and add to stack
        s.push(current);
        current = current.left;
      }

      current = s.pop();
      if (lastElement != null
          && lastElement >= current.data) { // Next value is smaller than lastElement.
        return false;
      }
      lastElement = current.data;
      current = current.right; // repeat the same with right of current, if not pick next element from the stack
    }
    return true; // all elements are in-order
  }
}

/*
Better - Top-down approach
 */
class Solution2 {

  public boolean isValidBST(TreeNode root) {
    return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  private boolean isValidBST(TreeNode node, long min, long max) {
    if (node == null) {
      return true;
    }
    if (node.data <= min || node.data >= max) {
      return false;
    }
    return isValidBST(node.left, min, node.data) && isValidBST(node.right, node.data, max);
  }
}

/*
Better - Bottom-up approach
 */
class Solution3 {

  public boolean isValidBST(TreeNode root) {
    Result result = isValidBSTBottomUp(root);
    return result.validBst();
  }

  private Result isValidBSTBottomUp(TreeNode root) {
    if (root == null) {
      return new Result(true, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
      // this is to enable validation of leaf nodes and where one child exists
    }

    Result leftResult = isValidBSTBottomUp(root.left);
    Result rightResult = isValidBSTBottomUp(root.right);
    if (!leftResult.validBst()
        || !rightResult.validBst()
        || !(root.data > leftResult.max())
        || !(root.data < rightResult.min())
    ) {
      return new Result(false, 0, 0, 0);
    }

    int count = leftResult.count() + rightResult.count() + 1;
    return new Result(true,
        Math.min(root.data, leftResult.min()),
        // if left doesn't exist, min is Int.MAX, so use node.data
        Math.max(root.data, rightResult.max()),
        // if right doesn't exist, max is Int.MIN, so use node.data
        count);
  }
}

record Result(boolean validBst, int min, int max, int count) {

}

/*
Optimal - Using Morris Traversal
Time Complexity - O(n)
Space Complexity - O(1)
 */
class Solution4 {

  public boolean isValidBST(TreeNode root) {
    Integer lastElement = null;

    TreeNode current = root;
    while (current != null) {
      if (current.left == null) {
        if (lastElement != null
            && lastElement >= current.data) { // Next value is smaller than lastElement.
          return false;
        }
        lastElement = current.data;
        current = current.right;
      } else {
        TreeNode predecessor = findPredecessor(current);
        if (predecessor.right != null) { // a temp link already made to the current node
          predecessor.right = null; // resetting the link
          if (lastElement != null
              && lastElement >= current.data) { // Next value is smaller than lastElement.
            return false;
          }
          lastElement = current.data;
          current = current.right; // When a cycle is completed, the left subtree of the node is completed
        } else {
          predecessor.right = current; // created a temp link to the current node, to come back to it later
          current = current.left;
        }
      }
    }
    return true; // all elements are in-order
  }

  private static TreeNode findPredecessor(TreeNode root) {
    TreeNode predecessor = root.left;
    if (predecessor == null) {
      throw new IllegalArgumentException("No predecessor found");
    }
    // Till there is a right element to the predecessor AND the right element is not root
    while (predecessor.right != null
        && predecessor.right != root) { // modified to check cycle back to root
      predecessor = predecessor.right;
    }
    return predecessor;
  }
}