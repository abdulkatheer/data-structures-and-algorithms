package io.abdul.binary_tree.medium_problems.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1: [20, 9, -10, null, null, 15, 7] → Output: 34
    TreeNode t1 = new TreeNode(20,
        new TreeNode(9),
        new TreeNode(-10,
            new TreeNode(15),
            new TreeNode(7)));
    assertEquals(34, solution.maxPathSum(t1));

    // Case 2: [-10, 9, 20, null, null, 15, 7] → Output: 42
    TreeNode t2 = new TreeNode(-10,
        new TreeNode(9),
        new TreeNode(20,
            new TreeNode(15),
            new TreeNode(7)));
    assertEquals(42, solution.maxPathSum(t2));

    // Case 3: [1, 2, 3, null, 4] → Output: 10
    TreeNode t3 = new TreeNode(1,
        new TreeNode(2, null, new TreeNode(4)),
        new TreeNode(3));
    assertEquals(10, solution.maxPathSum(t3));

    // Case 4: [1] → Output: 1
    TreeNode t4 = new TreeNode(1);
    assertEquals(1, solution.maxPathSum(t4));

    // Case 5: [-3, -2, -1] → Output: -1 (single max node)
    TreeNode t5 = new TreeNode(-3,
        new TreeNode(-2),
        new TreeNode(-1));
    assertEquals(-1, solution.maxPathSum(t5));

    // Case 6: [10, 2, 10, null, null, -20, 1] → Output: 21
    TreeNode t6 = new TreeNode(10,
        new TreeNode(2),
        new TreeNode(10,
            new TreeNode(-20),
            new TreeNode(1)));
    assertEquals(23, solution.maxPathSum(t6));

    // [-5 -10 -9 -2 -4 null -1]
    TreeNode t7 = new TreeNode(-5,
        new TreeNode(-10,
            new TreeNode(-2), new TreeNode(-4)),
        new TreeNode(-9,
            null, new TreeNode(-1)));
    assertEquals(-1, solution.maxPathSum(t7));
  }
}

/*
Brute
T - O(n^2) - every node in the tree explores every other node
S - O(n)

Max path sum can go through any root node
This is similar to dia, but we need to sum the node's values

If we just consider root, max path sum can be at root or root.left or root.right
 */
class Solution {

  public int maxPathSum(TreeNode root) {
    return dfs(root);
  }

  private int dfs(TreeNode node) {
    if (node == null) {
      return -(int) 1e9; // can't be zero as it'll replace negative results
    }

    // compute path sum through this node as center
    int left = maxDownward(node.left); // a path with max sum on left or zero if negative
    int right = maxDownward(node.right); // a path with max sum on right or zero if negative
    int pathSum = node.data + left + right; // max path sum at current node

    return Math.max(pathSum, Math.max(dfs(node.left), dfs(node.right)));
  }

  // Returns the max sum from node downward (only one branch)
  private int maxDownward(TreeNode node) {
    if (node == null) {
      return 0; // 0 bcz we don't need negative paths
    }

    int left = maxDownward(node.left);
    int right = maxDownward(node.right);

    return Math.max(0, node.data + Math.max(left, right));  // ignore negative
  }
}

/*
Optimal
T - O(n)
S - O(n)

We can maintain the max at each step from bottom-up
 */
class Solution2 {

  public int maxPathSum(TreeNode root) {
    int[] max = new int[1];
    max[0] = Integer.MIN_VALUE; // to count negatives as well
    maxDownward(root, max);
    return max[0];
  }

  private int maxDownward(TreeNode node, int[] max) {
    if (node == null) {
      return 0; // 0 bcz we don't negative paths
    }

    int left = maxDownward(node.left, max); // max left path or zero
    int right = maxDownward(node.right, max); // max right path or zero
    max[0] = Math.max(max[0], node.data + left + right); // max path if the current node is at the center
    // the current node may be negative, but we take for sure (as we need to consider at least one node)

    return Math.max(0, node.data + Math.max(left, right));  // ignore negative
  }
}