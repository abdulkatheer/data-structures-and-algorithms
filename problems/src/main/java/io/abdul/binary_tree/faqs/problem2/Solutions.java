package io.abdul.binary_tree.faqs.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
    // Case 1: root = [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9]
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 4, 5, 6, 7, null, null, 8, 9));
    List<Integer> expected1 = Arrays.asList(1, 2, 4, 8, 9, 6, 7, 3);
    assertEquals(expected1, solution.boundary(root1));

    // Case 2: root = [1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8]
    TreeNode root2 = TreeNode.buildTree(
        Arrays.asList(1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8));
    List<Integer> expected2 = Arrays.asList(1, 2, 4, 6, 5, 7, 8);
    assertEquals(expected2, solution.boundary(root2));

    // Case 3: root = [5, 1, 2, 8, null, 4, 5, null, 6]
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(5, 1, 2, 8, null, 4, 5, null, 6));
    List<Integer> expected3 = Arrays.asList(5, 1, 8, 6, 4, 5, 2);
    assertEquals(expected3, solution.boundary(root3));

    // Case 4: Skewed left: [1, 2, null, 3, null, 4]
    TreeNode root4 = TreeNode.buildTree(Arrays.asList(1, 2, null, 3, null, 4));
    List<Integer> expected4 = Arrays.asList(1, 2, 3, 4);
    assertEquals(expected4, solution.boundary(root4));

    // Case 5: Skewed right: [1, null, 2, null, 3, null, 4]
    TreeNode root5 = TreeNode.buildTree(Arrays.asList(1, null, 2, null, 3, null, 4));
    List<Integer> expected5 = Arrays.asList(1, 4, 3, 2);
    assertEquals(expected5, solution.boundary(root5));

    // Case 6: Single node
    TreeNode root6 = TreeNode.buildTree(Arrays.asList(42));
    List<Integer> expected6 = Arrays.asList(42);
    assertEquals(expected6, solution.boundary(root6));

    // Case 7: Only root and one leaf on left
    TreeNode root7 = TreeNode.buildTree(Arrays.asList(10, 20));
    List<Integer> expected7 = Arrays.asList(10, 20);
    assertEquals(expected7, solution.boundary(root7));

    // Case 8: Only root and one leaf on right
    TreeNode root8 = TreeNode.buildTree(Arrays.asList(10, null, 30));
    List<Integer> expected8 = Arrays.asList(10, 30);
    assertEquals(expected8, solution.boundary(root8));

    TreeNode root9 = TreeNode.buildTree(
        new Integer[]{1, null, 15, 69, null, null, 97, null, 96, 45, null, null, 70, null, 61, null,
            67, null, 55, null, null});
    List<Integer> expected9 = Arrays.asList(1, 55, 67, 61, 70, 45, 96, 97, 69, 15);
    assertEquals(expected9, solution.boundary(root9));
  }
}

/*
Optimal
T - O(n)
S - O(n)

Left boundary:
from root, prefer left. if null take right. How long? Until the node is a lead node (both left,right of it are null)
Add before recursive call, to get in order

Right boundary:
from root, prefer right. if null take left. How long? Until the node is a lead node (both left,right of it are null)
Add data while returning, so that we get reverse of it.

Bottom boundary:
preorder / any other traversal, but add only the lead nodes
 */
class Solution {

  public List<Integer> boundary(TreeNode root) {
    List<Integer> result = new ArrayList<>();

    if (isLeaf(root)) {
      result.add(root.data);
      return result;
    }
    result.add(root.data);
    leftBoundary(root.left, result);
    bottomBoundary(root, result);
    rightBoundary(root.right, result);
    return result;
  }

  private void leftBoundary(TreeNode root, List<Integer> leftBoundary) {
    if (root == null || isLeaf(root)) { // if null or a leaf
      return;
    }

    leftBoundary.add(root.data);
    if (root.left != null) {
      leftBoundary(root.left, leftBoundary);
    } else {
      leftBoundary(root.right, leftBoundary);
    }
  }

  private void rightBoundary(TreeNode root, List<Integer> rightBoundary) {
    if (root == null || isLeaf(root)) { // if null or a leaf
      return;
    }

    if (root.right != null) {
      rightBoundary(root.right, rightBoundary);
    } else {
      rightBoundary(root.left, rightBoundary);
    }
    rightBoundary.add(root.data);
  }

  private void bottomBoundary(TreeNode root, List<Integer> bottomBoundary) {
    if (root == null) {
      return;
    }

    if (isLeaf(root)) { // access root, if leaf process
      bottomBoundary.add(root.data);
      return;
    }

    bottomBoundary(root.left, bottomBoundary);
    bottomBoundary(root.right, bottomBoundary);
  }

  private static boolean isLeaf(TreeNode root) {
    return root.left == null && root.right == null;
  }
}

class Solution2 {

  public List<Integer> boundary(TreeNode root) {
    List<Integer> result = new ArrayList<>();

    if (isLeaf(root)) {
      result.add(root.data);
      return result;
    }

    result.add(root.data);
    leftBoundary(root.left, result);
    bottomBoundary(root, result);
    rightBoundary(root.right, result);
    return result;
  }

  private void leftBoundary(TreeNode root, List<Integer> leftBoundary) {
    while (root != null && !isLeaf(root)) {
      leftBoundary.add(root.data);

      if (root.left != null) {
        root = root.left;
      } else {
        root = root.right;
      }
    }
  }

  private void rightBoundary(TreeNode root, List<Integer> rightBoundary) {
    List<Integer> temp = new ArrayList<>();// bcz we need to reverse it
    while (root != null && !isLeaf(root)) {
      temp.add(root.data);

      if (root.right != null) {
        root = root.right;
      } else {
        root = root.left;
      }
    }

    for (int i = temp.size() - 1; i >= 0; i--) {
      rightBoundary.add(temp.get(i));
    }
  }

  // preorder traversal
  private void bottomBoundary(TreeNode root, List<Integer> bottomBoundary) {
    Stack<TreeNode> stack = new Stack<>();

    TreeNode c = root;

    while (true) {
      if (c != null) {
        if (isLeaf(c)) {
          bottomBoundary.add(c.data);
        }
        stack.push(c);
        c = c.left;
      } else {
        if (stack.isEmpty()) {
          break;
        }
        TreeNode r = stack.pop();
        c = r.right;
      }
    }
  }

  private static boolean isLeaf(TreeNode root) {
    return root.left == null && root.right == null;
  }
}