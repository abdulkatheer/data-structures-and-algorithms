package io.abdul.recursion.practice.problem5;

// https://www.techiedelight.com/find-total-number-unique-paths-maze-source-destination/
public class MatrixPathIII {
    public static void main(String[] args) {
        System.out.println(Solution.countPaths(new int[][]
                {
                        {1, 1, 1, 1},
                        {1, 1, 0, 1},
                        {0, 1, 0, 1},
                        {1, 1, 1, 1}
                }, 0, 0, 3, 3));
    }
}

/*

Given a `N × N` binary matrix, find the total number of unique paths that a robot can take to reach a given destination from a given source.

Positions in the matrix will either be open or blocked with an obstacle. The value 0 represents a blocked cell and 1 represents an open cell. The robot can only move to positions without obstacles, i.e., the solution should find paths that contain only open cells. Retracing the one or more cells back and forth is not considered a new path. The set of all cells covered in a single path should be unique from other paths. At any given moment, the robot can only move one step in either of the four directions (Top, Down, Left, Right).

Input:

matrix = [
	[1, 1, 1, 1],
	[1, 1, 0, 1],
	[0, 1, 0, 1],
	[1, 1, 1, 1]
]

src  = (0, 0)
dest = (3, 3)

Output: 4

Explanation: The above matrix contains 4 unique paths from source to destination (Marked with x).

[x  x  x  x]	[x  x  1  1]	[x  1  1  1]	[x  x  x  x]
[1  1  0  x]	[1  x  0  1]	[x  x  0  1]	[x  x  0  x]
[0  1  0  x]	[0  x  0  1]	[0  x  0  1]	[0  1  0  x]
[1  1  1  x]	[1  x  x  x]	[1  x  x  x]	[1  1  1  x]

*/

class Solution {

    public static int countPaths(int[][] mat, int srcRow, int srcColumn, int destRow, int destColumn) {
        return count(mat, srcRow, srcColumn, destRow, destColumn);
    }

    private static int count(int[][] mat, int r, int c, int destRow, int destColumn) {
        if (r == destRow && c == destColumn) { // Met the end
            return 1;
        }

        if (r < 0 || r >= mat.length || c < 0 || c >= mat[0].length) { // Not a valid path
            return 0;
        }

        if (mat[r][c] == -1) { // Already visited
            return 0;
        }

        if (mat[r][c] == 0) { // Blocked
            return 0;
        }

        mat[r][c] = -1;
        int count = 0;

        // Left
        count += count(mat, r, c - 1, destRow, destColumn);

        // Up
        count += count(mat, r - 1, c, destRow, destColumn);

        // Right
        count += count(mat, r, c + 1, destRow, destColumn);

        // Down
        count += count(mat, r + 1, c, destRow, destColumn);

        mat[r][c] = 1; // Backtrack and explore other paths

        return count;
    }
}