package io.abdul.recursion.faqs_hard.problem4;

import java.util.ArrayList;
import java.util.List;

// https://takeuforward.org/plus/dsa/recursion/faqs-hard/rat-in-a-maze
public class RateMaze {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findPath(new int[][]{{1, 0, 0, 0}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}}));
        System.out.println(solution.findPath(new int[][]{{1, 0}, {1, 0}}));
    }
}

/*
At any given position, 4 directions are possible
Base case - Invalid position, blocked, destination

00 01 02
10 11 12
20 21 22
 */
class Solution {
    public List<String> findPath(int[][] grid) {
        List<String> result = new ArrayList<>();
        findPath(grid, 0, 0, new StringBuilder(), result);
        return result;
    }

    private void findPath(int[][] grid, int r, int c, StringBuilder buffer, List<String> result) {

        if (c < 0 || c >= grid.length || r < 0 || r >= grid.length) { // Invalid position
            return;
        }

        if (grid[r][c] == 0) { // Blocked
            return;
        }

        if (grid[r][c] == -1) { // Visited
            return;
        }

        if (c == grid.length - 1 && r == grid.length - 1) { // Success
            result.add(buffer.toString());
            return;
        }

        grid[r][c] = -1;

        // Left
        buffer.append('L');
        findPath(grid, r, c - 1, buffer, result);
        buffer.deleteCharAt(buffer.length() - 1);

        // Up
        buffer.append('U');
        findPath(grid, r - 1, c, buffer, result);
        buffer.deleteCharAt(buffer.length() - 1);

        // Right
        buffer.append('R');
        findPath(grid, r, c + 1, buffer, result);
        buffer.deleteCharAt(buffer.length() - 1);

        // Down
        buffer.append('D');
        findPath(grid, r + 1, c, buffer, result);
        buffer.deleteCharAt(buffer.length() - 1);

        grid[r][c] = 1;
    }
}