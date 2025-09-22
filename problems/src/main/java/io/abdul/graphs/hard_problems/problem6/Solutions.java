package io.abdul.graphs.hard_problems.problem6;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
With BFS, natural O(V+E) solution can be obtained.
With DFS, recursive calls to be made to find the best path. Possible, but with more time.
 */
public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Example 1
    int[][] edges1 = {
        {0, 1}, {0, 3}, {3, 4}, {4, 5}, {5, 6},
        {1, 2}, {2, 6}, {6, 7}, {7, 8}, {6, 8}
    };
    int[] expected1 = {0, 1, 2, 1, 2, 3, 3, 4, 4};
    assertArrayEquals(expected1, sol.shortestPath(edges1, 9, 10));

    // Example 2
    int[][] edges2 = {
        {1, 0}, {2, 1}, {0, 3}, {3, 7}, {3, 4},
        {7, 4}, {7, 6}, {4, 5}, {4, 6}, {6, 5}
    };
    int[] expected2 = {0, 1, 2, 1, 2, 3, 3, 2};
    assertArrayEquals(expected2, sol.shortestPath(edges2, 8, 10));

    // Example 3
    int[][] edges3 = {{1, 2}};
    int[] expected3 = {0, -1, -1};  // only node 0 is source, no path to 1 or 2
    assertArrayEquals(expected3, sol.shortestPath(edges3, 3, 1));

    // Edge case: single node graph
    int[][] edges4 = {};
    int[] expected4 = {0};
    assertArrayEquals(expected4, sol.shortestPath(edges4, 1, 0));

    // Edge case: disconnected graph
    int[][] edges5 = {{0, 1}, {2, 3}};
    int[] expected5 = {0, 1, -1, -1};
    assertArrayEquals(expected5, sol.shortestPath(edges5, 4, 2));

    // Edge case: line graph
    int[][] edges6 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
    int[] expected6 = {0, 1, 2, 3, 4};
    assertArrayEquals(expected6, sol.shortestPath(edges6, 5, 4));

    // Edge case: star graph (all connected to source)
    int[][] edges7 = {{0, 1}, {0, 2}, {0, 3}, {0, 4}};
    int[] expected7 = {0, 1, 1, 1, 1};
    assertArrayEquals(expected7, sol.shortestPath(edges7, 5, 4));
  }
}

/*
BFS
 */
class Solution {

//  public static final int MAX_TOTAL_DISTANCE = (int) 1e5;
  // as per problem there can be up to 10^4 nodes only. So adding all will not exceed 10^4.

  public int[] shortestPath(int[][] edges, int N, int M) {
    ArrayList<Integer>[] adjList = toAdjacencyList(N, edges);

    boolean[] visited = new boolean[N];
    int[] shortestPath = new int[N];
//    Arrays.fill(shortestPath, MAX_TOTAL_DISTANCE);
    Arrays.fill(shortestPath, -1);

    // only visit nodes which are connected to 0, so we don't need for loop to visit every node
    bfs(0, adjList, visited, shortestPath);

//    for (int i = 0; i < shortestPath.length; i++) {
//      if (shortestPath[i] >= MAX_TOTAL_DISTANCE) {
//        shortestPath[i] = -1;
//      }
//    }

    return shortestPath;
  }

  private ArrayList<Integer>[] toAdjacencyList(int n, int[][] edges) {
    ArrayList<Integer>[] adjList = new ArrayList[n];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int[] edge : edges) {
      int src = edge[0];
      int dest = edge[1];
      adjList[src].add(dest);
      adjList[dest].add(src);
    }

    return adjList;
  }

  private void bfs(int startNode, ArrayList<Integer>[] adjList, boolean[] visited,
      int[] shortestPath) {
    Queue<Integer> q = new LinkedList<>();
    q.add(startNode);
    visited[startNode] = true;

    int level = 0; // only the startNode will be at first level, so it will have 0 distance
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) { // visit all nodes at this level
        Integer node = q.poll();
        shortestPath[node] = level;

        for (Integer adjNode : adjList[node]) {
          if (!visited[adjNode]) {
            q.add(adjNode);
            visited[adjNode] = true;
          }
        }
      }
      level++;
    }
  }
}
