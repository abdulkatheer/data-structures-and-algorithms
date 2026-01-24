package io.abdul.problem55;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// https://leetcode.com/problems/number-of-ways-to-reorder-array-to-get-same-bst/
// tag:math tag:dynamic_programming tag:binary_search_tree
public class Solutions {

}

/*
Intuitions are,
Given that numbers are distinct.
the first node in the tree decides the root. So it's position can't be changed.
nodes smaller than root come in left subtree and bigger in right subtree.
The smaller numbers are in some order. If we don't change the relative order and arrange them in all possible ways, we'll get same BST.
Same goes to bigger numbers as well.

Ex:
5 1 8 3 7 9 4 2 6
5 can't change its position
1 3 4 2 come in left subtree
8 7 9 6 come in right subtree
At this level, 1 3 4 2 and 8 7 9 6 can be placed anywhere without changing relative order.
Meaning, in how many ways we can arrange 1 3 4 2 or 8 7 9 6 - Permutations
4P8 = 8!/4!
But this counts all the orders of 4. We need to eliminate the ones which changes the relative order.
That becomes nC8 = 8!/4! * 4!


Okay! But not all BSTs formed by changing relative order results in different BST than original.
We can't find that having 5 as root. We need to repeat this process until it goes to a smallest subtree (1 or 2 nodes).

So the combination multiplies with combinations of left and right subtree

---
Meaning 4C8 is the combination at level 1 and it multplies with combination(left) and combination(right)
*/

class Solution {
  private static final int MOD = ((int) 1e9) + 7;

  public int numOfWays(int[] nums) {
    int[][] p = pascalsTriangle(nums.length);

    List<Integer> tree = Arrays.stream(nums)
        .boxed()
        .collect(Collectors.toList());
    return (int) ((numOfWays(tree, p) - 1) % MOD);
  }

  private long numOfWays(List<Integer> tree, int[][] p) {
    if (tree.size() <= 2) {
      // empty tree
      // root only
      // root and only child
      return 1;
    }

    int n = tree.size();
    int root = tree.get(0);
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    for (int i = 1; i < n; i++) {
      int node = tree.get(i);
      if (node < root) {
        left.add(node);
      } else {
        right.add(node);
      }
    }

    long result = ((numOfWays(left, p) % MOD) * ( numOfWays(right, p) % MOD)) % MOD;

    int c = p[n-1][left.size()];
    return (result * c) % MOD;
  }

  // DP approach to build Pascal's Triangle
  private int[][] pascalsTriangle(int n) {
    // we only need for n-1 as our max n is excluding the root
    int[][] p = new int[n][n]; // row 1 for n=1

    // All 0Cx and xCx are 1
    for (int i = 0; i < n; i++) {
      p[i][0] = p[i][i] = 1;
    }

    for (int i = 2; i < n; i++) {
      for (int j = 1; j < i; j++) {
        p[i][j] = (int) (((long) p[i-1][j-1] + p[i-1][j]) % MOD);
      }
    }

    return p;
  }
}
