package io.abdul.binary_search_tree.faqs.problem4;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.Arrays;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Case 1: Fully valid BST
    TreeNode root1 = buildTree(Arrays.asList(2, 1, 3));
    assertEquals(3, solution.largestBST(root1));

    // Case 2: Right skewed valid BST
    TreeNode root2 = buildTree(Arrays.asList(10, null, 20, null, 30, null, 40, null, 50));
    assertEquals(5, solution.largestBST(root2));

//     Case 3: Not fully BST, only subtree (2 and nulls) is valid
    TreeNode root3 = buildTree(Arrays.asList(3, 1, 4, null, null, 2));
    assertEquals(2, solution.largestBST(root3));

    // Case 4: Single node
    TreeNode root4 = buildTree(List.of(1));
    assertEquals(1, solution.largestBST(root4));

    // Case 5: No valid BSTs due to ordering
    TreeNode root5 = buildTree(Arrays.asList(5, 6, 4)); // violates BST
    assertEquals(1, solution.largestBST(root5)); // Each node is BST alone

    // Case 6: Tree with multiple BST subtrees
    TreeNode root6 = buildTree(Arrays.asList(25, 18, 50, 19, 20, 35, 60));
    assertEquals(3, solution.largestBST(root6)); // 35-50-60 is valid
  }
}

/*
Brute - check each and every node is a validBST and count along the way
T - O(n^2)
S - O(n) - stack
 */
class Solution {

  public int largestBST(TreeNode root) {
    int[] max = {0};
    inorderTraversal(root, max, new int[]{0});
    return max[0];
  }

  private void inorderTraversal(TreeNode root, int[] max, int[] count) {
    if (root == null) {
      return;
    }

    inorderTraversal(root.left, max, count);
    count[0] = 0;
    if (isValidBST(root, count)) {
      max[0] = Math.max(max[0], count[0]);
    }
    inorderTraversal(root.right, max, count);
  }

  private boolean isValidBST(TreeNode root, int[] length) {
    return isValidBST(root, length, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private boolean isValidBST(TreeNode node, int[] length, int min, int max) {
    if (node == null) {
      return true;
    }

    if (node.data <= min || node.data >= max) {
      return false;
    }
    length[0]++;
    return isValidBST(node.left, length, min, node.data) && isValidBST(node.right, length,
        node.data, max);
  }
}

/*
Optimal - isValidBST in bottom up approach
if isValidBST evaluates from the bottom up, we can find the number

From the leaf node to root
For leaf, max of left
 */
class Solution2 {

  public int largestBST(TreeNode root) {
    int[] max = {0};
    isValidBST(root, max);
    return max[0];
  }

  private Result isValidBST(TreeNode root, int[] max) {
    if (root == null) {
      return new Result(true, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
      // this is to enable validation of leaf nodes and where one child exists
    }

    Result leftResult = isValidBST(root.left, max);
    Result rightResult = isValidBST(root.right, max);
    if (!leftResult.validBst()
        || !rightResult.validBst()
        || !(root.data > leftResult.max())
        || !(root.data < rightResult.min())
    ) {
      return new Result(false, 0, 0, 0);
    }

    int count = leftResult.count() + rightResult.count() + 1;
    max[0] = Math.max(max[0], count);
    return new Result(true,
        Math.min(root.data, leftResult.min()),
        // if left doesn't exist, min is Int.MAX, so use node.data
        Math.max(root.data, rightResult.max()),
        // if right doesn't exist, max is Int.MIN, so use node.data
        count);
  }
}

record Result(boolean validBst, int min, int max, int count) {

}