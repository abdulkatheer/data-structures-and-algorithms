package io.abdul.binary_tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

  public static boolean areEqual(TreeNode a, TreeNode b) {
    if (a == null && b == null) {
      return true;
    }
    if (a == null || b == null || a.data != b.data) {
      return false;
    }
    return areEqual(a.left, b.left) && areEqual(a.right, b.right);
  }

  // Helper to build a tree from level-order array with nulls
  // Level-order with Dynamic Gaps (Skipped Nulls)
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

  // Strict 2i+1, 2i+2 Rule (Complete Binary Tree Indexing)

//  public static TreeNode buildTree(Integer[] values) {
//    if (values == null || values.length == 0 || values[0] == null) {
//      return null;
//    }
//
//    TreeNode[] nodes = new TreeNode[values.length];
//    for (int i = 0; i < values.length; i++) {
//      if (values[i] != null) {
//        nodes[i] = new TreeNode(values[i]);
//      }
//    }
//
//    for (int i = 0; i < values.length; i++) {
//      if (nodes[i] != null) {
//        int leftIdx = 2 * i + 1;
//        int rightIdx = 2 * i + 2;
//        if (leftIdx < values.length) {
//          nodes[i].left = nodes[leftIdx];
//        }
//        if (rightIdx < values.length) {
//          nodes[i].right = nodes[rightIdx];
//        }
//      }
//    }
//
//    return nodes[0];
//  }
//
//  public static TreeNode buildTree(List<Integer> values) {
//    if (values == null || values.isEmpty() || values.get(0) == null) {
//      return null;
//    }
//
//    TreeNode[] nodes = new TreeNode[values.size()];
//    for (int i = 0; i < values.size(); i++) {
//      if (values.get(i) != null) {
//        nodes[i] = new TreeNode(values.get(i));
//      }
//    }
//
//    for (int i = 0; i < values.size(); i++) {
//      if (nodes[i] != null) {
//        int leftIdx = 2 * i + 1;
//        int rightIdx = 2 * i + 2;
//        if (leftIdx < values.size()) {
//          nodes[i].left = nodes[leftIdx];
//        }
//        if (rightIdx < values.size()) {
//          nodes[i].right = nodes[rightIdx];
//        }
//      }
//    }
//
//    return nodes[0];
//  }

  public static Map<Integer, TreeNode> mapTreeByValue(TreeNode root) {
    Map<Integer, TreeNode> map = new HashMap<>();
    mapByValueHelper(root, map);
    return map;
  }

  private static void mapByValueHelper(TreeNode node, Map<Integer, TreeNode> map) {
    if (node != null) {
      map.put(node.data, node);
      mapByValueHelper(node.left, map);
      mapByValueHelper(node.right, map);
    }
  }

  public static TreeNode find(TreeNode root, int targetVal) {
    if (root == null) {
      return null;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      TreeNode current = queue.poll();
      if (current.data == targetVal) {
        return current;
      }
      if (current.left != null) {
        queue.offer(current.left);
      }
      if (current.right != null) {
        queue.offer(current.right);
      }
    }

    return null; // not found
  }
}