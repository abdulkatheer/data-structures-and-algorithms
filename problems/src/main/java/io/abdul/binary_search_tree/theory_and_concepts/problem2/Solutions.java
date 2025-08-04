package io.abdul.binary_search_tree.theory_and_concepts.problem2;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
//    Solution3 solution = new Solution3();
    Solution4 solution = new Solution4();

    assertAll("Floor and Ceil in BST",
//
//        // Case 1: key = 11 => floor = 10, ceil = 12
//        () -> {
//          TreeNode root = TreeNode.buildTree(new Integer[]{8, 4, 12, 2, 6, 10, 14});
//          List<Integer> result = solution.floorCeilOfBST(root, 11);
//          assertEquals(List.of(10, 12), result, "Key = 11");
//        },
//
//        // Case 2: key = 15 => floor = 14, ceil = -1
//        () -> {
//          TreeNode root = TreeNode.buildTree(new Integer[]{8, 4, 12, 2, 6, 10, 14});
//          List<Integer> result = solution.floorCeilOfBST(root, 15);
//          assertEquals(List.of(14, -1), result, "Key = 15");
//        },
//
//        // Case 3: key = 1 => floor = -1, ceil = 2
//        () -> {
//          TreeNode root = TreeNode.buildTree(new Integer[]{8, 4, 12, 2, 6, 10, 14});
//          List<Integer> result = solution.floorCeilOfBST(root, 1);
//          assertEquals(List.of(-1, 2), result, "Key = 1");
//        },

        // Case 4: key = 6 => exact match
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{8, 4, 12, 2, 6, 10, 14});
          List<Integer> result = solution.floorCeilOfBST(root, 6);
          assertEquals(List.of(6, 6), result, "Key = 6");
        },

        // Case 5: skewed left
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{10, 5, null, 2});
          List<Integer> result = solution.floorCeilOfBST(root, 1);
          assertEquals(List.of(-1, 2), result, "Key = 1 (left skew)");
        },

        // Case 6: skewed right
        () -> {
          TreeNode root = TreeNode.buildTree(new Integer[]{5, null, 10, null, 15});
          List<Integer> result = solution.floorCeilOfBST(root, 20);
          assertEquals(List.of(15, -1), result, "Key = 20 (right skew)");
        },

        // Case 7: root-only
        () -> {
          TreeNode root = new TreeNode(7);
          List<Integer> result = solution.floorCeilOfBST(root, 7);
          assertEquals(List.of(7, 7), result, "Root-only match");
        },

        // Case 8: Empty tree
        () -> {
          List<Integer> result = solution.floorCeilOfBST(null, 5);
          assertEquals(List.of(-1, -1), result, "Empty tree");
        }
    );
  }
}

class Solution {

  public List<Integer> floorCeilOfBST(TreeNode root, int key) {
    return List.of(floor(root, key), ceil(root, key));
  }

  private int floor(TreeNode root, int key) {
    TreeNode current = root;

    int floor = -1;
    while (current != null) {
      if (current.data == key) {
        floor = key; // exact floor
        break;
      } else if (key > current.data) {
        floor = current.data; // possible floor
        current = current.right; // look for better
      } else {
        current = current.left;
      }
    }

    return floor;
  }

  private int ceil(TreeNode root, int key) {
    TreeNode current = root;

    int ceil = -1;
    while (current != null) {
      if (key == current.data) {
        ceil = key;
        break;
      } else if (key < current.data) {
        ceil = current.data;
        current = current.left;
      } else {
        current = current.right;
      }
    }

    return ceil;
  }
}

class Solution2 {

  public List<Integer> floorCeilOfBST(TreeNode root, int key) {
    return List.of(floor(root, key), ceil(root, key));
  }

  private int floor(TreeNode root, int key) {
    if (root == null) {
      return -1;
    }

    if (key == root.data) {
      return root.data;
    } else if (key > root.data) {
      int floor = floor(root.right, key);
      return floor == -1 ? root.data : floor;
    } else {
      return floor(root.left, key);
    }
  }

  private int ceil(TreeNode root, int key) {
    if (root == null) {
      return -1;
    }

    if (key == root.data) {
      return key;
    } else if (key < root.data) {
      int ceil = ceil(root.left, key);
      return ceil == -1 ? root.data : ceil;
    } else {
      return ceil(root.right, key);
    }
  }
}

class Solution3 {

  public List<Integer> floorCeilOfBST(TreeNode root, int key) {
    ArrayList<Integer> result = new ArrayList<>(2);
    result.add(-1);
    result.add(-1);
    floorAndCeil(root, key, result);
    return result;
  }

  private void floorAndCeil(TreeNode root, int key, List<Integer> result) {
    if (root == null) {
      return;
    }

    if (key == root.data) {
      result.set(0, key);
      result.set(1, key);
    } else if (key < root.data) {
      result.set(1, root.data); // possible ceil
      floorAndCeil(root.left, key, result); // floor and better ceil on the left
    } else {
      result.set(0, root.data); // possible ceil
      floorAndCeil(root.right, key, result); // better floor and ceil on the right
    }
  }
}

class Solution4 {

  public List<Integer> floorCeilOfBST(TreeNode root, int key) {
    ArrayList<Integer> result = new ArrayList<>(2);
    result.add(-1);
    result.add(-1);

    TreeNode current = root;

    while (current != null) {
      if (key == current.data) {
        result.set(0, key);
        result.set(1, key);
        break;
      } else if (key < current.data) {
        result.set(1, current.data); // possible ceil
        current = current.left; // floor and better ceil on the left
      } else {
        result.set(0, current.data); // possible ceil
        current = current.right; // better floor and ceil on the right
      }
    }

    return result;
  }
}