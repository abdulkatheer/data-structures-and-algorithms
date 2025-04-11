package io.abdul.recursion.practice.problem7;

import java.util.*;

// https://www.techiedelight.com/print-all-hamiltonian-path-present-in-a-graph/
public class HamiltonianPath {
    public static void main(String[] args) {
        System.out.println(Solution.findHamiltonianPaths(4, new int[][]{{0, 1}, {2, 3}}));
        System.out.println(Solution.findHamiltonianPaths(4, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}}));
    }
}

/*

Given an undirected graph, return all Hamiltonian paths present in it. The Hamiltonian path is a path that visits each vertex exactly once.

Input: Graph [edges = [(0, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 3)], n = 4]
Output: {[0, 1, 2, 3], [0, 1, 3, 2], [0, 2, 1, 3], [0, 2, 3, 1], [0, 3, 1, 2], [0, 3, 2, 1], [1, 0, 2, 3], [1, 0, 3, 2], [1, 2, 0, 3], [1, 2, 3, 0], [1, 3, 0, 2], [1, 3, 2, 0], [2, 0, 1, 3], [2, 0, 3, 1], [2, 1, 0, 3], [2, 1, 3, 0], [2, 3, 0, 1], [2, 3, 1, 0], [3, 0, 1, 2], [3, 0, 2, 1], [3, 1, 0, 2], [3, 1, 2, 0], [3, 2, 0, 1], [3, 2, 1, 0]}

Input: Graph [edges = [(0, 1), (2, 3)], n = 4]
Output: {}
Explanation: The graph is not connected.

Constraints:

• The graph is implemented using an adjacency list.
• The maximum number of nodes in the graph is 100, i.e., 0 <= n < 100, and each node is represented by its numeric value.

*/

class Solution {
    public static Set<List<Integer>> findHamiltonianPaths(int n, int[][] edges) {
        Set<List<Integer>> result = new LinkedHashSet<>();
        List<List<Integer>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            adjList.get(from).add(to);
            adjList.get(to).add(from);
        }

        boolean[] visited = new boolean[n];

        for (int startingNode = 0; startingNode < n; startingNode++) {
            find(startingNode, adjList, 0, visited, result, new ArrayList<>(n));
        }
        return result;
    }

    private static void find(int node, List<List<Integer>> adjList, int visitedCount, boolean[] visited, Set<List<Integer>> result, List<Integer> path) {
        if (visitedCount == visited.length) { // All nodes visited
            result.add(new ArrayList<>(path));
            return;
        }

        if (visited[node]) { // Already visited
            return;
        }

        visited[node] = true;
        path.add(node);

        List<Integer> edges = adjList.get(node);

        for (Integer nextNode : edges) {
            find(nextNode, adjList, visitedCount + 1, visited, result, path);
        }

        visited[node] = false;
        path.remove(path.size() - 1);
    }
}
