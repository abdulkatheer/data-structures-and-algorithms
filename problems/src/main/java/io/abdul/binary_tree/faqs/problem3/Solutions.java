package io.abdul.binary_tree.faqs.problem3;

import static io.abdul.binary_tree.TreeNode.buildTree;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.abdul.binary_tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

//    // Case 1
//    TreeNode root1 = buildTree(new Integer[]{3, 9, 20, null, null, 15, 7});
//    List<List<Integer>> expected1 = Arrays.asList(
//        Arrays.asList(9),
//        Arrays.asList(3, 15),
//        Arrays.asList(20),
//        Arrays.asList(7)
//    );
//    assertEquals(expected1, solution.verticalTraversal(root1));
//
//    // Case 2
//    TreeNode root2 = buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
//    List<List<Integer>> expected2 = Arrays.asList(
//        Arrays.asList(4),
//        Arrays.asList(2),
//        Arrays.asList(1, 5, 6),
//        Arrays.asList(3),
//        Arrays.asList(7)
//    );
//    assertEquals(expected2, solution.verticalTraversal(root2));
//
//    // Case 3
//    TreeNode root3 = buildTree(new Integer[]{5, 1, 2, 8, null, 4, 5, null, 6});
//    List<List<Integer>> expected3 = Arrays.asList(
//        Arrays.asList(8),
//        Arrays.asList(1, 6),
//        Arrays.asList(5, 4),
//        Arrays.asList(2),
//        Arrays.asList(5)
//    );
//    assertEquals(expected3, solution.verticalTraversal(root3));
//
//    // Case 4 - Single node
//    TreeNode root4 = buildTree(new Integer[]{10});
//    List<List<Integer>> expected4 = Arrays.asList(
//        Arrays.asList(10)
//    );
//    assertEquals(expected4, solution.verticalTraversal(root4));
//
//    // Case 5 - Left-skewed tree
//    TreeNode root5 = buildTree(new Integer[]{1, 2, null, 3, null, 4});
//    List<List<Integer>> expected5 = Arrays.asList(
//        Arrays.asList(4),
//        Arrays.asList(3),
//        Arrays.asList(2),
//        Arrays.asList(1)
//    );
//    assertEquals(expected5, solution.verticalTraversal(root5));

    // Case 6 – Large complex tree input
    TreeNode root6 = buildTree(parseInput("1 65 83 72 74 -10 null null -92 15 -37 92 null null -61 null 24 null 44 23 -81 85 68 36 50 null 61 null null 71 -36 null 73 null 51 null null 39 59 -62 null 37 null null null -17 10 null null -93 null null null 78 39 -53 87 84 92 null 61 97 65 null null null null null null -29 null null -16 null 83 null 20 null null -99 null null null null 18 14 -21 51 70 87 66 null null 15 -64 -4 null 79 8 64 null null null null null 23 null 25 null null null 43 -80 -24 27 null 89 null -62 null null 67 null null 64 null null null -23 null null null null null null null null 42 25 -69 null 35 -50 null null null null null null"));
    List<List<Integer>> expected6 = convertExpected(
        "-53\n72 23 37 -29 84 97 87 23\n65 -92 15 92 36 71 85 -93 -17 87 -99 -16 15 -62 67\n1 -10 74 -81 -61 24 39 73 65 92 14 18 66 25 43\n83 -37 -36 50 68 10 78 83 -64 -4 79\n44 -62 51 59 61 -21 51 -80 -24\n61 39 20 8 64 64 -69\n70 27 89 42\n-23 35\n25\n-50\n"
    );
    assertEquals(expected6, solution.verticalTraversal(root6));
  }

  // Parses space-separated input string into Integer[] (nulls as null)
  private static Integer[] parseInput(String input) {
    return Arrays.stream(input.split(" "))
        .map(s -> s.equals("null") ? null : Integer.valueOf(s))
        .toArray(Integer[]::new);
  }

  // Parses expected \n-separated string into List<List<Integer>>
  private static List<List<Integer>> convertExpected(String expectedStr) {
    List<List<Integer>> result = new ArrayList<>();
    for (String line : expectedStr.strip().split("\n")) {
      List<Integer> col = new ArrayList<>();
      for (String num : line.trim().split("\\s+")) {
        col.add(Integer.parseInt(num));
      }
      result.add(col);
    }
    return result;
  }
}

