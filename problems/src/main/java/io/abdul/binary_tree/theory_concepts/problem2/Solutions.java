package io.abdul.binary_tree.theory_concepts.problem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solutions {

}

/**
 * Definition for a binary tree node.
 **/
class TreeNode {

  int data;
  TreeNode left;
  TreeNode right;


  TreeNode(int val) {
    data = val;
    left = null;
    right = null;
  }
}

/*
Recursive solution

 */
class Solution {

  public List<Integer> preorder(TreeNode root) {
    ArrayList<Integer> data = new ArrayList<>();
    preorderTraversal(root, data);
    return data;
  }

  private void preorderTraversal(TreeNode root, List<Integer> data) {
    if (root == null) {
      return;
    }

    data.add(root.data);
    preorderTraversal(root.left, data);
    preorderTraversal(root.right, data);
  }
}

/*
Iterative solution

 */
class Solution2 {

  public List<Integer> preorder(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode currentNode = root;
    List<Integer> data = new ArrayList<>();

    while (true) {
      if (currentNode != null) {
        data.add(currentNode.data); // process root
        stack.push(currentNode);
        currentNode = currentNode.left;
      } else {
        if (stack.isEmpty()) { // processed all roots
          break;
        }

        TreeNode r = stack.pop();
        currentNode = r.right;
      }
    }

    return data;
  }
}