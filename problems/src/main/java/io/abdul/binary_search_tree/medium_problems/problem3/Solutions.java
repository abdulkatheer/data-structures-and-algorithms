package io.abdul.binary_search_tree.medium_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
//    Solution3 solution = new Solution3();
    Solution4 solution = new Solution4();

    assertAll("kth Smallest and Largest Element in BST",

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{3, 1, 4, null, 2});
          List<Integer> result = solution.kLargesSmall(root, 1);
          assertEquals(List.of(1, 4), result);
        },

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{3, 1, 4, null, 2});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(2, 3), result);
        },

        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, 3, 6, 2, null, null, null, 1});
          List<Integer> result = solution.kLargesSmall(root, 3);
          assertEquals(List.of(3, 3), result);
        },

        // Right-skewed
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{1, null, 2, null, 3, null, 4});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(2, 3), result);
        },

        // Single-node BST
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{42});
          List<Integer> result = solution.kLargesSmall(root, 1);
          assertEquals(List.of(42, 42), result);
        },

        // Left-skewed
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 8, null, 6, null, 4});
          List<Integer> result = solution.kLargesSmall(root, 2);
          assertEquals(List.of(6, 8), result);
        }
    );
  }
}

/*
Brute-force: Build the sorted data using in-order
T - O(n)
S - O(n)

 */
class Solution {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    ArrayList<Integer> result = new ArrayList<>();
    inorder(root, result);
    return List.of(result.get(k - 1), result.get(result.size() - k));
  }

  private void inorder(TreeNode root, List<Integer> result) {
    if (root == null) {
      return;
    }

    inorder(root.left, result);
    result.add(root.data);
    inorder(root.right, result);
  }
}

/*
Optimal - Counting with inorder and modified inorder
T - O(n)
S - O(1)

 */
class Solution2 {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    List<Integer> result = new ArrayList<>(2);
    inorder(root, 0, k, result);
    reverseInorder(root, 0, k, result);
    return result;
  }

  private int inorder(TreeNode root, int count, int k, List<Integer> result) {
    if (root == null) {
      return count;
    }
    count = inorder(root.left, count, k, result); // process left
    if (count == -1) {
      return -1;
    }

    count++; // process root

    if (count == k) {
      result.add(root.data);
      return -1;
    }

    count = inorder(root.right, count, k, result); // process right
    if (count == -1) {
      return -1;
    }

    return count;
  }

  private int reverseInorder(TreeNode root, int count, int k, List<Integer> result) {
    if (root == null) {
      return count;
    }
    count = reverseInorder(root.right, count, k, result); // process right
    if (count == -1) {
      return -1;
    }

    count++; // process root

    if (count == k) {
      result.add(root.data);
      return -1;
    }

    count = reverseInorder(root.left, count, k, result); // process left
    if (count == -1) {
      return -1;
    }

    return count;
  }
}

class Solution3 {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    ArrayList<Integer> result = new ArrayList<>();
    result.add(-1);
    result.add(-1);
    int[] pos = {0};
    inorderTraversal(root, k, pos, result);
    pos[0] = 0;
    reverseInorderTraversal(root, k, pos, result);
    return result;
  }

  private void inorderTraversal(TreeNode root, int k, int[] pos, List<Integer> result) {
    if (root == null) {
      return;
    }

    if (pos[0] == -1) { // flag to tell that it's found
      return;
    }

    inorderTraversal(root.left, k, pos, result);

    if (pos[0] == -1) { // found in the above traversal
      return;
    }
    pos[0]++;
    if (k == pos[0]) {
      result.set(0, root.data);
      pos[0] = -1;
      return;
    }

    inorderTraversal(root.right, k, pos, result);
  }

  private void reverseInorderTraversal(TreeNode root, int k, int[] pos, List<Integer> result) {
    if (root == null) {
      return;
    }

    if (pos[0] == -1) { // flag to tell that it's found
      return;
    }

    reverseInorderTraversal(root.right, k, pos, result);

    if (pos[0] == -1) { // found in the above traversal
      return;
    }
    pos[0]++;
    if (k == pos[0]) {
      result.set(1, root.data);
      pos[0] = -1;
      return;
    }

    reverseInorderTraversal(root.left, k, pos, result);
  }
}

class Solution4 {

  public List<Integer> kLargesSmall(TreeNode root, int k) {
    return List.of(firstKth(root, k), lastKth(root, k));
  }

  private int firstKth(TreeNode root, int k) {
    TreeNode current = root;
    Stack<TreeNode> stack = new Stack<>();
    int c = 0;

    while (true) {
      if (current != null) {
        stack.push(current);
        current = current.left;
      } else {
        if (stack.isEmpty()) {
          break;
        }
        TreeNode r = stack.pop();
        c++;
        if (c == k) {
          return r.data;
        }

        current = r.right;
      }
    }

    return -1;
  }

  private int lastKth(TreeNode root, int k) {
    TreeNode current = root;
    Stack<TreeNode> stack = new Stack<>();
    int c = 0;

    while (true) {
      if (current != null) {
        stack.push(current);
        current = current.right;
      } else {
        if (stack.isEmpty()) {
          break;
        }
        TreeNode r = stack.pop();
        c++;
        if (c == k) {
          return r.data;
        }

        current = r.left;
      }
    }

    return -1;
  }
}