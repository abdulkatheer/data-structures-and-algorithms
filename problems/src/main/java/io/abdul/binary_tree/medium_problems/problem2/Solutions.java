package io.abdul.binary_tree.medium_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1: p = [1,2,3], q = [1,2,3] → true
    TreeNode p1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
    TreeNode q1 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
    assertTrue(solution.isSameTree(p1, q1));

    // Case 2: p = [1,2,1], q = [1,1,2] → false
    TreeNode p2 = new TreeNode(1, new TreeNode(2), new TreeNode(1));
    TreeNode q2 = new TreeNode(1, new TreeNode(1), new TreeNode(2));
    assertFalse(solution.isSameTree(p2, q2));

    // Case 3:
    // p = [5,1,2,8,null,null,5,null,4,null,null,7]
    TreeNode p3 = new TreeNode(5,
        new TreeNode(1,
            new TreeNode(8, null, new TreeNode(4)),
            null),
        new TreeNode(2,
            null,
            new TreeNode(5, new TreeNode(7), null)));

    // q = [5,1,2,8,null,null,4,null,5,null,null,7]
    TreeNode q3 = new TreeNode(5,
        new TreeNode(1,
            new TreeNode(8, null, new TreeNode(5)),
            null),
        new TreeNode(2,
            null,
            new TreeNode(4, new TreeNode(7), null)));

    assertFalse(solution.isSameTree(p3, q3));
  }
}

/*
Recursive - Modified preorder traversal
T - O(min(p,q))
S - O(min(p,q))

Preorder traverse both
 */
class Solution {

  public boolean isSameTree(TreeNode p, TreeNode q) {
    return preorderTraverse(p, q);
  }

  private boolean preorderTraverse(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
      return true;
    } else if (p == null) {
      return false;
    } else if (q == null) {
      return false;
    } else if (p.data != q.data) { // check root
      return false;
    }

    // Check left and right
    return preorderTraverse(p.left, q.left) && preorderTraverse(p.right, q.right);
  }
}

/*
Iterative - Modified preorder traversal
T - O(min(p,q))
S - O(min(p,q))

 */
class Solution2 {

  public boolean isSameTree(TreeNode p, TreeNode q) {

    Stack<TreeNode> pStack = new Stack<>();
    Stack<TreeNode> qStack = new Stack<>();

    boolean same = true;
    TreeNode pCurrent = p;
    TreeNode qCurrent = q;
    while (true) {
      if (pCurrent != null && qCurrent != null) {
        if (pCurrent.data != qCurrent.data) {
          same = false;
          break;
        }
        pStack.push(pCurrent);
        qStack.push(qCurrent);
        pCurrent = pCurrent.left;
        qCurrent = qCurrent.left;
      } else if (pCurrent == null && qCurrent == null) {
        if (pStack.isEmpty() && qStack.isEmpty()) {
          break; // all matched
        } else if (!pStack.isEmpty() && !qStack.isEmpty()) {
          pCurrent = pStack.pop().right;
          qCurrent = qStack.pop().right;
        } else {
          same = false;
          break;
        }
      } else {
        same = false;
        break;
      }
    }

    return same;
  }
}