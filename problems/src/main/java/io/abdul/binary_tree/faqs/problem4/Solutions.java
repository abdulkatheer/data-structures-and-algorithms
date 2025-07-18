package io.abdul.binary_tree.faqs.problem4;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Test 1: Balanced Tree
    assertTopViewEquals(
        new Integer[]{1, 2, 3, 4, 5, 6, 7},
        Arrays.asList(4, 2, 1, 3, 7),
        sol
    );

    // Test 2: Another complete tree
    assertTopViewEquals(
        new Integer[]{10, 20, 30, 40, 60, 90, 100},
        Arrays.asList(40, 20, 10, 30, 100),
        sol
    );

    // Test 3: Tree with overlapping columns
    assertTopViewEquals(
        new Integer[]{5, 1, 2, 8, null, 4, 5, null, 6},
        Arrays.asList(8, 1, 5, 2, 5),
        sol
    );

    // Additional Test Case 1: Skipped nulls (dynamic gaps)
    assertTopViewEquals(new Integer[]{1, 2, null, 3, null, 4, null}, List.of(4, 3, 2, 1), sol);

    // Additional Test Case 2: Complex tree with mixed gaps
    assertTopViewEquals(new Integer[]{1, 48, null, null, 64, -15, 93, null, null, -45, null, 33},
        List.of(48, 1, 93), sol);

    // Additional Test Case 3: Tree with negative values and dynamic skips
    assertTopViewEquals(new Integer[]{42, -10, 30, null, -20, 25, null}, List.of(-10, 42, 30), sol);

  }

  private static void assertTopViewEquals(Integer[] input, List<Integer> expected,
      Solution2 solution) {
    TreeNode root = buildTree(input);
    List<Integer> actual = solution.topView(root);
    assertEquals(expected, actual, "Failed on input: " + Arrays.toString(input));
  }
}

// Doesn't work
class Solution {

  public List<Integer> topView(TreeNode root) {
    List<Integer> result = new ArrayList<>();

    leftBoundary(root.left, result);
    result.add(root.data);
    rightBoundary(root.right, result);

    return result;
  }

  private void leftBoundary(TreeNode root, List<Integer> boundary) {
    if (root == null) {
      return;
    }

    leftBoundary(root.left, boundary);
    boundary.add(root.data);
  }

  private void rightBoundary(TreeNode root, List<Integer> boundary) {
    if (root == null) {
      return;
    }

    boundary.add(root.data);
    rightBoundary(root.right, boundary);
  }
}

/*
Optimal
T - O(n)
S - O(n)

Level order traversal
 */
class Solution2 {

  public List<Integer> topView(TreeNode root) {
    Queue<TreeNodeWrapper> q = new LinkedList<>();
    q.add(new TreeNodeWrapper(0, root));

    Map<Integer, Integer> result = new TreeMap<>(); // ordered by level

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        TreeNodeWrapper n = q.poll();

        if (!result.containsKey(n.level)) {
          result.put(n.level, n.node.data);
        }

        if (n.node.left != null) {
          q.offer(new TreeNodeWrapper(n.level - 1, n.node.left));
        }

        if (n.node.right != null) {
          q.offer(new TreeNodeWrapper(n.level + 1, n.node.right));
        }
      }
    }

    return result.values().stream().toList();
  }
}

class TreeNodeWrapper {

  final int level;
  final TreeNode node;

  public TreeNodeWrapper(int level, TreeNode node) {
    this.level = level;
    this.node = node;
  }
}