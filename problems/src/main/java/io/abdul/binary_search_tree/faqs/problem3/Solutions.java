package io.abdul.binary_search_tree.faqs.problem3;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static io.abdul.binary_tree.TreeNode.isValidBST;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1
    TreeNode root1 = buildTree(Arrays.asList(1, 3, null, null, 2));
    solution.recoverTree(root1);
    assertTrue(isValidBST(root1));

    // Case 2
    TreeNode root2 = buildTree(Arrays.asList(3, 1, 4, null, null, 2));
    solution.recoverTree(root2);
    assertTrue(isValidBST(root2));

    // Case 3 - already valid, but check that structure remains unchanged
    TreeNode root3 = buildTree(Arrays.asList(2, 1, 3));
    solution.recoverTree(root3);
    assertTrue(isValidBST(root3));

    // Case 4 - adjacent nodes swapped in inorder
    TreeNode root4 = buildTree(Arrays.asList(2, 1, 4, null, null, 3));
    solution.recoverTree(root4);
    assertTrue(isValidBST(root4));

    // Case 5 - complete binary tree, swap non-adjacent
    TreeNode root5 = buildTree(Arrays.asList(6, 3, 8, 1, 4, 7, 10));
    swapValues(root5.left, root5.right.right); // swap 3 and 10
    solution.recoverTree(root5);
    assertTrue(isValidBST(root5));
  }

  private static void swapValues(TreeNode a, TreeNode b) {
    int temp = a.data;
    a.data = b.data;
    b.data = temp;
  }
}

/*
Brute - inorder traversal and sorting
T - O(n logn)
S - O(n)

 */
class Solution {

  void recoverTree(TreeNode root) {
    ArrayList<Integer> data = new ArrayList<>();
    inorderTraversal(root, data);
    Collections.sort(data);
    fixTree(root, data, new int[]{0});
  }

  private void inorderTraversal(TreeNode root, List<Integer> data) {
    if (root == null) {
      return;
    }

    inorderTraversal(root.left, data);
    data.add(root.data);
    inorderTraversal(root.right, data);
  }

  private void fixTree(TreeNode root, List<Integer> data, int[] index) {
    if (root == null) {
      return;
    }

    fixTree(root.left, data, index);
    root.data = data.get(index[0]++);
    fixTree(root.right, data, index);
  }
}

/*
Optimal - find violations
T - O(n)
S - O(n) stack

 */
class Solution2 {

  void recoverTree(TreeNode root) {
    TreeNode[] first = {null};
    TreeNode[] middle = {null};
    TreeNode[] last = {null};
    inorderTraversal(root, new TreeNode[]{null}, first, middle, last);

    if (first[0] != null && last[0] != null) {
      int temp = first[0].data;
      first[0].data = last[0].data;
      last[0].data = temp;
    } else if (first[0] != null && middle[0] != null) {
      int temp = first[0].data;
      first[0].data = middle[0].data;
      middle[0].data = temp;
    }
  }

  private void inorderTraversal(TreeNode root, TreeNode[] prev, TreeNode[] first, TreeNode[] middle,
      TreeNode[] last) {
    if (root == null) {
      return;
    }

    inorderTraversal(root.left, prev, first, middle, last);
    if (prev[0] != null && root.data < prev[0].data) {
      if (first[0] == null) { // first violation
        first[0] = prev[0];
        middle[0] = root;
      } else { // second violation
        last[0] = root;
      }
    }
    prev[0] = root;
    inorderTraversal(root.right, prev, first, middle, last);
  }
}