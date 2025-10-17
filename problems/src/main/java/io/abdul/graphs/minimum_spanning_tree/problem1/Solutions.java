package io.abdul.graphs.minimum_spanning_tree.problem1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
    testUnionByRank();
    testUnionBySize();
  }

  private static void testUnionByRank() {
    // Example 1
//    DisjointSet ds1 = new DisjointSet(5);
    DisjointSet2 ds1 = new DisjointSet2(5);
    ds1.unionByRank(0, 1);
    ds1.unionByRank(3, 4);
    assertTrue(ds1.find(0, 1), "0 and 1 should be in the same set after unionByRank");
    assertFalse(ds1.find(0, 3), "0 and 3 should not be in the same set");

    // Example 2 (chain unions)
//    DisjointSet ds2 = new DisjointSet(3);
    DisjointSet2 ds2 = new DisjointSet2(3);
    ds2.unionByRank(0, 1);
    ds2.unionByRank(1, 2);
    assertTrue(ds2.find(0, 2), "0 and 2 should be connected after chained unions");
    assertTrue(ds2.find(0, 1), "0 and 1 should remain connected");

    // Example 3 (mix and deeper union)
//    DisjointSet ds3 = new DisjointSet(5);
    DisjointSet2 ds3 = new DisjointSet2(5);
    ds3.unionByRank(0, 1);
    ds3.unionByRank(1, 2);
    ds3.unionByRank(3, 4);
    assertTrue(ds3.find(0, 2), "0 and 2 should be in same set via 1");
    assertFalse(ds3.find(1, 3), "1 and 3 should be in different sets");

    // Deeper rank-based merging
//    DisjointSet ds4 = new DisjointSet(6);
    DisjointSet2 ds4 = new DisjointSet2(6);
    ds4.unionByRank(0, 1);
    ds4.unionByRank(2, 3);
    ds4.unionByRank(1, 2);
    ds4.unionByRank(4, 5);
    assertTrue(ds4.find(0, 3), "0 and 3 should be connected after merging sets");
    assertFalse(ds4.find(0, 4), "0 and 4 should not be connected");
    ds4.unionByRank(3, 4);
    assertTrue(ds4.find(0, 5), "After merging 3 and 4, all 0–5 should be connected");

    // Edge cases
//    DisjointSet ds5 = new DisjointSet(4);
    DisjointSet2 ds5 = new DisjointSet2(4);
    assertTrue(ds5.find(2, 2), "An element should always be in the same set as itself");
    assertFalse(ds5.find(0, 3), "Initially 0 and 3 are not in the same set");
  }

  private static void testUnionBySize() {
    // Example 1
    DisjointSet3 ds1 = new DisjointSet3(5);
    ds1.unionBySize(0, 1);
    ds1.unionBySize(3, 4);
    assertTrue(ds1.find(0, 1), "0 and 1 should be in the same set after unionBySize");
    assertFalse(ds1.find(0, 3), "0 and 3 should not be in the same set");

    // Example 2 (chain unions)
    DisjointSet3 ds2 = new DisjointSet3(3);
    ds2.unionBySize(0, 1);
    ds2.unionBySize(1, 2);
    assertTrue(ds2.find(0, 2), "0 and 2 should be connected after chained unions");
    assertTrue(ds2.find(0, 1), "0 and 1 should remain connected");

    // Example 3 (mix and deeper union)
    DisjointSet3 ds3 = new DisjointSet3(5);
    ds3.unionBySize(0, 1);
    ds3.unionBySize(1, 2);
    ds3.unionBySize(3, 4);
    assertTrue(ds3.find(0, 2), "0 and 2 should be in same set via 1");
    assertFalse(ds3.find(1, 3), "1 and 3 should be in different sets");

    // Deeper rank-based merging
    DisjointSet3 ds4 = new DisjointSet3(6);
    ds4.unionBySize(0, 1);
    ds4.unionBySize(2, 3);
    ds4.unionBySize(1, 2);
    ds4.unionBySize(4, 5);
    assertTrue(ds4.find(0, 3), "0 and 3 should be connected after merging sets");
    assertFalse(ds4.find(0, 4), "0 and 4 should not be connected");
    ds4.unionBySize(3, 4);
    assertTrue(ds4.find(0, 5), "After merging 3 and 4, all 0–5 should be connected");

    // Edge cases
    DisjointSet3 ds5 = new DisjointSet3(4);
    assertTrue(ds5.find(2, 2), "An element should always be in the same set as itself");
    assertFalse(ds5.find(0, 3), "Initially 0 and 3 are not in the same set");
  }
}

/*
Union by rank
This is without 'path compression'. So rank of ultimateParent's will be height
 */
class DisjointSet {

  private final int[] parent;
  private final int[] rank;

  public DisjointSet(int n) {
    parent = new int[n];
    rank = new int[n];

    // Initially all nodes are disconnected and it itself is the parent
    // Ranks are zero as all are of same height
    for (int i = 0; i < parent.length; i++) {
      parent[i] = i;
    }
  }

