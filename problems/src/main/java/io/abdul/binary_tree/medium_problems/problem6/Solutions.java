package io.abdul.binary_tree.medium_problems.problem6;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertTrue(solution.isSymmetric(buildTree(new Integer[]{1, 2, 2, 3, 4, 4, 3})));
    assertFalse(solution.isSymmetric(buildTree(new Integer[]{1, 2, 2, null, 3, null, 3})));
    assertFalse(solution.isSymmetric(buildTree(new Integer[]{1, 2, 3})));
    assertTrue(solution.isSymmetric(buildTree(new Integer[]{1})));
    assertTrue(solution.isSymmetric(buildTree(new Integer[]{1, 2, 2, null, 3, 3, null})));
    assertTrue(solution.isSymmetric(
        buildTree(new Integer[]{1, 2, 2, 3, null, null, 3, 4, null, null, 4})));
    assertTrue(solution.isSymmetric(buildTree(new Integer[]{1, 2, 2, null, 3, 3, null})));
    assertFalse(solution.isSymmetric(buildTree(new Integer[]{1, 2, 2, 3, 4, 3, 4})));
  }

}

/*
Recursive
T - O(n)
S - O(n)

We need to traverse root,left,right for left subtree, and root,right,left for right subtree

left - preorder traversal
right - reverse postorder traversal

 */
class Solution {

  public boolean isSymmetric(TreeNode root) {
    return traverse(root, root);
  }

  private boolean traverse(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) {
      return true;
    }
    if (root1 == null || root2 == null) {
      return false;
    }

    if (root1.data != root2.data) {
      return false;
    }

    return traverse(root1.left, root2.right) && traverse(root1.right, root2.left);
  }
}

class Solution2 {

  public boolean isSymmetric(TreeNode root) {
    return traverse(root, root);
  }

  private boolean traverse(TreeNode root1, TreeNode root2) {
    Stack<TreeNode> s1 = new Stack<>();
    Stack<TreeNode> s2 = new Stack<>();

    TreeNode c1 = root1;
    TreeNode c2 = root2;

    boolean mirror = true;
    while (true) {
      if (c1 != null && c2 != null) {
        if (c1.data != c2.data) {
          mirror = false;
          break;
        }
        s1.push(c1);
        s2.push(c2);
        c1 = c1.left;
        c2 = c2.right;
      } else if (c1 == null && c2 == null) {
        if (s1.isEmpty() && s2.isEmpty()) {
          break; // all matched
        }
        if (s1.isEmpty() || s2.isEmpty()) {
          mirror = false;
          break;
        }
        c1 = s1.pop().right;
        c2 = s2.pop().left;
      } else {
        mirror = false;
        break;
      }
    }

    return mirror;
  }
}