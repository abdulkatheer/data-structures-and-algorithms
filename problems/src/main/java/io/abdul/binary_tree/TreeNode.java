package io.abdul.binary_tree;

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
}