package io.abdul.binary_tree.medium_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1: [1,2,3,4,5]  → diameter = 3
    TreeNode t1 = new TreeNode(1,
        new TreeNode(2,
            new TreeNode(4),
            new TreeNode(5)),
        new TreeNode(3));
    assertEquals(3, solution.diameterOfBinaryTree(t1));

    // Case 2: [1,2,3,null,4,null,5] → diameter = 4
    TreeNode t2 = new TreeNode(1,
        new TreeNode(2, null, new TreeNode(4)),
        new TreeNode(3, null, new TreeNode(5)));
    assertEquals(4, solution.diameterOfBinaryTree(t2));

    // Case 3: [5,1,2,8,3,null,5,null,4] → diameter = 5
    TreeNode t3 = new TreeNode(5,
        new TreeNode(1,
            new TreeNode(8, null, new TreeNode(4)),
            new TreeNode(3)),
        new TreeNode(2, null, new TreeNode(5)));
    assertEquals(5, solution.diameterOfBinaryTree(t3));

    // Case 4: single node → diameter = 0
    TreeNode t4 = new TreeNode(42);
    assertEquals(0, solution.diameterOfBinaryTree(t4));

    // Case 5: left‑skewed chain of 5 nodes → diameter = 4
    TreeNode t5 = new TreeNode(1,
        new TreeNode(2,
            new TreeNode(3,
                new TreeNode(4,
                    new TreeNode(5),
                    null),
                null),
            null),
        null);
    assertEquals(4, solution.diameterOfBinaryTree(t5));

    // Case 6: perfect balanced tree depth 3 → diameter = 4
    TreeNode t6 = new TreeNode(1,
        new TreeNode(2,
            new TreeNode(4),
            new TreeNode(5)),
        new TreeNode(3,
            new TreeNode(6),
            new TreeNode(7)));
    assertEquals(4, solution.diameterOfBinaryTree(t6));
  }
}

/*
Brute
T - O(n^2) - every node in the tree explores every other node
S - O(n)

Longest distance between two element can exist between the deepest leaf node in left and right subtree
And the max can happen for any subtree in the tree. So find dia for all.

 */
class Solution {

  public int diameterOfBinaryTree(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int currentDia = height(root.left) + height(root.right);
    int leftDia = diameterOfBinaryTree(root.left);
    int rightDia = diameterOfBinaryTree(root.right);
    return Math.max(currentDia, Math.max(leftDia, rightDia));
  }

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

We can find in DFS way, and save max dia in an output argument
 */
class Solution2 {

  public int diameterOfBinaryTree(TreeNode root) {
    int[] dia = new int[1];
    height(root, dia);
    return dia[0];
  }

  private int height(TreeNode root, int[] dia) {
    if (root == null) {
      return 0;
    }

    int leftH = height(root.left, dia);
    int rightH = height(root.right, dia);
    int currentDia = leftH + rightH;
    dia[0] = Math.max(dia[0], currentDia);
    return 1 + Math.max(leftH, rightH);
  }
}