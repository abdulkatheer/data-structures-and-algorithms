package io.abdul.binary_tree.faqs.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Case 1: root = [1, 2, 3, null, 4, 8, 5]
    TreeNode root1 = TreeNode.buildTree(new Integer[]{1, 2, 3, null, 4, 8, 5});
    List<List<Integer>> expected1 = Arrays.asList(
        Arrays.asList(1),
        Arrays.asList(3, 2),
        Arrays.asList(4, 8, 5)
    );
    assertEquals(expected1, sol.zigzagLevelOrder(root1));

    // Case 2: root = [3, 9, 20, null, null, 15, 7]
    TreeNode root2 = TreeNode.buildTree(new Integer[]{3, 9, 20, null, null, 15, 7});
    List<List<Integer>> expected2 = Arrays.asList(
        Arrays.asList(3),
        Arrays.asList(20, 9),
        Arrays.asList(15, 7)
    );
    assertEquals(expected2, sol.zigzagLevelOrder(root2));

    // Case 3: root = [5, 1, 2, 8, null, 4, 5, null, 6]
    TreeNode root3 = TreeNode.buildTree(new Integer[]{5, 1, 2, 8, null, 4, 5, null, 6});
    List<List<Integer>> expected3 = Arrays.asList(
        Arrays.asList(5),
        Arrays.asList(2, 1),
        Arrays.asList(8, 4, 5),
        Arrays.asList(6)
    );
    assertEquals(expected3, sol.zigzagLevelOrder(root3));

    // Case 4: Single node
    TreeNode root4 = TreeNode.buildTree(new Integer[]{10});
    List<List<Integer>> expected4 = Arrays.asList(
        Arrays.asList(10)
    );
    assertEquals(expected4, sol.zigzagLevelOrder(root4));

    // Case 5: Skewed left: [1, 2, null, 3, null, 4]
    TreeNode root5 = TreeNode.buildTree(new Integer[]{1, 2, null, 3, null, 4});
    List<List<Integer>> expected5 = Arrays.asList(
        Arrays.asList(1),
        Arrays.asList(2),
        Arrays.asList(3),
        Arrays.asList(4)
    );
    assertEquals(expected5, sol.zigzagLevelOrder(root5));
  }
}

/*
Optimal - Modified level order traversal
T - O(n)
S - O(n)

In normal level order traversal, we get all from left to right (drain queue from front and add child to back)
Here we do it this and opposite of this in vice versa mode.
 */
class Solution {

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {

    List<List<Integer>> result = new ArrayList<>();

    Deque<TreeNode> q = new LinkedList<>();

    q.addLast(root);
    boolean leftToRight = true;

    while (!q.isEmpty()) {
      int size = q.size();
      List<Integer> data = new ArrayList<>(size);
      if (leftToRight) {
        for (int i = 0; i < size; i++) {
          TreeNode node = q.removeFirst();
          data.add(node.data);

          if (node.left != null) {
            q.addLast(node.left);
          }

          if (node.right != null) {
            q.addLast(node.right);
          }
        }

        leftToRight = false;
      } else {
        for (int i = 0; i < size; i++) {
          TreeNode node = q.removeLast();
          data.add(node.data);

          if (node.right != null) {
            q.addFirst(node.right);
          }

          if (node.left != null) {
            q.addFirst(node.left);
          }
        }

        leftToRight = true;
      }

      result.add(data);
    }

    return result;
  }
}