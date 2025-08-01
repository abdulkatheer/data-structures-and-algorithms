package io.abdul.binary_search_tree.faqs.problem2;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
//    Solution2 solution = new Solution2();
    Solution3 solution = new Solution3();


    // Case 1: Multiple valid pairs
    TreeNode root1 = buildTree(Arrays.asList(5, 3, 6, 2, 4, null, 7));
    assertTrue(solution.twoSumBST(root1, 9));  // 2+7, 3+6, 5+4

    // Case 2: No valid pair
    TreeNode root2 = buildTree(Arrays.asList(5, 3, 6, 2, 4, null, 7));
    assertFalse(solution.twoSumBST(root2, 14));  // no such pair

    // Case 3: Exactly one valid pair
    TreeNode root3 = buildTree(Arrays.asList(5, 3, 6, 2, 4, null, 7));
    assertTrue(solution.twoSumBST(root3, 12));  // 5+7

    // Case 4: Only two nodes
    TreeNode root4 = buildTree(Arrays.asList(1, null, 3));
    assertTrue(solution.twoSumBST(root4, 4));  // 1+3

    // Case 5: Duplicates don't count as pair unless distinct nodes
    TreeNode root5 = buildTree(Arrays.asList(1));
    assertFalse(solution.twoSumBST(root5, 2));  // only one node

    // Case 6: Negative values
    TreeNode root6 = buildTree(Arrays.asList(0, -2, 3, null, -1));
    assertTrue(solution.twoSumBST(root6, -3));  // -2 + -1

    // Case 7: Large value no pair
    TreeNode root7 = buildTree(Arrays.asList(5, 3, 6, 2, 4, null, 7));
    assertFalse(solution.twoSumBST(root7, 100));  // no such pair
  }
}

/*
Better
T - O(n)
S - O(n) - 2n; n - HashMap, n - Stack
 */
class Solution {

  public boolean twoSumBST(TreeNode root, int k) {
    HashSet<Integer> pastNums = new HashSet<>();

    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;
    while (true) {
      if (current != null) {
        stack.push(current);
        current = current.left;
      } else {
        if (stack.isEmpty()) {
          break;
        }
        TreeNode r = stack.pop();
        if (pastNums.contains(k - r.data)) {
          return true;
        }
        pastNums.add(r.data);
        current = r.right;
      }
    }

    return false;
  }
}

/*
Better - Two pointer
T - O(n) - 2n; n - build sorted data; n - find target
S - O(n) - 2n; n - sorted data; n - stack

 */
class Solution2 {

  public boolean twoSumBST(TreeNode root, int k) {
    List<Integer> data = new ArrayList<>();

    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;

    while (true) {
      if (current != null) {
        stack.push(current);
        current = current.left;
      } else {
        if (stack.isEmpty()) {
          break;
        }
        TreeNode r = stack.pop();
        data.add(r.data);
        current = r.right;
      }
    }

    int left = 0, right = data.size() - 1;
    while (left < right) {
      int sum = data.get(left) + data.get(right);

      if (sum == k) {
        return true;
      }

      if (sum < k) {
        left++;
      } else {
        right--;
      }
    }

    return false;
  }
}

/*
Optimal - Iterator
T - O(n) - n - both iterators at most traverse n elements combined
S - O(n) - Stack of both iterators hold at most n elements combined
 */
class Solution3 {

  public boolean twoSumBST(TreeNode root, int k) {
    BSTIterator leftItr = new BSTIterator(root, false);
    BSTIterator rightItr = new BSTIterator(root, true);

    TreeNode left = leftItr.next();
    TreeNode right = rightItr.next();
    while (left != right) { // given that at least one element exists in the BST
      int sum = left.data + right.data;

      if (sum == k) {
        return true;
      } else if (sum < k) {
        left = leftItr.next();
      } else {
        right = rightItr.next();
      }
    }

    return false;
  }
}

class BSTIterator {

  private final Stack<TreeNode> stack = new Stack<>();
  private final boolean reverse;

  public BSTIterator(TreeNode root, boolean reverse) {
    this.reverse = reverse;
    populate(root);
  }

  public boolean hasNext() {
    return stack.isEmpty();
  }

  public TreeNode next() {
    TreeNode r = stack.pop();
    if (reverse) {
      populate(r.left);
    } else {
      populate(r.right);
    }
    return r;
  }

  private void populate(TreeNode root) {
    TreeNode current = root;
    if (reverse) {
      while (current != null) {
        stack.push(current);
        current = current.right;
      }
    } else {
      while (current != null) {
        stack.push(current);
        current = current.left;
      }
    }
  }
}