package io.abdul.binary_tree.faqs.problem8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.Arrays;
import java.util.Map;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Helper map to find nodes by value
    Map<Integer, TreeNode> map;

    // Case 1: root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4], p = 5, q = 1 => LCA = 3
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4));
    map = TreeNode.mapTreeByValue(root1);
    assertEquals(3, solution.lowestCommonAncestor(root1, map.get(5), map.get(1)).data);

    // Case 2: root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4], p = 5, q = 4 => LCA = 5
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4));
    map = TreeNode.mapTreeByValue(root2);
    assertEquals(5, solution.lowestCommonAncestor(root2, map.get(5), map.get(4)).data);

    // Case 3: root = [7, 1, 2, 8, 10, 4, 5, null, 6], p = 6, q = 10 => LCA = 1
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(7, 1, 2, 8, 10, 4, 5, null, 6));
    map = TreeNode.mapTreeByValue(root3);
    assertEquals(1, solution.lowestCommonAncestor(root3, map.get(6), map.get(10)).data);

  }
}

/*
Brute-force
T - O(n) - 3n; n to find path to p; n for q; n to find the LCA
S - O(n) - 3n; n for stack; n for path to p; n for q

- Tree is acyclic and connected structure
- So there will be only one path exists from any of the ancestors to a node
- At least one path exists to p and q
- At least one LCA exists (root)

Given that, we can find the path from to p and q

Let's say they're
p -> a b c x z
q -> a b c k

Start from front and stop at x,k. c is the last common path and our answer
 */

/*
Optimal

We try to eliminate the additional space required.
At any node, if we can find the path to both p and q, that's our answer.
As it's tail recursion, the first node from bottom up which has connection to both is the LCA.
 */

class Solution {

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    return lca(root, p, q);
  }

  private TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
      return null;
    }

    /*
    If ancestor and p/q is the LCA, how does below work
    Let's say q passes through p. So p is the LCA for both p and q
    root == p matches, so we don't try to look for q and return root
    We don't find q anywhere else up above. So root (p) will be propagated back as answer.

    - p and q definitely exist
    - if we find p/q, and they're LCA, we don't try to find the other element.
     */
    if (root == p || root == q) {
      return root;
    }

    // as all elements are unique, we don't have to worry about duplication
    TreeNode found1 = lca(root.left, p, q);
    TreeNode found2 = lca(root.right, p, q);

    if (found1 != null && found2 != null) {
      return root; // path exists from root to both p and p, just propagate this answer back
    }

    // one of them or both are null, returning non-null, so that we can find the other somewhere else
    return found1 != null ? found1 : found2;
  }
}