  public boolean find(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    return uUltimateParent == vUltimateParent;
  }

  public void unionByRank(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    // we want the tree to be as shallow as possible to reduce lookup time
    if (rank[uUltimateParent] > rank[vUltimateParent]) {
      // attach v to u
      parent[vUltimateParent] = uUltimateParent;
    } else if (rank[vUltimateParent] > rank[uUltimateParent]) {
      // attach u to v
      parent[uUltimateParent] = vUltimateParent;
    } else {
      // rank is same, can attach either way, that will increase height by 1
      // attach u to v
      parent[u] = vUltimateParent;
      rank[vUltimateParent]++;
    }
  }

  public void unionBySize(int u, int v) {
    throw new RuntimeException("Not implemented");
  }

  private int findUltimateParent(int x) {
    return findUltimateParentIterative(x);
  }

  private int findUltimateParentRecursive(int x) {
    if (x == parent[x]) {
      return x;
    }
    return findUltimateParentRecursive(parent[x]);
  }

  private int findUltimateParentIterative(int x) {
    while (x != parent[x]) {
      x = parent[x];
    }
    return x;
  }
}

/*
Optimization - Path compression
 */
class DisjointSet2 {

  private final int[] parent;
  private final int[] rank;

  public DisjointSet2(int n) {
    parent = new int[n];
    rank = new int[n];

    // Initially all nodes are disconnected and it itself is the parent
    // Ranks are zero as all are of same height
    for (int i = 0; i < parent.length; i++) {
      parent[i] = i;
    }
  }

  public boolean find(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    return uUltimateParent == vUltimateParent;
  }

  public void unionByRank(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    if (uUltimateParent == vUltimateParent) {
      return;
    }

    // we want the tree to be as shallow as possible to reduce lookup time
    if (rank[uUltimateParent] > rank[vUltimateParent]) {
      // attach v to u
      parent[vUltimateParent] = uUltimateParent;
    } else if (rank[vUltimateParent] > rank[uUltimateParent]) {
      // attach u to v
      parent[uUltimateParent] = vUltimateParent;
    } else {
      // rank is same, can attach either way, that will increase height by 1
      // attach u to v
      parent[u] = vUltimateParent;
      rank[vUltimateParent]++;
    }
  }

  public void unionBySize(int u, int v) {
    throw new RuntimeException("Not implemented");
  }

  private int findUltimateParent(int x) {
    return findUltimateParentIterative(x);
  }

  private int findUltimateParentRecursive(int x) {
    if (x == parent[x]) {
      return x;
    }
    return parent[x] = findUltimateParentRecursive(parent[x]);
  }

  private int findUltimateParentIterative(int x) {
    Stack<Integer> stack = new Stack<>();
    while (x != parent[x]) {
      stack.push(x);
      x = parent[x];
    }
    while (!stack.isEmpty()) {
      parent[stack.pop()] = x;
    }
    return x;
  }
}

/*
Union by Size
Union by Rank - Ranks are distorted during path compression, they're not the real heights anymore.
But by size, it'll maintain number of nodes in each set.
 */
class DisjointSet3 {

  private final int[] parent;
  private final int[] sizes;

  public DisjointSet3(int n) {
    parent = new int[n];
    sizes = new int[n];

    // Initially all nodes are disconnected and it itself is the parent
    // Ranks are zero as all are of same height
    for (int i = 0; i < parent.length; i++) {
      parent[i] = i;
    }

    Arrays.fill(sizes, 1); // by default each set has only one node in it
  }

  public boolean find(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    return uUltimateParent == vUltimateParent;
  }

  public void unionByRank(int u, int v) {
    throw new RuntimeException("Not implemented");
  }

  public void unionBySize(int u, int v) {
    int uUltimateParent = findUltimateParent(u);
    int vUltimateParent = findUltimateParent(v);

    if (sizes[uUltimateParent] > sizes[vUltimateParent]) {
      parent[vUltimateParent] = uUltimateParent;
      sizes[uUltimateParent] += sizes[vUltimateParent];
    } else if (sizes[vUltimateParent] > sizes[uUltimateParent]) {
      parent[uUltimateParent] = vUltimateParent;
      sizes[vUltimateParent] += sizes[uUltimateParent];
    } else {
      parent[uUltimateParent] = vUltimateParent;
      sizes[vUltimateParent] += sizes[uUltimateParent];
    }
  }

  private int findUltimateParent(int x) {
    return findUltimateParentIterative(x);
  }

  private int findUltimateParentRecursive(int x) {
    if (x == parent[x]) {
      return x;
    }
    return parent[x] = findUltimateParentRecursive(parent[x]);
  }

  private int findUltimateParentIterative(int x) {
    Stack<Integer> stack = new Stack<>();
    while (x != parent[x]) {
      stack.push(x);
      x = parent[x];
    }
    while (!stack.isEmpty()) {
      parent[stack.pop()] = x;
    }
    return x;
  }
}
