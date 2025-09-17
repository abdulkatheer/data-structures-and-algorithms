package io.abdul.graphs.hard_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/*
Toposort variation
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1: Simple linear dependency
    int N1 = 4;
    int[][] arr1 = {{1, 0}, {2, 1}, {3, 2}};
    int[] result1 = sol.findOrder(N1, arr1);
    assertEquals(N1, result1.length, "Result length should match N");
    assertTrue(isValidOrder(N1, arr1, result1), "Order must satisfy prerequisites");

    // Example 2: Cycle exists → no valid ordering
    int N2 = 4;
    int[][] arr2 = {{0, 1}, {3, 2}, {1, 3}, {3, 0}};
    int[] result2 = sol.findOrder(N2, arr2);
    assertEquals(0, result2.length, "Should return empty array when no valid order exists");

    // Example 3: Simple valid case with two courses
    int N3 = 2;
    int[][] arr3 = {{1, 0}};
    int[] result3 = sol.findOrder(N3, arr3);
    assertEquals(N3, result3.length, "Result length should match N");
    assertTrue(isValidOrder(N3, arr3, result3), "Order must satisfy prerequisites");

    // Edge Case 1: No prerequisites (any order is valid)
    int N4 = 3;
    int[][] arr4 = {};
    int[] result4 = sol.findOrder(N4, arr4);
    assertEquals(N4, result4.length, "Any order is valid when there are no prerequisites");
    Arrays.sort(result4);
    assertArrayEquals(new int[]{0, 1, 2}, result4, "All nodes should appear in result");

    // Edge Case 2: Self dependency (impossible)
    int N5 = 1;
    int[][] arr5 = {{0, 0}};
    int[] result5 = sol.findOrder(N5, arr5);
    assertEquals(0, result5.length, "Should return empty array when self-loop exists");

    // Edge Case 3: Multiple chains with no cycle
    int N6 = 5;
    int[][] arr6 = {{1, 0}, {2, 1}, {3, 1}, {4, 2}};
    int[] result6 = sol.findOrder(N6, arr6);
    assertEquals(N6, result6.length, "Valid topological order exists");
    assertTrue(isValidOrder(N6, arr6, result6), "Order must satisfy prerequisites");

    // Edge Case 4: Complex cycle
    int N7 = 4;
    int[][] arr7 = {{1, 0}, {2, 1}, {0, 2}};
    int[] result7 = sol.findOrder(N7, arr7);
    assertEquals(0, result7.length, "Should return empty array when cycle is present");
  }

  private static boolean isValidOrder(int N, int[][] prerequisites, int[] result) {
    if (result.length == 0) {
      return false;  // Indicates no valid order
    }

    int[] position = new int[N];
    for (int i = 0; i < N; i++) {
      position[result[i]] = i;
    }

    for (int[] pair : prerequisites) {
      int course = pair[0];
      int prereq = pair[1];
      if (position[prereq] > position[course]) {
        return false;  // prereq comes after course → invalid
      }
    }
    return true;
  }
}

/*
DFS - Toposort
 */
class Solution {

  public int[] findOrder(int N, int[][] arr) {
    ArrayList<Integer>[] adjList = new ArrayList[N];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int[] course : arr) {
      adjList[course[1]].add(course[0]);
    }

    boolean[] visited = new boolean[N];
    boolean[] pathVisited = new boolean[N];
    List<Integer> topo = new ArrayList<>();

    for (int i = 0; i < N; i++) {
      if (!visited[i]) {
        if (dfs(i, adjList, visited, pathVisited, topo)) {
          return new int[]{};
        }
      }
    }

    Collections.reverse(topo);
    return topo.stream().mapToInt(i -> i).toArray();
  }

  private boolean dfs(int startNode, ArrayList<Integer>[] adjList, boolean[] visited,
      boolean[] pathVisited,
      List<Integer> topo) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;
    pathVisited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjVisitedCount = peeked[1];

      if (adjVisitedCount < adjList[node].size()) {
        Integer adjNode = adjList[node].get(adjVisitedCount);
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
          pathVisited[adjNode] = true;
        } else if (pathVisited[adjNode]) {
          return true;
        }
        peeked[1]++;
      } else {
        stack.pop();
        pathVisited[node] = false;
        topo.add(node);
      }
    }
    return false;
  }
}

/*
BFS - Toposort
 */
class Solution2 {

  public int[] findOrder(int N, int[][] arr) {
    ArrayList<Integer>[] adjList = new ArrayList[N];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int[] course : arr) {
      adjList[course[1]].add(course[0]);
    }

    int[] indegree = new int[N];
    for (ArrayList<Integer> adjNodes : adjList) {
      for (Integer adjNode : adjNodes) {
        indegree[adjNode]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < N; i++) {
      if (indegree[i] == 0) {
        q.add(i);
      }
    }

    List<Integer> topo = new ArrayList<>();
    bfs(adjList, q, topo, indegree);

    if (topo.size() != N) {
      return new int[]{};
    } else {
      return topo.stream().mapToInt(i -> i).toArray();
    }
  }

  private void bfs(ArrayList<Integer>[] adjList, Queue<Integer> q, List<Integer> topo, int[] indegree ) {
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        topo.add(node);

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
