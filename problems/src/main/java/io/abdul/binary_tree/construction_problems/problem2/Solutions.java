package io.abdul.binary_tree.construction_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;

public class Solutions {

  static Solution solution = new Solution();


  public static void main(String[] args) {

    // Example 1
    assertTreeBuild(
        new int[]{3, 9, 20, 15, 7},
        new int[]{9, 3, 15, 20, 7},
        new Integer[]{3, 9, 20, null, null, 15, 7}
    );

    // Example 2
    assertTreeBuild(
        new int[]{3, 4, 5, 6, 2, 9},
        new int[]{5, 4, 6, 3, 2, 9},
        new Integer[]{3, 4, 2, 5, 6, null, 9}
    );

    // Example 3
    assertTreeBuild(
        new int[]{5, 1, 8, 6, 2, 4, 7},
        new int[]{8, 6, 1, 5, 4, 2, 7},
        new Integer[]{5, 1, 2, 8, null, 4, 7, null, 6}
    );

    // Edge: Single node
    assertTreeBuild(
        new int[]{1},
        new int[]{1},
        new Integer[]{1}
    );

    // Edge: Left-skewed
    assertTreeBuild(
        new int[]{4, 3, 2, 1},
        new int[]{1, 2, 3, 4},
        new Integer[]{4, 3, null, 2, null, 1}
    );

    // Edge: Right-skewed
    assertTreeBuild(
        new int[]{1, 2, 3, 4},
        new int[]{1, 2, 3, 4},
        new Integer[]{1, null, 2, null, 3, null, 4}
    );
  }

  private static void assertTreeBuild(int[] preorder, int[] inorder, Integer[] expectedLevelOrder) {
    TreeNode root = solution.buildTree(preorder, inorder);
    TreeNode expected = TreeNode.buildTree(expectedLevelOrder);
    assertTrue(TreeNode.areEqual(root, expected));
  }
}

/*

in preorder, root is at the front
in inorder, find the root, and whoever is on left of it are part of left subtree and right are part of right subtree

1. We know the root is preorder[0] and it can be found in inorder between 0 and n-1. Find root in inorder.
2. Let's say rootPos in inorder is x, startPos to x-1 in inorder is the left subtree (but we don't know who is root)
3. x+1 to endPos is the right subtree (again we don't know who is root)
4. Start building left subtree with startPos to rootPos-1 for inorder. If left subtree exists, the next element in preorder will be the left root
5. Start building right subtree with rootPos+1 to endPost for inorder. If right subtree exists, currentRootPos + length of left subtree + 1 is the right root.
Bcz preorder stores root, left, right. currentRoot is root, and all left subtree element and then right root.
 */
class Solution {

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return build(preorder, inorder, 0, 0, inorder.length - 1);
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