package io.abdul.recursion.practice.problem6;

// https://leetcode.com/problems/unique-paths-iii/description/
public class UniquePathsIII {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));
    }
}

class Solution {
    public int uniquePathsIII(int[][] grid) {
        int srcRow = -1, srcColumn = -1, destRow = -1, destColumn = -1;

//        int[] visited = new int[grid.length];
        int[] visited = new int[1];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    srcRow = i;
                    srcColumn = j;
                }

                if (grid[i][j] == 2) {
                    destRow = i;
                    destColumn = j;
                }

                if (grid[i][j] == -1) {
//                    visited[i] += 1;
                    visited[0] += 1;
                }
            }
        }


        return uniquePathsIII(grid, srcRow, srcColumn, destRow, destColumn, visited);
    }

    private int uniquePathsIII(int[][] grid, int r, int c, int destRow, int destColumn, int[] visited) {
        if (r == destRow && c == destColumn) { // Met the end
            grid[r][c] = -1;
//            visited[r] += 1;
            visited[0] += 1;

//            boolean allVisited = true;
//            for (int i = 0; i < visited.length; i++) {
//                if (visited[i] != grid[i].length) {
//                    allVisited = false;
//                    break;
//                }
//            }

            boolean allVisited = visited[0] == grid.length * grid[0].length;

            grid[r][c] = 0;
//            visited[r] -= 1;
            visited[0] -= 1;
            return allVisited ? 1 : 0;
        }

        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) { // Invalid direction
            return 0;
        }

        if (grid[r][c] == -1) { // Blocked or already visited
            return 0;
        }

        grid[r][c] = -1;
//        visited[r] += 1;
        visited[0] += 1;
        int count = 0;

        // Left
        count += uniquePathsIII(grid, r, c - 1, destRow, destColumn, visited);

        // Right
        count += uniquePathsIII(grid, r, c + 1, destRow, destColumn, visited);

        // Up
        count += uniquePathsIII(grid, r - 1, c, destRow, destColumn, visited);

        // Down
        count += uniquePathsIII(grid, r + 1, c, destRow, destColumn, visited);

        grid[r][c] = 0;
//        visited[r] -= 1;
        visited[0] -= 1;

        return count;
    }
}
