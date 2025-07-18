package io.abdul.binary_tree.faqs.problem7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    // Case 1: [1, 2, 3, null, 5, null, 4]
    TreeNode root1 = TreeNode.buildTree(Arrays.asList(1, 2, 3, null, 5, null, 4));
    List<List<Integer>> expected1 = Arrays.asList(
        Arrays.asList(1, 2, 5),
        Arrays.asList(1, 3, 4)
    );
    assertEquals(expected1, solution.allRootToLeaf(root1));

    // Case 2: [1, 2, 3, 4, 5]
    TreeNode root2 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 4, 5));
    List<List<Integer>> expected2 = Arrays.asList(
        Arrays.asList(1, 2, 4),
        Arrays.asList(1, 2, 5),
        Arrays.asList(1, 3)
    );
    assertEquals(expected2, solution.allRootToLeaf(root2));

    // Case 3: [1, 2, 3, 4, null, 5, 6, null, 7]
    TreeNode root3 = TreeNode.buildTree(Arrays.asList(1, 2, 3, 4, null, 5, 6, null, 7));
    List<List<Integer>> expected3 = Arrays.asList(
        Arrays.asList(1, 2, 4, 7),
        Arrays.asList(1, 3, 5),
        Arrays.asList(1, 3, 6)
    );
    assertEquals(expected3, solution.allRootToLeaf(root3));
  }
}

class Solution {

  public List<List<Integer>> allRootToLeaf(TreeNode root) {
    ArrayList<List<Integer>> result = new ArrayList<>();
    rootToLeaf(root, new ArrayList<>(), result);
    return result;
  }

  private void rootToLeaf(TreeNode root, List<Integer> temp, List<List<Integer>> result) {
    if (root == null) {
      return;
    }

    if (isLeaf(root)) {
      temp.add(root.data);
      result.add(new ArrayList<>(temp));
      temp.remove(temp.size() - 1);
      return;
    }

    temp.add(root.data);

    rootToLeaf(root.left, temp, result);
    rootToLeaf(root.right, temp, result);

    temp.remove(temp.size() - 1);
  }

  private static boolean isLeaf(TreeNode root) {
    return root.left == null && root.right == null;
  }
}