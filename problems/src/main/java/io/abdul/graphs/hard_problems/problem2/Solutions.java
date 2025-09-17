package io.abdul.graphs.hard_problems.problem2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*
We can build a graph with given data.
If we find cycle while traversing, we won't be able to complete course
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1: Possible to finish all courses (linear order)
    int N1 = 4;
    int[][] arr1 = {{1, 0}, {2, 1}, {3, 2}};
    assertTrue(sol.canFinish(N1, arr1), "Should return true (linear dependency)");

    // Example 2: Cycle exists, not possible
    int N2 = 4;
    int[][] arr2 = {{0, 1}, {3, 2}, {1, 3}, {3, 0}};
    assertFalse(sol.canFinish(N2, arr2), "Should return false (cycle present)");

    // Example 3: Simple valid case
    int N3 = 2;
    int[][] arr3 = {{1, 0}};
    assertTrue(sol.canFinish(N3, arr3), "Should return true (simple valid)");

    // Edge Case 1: No prerequisites (all courses independent)
    int N4 = 3;
    int[][] arr4 = {};
    assertTrue(sol.canFinish(N4, arr4), "Should return true (no prerequisites)");

    // Edge Case 2: Self dependency (impossible)
    int N5 = 1;
    int[][] arr5 = {{0, 0}};
    assertFalse(sol.canFinish(N5, arr5), "Should return false (self-loop)");

    // Edge Case 3: Multiple chains with no cycle
    int N6 = 5;
    int[][] arr6 = {{1, 0}, {2, 1}, {3, 1}, {4, 2}};
    assertTrue(sol.canFinish(N6, arr6), "Should return true (multiple chains)");

    // Edge Case 4: Complex cycle involving multiple nodes
    int N7 = 4;
    int[][] arr7 = {{1, 0}, {2, 1}, {0, 2}};
    assertFalse(sol.canFinish(N7, arr7), "Should return false (complex cycle)");

    int N8 = 9;
    int[][] arr8 = {{3, 8}};
    assertTrue(sol.canFinish(N8, arr8));
  }
}

/*
DFS - Cycle detection in Directed Graph
 */
class Solution {

  public boolean canFinish(int N, int[][] arr) {
    ArrayList[] adjList = new ArrayList[N];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList();
    }

    for (int[] course : arr) {
      adjList[course[1]].add(course[0]);
    }

    boolean[] visited = new boolean[N];
    boolean[] pathVisited = new boolean[N];
    for (int i = 0; i < N; i++) {
      if (!visited[i]) {
        if (dfs(i, adjList, visited, pathVisited)) {
          return false; // if cycle detected, we can't complete course
        }
      }
    }

    return true;
  }

  private boolean dfs(int startNode, ArrayList[] adjList, boolean[] visited,
      boolean[] pathVisited) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;
    pathVisited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjVistedCount = peeked[1];

      if (adjVistedCount < adjList[node].size()) {
        int adjNode = (int) adjList[node].get(adjVistedCount);
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
          pathVisited[adjNode] = true;
        } else if (pathVisited[adjNode]) {
          return true; // cycle detected
        }
        peeked[1]++;
      } else {
        stack.pop();
        pathVisited[node] = false;
      }
    }

    return false;
  }
}

/*
BFS - Cycle detection in Directed Graph using Kahn's algorithm
 */
class Solution2 {

  public boolean canFinish(int N, int[][] arr) {
    ArrayList<Integer>[] adjList = new ArrayList[N];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList();
    }

    for (int[] course : arr) {
      adjList[course[1]].add(course[0]);
    }

    int[] indegree = new int[N];
    for (ArrayList<Integer> adjNodes : adjList) {
      for (int adjNode : adjNodes) {
        indegree[adjNode]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < N; i++) {
      if (indegree[i] == 0) {
        q.add(i);
      }
    }
    List<Integer> toposort = new ArrayList<>();
    bfs(adjList, q, indegree, toposort);

    return toposort.size() == N;
  }

  private void bfs(ArrayList<Integer>[] adjList, Queue<Integer> q, int[] indegree, List<Integer> toposort) {
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        toposort.add(node);

        for (Integer adjNode : adjList[node]) {
          indegree[adjNode]--;
          if (indegree[adjNode] == 0) {
            q.add(adjNode);
          }
        }
      }
    }
  }
}