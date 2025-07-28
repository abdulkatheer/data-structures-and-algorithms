package io.abdul.binary_search_tree.medium_problems.problem6;

import static io.abdul.binary_tree.TreeNode.isValidBST;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();
//    Solution3 solution = new Solution3();
//    Solution4 solution = new Solution4();

    assertAll("Construct BST from Preorder",

        // Test 1: Standard BST structure
        () -> {
          int[] preorder = {8, 5, 1, 7, 10, 12};
          TreeNode root = solution.bstFromPreorder(preorder);
          assertTrue(isValidBST(root));
          assertEquals(Arrays.toString(preorder), Arrays.toString(preorderTraversal(root)));
        },

        // Test 2: Right-skewed tree
        () -> {
          int[] preorder = {1, 3};
          TreeNode root = solution.bstFromPreorder(preorder);
          assertTrue(isValidBST(root));
          assertEquals(Arrays.toString(preorder), Arrays.toString(preorderTraversal(root)));
        },

        // Test 3: Full BST with multiple levels
        () -> {
          int[] preorder = {5, 3, 2, 4, 6, 7};
          TreeNode root = solution.bstFromPreorder(preorder);
          assertTrue(isValidBST(root));
          assertEquals(Arrays.toString(preorder), Arrays.toString(preorderTraversal(root)));
        },

        // Test 4: Single node
        () -> {
          int[] preorder = {42};
          TreeNode root = solution.bstFromPreorder(preorder);
          assertTrue(isValidBST(root));
          assertEquals(Arrays.toString(preorder), Arrays.toString(preorderTraversal(root)));
        },

        // Test 5: Right-heavy long chain
        () -> {
          int[] preorder = {10, 20, 30, 40};
          TreeNode root = solution.bstFromPreorder(preorder);
          assertTrue(isValidBST(root));
          assertEquals(Arrays.toString(preorder), Arrays.toString(preorderTraversal(root)));
        }
    );
  }

  private static int[] preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result.stream().mapToInt(i -> i).toArray();
  }

  private static void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) {
      return;
    }
    result.add(node.data);
    preorderHelper(node.left, result);
    preorderHelper(node.right, result);
  }
}

/*
Brute-force
T - O(n^2)
S - O(n)

 */
class Solution {

  public TreeNode bstFromPreorder(int[] preorder) {
    Stack<TreeNode> stack = new Stack<>();

    TreeNode root = new TreeNode(preorder[0]);
    stack.push(root);
    for (int i = 1; i < preorder.length; i++) {
      TreeNode current = stack.peek();

      // pop all smaller than the new node
      // the last removed element is smaller than the current, and the top of the stack will be bigger than the current
      while (!stack.isEmpty() && stack.peek().data < preorder[i]) {
        current = stack.pop();
      }

      TreeNode child = new TreeNode(preorder[i]);
      if (preorder[i] < current.data) {
        // if above while loop not executed, preorder[i] must be greater than current.data
        current.left = child;
      } else {
        // if above while loop executed at least one, preorder[i] must be smaller than current.data
        current.right = child;
      }
      stack.push(child);
    }

    return root;
  }
}

/*
Better - preorder and inorder
T - O(n logn)
S - O(n)

inorder traversal of a BST is nothing but the sorted data
So if we sort the preorder, we get inorder
And we can treat this as 'Build Unique Binary Tree from preorder and inorder traversal' problem
 */
class Solution2 {

  public TreeNode bstFromPreorder(int[] preorder) {
    int n = preorder.length;
    int[] inorder = new int[n];
    System.arraycopy(preorder, 0, inorder, 0, inorder.length);
    Arrays.sort(inorder);

    return build(preorder, inorder, 0, 0, n - 1);
  }

  private TreeNode build(int[] preorder, int[] inorder, int preOrderRoot, int sI, int eI) {
    if (sI > eI) { // no more elements
      return null;
    }

    int rootPos = -1;
    for (int i = sI; i <= eI; i++) {
      if (preorder[preOrderRoot] == inorder[i]) { // found root in inorder
        rootPos = i;
        break;
      }
    }

    TreeNode root = new TreeNode(inorder[rootPos]);

    int leftElementsCount = rootPos - sI;
    int leftRootInPreorder = preOrderRoot + 1;
    int rightRootInPreorder = preOrderRoot + leftElementsCount + 1;
    root.left = build(preorder, inorder, leftRootInPreorder, sI, rootPos - 1);
    root.right = build(preorder, inorder, rightRootInPreorder, rootPos + 1, eI);

    return root;
  }
}

/*
Better - explicit upperBound search
T - O(n logn)
S - O(h) - stack
 */
class Solution3 {

  public TreeNode bstFromPreorder(int[] preorder) {
    return bst(preorder, 0, preorder.length - 1);
  }

  private TreeNode bst(int[] preorder, int start, int end) {
    if (start > end) {
      return null;
    }

    TreeNode root = new TreeNode(preorder[start]);
    int upperBound = upperBound(preorder, start + 1, root.data);
    root.left = bst(preorder, start + 1, upperBound - 1);
    root.right = bst(preorder, upperBound, end);

    return root;
  }

  private int upperBound(int[] preorder, int start, int key) {
    int low = start, high = preorder.length - 1;
    int upperBound = preorder.length;

    while (low <= high) {
      int mid = (low + high) / 2;
      if (preorder[mid] > key) {
        upperBound = mid;
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }

    return upperBound;
  }
}

/*
Optimal - implicit upperBound
T - O(n)
S - O(h) - stack

We don't have to calculate the bound every time
root | left subtree | right subtree
Initially
root.data is the upper bound for left subtree
Int.MAX is the upper bound for right subtree
 */
class Solution4 {

  public TreeNode bstFromPreorder(int[] preorder) {
    return bst(preorder, Integer.MAX_VALUE, new int[]{0});
  }

  private TreeNode bst(int[] preorder, int upperBound, int[] index) {
    if (index[0] == preorder.length || preorder[index[0]] > upperBound) {
      return null;
    }

    TreeNode root = new TreeNode(preorder[index[0]]);
    index[0]++;

    // for all elements left to root, root.data is the upperBound
    root.left = bst(preorder, root.data, index);
    root.right = bst(preorder, upperBound, index);

    return root;
  }
}