package io.abdul.binary_tree.medium_problems.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 2: Single node
    TreeNode root2 = new TreeNode(1);
    assertEquals(1, solution.maxDepth(root2));

    // Case 3: Left skewed tree (3 -> 2 -> 1)
    TreeNode root3 = new TreeNode(3);
    root3.left = new TreeNode(2);
    root3.left.left = new TreeNode(1);
    assertEquals(3, solution.maxDepth(root3));

    // Case 4: Right skewed tree (1 -> 2 -> 3)
    TreeNode root4 = new TreeNode(1);
    root4.right = new TreeNode(2);
    root4.right.right = new TreeNode(3);
    assertEquals(3, solution.maxDepth(root4));

    // Case 5: Balanced tree
    TreeNode root5 = new TreeNode(1);
    root5.left = new TreeNode(2);
    root5.right = new TreeNode(3);
    root5.left.left = new TreeNode(4);
    root5.left.right = new TreeNode(5);
    root5.right.left = new TreeNode(6);
    root5.right.right = new TreeNode(7);
    assertEquals(3, solution.maxDepth(root5));

    // Case 6: Unbalanced tree [5, 1, 2, 8, null, null, 5, null, 4, null, null, 7]
    TreeNode root6 = new TreeNode(5);
    root6.left = new TreeNode(1);
    root6.right = new TreeNode(2);
    root6.left.left = new TreeNode(8);
    root6.left.left.right = new TreeNode(4);
    root6.right.right = new TreeNode(5);
    root6.right.right.left = new TreeNode(7);
    assertEquals(4, solution.maxDepth(root6));
  }
}

/*
Recursive

 */
class Solution {

  public int maxDepth(TreeNode root) {
    return findMaxDepth(root);
  }

  private int findMaxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }

    return 1 + Math.max(findMaxDepth(root.left), findMaxDepth(root.right));
  }
}

/*
Iterative - Level order traversal

 */
class Solution2 {

  public int maxDepth(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();

    queue.add(root);

    int level = 0;
    while (!queue.isEmpty()) {
      int nodesInLevel = queue.size();

      // remove all nodes in this level and add all of their children
      for (int i = 0; i < nodesInLevel; i++) {
        TreeNode node = queue.poll();

        if (node.left != null) {
          queue.add(node.left);
        }

        if (node.right != null) {
          queue.add(node.right);
        }
      }

      level++;
    }

    return level;
  }
}

