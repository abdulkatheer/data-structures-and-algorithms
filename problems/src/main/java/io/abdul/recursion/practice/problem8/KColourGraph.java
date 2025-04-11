package io.abdul.recursion.practice.problem8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.techiedelight.com/?problem=KColorableGraph
public class KColourGraph {
    public static void main(String[] args) {
        print(Solution.kColourConfigurations(6, new int[][]{{0, 1}, {0, 4}, {0, 5}, {4, 5}, {1, 4}, {1, 3}, {2, 3}, {2, 4}}, 3));
    }

    private static void print(List<int[]> data) {
        for (int[] datum : data) {
            System.out.print(Arrays.toString(datum) + " ; ");
        }
    }
}

/*

Given an undirected graph and a positive integer k, check if the graph is k–colorable or not.

The vertex coloring is a way of coloring the vertices of a graph such that no two adjacent vertices share the same color. A coloring using at most k colors is called a (proper) k–coloring, and a graph that can be assigned a (proper) k–coloring is k–colorable.

Input: Graph [edges = [(0, 1), (0, 4), (0, 5), (4, 5), (1, 4), (1, 3), (2, 3), (2, 4)], n = 6], k = 3

 0 ───── 1 ────── 2
 │ \	 │  \   / │
 │   \   │	  X   │
 │	   \ │  /   \ │
 5 ───── 4 ────── 3

Output: true

Explanation: The graph can be colored using 3 colors in several ways as shown below. However, the graph is not 2–colorable.

 ┌─────────────────────────────────────────┐
 │  #0  │  #1  │  #2  │  #3  │  #4  │  #5  │
 │──────│──────│──────│──────│──────│──────│
 │  C1  │  C2  │  C1  │  C3  │  C3  │  C2  │
 │  C1  │  C2  │  C2  │  C1  │  C3  │  C2  │
 │  C1  │  C2  │  C2  │  C3  │  C3  │  C2  │
 │  C1  │  C3  │  C1  │  C2  │  C2  │  C3  │
 │  C1  │  C3  │  C3  │  C1  │  C2  │  C3  │
 │  C1  │  C3  │  C3  │  C2  │  C2  │  C3  │
 │  C2  │  C1  │  C1  │  C2  │  C3  │  C1  │
 │  C2  │  C1  │  C1  │  C3  │  C3  │  C1  │
 │  C2  │  C1  │  C2  │  C3  │  C3  │  C1  │
 │  C2  │  C3  │  C2  │  C1  │  C1  │  C3  │
 │  C2  │  C3  │  C3  │  C1  │  C1  │  C3  │
 │  C2  │  C3  │  C3  │  C2  │  C1  │  C3  │
 │  C3  │  C1  │  C1  │  C2  │  C2  │  C1  │
 │  C3  │  C1  │  C1  │  C3  │  C2  │  C1  │
 │  C3  │  C1  │  C3  │  C2  │  C2  │  C1  │
 │  C3  │  C2  │  C2  │  C1  │  C1  │  C2  │
 │  C3  │  C2  │  C2  │  C3  │  C1  │  C2  │
 │  C3  │  C2  │  C3  │  C1  │  C1  │  C2  │
 └─────────────────────────────────────────┘


Input: Graph [edges = [(0, 1), (0, 2), (0, 3), (1, 2), (1, 3), (2, 3)], n = 4], k = 3

 0 ───── 1
 │ \   / │
 │   X   │
 │ /   \ │
 2 ───── 3

Output: false
Explanation: The graph cannot be colored using 3 colors. The graph is 4–colorable.


Constraints:

• The graph is implemented using an adjacency list.
• The maximum number of nodes in the graph is 100, i.e., 0 <= n < 100, and each node is represented by its numeric value.
• The graph is connected, i.e., every node can be reached starting from all other nodes.

*/

class Solution {
	/*
		// Definition for a Graph
		class Graph
		{
			// List of lists to represent an adjacency list
			List<List<Integer>> adjList;

			// Total number of nodes in the graph
			int n;
		}
	*/

    public static List<int[]> kColourConfigurations(int n, int[][] edges, int k) {
        List<int[]> result = new ArrayList<>();
        int[] buffer = new int[n];

        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }

        kColourConfigurations(0, k, result, buffer, adjacencyList);
        return result;
    }

    private static void kColourConfigurations(int n, int k, List<int[]> result, int[] buffer, List<List<Integer>> adjacencyList) {
        if (n >= buffer.length) { // All nodes coloured
            result.add(Arrays.copyOf(buffer, buffer.length));
            return;
        }

        if (buffer[n] != 0) { // Already coloured
            return;
        }

        for (int colour = 1; colour <= k; colour++) {
            if (canColourNode(n, colour, buffer, adjacencyList)) {
                buffer[n] = colour;
                kColourConfigurations(n + 1, k, result, buffer, adjacencyList);
                buffer[n] = 0;
            }
        }
    }

    private static boolean canColourNode(int node, int colour, int[] colours, List<List<Integer>> adjacencyList) {
        for (Integer adjacentNode : adjacencyList.get(node)) {
            if (colours[adjacentNode] == colour) {
                return false;
            }
        }
        return true;
    }
}
