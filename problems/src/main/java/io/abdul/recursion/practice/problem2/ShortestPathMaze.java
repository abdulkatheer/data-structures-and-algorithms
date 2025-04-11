package io.abdul.recursion.practice.problem2;

import java.util.ArrayList;
import java.util.List;

// https://www.techiedelight.com/find-shortest-path-in-maze/
public class ShortestPathMaze {
    public static void main(String[] args) {
        System.out.println(Solution.findShortestPath(new int[][]{
                {
                        1, 1, 1, 1, 1, 0, 0, 1, 1, 1
                },
                {
                        0, 1, 1, 1, 1, 1, 0, 1, 0, 1
                },
                {
                        0, 0, 1, 0, 1, 1, 1, 0, 0, 1
                },
                {
                        1, 0, 1, 1, 1, 0, 1, 1, 0, 1
                },
                {
                        0, 0, 0, 1, 0, 0, 0, 1, 0, 1
                },
                {
                        1, 0, 1, 1, 1, 0, 0, 1, 1, 0
                },
                {
                        0, 0, 0, 0, 1, 0, 0, 1, 0, 1
                },
                {
                        0, 1, 1, 1, 1, 1, 1, 1, 0, 0
                },
                {
                        1, 1, 1, 1, 1, 0, 0, 1, 1, 1
                },
                {
                        0, 0, 1, 0, 0, 1, 1, 0, 0, 1
                }
        }, 0, 0, 7, 5));
    }
}

class Solution {
	/* The Pair<U, V> class have
		1. Two member variables, first and second.
		2. Factory method `Pair.of(U, V)` for creating its immutable instance.
		3. equals() and hashCode() methods overridden.
	*/

    public static int findShortestPath(int[][] mat, int srcRow, int srcColumn, int destRow, int destColumn) {
        List<Integer> distances = new ArrayList<>();
        findPaths(mat, srcRow, srcColumn, destRow, destColumn, 0, distances);

        if (distances.isEmpty()) {
            return -1;
        }
        int shortest = Integer.MAX_VALUE;

        for (Integer distance : distances) {
            if (distance < shortest) {
                shortest = distance;
            }
        }

        return shortest;
    }

    /*
    00 01 02
    10 11 12
    20 21 22
     */
    private static void findPaths(int[][] mat, int r, int c, int destRow, int destColumn, int distance, List<Integer> distances) {
        if (r == destRow && c == destColumn) {
            distances.add(distance);
            return;
        }

        if (r < 0 || r >= mat.length || c < 0 || c >= mat[0].length) {
            return;
        }

        if (mat[r][c] == 0) { // Blocked
            return;
        }

        if (mat[r][c] == -1) { // Already visited
            return;
        }

        mat[r][c] = -1;

        // Left
        findPaths(mat, r, c - 1, destRow, destColumn, distance + 1, distances);

        // Up
        findPaths(mat, r - 1, c, destRow, destColumn, distance + 1, distances);

        // Right
        findPaths(mat, r, c + 1, destRow, destColumn, distance + 1, distances);

        // Down
        findPaths(mat, r + 1, c, destRow, destColumn, distance + 1, distances);

        mat[r][c] = 1; // Backtrack and try different approaches
    }
}