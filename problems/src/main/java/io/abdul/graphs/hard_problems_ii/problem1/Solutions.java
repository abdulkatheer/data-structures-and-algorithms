package io.abdul.graphs.hard_problems_ii.problem1;

import java.util.Stack;

public class Solutions {

}

/*
The number of distinct edges we need to connect all nodes are n-1
Even if we have n+100 edges, all may not lead to the required n-1 edges.
So we count the duplicates out of n+100
and remaining of n+100 are the unique ones.
If unique ones < n-1, then we can trade off with the duplicate ones.
Sp requiredUniqueOnes <= duplicateOnes to replace

How to find requiredUniqueOnes?
We need the disconnected components. One approach is just to find the number of duplicates/unique ones.
And thereby we can find requiredUnique ones.
 */
class Solution {

  public int solve(int n, int[][] edges) {
    int edgesRequired = n - 1;
    if (edges.length < edgesRequired) {
      return -1;
    }

    DisjointSet set = new DisjointSet(n);

    int duplicates = 0;
    for (int[] edge : edges) {
      if (!set.union(edge[0], edge[1])) {
        duplicates++;
      }
    }

    int validEdges = edges.length - duplicates;
    int edgesNeeded = edgesRequired - validEdges;

    return edgesNeeded <= duplicates ? edgesNeeded : -1;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }
    }

    boolean union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return false;
      }

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }

      return true;
    }

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }
  }
}

class Solution2 {

  public int solve(int n, int[][] edges) {
    int validEdges = n - 1;
    if (edges.length < validEdges) {
      return -1;
    }

    DisjointSet set = new DisjointSet(n);

    for (int[] edge : edges) {
      set.union(edge[0], edge[1]);
    }

    // To connect 3 components, we need 2 edges
    // We've already checked the number of edges >= requiredEdges
    // Let's say edges=100, requiredEdges=80, validEdges=30, requiredValidEdges=50 which is less than 70
    return set.numberOfComponents() - 1;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }
    }

    void union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return;
      }

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }
    }

    int numberOfComponents() {
      int components = 0;
      for (int i = 0; i < parents.length; i++) {
        if (parents[i] == i) {
          components++;
        }
      }

      return components;
    }

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }
  }
}

