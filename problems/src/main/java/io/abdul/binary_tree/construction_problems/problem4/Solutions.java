package io.abdul.binary_tree.construction_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Solutions {

  static Solution solution = new Solution();

  public static void main(String[] args) {
    List<TestCase> testCases = List.of(
        new TestCase("Balanced", new Integer[]{2, 1, 3}),
        new TestCase("Unbalanced", new Integer[]{7, 3, 15, null, null, 9, 20}),
        new TestCase("Full", new Integer[]{10, 20, 30, 40, 50, 60}),
        new TestCase("SingleNode", new Integer[]{1}),
        new TestCase("LeftChain", new Integer[]{1, 2, null, 3, null, 4}),
        new TestCase("RightChain", new Integer[]{1, null, 2, null, 3, null, 4}),
        new TestCase("Empty", new Integer[]{}),
        new TestCase("DeepLeft", new Integer[]{5, 4, null, 3, null, 2, null, 1}),
        new TestCase("MixedNulls", new Integer[]{1, 2, 3, null, 4, null, 5}),
        new TestCase("ThreeLevelFull", new Integer[]{1, 2, 3, 4, 5, 6, 7})
    );

    for (TestCase tc : testCases) {
      TreeNode root = TreeNode.buildTree(tc.input);
      String serialized = solution.serialize(root);
      TreeNode deserialized = solution.deserialize(serialized);
      assertTrue(TreeNode.areEqual(root, deserialized), "Failed for: " + tc.name);
    }
  }

  static class TestCase {

    String name;
    Integer[] input;

    TestCase(String name, Integer[] input) {
      this.name = name;
      this.input = input;
    }
  }
}

class Solution {

  public String serialize(TreeNode root) {
    if (root == null) {
      return "";
    }

    List<String> data = new ArrayList<>();

    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);

    while (!q.isEmpty()) {
      int size = q.size();
      boolean hasNodes = false;

      for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        if (node != null) {
          data.add(String.valueOf(node.data));
          // even if left/right is null, we need to add them to maintain structure
          q.add(node.left);
          q.add(node.right);

          if (node.left != null
              || node.right != null) { // at least one node exist for next level, so we can traverse
            hasNodes = true;
          }
        } else {
          data.add(null);
        }
      }

      if (!hasNodes) {
        break;
      }
    }

    // we stop after adding all nulls to the last level
    // also there may be some other level before that, so remove all trailing nulls
    int pos = data.size() - 1;

    while (pos >= 0 && data.get(pos) == null) {
      data.remove(pos);
      pos--;
    }

    return data.stream()
        .map(s -> s == null ? "-" : s)
        .collect(Collectors.joining(","));
  }

  public TreeNode deserialize(String data) {
    if (data.isEmpty()) {
      return null;
    }

    List<Integer> dataInt = Arrays.stream(data.split(","))
        .map(s -> s.equals("-") ? null : Integer.valueOf(s))
        .toList();

    if (dataInt.get(0) == null) {
      return null;
    }
    TreeNode root = new TreeNode(dataInt.get(0));

    int i = 1;
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      TreeNode node = q.poll();

      if (i < dataInt.size() && dataInt.get(i) != null) {
        node.left = new TreeNode(dataInt.get(i));
        q.add(node.left); // add only non nulls to queue as for nulls there won't be any children
      }
      i++; // pass through the node even if it is null

      if (i < dataInt.size() && dataInt.get(i) != null) {
        node.right = new TreeNode(dataInt.get(i));
        q.add(node.right);
      }
      i++;
    }

    return root;
  }
}