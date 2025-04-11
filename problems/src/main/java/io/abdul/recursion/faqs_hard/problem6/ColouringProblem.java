package io.abdul.recursion.faqs_hard.problem6;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/faqs-hard/m-coloring-problem
public class ColouringProblem {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.graphColoring(new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2}}, 3, 4));
        System.out.println(solution.graphColoring(new int[][]{{0, 1}, {1, 2}, {0, 2}}, 2, 3));
        System.out.println(solution.graphColoring(new int[][]{{0, 1}, {1, 2}, {0, 2}, {2, 3}, {2, 4}, {3, 4}}, 3, 5));

    }
}

class Solution {
    boolean graphColoring(int[][] edges, int m, int n) {
        // Create Adjacency list
        List<List<Integer>> adjacencyList = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }

        int[] colours = new int[n];

        return graphColoring(m, 0, n, colours, adjacencyList);

    }

    boolean graphColoring(int m, int node, int n, int[] colours, List<List<Integer>> adjacencyList) {
        if (node >= n) { // All nodes are coloured
            return true;
        }

        for (int colour = 1; colour <= m; colour++) {
            if (canColour(node, colour, colours, adjacencyList)) {
                colours[node] = colour;
                if (graphColoring(m, node + 1, n, colours, adjacencyList)) {
                    return true;
                }
                colours[node] = 0; // Reset and try diff color
            }
        }

        return false; // When for loop exits with no further options
    }

    private boolean canColour(int node, int colour, int[] colours, List<List<Integer>> adjacencyList) {
        List<Integer> adjacentNodes = adjacencyList.get(node);
        for (Integer adjacentNode : adjacentNodes) {
            if (colours[adjacentNode] == colour) {
                return false;
            }
        }

        return true;
    }

}
