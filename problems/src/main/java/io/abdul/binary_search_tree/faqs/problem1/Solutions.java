package io.abdul.binary_search_tree.faqs.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_search_tree.faqs.problem1.Solution.BSTIterator;
//import io.abdul.binary_search_tree.faqs.problem1.Solution2.BSTIterator;
import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    // Case 1: Full traversal
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(7, 3, 15, null, null, 9, 20));
    BSTIterator it1 = new BSTIterator(root1);
    List<Integer> result1 = new ArrayList<>();
    while (it1.hasNext()) {
      result1.add(it1.next());
    }
    assertEquals(Arrays.asList(3, 7, 9, 15, 20), result1);

    // Case 2: Partial traversal with hasNext() in between
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(7, 3, 15, null, null, 9, 20));
    BSTIterator it2 = new BSTIterator(root2);
    assertEquals(3, it2.next());
    assertEquals(7, it2.next());
    assertEquals(9, it2.next());
    assertTrue(it2.hasNext());
    assertEquals(15, it2.next());
    assertTrue(it2.hasNext());

    // Case 3: Stop just before empty
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(7, 3, 15, null, null, 9, 20));
    BSTIterator it3 = new BSTIterator(root3);
    assertEquals(3, it3.next());
    assertEquals(7, it3.next());
    assertEquals(9, it3.next());
    assertEquals(15, it3.next());
    assertEquals(20, it3.next());
    assertFalse(it3.hasNext());

    // Case 4: Single node
    TreeNode root4 = new TreeNode(10);
    BSTIterator it4 = new BSTIterator(root4);
    assertTrue(it4.hasNext());
    assertEquals(10, it4.next());
    assertFalse(it4.hasNext());

    // Case 5: Left-skewed tree
    TreeNode root5 = TreeNode.buildTree(Arrays.asList(5, 3, null, 2, null, 1));
    BSTIterator it5 = new BSTIterator(root5);
    List<Integer> result5 = new ArrayList<>();
    while (it5.hasNext()) {
      result5.add(it5.next());
    }
    assertEquals(Arrays.asList(1, 2, 3, 5), result5);

    // Case 6: Empty tree
    TreeNode root6 = null;
    BSTIterator it6 = new BSTIterator(root6);
    assertFalse(it6.hasNext());
  }
}

/*
Brute
T - O(n) - 2n
S - O(n)

Convert Tree to List using inorder traversal
 */
class Solution {

  static class BSTIterator {

    private final List<Integer> data = new ArrayList<>();
    private int i = 0;

    public BSTIterator(TreeNode root) {
      inorderTraversal(root, data);
    }

    public boolean hasNext() {
      return i < data.size();
    }

    public int next() {
      return data.get(i++);
    }

    private static void inorderTraversal(TreeNode root, List<Integer> data) {
      Stack<TreeNode> stack = new Stack<>();

      TreeNode current = root;
      while (true) {
        if (current != null) {
          stack.push(current);
          current = current.left;
        } else {
          if (stack.isEmpty()) {
            return;
          }
          TreeNode r = stack.pop();
          data.add(r.data);
          current = r.right;
        }
      }
    }
  }
}

/*
Optimal
T - O(n)
S - O(n)

No additional space. Modified iterative inorder traversal.
 */
class Solution2 {

  static class BSTIterator {

    private final Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
      populate(root); // this is nothing but the if condition in the while loop
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public int next() {
      TreeNode current = stack.pop(); // this is nothing but processing the root
      populate(
          current.right); // this is the else block where we switch to right when left and root is processed
      return current.data;
    }

    private void populate(TreeNode root) {
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
    }
  }
}