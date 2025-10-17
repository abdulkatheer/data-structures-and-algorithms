package io.abdul.graphs.minimum_spanning_tree.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // --- Test 1: Example 1 ---
    int V1 = 4;
    int[][] edges1 = {{0, 1, 1}, {1, 2, 2}, {2, 3, 3}, {0, 3, 4}};
    List<List<List<Integer>>> adj1 = buildAdjList(V1, edges1);
    int expected1 = 6; // 1 + 2 + 3
    assertEquals(expected1, sol.spanningTree(V1, adj1),
        "Example 1: Classic MST chain structure");

    // --- Test 2: Example 2 ---
    int V2 = 3;
    int[][] edges2 = {{0, 1, 5}, {1, 2, 10}, {2, 0, 15}};
    List<List<List<Integer>>> adj2 = buildAdjList(V2, edges2);
    int expected2 = 15; // 5 + 10
    assertEquals(expected2, sol.spanningTree(V2, adj2),
        "Example 2: Fully connected triangle");

    // --- Test 3: Example 3 (repeat of Example 1) ---
    int V3 = 4;
    int[][] edges3 = {{0, 1, 1}, {1, 2, 2}, {2, 3, 3}, {0, 3, 4}};
    List<List<List<Integer>>> adj3 = buildAdjList(V3, edges3);
    int expected3 = 6;
    assertEquals(expected3, sol.spanningTree(V3, adj3),
        "Example 3: Duplicate of example 1 for consistency");

    // --- Test 4: Star graph ---
    int V4 = 5;
    int[][] edges4 = {{0, 1, 1}, {0, 2, 2}, {0, 3, 3}, {0, 4, 4}};
    List<List<List<Integer>>> adj4 = buildAdjList(V4, edges4);
    int expected4 = 10; // 1 + 2 + 3 + 4
    assertEquals(expected4, sol.spanningTree(V4, adj4),
        "Star graph: center 0 connected to all others");

    // --- Test 5: Fully connected with equal weights ---
    int V5 = 4;
    int[][] edges5 = {
        {0, 1, 1}, {0, 2, 1}, {0, 3, 1},
        {1, 2, 1}, {1, 3, 1}, {2, 3, 1}
    };
    List<List<List<Integer>>> adj5 = buildAdjList(V5, edges5);
    int expected5 = 3; // any 3 edges of weight 1 form MST
    assertEquals(expected5, sol.spanningTree(V5, adj5),
        "All edges have same weight: MST weight = (V-1)*w");

    // --- Test 6: Linear chain graph ---
    int V6 = 5;
    int[][] edges6 = {{0, 1, 2}, {1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
    List<List<List<Integer>>> adj6 = buildAdjList(V6, edges6);
    int expected6 = 14; // sum of all since already tree
    assertEquals(expected6, sol.spanningTree(V6, adj6),
        "Already a tree: total weight is sum of all edges");

    // --- Test 7: Multiple MSTs with same weight ---
    int V7 = 4;
    int[][] edges7 = {{0, 1, 1}, {1, 2, 2}, {0, 2, 2}, {2, 3, 1}};
    List<List<List<Integer>>> adj7 = buildAdjList(V7, edges7);
    int expected7 = 4; // can form MST 0-1(1),2-3(1),1-2(2)
    assertEquals(expected7, sol.spanningTree(V7, adj7),
        "Multiple MSTs possible but same total weight");
  }

  private static List<List<List<Integer>>> buildAdjList(int V, int[][] edges) {
    List<List<List<Integer>>> adj = new ArrayList<>();
    for (int i = 0; i < V; i++) {
      adj.add(new ArrayList<>());
    }
    for (int[] e : edges) {
      int u = e[0], v = e[1], w = e[2];
      adj.get(u).add(Arrays.asList(v, w));
      adj.get(v).add(Arrays.asList(u, w)); // undirected
    }
    return adj;
  }
}

// Prim's Algorithm
class Solution {

  public int spanningTree(int V, List<List<List<Integer>>> adj) {
    List<int[]> mstEdges = new ArrayList<>(V - 1);
    boolean[] inMst = new boolean[V];

    PriorityQueue<int[]> q =
        new PriorityQueue<>(Comparator.comparingInt(o -> o[2])); // parent, node, weight
    q.add(new int[]{-1, 0, 0});

    while (!q.isEmpty()) {
      if (mstEdges.size() == V) {
        // inherently means that all nodes are visited with V-1 edges and 1 dummy additional edge for first node
        break;
      }

      int[] nodeData = q.poll(); // visit the next smallest edge
      int node = nodeData[1];

      if (inMst[node]) { // Better path already found, ignore
        continue;
      }

      mstEdges.add(nodeData);
      inMst[node] = true;

      // visit adjacentNodes
      List<List<Integer>> adjNodesData = adj.get(node);

      for (List<Integer> adjNodeData : adjNodesData) {
        int adjNode = adjNodeData.get(0);
        int edgeWeight = adjNodeData.get(1);

        if (!inMst[adjNode]) {
          q.add(new int[]{node, adjNode, edgeWeight});
        }
      }
    }

    mstEdges.remove(0);

    if (mstEdges.size() != V - 1) {
      return -1;
    }

    int mstWeight = 0;
    for (int[] mstEdge : mstEdges) {
      mstWeight += mstEdge[2];
    }

    return mstWeight;
  }
}

// Kruskal's Algorithm
/*
Sort edges by weight
Greedy solution:
1) Optimal substructure - Optimal solutions to subgraphs can be used to build an optimal solution for the entire graph
2) Greedy property - Choosing the smallest weighted edge at each step will give us the smallest weight for whole graph

Using Disjoint set property to know if two nodes are in same component
 */
class Solution2 {

  public int spanningTree(int V, List<List<List<Integer>>> adj) {
    List<int[]> edgeList = toEdgeList(adj);
    edgeList.sort(Comparator.comparingInt(o -> o[2]));

    DisjointSet set = new DisjointSet(V);

    List<int[]> mstEdges = new ArrayList<>();
    int mstWeight = 0;
    for (int[] edge : edgeList) {
      if (mstEdges.size() == V - 1) {
        // we've found all edges of MST, inherently means that all nodes are visited
        break;
      }

      int u = edge[0];
      int v = edge[1];
      int w = edge[2];

      if (set.union(u, v)) {
        mstEdges.add(edge);
        mstWeight += w;
      }
    }

    return mstWeight;
  }

  public List<int[]> toEdgeList(List<List<List<Integer>>> adj) {
    List<int[]> edgeList = new ArrayList<>();
    for (int i = 0; i < adj.size(); i++) {
      for (List<Integer> adjNodeData : adj.get(i)) {
        edgeList.add(new int[]{i, adjNodeData.get(0), adjNodeData.get(1)});
      }
    }

    return edgeList;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < parents.length; i++) {
        parents[i] = i;
      }
    }

    /*
    Returns false if u and v are already part of same component
     */
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

