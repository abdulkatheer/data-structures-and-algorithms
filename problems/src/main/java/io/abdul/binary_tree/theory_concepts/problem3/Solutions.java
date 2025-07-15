package io.abdul.binary_tree.theory_concepts.problem3;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Solutions {

}

/*
Recursive solution

 */
class Solution {
  public List<Integer> postorder(TreeNode root) {
    ArrayList<Integer> data = new ArrayList<>();
    postorderTraversal(root, data);
    return data;
  }

  private void postorderTraversal(TreeNode root, List<Integer> data) {
    if (root == null) {
      return;
    }

    postorderTraversal(root.left, data);
    postorderTraversal(root.right, data);
    data.add(root.data);
  }
}

/*
Iterative solution

 */
class Solution2 {

  public List<Integer> postorder(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode currentNode = root;
    List<Integer> data = new ArrayList<>();

    // difficult to do left,right,root
    // so we do root,right,left and reverse it

    while (true) {
      if (currentNode != null) {
        data.add(currentNode.data);
        stack.push(currentNode);
        currentNode = currentNode.right;
      } else {
        if (stack.isEmpty()) {
          break;
        }

        TreeNode r = stack.pop();
        currentNode = r.left;
      }
    }
    Collections.reverse(data);
    return data;
  }
}