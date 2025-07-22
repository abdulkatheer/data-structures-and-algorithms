package io.abdul.binary_tree.construction_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  static Solution solution = new Solution();

  public static void main(String[] args) {
// Example 1
    assertTreeBuildPostIn(
        new int[]{9, 15, 7, 20, 3},
        new int[]{9, 3, 15, 20, 7},
        new Integer[]{3, 9, 20, null, null, 15, 7}
    );

    // Example 2
    assertTreeBuildPostIn(
        new int[]{5, 6, 4, 9, 2, 3},
        new int[]{5, 4, 6, 3, 2, 9},
        new Integer[]{3, 4, 2, 5, 6, null, 9}
    );

    // Example 3
    assertTreeBuildPostIn(
        new int[]{6, 8, 1, 4, 7, 2, 5},
        new int[]{8, 6, 1, 5, 4, 2, 7},
        new Integer[]{5, 1, 2, 8, null, 4, 7, null, 6}
    );

    // Edge: Single Node
    assertTreeBuildPostIn(
        new int[]{1},
        new int[]{1},
        new Integer[]{1}
    );

    // Edge: Left Skewed Tree
    assertTreeBuildPostIn(
        new int[]{1, 2, 3, 4},
        new int[]{1, 2, 3, 4},
        new Integer[]{4, 3, null, 2, null, 1}
    );

    // Edge: Right Skewed Tree
    assertTreeBuildPostIn(
        new int[]{4, 3, 2, 1},
        new int[]{1, 2, 3, 4},
        new Integer[]{1, null, 2, null, 3, null, 4}
    );
  }

  private static void assertTreeBuildPostIn(int[] postorder, int[] inorder, Integer[] expectedLevelOrder) {
    TreeNode root = solution.buildTree(inorder, postorder);
    TreeNode expected = TreeNode.buildTree(expectedLevelOrder);
    assertTrue(TreeNode.areEqual(root, expected));
  }
}

class Solution {

  public TreeNode buildTree(int[] inorder, int[] postorder) {
    return build(inorder, postorder, postorder.length - 1, 0, inorder.length - 1);
  }

  private TreeNode build(int[] inorder, int[] postorder, int rootInPostorder, int startIn,
      int endIn) {
    if (startIn > endIn) {
      return null;
    }

    TreeNode root = new TreeNode(postorder[rootInPostorder]);

    int rootInInorder = -1;
    for (int i = startIn; i <= endIn; i++) {
      if (inorder[i] == postorder[rootInPostorder]) {
        rootInInorder = i;
        break;
      }
    }

    int elementsInRightSubtree = endIn - rootInInorder;
    int leftRootInPostorder = rootInPostorder - elementsInRightSubtree - 1;
    int rightRootInPostOrder = rootInPostorder - 1;
    root.left = build(inorder, postorder, leftRootInPostorder, startIn, rootInInorder - 1);
    root.right = build(inorder, postorder, rightRootInPostOrder, rootInInorder + 1, endIn);

    return root;
  }
}