package io.abdul.binary_tree.medium_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1: [3, 9, 20, null, null, 15, 7] → true
    TreeNode t1 = new TreeNode(3,
        new TreeNode(9),
        new TreeNode(20, new TreeNode(15), new TreeNode(7))
    );
    assertTrue(solution.isBalanced(t1));

    // Case 2: [1, 2, null, null, 3] → false
    TreeNode t2 = new TreeNode(1,
        new TreeNode(2, null, new TreeNode(3)),
        null
    );
    assertFalse(solution.isBalanced(t2));

    // Case 3: [5, 1, 2, 8, 3, null, 5, null, 4] → false
    TreeNode t3 = new TreeNode(5,
        new TreeNode(1,
            new TreeNode(8, null, new TreeNode(4)),
            new TreeNode(3)
        ),
        new TreeNode(2, null, new TreeNode(5))
    );
    assertTrue(solution.isBalanced(t3));

    // Case 4: Single node → true
    TreeNode t4 = new TreeNode(42);
    assertTrue(solution.isBalanced(t4));

    // Case 5: Empty tree → true
    TreeNode t5 = null;
    assertTrue(solution.isBalanced(t5));

    // Case 6: Perfectly balanced full binary tree → true
    TreeNode t6 = new TreeNode(1,
        new TreeNode(2, new TreeNode(4), new TreeNode(5)),
        new TreeNode(3, new TreeNode(6), new TreeNode(7))
    );
    assertTrue(solution.isBalanced(t6));

    // Case 7: Left-skewed (deep) tree → false
    TreeNode t7 = new TreeNode(1,
        new TreeNode(2,
            new TreeNode(3,
                new TreeNode(4), null
            ),
            null
        ),
        null
    );
    assertFalse(solution.isBalanced(t7));

    // Case 8: Right-skewed shallow tree of 2 levels → true
    TreeNode t8 = new TreeNode(1, null, new TreeNode(2));
    assertTrue(solution.isBalanced(t8));

    // Case 9: Root with one child having deep subtree → false
    TreeNode t9 = new TreeNode(1,
        new TreeNode(2,
            new TreeNode(3,
                new TreeNode(4), null
            ),
            null
        ),
        new TreeNode(5)
    );
    assertFalse(solution.isBalanced(t9));
  }
}

/*
Brute-force
T - O(n^2) - each element traverses every other element
S - O(n)

A tree is balanced, if height of left and right subtree is not differing by more than 1
and also left subtree and right subtree are balanced too
 */
class Solution {

  /*
  T - O(n^2)
  S - O(n)
  */
  public boolean isBalanced(TreeNode root) {
    if (root == null) {
      return true;
    }

    int leftH = height(root.left);
    int rightH = height(root.right);

    return Math.abs(leftH - rightH) <= 1 && isBalanced(root.left) && isBalanced(root.right);
  }

  /*
  T - O(n^2)
  S - O(n)
   */
  private int height(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftH = height(root.left);
    int rightH = height(root.right);

    return 1 + Math.max(leftH, rightH);
  }
}

/*
Optimal
T - O(n)
S - O(n)

height() function calculates the right of the root node in O(n) time
It works by finding the heights of the leaf nodes and building it up.
This is DFS.

So while coming up, if we verify the heights are balanced at each step, then we can find the result then and there.

But height has to return height, how can we manage to say balanced or not?

-1? -1 is not a valid height. So we can use that magic number to identify that height has not been calculated as somewhere in the tree is not balanced.
 */

class Solution2 {

  public boolean isBalanced(TreeNode root) {
    return height(root) != -1;
  }

  private int height(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftH = height(root.left);

    if (leftH == -1) { // left tree is unbalanced, so stop
      return -1;
    }

    int rightH = height(root.right);

    if (rightH == -1) { // right tree is unbalanced, so stop
      return -1;
    }

    if (Math.abs(leftH - rightH) > 1) { // current (root) tree is unbalanced, so stop
      return -1;
    }

    return 1 + Math.max(leftH, rightH); // root, left and right are balanced, calculate height
  }
}