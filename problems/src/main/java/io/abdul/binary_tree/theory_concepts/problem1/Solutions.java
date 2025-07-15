package io.abdul.binary_tree.theory_concepts.problem1;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solutions {

}

class Solution {

  public List<Integer> inorder(TreeNode root) {
    ArrayList<Integer> data = new ArrayList<>();
    inorderTraversal(root, data);
    return data;
  }

  private void inorderTraversal(TreeNode root, List<Integer> data) {
    if (root == null) {
      return;
    }

    inorderTraversal(root.left, data);
    data.add(root.data);
    inorderTraversal(root.right, data);
  }
}

/*
Iterative solution

It's a normal recursion, has two recursive calls. So we need to mimic exact stack behavior in iteration.
 */
class Solution2 {

  public List<Integer> inorder(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode currentNode = root;
    List<Integer> data = new ArrayList<>();

    while (true) {
      if (currentNode != null) {
        // keep parent in the stack, so that after processing left, we process the root
        stack.push(currentNode);
        currentNode = currentNode.left;
      } else { // Processed leaf in the last iteration, base case
        if (stack.isEmpty()) { // all roots are processed
          break; // stop here
        }

        TreeNode r = stack.pop();
        data.add(r.data);
        currentNode = r.right;
      }
    }

    return data;
  }
}