package io.abdul.graphs.minimum_spanning_tree.problem1;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Solutions {

  public static void main(String[] args) {
//    // Example 1
//    DisjointSet ds1 = new DisjointSet(5);
//    ds1.unionByRank(0, 1);
//    ds1.unionByRank(3, 4);
//    assertTrue(ds1.find(0, 1), "0 and 1 should be in the same set after unionByRank");
//    assertFalse(ds1.find(0, 3), "0 and 3 should not be in the same set");
//
//    // Example 2 (chain unions)
//    DisjointSet ds2 = new DisjointSet(3);
//    ds2.unionByRank(0, 1);
//    ds2.unionByRank(1, 2);
//    assertTrue(ds2.find(0, 2), "0 and 2 should be connected after chained unions");
//    assertTrue(ds2.find(0, 1), "0 and 1 should remain connected");
//
//    // Example 3 (mix and deeper union)
//    DisjointSet ds3 = new DisjointSet(5);
//    ds3.unionByRank(0, 1);
//    ds3.unionByRank(1, 2);
//    ds3.unionByRank(3, 4);
//    assertTrue(ds3.find(0, 2), "0 and 2 should be in same set via 1");
//    assertFalse(ds3.find(1, 3), "1 and 3 should be in different sets");

    // Deeper rank-based merging
    DisjointSet ds4 = new DisjointSet(6);
    ds4.unionByRank(0, 1);
    ds4.unionByRank(2, 3);
    ds4.unionByRank(1, 2);
    ds4.unionByRank(4, 5);
    assertTrue(ds4.find(0, 3), "0 and 3 should be connected after merging sets");
    assertFalse(ds4.find(0, 4), "0 and 4 should not be connected");
    ds4.unionByRank(3, 4);
    assertTrue(ds4.find(0, 5), "After merging 3 and 4, all 0–5 should be connected");

    // Edge cases
    DisjointSet ds5 = new DisjointSet(4);
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
    if (x == parent[x]) {
      return x;
    }
    return findUltimateParent(parent[x]);
  }
}

