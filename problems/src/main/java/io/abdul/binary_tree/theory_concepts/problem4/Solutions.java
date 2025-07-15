package io.abdul.binary_tree.theory_concepts.problem4;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solutions {

}

/*
We need to collect data at each level from left to right
Queue initially has root alone
Deque it and add left and right to the queue
and add 1 to result[0]

2 and 3 are in queue now
Deque 2 add to result[1]
Add left and right of 2 to the queue
Deque 3 add to result[1]
Add left and right of 3 to the queue
 */
class Solution {

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> data = new ArrayList<>();
    if (root == null) {
      return data;
    }

    Queue<TreeNode> queue = new LinkedList<>();

    queue.add(root);

    while (!queue.isEmpty()) { // goes on from level 0 to level n-1
      int size = queue.size();
      List<Integer> levelData = new ArrayList<>(size);

      for (int i = 0; i < size; i++) { // get all the current level elements
        TreeNode n = queue.poll();

        levelData.add(n.data);

        if (n.left != null) {
          queue.add(n.left);
        }

        if (n.right != null) {
          queue.add(n.right);
        }
      }

      data.add(levelData);
      // all nodes of the current level are removed from queue, added to data and their children are added to queue in order
    }

    return data;
  }
}