/*
T - O(n log n) - O(n) - iterate all; O(log n) to insert into map<y>; O(log n) to insert into map<x>; O(n log n) to sort at the end
S - O(n)

We do a level order traversal and find x,y for all nodes
then we need group them by y and sort elements inside and also sort elements by y
 */
class Solution {

  public List<List<Integer>> verticalTraversal(TreeNode root) {
    Map<Integer, Map<Integer, List<Integer>>> data = new TreeMap<>();
    // we need to sort by y and then x

    Queue<TreeNodeWrapper> q = new LinkedList<>();
    q.add(new TreeNodeWrapper(root, 0, 0));

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        TreeNodeWrapper node = q.poll();
        if (!data.containsKey(node.y)) {
          data.put(node.y, new TreeMap<>());
        }
        if (!data.get(node.y).containsKey(node.x)) {
          data.get(node.y).put(node.x, new ArrayList<>());
        }

        data.get(node.y).get(node.x).add(node.node.data);

        if (node.node.left != null) {
          q.add(new TreeNodeWrapper(node.node.left, node.x + 1, node.y - 1));
        }
        if (node.node.right != null) {
          q.add(new TreeNodeWrapper(node.node.right, node.x + 1, node.y + 1));
        }
      }
    }

    List<List<Integer>> result = new ArrayList<>();

    for (Map<Integer, List<Integer>> byX : data.values()) {
      List<Integer> dataAtY = new ArrayList<>();
      for (List<Integer> dataAtX : byX.values()) {
        Collections.sort(dataAtX);
        dataAtY.addAll(dataAtX);
      }
      result.add(dataAtY);
    }

    return result;
  }
}

/*
Optimal
T - O(n) - O(n) - iterate all; O(log n) to insert into map<y>; O(log n) to insert into map<x>; O(log n) to insert into Priority Queue;
S - O(n)

*/

class Solution2 {

  public List<List<Integer>> verticalTraversal(TreeNode root) {
    Map<Integer, Map<Integer, PriorityQueue<Integer>>> data = new TreeMap<>();
    // we need to sort by y and then x

    Queue<TreeNodeWrapper> q = new LinkedList<>();
    q.add(new TreeNodeWrapper(root, 0, 0));

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        TreeNodeWrapper node = q.poll();
        if (!data.containsKey(node.y)) {
          data.put(node.y, new TreeMap<>());
        }
        if (!data.get(node.y).containsKey(node.x)) {
          data.get(node.y).put(node.x, new PriorityQueue<>());
        }

        data.get(node.y).get(node.x).add(node.node.data);

        if (node.node.left != null) {
          q.add(new TreeNodeWrapper(node.node.left, node.x + 1, node.y - 1));
        }
        if (node.node.right != null) {
          q.add(new TreeNodeWrapper(node.node.right, node.x + 1, node.y + 1));
        }
      }
    }

    List<List<Integer>> result = new ArrayList<>();

    for (Map<Integer, PriorityQueue<Integer>> byX : data.values()) {
      List<Integer> dataAtY = new ArrayList<>();
      for (PriorityQueue<Integer> dataAtX : byX.values()) {
        while (!dataAtX.isEmpty()) {
          dataAtY.add(dataAtX.poll());
        }
      }
      result.add(dataAtY);
    }

    return result;
  }
}

class TreeNodeWrapper {

  final TreeNode node;
  final int x;
  final int y;

  public TreeNodeWrapper(TreeNode node, int x, int y) {
    this.node = node;
    this.x = x;
    this.y = y;
  }
}