package io.abdul.binary_search_tree.medium_problems.problem7;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();

    assertAll("Inorder Predecessor and Successor",

        // Test 1: Normal middle key
        () -> {
          TreeNode root = buildTree(new Integer[]{5, 2, 10, 1, 4, 7, 12});
          List<Integer> result = solution.succPredBST(root, 10);
          assertEquals(List.of(7, 12), result);
        },

        // Test 2: Key is the max node
        () -> {
          TreeNode root = buildTree(new Integer[]{5, 2, 10, 1, 4, 7, 12});
          List<Integer> result = solution.succPredBST(root, 12);
          assertEquals(List.of(10, -1), result);
        },

        // Test 3: Key is the min node
        () -> {
          TreeNode root = buildTree(new Integer[]{5, 2, 10, 1, 4, 7, 12});
          List<Integer> result = solution.succPredBST(root, 1);
          assertEquals(List.of(-1, 2), result);
        },

        // Test 4: Single node
        () -> {
          TreeNode root = new TreeNode(42);
          List<Integer> result = solution.succPredBST(root, 42);
          assertEquals(List.of(-1, -1), result);
        },

        // Test 5: Missing key (closest preds/succs)
        () -> {
          TreeNode root = buildTree(new Integer[]{5, 2, 10, 1, 4, 7, 12});
          List<Integer> result = solution.succPredBST(root, 6); // Not present
          assertEquals(List.of(5, 7), result);
        }
    );
  }
}

/*
Brute
T - O(n) - 2n
S - O(n) - 2n stack + ds

inorder traversal and find the predecessor and successor in sorted list
 */
class Solution {

  List<Integer> succPredBST(TreeNode root, int key) {
    ArrayList<Integer> data = new ArrayList<>();
    inorderTraversal(root, data);

    int predecessor = -1;
    int successor = -1;

    for (Integer datum : data) {
      if (datum < key) {
        predecessor = datum;
      } else if (datum > key) {
        successor = datum;
        break; // once successor is found, means a predecessor, key and successor all are visited
      }
    }

    return List.of(predecessor, successor);
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
Better - recursive
T - O(n)
S - O(n) - stack

Using BST property
When a node smaller than key is found, that could be a possible predecessor. We try to find a better predecessor by traversing right
When a node smaller than key is found, that could be a possible successor. We try to find a better successor by traversing left
When the exact node is found, if it has left subtree, then the predecessor will exist in its left subtree (left most element);
if it has right subtree, then the successor will exist in its right subtree (right most element)
 */
class Solution2 {

  List<Integer> succPredBST(TreeNode root, int key) {
    List<Integer> result = new ArrayList<>(2);
    result.add(-1);
    result.add(-1);
    traversal(root, key, result);
    return result;
  }

  private void traversal(TreeNode root, int key, List<Integer> result) {
    if (root == null) {
      return;
    }

    if (root.data < key) {
      result.set(0, root.data); // possible predecessor
      traversal(root.right, key, result);
    } else if (root.data > key) {
      result.set(1, root.data);
      traversal(root.left, key, result);
    } else {
      // key node found
      if (root.left != null) {
        // predecessor exists in left subtree - left subtree's right-most node
        result.set(0, findMax(root.left));
      }

      if (root.right != null) {
        // successor exists in right subtree - right subtree's left-most node
        result.set(1, findMin(root.right));
      }
    }
  }

  private int findMin(TreeNode root) {
    int min = root.data;
    while (root.left != null) {
      min = root.left.data;
      root = root.left;
    }

    return min;
  }

  private int findMax(TreeNode root) {
    int max = root.data;
    while (root.right != null) {
      max = root.right.data;
      root = root.right;
    }

    return max;
  }
}

/*
Optimal - iterative
T - O(n)
S - O(1)

 */
class Solution3 {

  List<Integer> succPredBST(TreeNode root, int key) {
    List<Integer> result = new ArrayList<>(2);
    result.add(-1);
    result.add(-1);

    TreeNode current = root;
    while (current != null) {
      if (current.data < key) {
        result.set(0, current.data);
        current = current.right;
      } else if (current.data > key) {
        result.set(1, current.data);
        current = current.left;
      } else {
        if (current.left != null) {
          result.set(0, findMax(current.left));
        }

        if (current.right != null) {
          result.set(1, findMin(current.right));
        }
        break;
      }
    }

    return result;
  }

  private int findMin(TreeNode root) {
    int min = root.data;
    while (root.left != null) {
      min = root.left.data;
      root = root.left;
    }
    return min;
  }

  private int findMax(TreeNode root) {
    int max = root.data;
    while (root.right != null) {
      max = root.right.data;
      root = root.right;
    }
    return max;
  }
}