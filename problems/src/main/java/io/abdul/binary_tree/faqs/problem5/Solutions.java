package io.abdul.binary_tree.faqs.problem5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Case 1: [20, 8, 22, 5, 3, null, 25, null, null, 10 ,14]
    TreeNode root1 = TreeNode.buildTree(
        Arrays.asList(20, 8, 22, 5, 3, null, 25, null, null, 10, 14));
    List<Integer> expected1 = Arrays.asList(5, 10, 3, 14, 25);
    assertEquals(expected1, solution.bottomView(root1));

    // Case 2: [20, 8, 22, 5, 3, 4, 25, null, null, 10 ,14]
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(20, 8, 22, 5, 3, 4, 25, null, null, 10, 14));
    List<Integer> expected2 = Arrays.asList(5, 10, 4, 14, 25);
    assertEquals(expected2, solution.bottomView(root2));

    // Case 3: [10, 20, 30, 40, 60]
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(10, 20, 30, 40, 60));
    List<Integer> expected3 = Arrays.asList(40, 20, 60, 30);
    assertEquals(expected3, solution.bottomView(root3));

    // Case 4: [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9]
    TreeNode root4 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 4, 5, 6, 7, null, null, 8, 9));
    List<Integer> expected4 = Arrays.asList(4, 8, 6, 9, 7);
    assertEquals(expected4, solution.bottomView(root4));

    // Case 5: [1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8]
    TreeNode root5 = TreeNode.buildTree(
        Arrays.asList(1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8));
    List<Integer> expected5 = Arrays.asList(6, 7, 3, 8);
    assertEquals(expected5, solution.bottomView(root5));
  }
}

/*

Level order traversal
 */
class Solution {

  public List<Integer> bottomView(TreeNode root) {
    Queue<TreeNodeWrapper> q = new LinkedList<>();
    q.offer(new TreeNodeWrapper(0, root));

    Map<Integer, Integer> lastNodeAtLevel = new TreeMap<>();
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        TreeNodeWrapper n = q.poll();
        lastNodeAtLevel.put(n.level, n.node.data);

        if (n.node.left != null) {
          q.offer(new TreeNodeWrapper(n.level - 1, n.node.left));
        }

        if (n.node.right != null) {
          q.offer(new TreeNodeWrapper(n.level + 1, n.node.right));
        }
      }
    }

    return lastNodeAtLevel.values().stream().toList();
  }
}

class TreeNodeWrapper {

  final int level;
  final TreeNode node;

  public TreeNodeWrapper(int level, TreeNode node) {
    this.level = level;
    this.node = node;
  }
}