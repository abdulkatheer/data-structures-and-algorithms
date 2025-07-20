package io.abdul.binary_tree.faqs.problem9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Case 1: Mixed tree
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(1, 3, 2, 5, 3, null, 9));
    assertEquals(4, solution.widthOfBinaryTree(root1));

    // Case 2: Sparse tree
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(1, 3, 2, 5, null, null, 9, 6, null, 7));
    assertEquals(7, solution.widthOfBinaryTree(root2));

    // Case 3: Mixed tree with gaps
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(5, 1, 2, 8, null, 4, 5, null, 6));
    assertEquals(4, solution.widthOfBinaryTree(root3));

    // Case 4: Only root
    TreeNode root4 = TreeNode.buildTree(Arrays.asList(1));
    assertEquals(1, solution.widthOfBinaryTree(root4));

    // Case 5: Left skewed tree (like linked list)
    TreeNode root5 = TreeNode.buildTree(Arrays.asList(1, 2, null, 3, null, 4, null));
    assertEquals(1, solution.widthOfBinaryTree(root5));

    // Case 6: Right skewed tree (like linked list)
    TreeNode root6 = TreeNode.buildTree(Arrays.asList(1, null, 2, null, 3, null, 4));
    assertEquals(1, solution.widthOfBinaryTree(root6));

    // Case 7: Complete binary tree (full width at last level)
    TreeNode root7 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    assertEquals(4, solution.widthOfBinaryTree(root7));
  }
}

/*
Brute-force

Level order traversal with numbers on nodes
 */

class Solution {

  public int widthOfBinaryTree(TreeNode root) {

    Queue<TreeNodeWrapper> q = new LinkedList<>();
    q.offer(new TreeNodeWrapper(0, root));

    int max = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      if (size == 1) { // Edge case, when only one node exists in a level
        max = Math.max(max, 1);
        TreeNodeWrapper n = q.poll();
        if (n.node.left != null) {
          q.offer(new TreeNodeWrapper(2 * n.position + 1, n.node.left));
        }

        if (n.node.right != null) {
          q.offer(new TreeNodeWrapper(2 * n.position + 2, n.node.right));
        }
      } else {
        int start = 0, end = 0;
        for (int i = 0; i < size; i++) {
          TreeNodeWrapper n = q.poll();

          if (i == 0) {
            start = n.position;
          } else if (i == size - 1) {
            end = n.position;
          }

          if (n.node.left != null) {
            q.offer(new TreeNodeWrapper(2 * n.position + 1, n.node.left));
          }

          if (n.node.right != null) {
            q.offer(new TreeNodeWrapper(2 * n.position + 2, n.node.right));
          }
        }

        max = Math.max(max, end - start + 1);
      }
    }

    return max;
  }
}

class TreeNodeWrapper {

  final int position;
  final TreeNode node;

  public TreeNodeWrapper(int position, TreeNode node) {
    this.position = position;
    this.node = node;
  }
}
