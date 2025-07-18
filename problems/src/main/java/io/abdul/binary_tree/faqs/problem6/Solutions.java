package io.abdul.binary_tree.faqs.problem6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Case 1: [1, 2, 3, null, 5, null, 4]
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(1, 2, 3, null, 5, null, 4));
    List<Integer> expected1 = Arrays.asList(1, 3, 4);
    assertEquals(expected1, solution.rightSideView(root1));

    // Case 2: [1, 2, 3, 6, 5, 8, 4]
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 6, 5, 8, 4));
    List<Integer> expected2 = Arrays.asList(1, 3, 4);
    assertEquals(expected2, solution.rightSideView(root2));

    // Case 3: [5, 1, 2, 8, null, 4, 5, null, 6]
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(5, 1, 2, 8, null, 4, 5, null, 6));
    List<Integer> expected3 = Arrays.asList(5, 2, 5, 6);
    assertEquals(expected3, solution.rightSideView(root3));

    // Optional Case 4: Right skewed tree
    TreeNode root4 = TreeNode.buildTree(Arrays.asList(1, null, 2, null, 3, null, 4));
    List<Integer> expected4 = Arrays.asList(1, 2, 3, 4);
    assertEquals(expected4, solution.rightSideView(root4));

    // Optional Case 5: Left skewed tree
    TreeNode root5 = TreeNode.buildTree(Arrays.asList(1, 2, null, 3, null, 4));
    List<Integer> expected5 = Arrays.asList(1, 2, 3, 4);
    assertEquals(expected5, solution.rightSideView(root5));
  }
}

/*
Optimal - Right view
T - O(n)
S - O(n)

Level order traversal
 */
class Solution {

  public List<Integer> rightSideView(TreeNode root) {
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    List<Integer> rightView = new ArrayList<>();
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        TreeNode n = q.poll();
        if (i == size - 1) { // last node in the level
          rightView.add(n.data);
        }

        if (n.left != null) {
          q.offer(n.left);
        }

        if (n.right != null) {
          q.offer(n.right);
        }
      }
    }
    return rightView;
  }
}