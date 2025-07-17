package io.abdul.binary_tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 **/
public class TreeNode {

  public int data;
  public TreeNode left;
  public TreeNode right;


  public TreeNode(int val) {
    data = val;
    left = null;
    right = null;
  }

  public TreeNode(int data, TreeNode left, TreeNode right) {
    this.data = data;
    this.left = left;
    this.right = right;
  }

  // Helper to build a tree from level-order array with nulls
  public static TreeNode buildTree(Integer[] values) {
    if (values == null || values.length == 0 || values[0] == null) {
      return null;
    }

    TreeNode root = new TreeNode(values[0]);
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    int i = 1;
    while (i < values.length) {
      TreeNode current = queue.poll();
      if (i < values.length && values[i] != null) {
        current.left = new TreeNode(values[i]);
        queue.offer(current.left);
      }
      i++;
      if (i < values.length && values[i] != null) {
        current.right = new TreeNode(values[i]);
        queue.offer(current.right);
      }
      i++;
    }
    return root;
  }

  public static TreeNode buildTree(List<Integer> values) {
    if (values == null || values.isEmpty() || values.get(0) == null) {
      return null;
    }

    TreeNode root = new TreeNode(values.get(0));
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    int i = 1;
    while (i < values.size()) {
      TreeNode current = queue.poll();
      if (i < values.size() && values.get(i) != null) {
        current.left = new TreeNode(values.get(i));
        queue.offer(current.left);
      }
      i++;
      if (i < values.size() && values.get(i) != null) {
        current.right = new TreeNode(values.get(i));
        queue.offer(current.right);
      }
      i++;
    }
    return root;
  }
}