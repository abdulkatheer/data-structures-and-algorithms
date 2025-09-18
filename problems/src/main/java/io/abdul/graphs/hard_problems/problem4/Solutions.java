package io.abdul.graphs.hard_problems.problem4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
Based on differing characters between each word, we'll build a Directed Graph.
If we don't have enough words to find out the order, return empty string.

Let's say there are 5 words. We can find the differing char by checking 1 & 2, 2 & 3, 3 & 4, 4 & 5
How to do this efficiently?
 */
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    String[] dict1 = {"baa", "abcd", "abca", "cab", "cad"};
    String res1 = sol.findOrder(dict1, 5, 4);
    assertTrue(isValidOrder(res1, dict1));

    // Example 2
    String[] dict2 = {"caa", "aaa", "aab"};
    String res2 = sol.findOrder(dict2, 3, 3);
    assertTrue(isValidOrder(res2, dict2));

    // Example 3
    String[] dict3 = {"abc", "bca", "cab"};
    String res3 = sol.findOrder(dict3, 3, 3);
    assertTrue(isValidOrder(res3, dict3));

    // Single word
    String[] dict4 = {"a"};
    String res4 = sol.findOrder(dict4, 1, 1);
    assertEquals("a", res4);

    // Two words, different first char
    String[] dict5 = {"ca", "cb"};
    String res5 = sol.findOrder(dict5, 2, 3);
    assertTrue(isValidOrder(res5, dict5));

    // Contradiction case
    String[] dict6 = {"ab", "abc"};
    String res6 = sol.findOrder(dict6, 2, 3);
    assertTrue(isValidOrder(res6, dict6));

    // Cycle case
    String[] dict7 = {"da", "db", "ca", "cb"};
    String res7 = sol.findOrder(dict7, 4, 4);
    assertTrue(isValidOrder(res7, dict7));
  }

  private static boolean isValidOrder(String order, String[] dict) {
    if (order == null || order.isEmpty()) {
      return false;
    }
    int[] pos = new int[26];
    for (int i = 0; i < order.length(); i++) {
      pos[order.charAt(i) - 'a'] = i;
    }
    for (int i = 0; i < dict.length - 1; i++) {
      String w1 = dict[i], w2 = dict[i + 1];
      int minLen = Math.min(w1.length(), w2.length());
      boolean found = false;
      for (int j = 0; j < minLen; j++) {
        if (w1.charAt(j) != w2.charAt(j)) {
          if (pos[w1.charAt(j) - 'a'] > pos[w2.charAt(j) - 'a']) {
            return false;
          }
          found = true;
          break;
        }
      }
      if (!found && w1.length() > w2.length()) {
        return false;
      }
    }
    return true;
  }
}

/*
DFS - Toposort
 */
class Solution {

  public static final int SMALLCASE_INDEX = 97;

  public String findOrder(String[] dict, int N, int K) {
    ArrayList<Integer>[] adjList = new ArrayList[K];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int i = 0; i < dict.length - 1; i++) {
      int[] difference = difference(dict[i], dict[i + 1]);
      if (difference != null) {
        adjList[difference[0] - SMALLCASE_INDEX].add(difference[1] - SMALLCASE_INDEX);
      }
    }

    boolean[] visited = new boolean[K];
    boolean[] pathVisited = new boolean[K];
    StringBuilder topo = new StringBuilder();
    for (int i = 0; i < K; i++) {
      if (!visited[i]) {
        dfs(i, adjList, visited, pathVisited, topo);
      }
    }

    return topo.length() != K ? "" : topo.reverse().toString();
  }

  private void dfs(int startNode, ArrayList<Integer>[] adjList, boolean[] visited,
      boolean[] pathVisited,
      StringBuilder topo) {
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[]{startNode, 0});
    visited[startNode] = true;
    pathVisited[startNode] = true;

    while (!stack.isEmpty()) {
      int[] peeked = stack.peek();
      int node = peeked[0];
      int adjVisitedIndex = peeked[1];

      if (adjVisitedIndex < adjList[node].size()) {
        Integer adjNode = adjList[node].get(adjVisitedIndex);
        if (!visited[adjNode]) {
          stack.push(new int[]{adjNode, 0});
          visited[adjNode] = true;
          pathVisited[adjNode] = true;
        } else if (pathVisited[adjNode]) { // cycle detected
          return;
        }
        peeked[1]++;
      } else {
        stack.pop();
        topo.append((char) (node + SMALLCASE_INDEX));
        pathVisited[node] = false;
      }
    }
  }

  private int[] difference(String a, String b) {
    int aLen = a.length();
    int bLen = b.length();

    for (int i = 0; i < aLen && i < bLen; i++) {
      if (a.charAt(i) != b.charAt(i)) {
        return new int[]{a.charAt(i), b.charAt(i)};
      }
    }

    return null;
  }
}

/*
BFS - Toposort
 */
class Solution2 {

  public static final int SMALLCASE_INDEX = 97;

  public String findOrder(String[] dict, int N, int K) {
    ArrayList<Integer>[] adjList = new ArrayList[K];
    for (int i = 0; i < adjList.length; i++) {
      adjList[i] = new ArrayList<>();
    }

    for (int i = 0; i < dict.length - 1; i++) {
      int[] difference = difference(dict[i], dict[i + 1]);
      if (difference != null) {
        adjList[difference[0] - SMALLCASE_INDEX].add(difference[1] - SMALLCASE_INDEX);
      }
    }

    int[] indegree = new int[K];
    for (ArrayList<Integer> adjNodes : adjList) {
      for (Integer adjNode : adjNodes) {
        indegree[adjNode]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    StringBuilder topo = new StringBuilder();
    for (int i = 0; i < K; i++) {
      if (indegree[i] == 0) {
        q.add(i);
      }
    }

    bfs(q, indegree, adjList, topo);

    return topo.length() != K ? "" : topo.toString();
  }

  private void bfs(Queue<Integer> q, int[] indegree, ArrayList<Integer>[] adjList,
      StringBuilder topo) {
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        Integer node = q.poll();
        topo.append((char) (node + SMALLCASE_INDEX));

        ArrayList<Integer> adjNodes = adjList[node];
        for (Integer adjNode : adjNodes) {
          indegree[adjNode]--;
          if (indegree[adjNode] == 0) {
            q.add(adjNode);
          }
        }
      }
    }
  }

  private int[] difference(String a, String b) {
    int aLen = a.length();
    int bLen = b.length();

    for (int i = 0; i < aLen && i < bLen; i++) {
      if (a.charAt(i) != b.charAt(i)) {
        return new int[]{a.charAt(i), b.charAt(i)};
      }
    }

    return null;
  }
}