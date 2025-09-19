package io.abdul.graphs.hard_problems.problem5;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/*
We can make this work for any source node.
If we find toposort order for the DAG, we'll get to know who comes before the source and who comes after.
Nodes coming before it or disconnected will not have any path from source. So it's distance will be -1 (as per problem requirement)
For Nodes coming after, we'll start updating distance from the first level of connection and forward. We keep min of all.

Max distance possible is 5 * 10^4 (nodes) X 10^4 (max distance per edge) = 5 * 10^8
So we'll keep 10^9 as Max
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    int[][] edges1 = {{0, 1, 2}, {0, 2, 1}};
    int[] expected1 = {0, 2, 1, -1};
    assertArrayEquals(expected1, sol.shortestPath(4, 2, edges1));

    // Example 2
    int[][] edges2 = {
        {0, 1, 2}, {0, 4, 1}, {4, 5, 4},
        {4, 2, 2}, {1, 2, 3}, {2, 3, 6}, {5, 3, 1}
    };
    int[] expected2 = {0, 2, 3, 6, 1, 5};
    assertArrayEquals(expected2, sol.shortestPath(6, 7, edges2));

    // Example 3
    int[][] edges3 = {{0, 1, 4}, {0, 2, 2}, {1, 2, 5}};
    int[] expected3 = {0, 4, 2};
    assertArrayEquals(expected3, sol.shortestPath(3, 3, edges3));

    // Edge case: Single node, no edges
    int[][] edges4 = {};
    int[] expected4 = {0};
    assertArrayEquals(expected4, sol.shortestPath(1, 0, edges4));

    // Edge case: Disconnected nodes
    int[][] edges5 = {{0, 1, 3}};
    int[] expected5 = {0, 3, -1, -1};
    assertArrayEquals(expected5, sol.shortestPath(4, 1, edges5));

    // Edge case: Long chain
    int[][] edges6 = {{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}};
    int[] expected6 = {0, 1, 2, 3, 4};
    assertArrayEquals(expected6, sol.shortestPath(5, 4, edges6));

    // Edge case: Multiple paths, need min distance
    int[][] edges7 = {{0, 1, 10}, {0, 2, 3}, {2, 1, 1}};
    int[] expected7 = {0, 4, 3};
    assertArrayEquals(expected7, sol.shortestPath(3, 3, edges7));
  }
}

/*
DFS
 */
class Solution {

  public int[] shortestPath(int N, int M, int[][] edges) {
    ArrayList<int[]>[] adjList = toAdjacencyList(N, edges);

    List<Integer> topo = toposort(N, adjList);

    return findShortestPath(0, topo, adjList);
  }

  private List<Integer> toposort(int n, ArrayList<int[]>[] adjList) {
    boolean[] visited = new boolean[n];
    List<Integer> topo = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfs(i, adjList, visited, topo);
      }
    }

    Collections.reverse(topo);
    return topo;
  }

  private void dfs(int startNode, ArrayList<int[]>[] adjList, boolean[] visited,
      List<Integer> topo) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjVisitedIndex = peeked[1];

      if (adjVisitedIndex < adjList[node].size()) {
        ArrayList<int[]> adjNodes = adjList[node];
        int adjNode = adjNodes.get(adjVisitedIndex)[0];
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
        }
        peeked[1]++;
      } else {
        stack.pop();
        topo.add(node);
      }
    }
  }

  private int[] findShortestPath(int source, List<Integer> topo, ArrayList<int[]>[] adjList) {
    int[] shortestPath = new int[topo.size()];
    Arrays.fill(shortestPath, (int) 1e9);
    shortestPath[source] = 0; // distance from source to source is 0

    for (Integer node : topo) {
      ArrayList<int[]> adjNodes = adjList[node];
      for (int[] adjNode : adjNodes) {
        int dest = adjNode[0];
        int weight = adjNode[1];
        shortestPath[dest] = Math.min(shortestPath[dest], shortestPath[node] + weight);
      }
    }

    for (int i = 0; i < shortestPath.length; i++) {
      if (shortestPath[i] >= (int) 1e9) {
        shortestPath[i] = -1;
      }
    }

    return shortestPath;
  }

  private static ArrayList<int[]>[] toAdjacencyList(int N, int[][] edges) {
    ArrayList<int[]>[] adjList = new ArrayList[N];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int[] edge : edges) {
      int src = edge[0];
      int dest = edge[1];
      int weight = edge[2];
      adjList[src].add(new int[]{dest, weight});
    }
    return adjList;
  }
}

/*
BFS
 */
class Solution2 {

  public int[] shortestPath(int N, int M, int[][] edges) {
    ArrayList<int[]>[] adjList = toAdjacencyList(N, edges);

    List<Integer> topo = toposort(N, adjList);

    return findShortestPath(0, N, topo, adjList);
  }

  private List<Integer> toposort(int n, ArrayList<int[]>[] adjList) {
    int[] indegree = new int[n];
    List<Integer> topo = new ArrayList<>();
    for (ArrayList<int[]> adjNodes : adjList) {
      for (int[] adjNode : adjNodes) {
        indegree[adjNode[0]]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < indegree.length; i++) {
      if (indegree[i] == 0) {
        q.add(i);
      }
    }

    while (!q.isEmpty()) {
      Integer node = q.poll();
      topo.add(node);

      ArrayList<int[]> adjNodes = adjList[node];
      for (int[] adjNode : adjNodes) {
        int theAdjNode = adjNode[0];
        indegree[theAdjNode]--;
        if (indegree[theAdjNode] == 0) {
          q.add(theAdjNode);
        }
      }
    }

    return topo;
  }

  private int[] findShortestPath(int source, int n, List<Integer> topo,
      ArrayList<int[]>[] adjList) {
    int[] shortestPath = new int[n];
    Arrays.fill(shortestPath, (int) 1e9);
    shortestPath[source] = 0;

    for (Integer node : topo) {
      for (int[] adjNode : adjList[node]) {
        int dest = adjNode[0];
        int weight = adjNode[1];
        shortestPath[dest] = Math.min(shortestPath[dest], shortestPath[node] + weight);
      }
    }

    for (int i = 0; i < shortestPath.length; i++) {
      if (shortestPath[i] >= (int) 1e9) {
        shortestPath[i] = -1;
      }
    }
    return shortestPath;
  }

  private ArrayList<int[]>[] toAdjacencyList(int n, int[][] edges) {
    ArrayList<int[]>[] adjList = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int[] edge : edges) {
      int source = edge[0];
      int dest = edge[1];
      int weight = edge[2];
      adjList[source].add(new int[]{dest, weight});
    }

    return adjList;
  }
}