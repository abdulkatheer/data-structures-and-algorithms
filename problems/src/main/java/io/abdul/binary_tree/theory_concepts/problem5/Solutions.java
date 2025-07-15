package io.abdul.binary_tree.theory_concepts.problem5;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solutions {

}

/*

            1     2     3
preorder - node left right
inorder -  left node right
postorder- left right node

We'll maintain a visit count for each node.
1 - consider for preorder
2 - consider for inorder
3 - consider for postorder

If a node is at 1, it's not processed before. And if we process in the first slot, that's preorder.
If a node is already processed once and now in second slot. that's inorder.
if in slot 3, it's postorder

 */
class Solution {

  List<List<Integer>> treeTraversal(TreeNode root) {
    Stack<Pair> stack = new Stack<>();
    List<Integer> preorder = new ArrayList<>();
    List<Integer> inorder = new ArrayList<>();
    List<Integer> postorder = new ArrayList<>();

    stack.push(new Pair(root));

    while (!stack.isEmpty()) {
      Pair node = stack.peek();

      if (node.visitCount == 1) {
        preorder.add(node.node.data);
        node.visitCount++;

        if (node.node.left != null) {
          stack.push(new Pair(node.node.left));
        }
      } else if (node.visitCount == 2) {
        inorder.add(node.node.data);
        node.visitCount++;

        if (node.node.right != null) {
          stack.push(new Pair(node.node.right));
        }
      } else { // 3
        postorder.add(node.node.data);
        stack.pop();
      }
    }

    return List.of(inorder, preorder, postorder);
  }
}

class Pair {

  TreeNode node;
  int visitCount = 1;

  public Pair(TreeNode node) {
    this.node = node;
  }
}