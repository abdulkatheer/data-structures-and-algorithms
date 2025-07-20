package io.abdul.binary_tree.faqs.problem12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  public static void main(String[] args) {
    Solution2 solution = new Solution2();

    assertNodeCount(new Integer[]{1, 2, 3, 4, 5, 6}, 6, solution); // complete and balanced
    assertNodeCount(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 9,
        solution); // complete but last level not full
    assertNodeCount(new Integer[]{1, 2, 3}, 3, solution); // 2 full levels
    assertNodeCount(new Integer[]{1}, 1, solution); // single node

    // Additional edge cases

    // Complete but last level has 1 node
    assertNodeCount(new Integer[]{1, 2, 3, 4}, 4, solution);

    // Complete tree, last level half filled
    assertNodeCount(new Integer[]{1, 2, 3, 4, 5, null, null}, 5, solution);

    // Perfect complete tree
    assertNodeCount(new Integer[]{1, 2, 3, 4, 5, 6, 7}, 7, solution);

    // Empty tree
    assertNodeCount(new Integer[]{}, 0, solution);
  }

  private static void assertNodeCount(Integer[] arr, int expected, Solution2 solution) {
    TreeNode root = TreeNode.buildTree(arr);
    int result = solution.countNodes(root);
    assertEquals(expected, result);
  }
}

/*
Brute-force - any traversal
T - O(n)
S - O(n)

 */
class Solution {

  public int countNodes(TreeNode root) {
    return -1;
  }
}

/*
Optimal
T - O(log n) * O(log n)
S - O(log n)

Using Complete Binary Tree property
If left nodes height and right node height are same, then it's a perfect binary tree
If not, it's a complete binary tree

If it's perfect, number of nodes is simple 2^h-1

 */
class Solution2 {

  public int countNodes(TreeNode root) {
    return count(root);
  }

  private int count(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int leftH = leftHeight(root);
    int rightH = rightHeight(root);

    if (leftH == rightH) { // perfect binary tree
      return (1 << leftH) - 1;
    }

    return 1 + count(root.left) + count(root.right);
  }

  private int leftHeight(TreeNode root) {
    int height = 0;

    while (root != null) {
      height++;
      root = root.left;
    }

    return height;
  }

  private int rightHeight(TreeNode root) {
    int height = 0;

    while (root != null) {
      height++;
      root = root.right;
    }

    return height;
  }
}