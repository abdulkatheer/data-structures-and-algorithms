package io.abdul.recursion.practice.problem1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// https://www.techiedelight.com/?problem=KnightTourProblem
public class KnightTour {
    public static void main(String[] args) {
        System.out.println(Solution.knightTours(1).size());
        System.out.println(Solution.knightTours(2).size());
        System.out.println(Solution.knightTours(3).size());
        System.out.println(Solution.knightTours(4).size());
        System.out.println(Solution.knightTours(5).size());
        System.out.println(Solution.knightTours(6).size());
        System.out.println(Solution.knightTours(7).size());
        System.out.println(Solution.knightTours(8).size());
    }
}

class Solution {
    public static Set<String> knightTours(int n) {
        Set<String> result = new HashSet<>();
        int[][] board = new int[n][n];
        knightTours(1, 0, 0, board, result);
        return result;
    }

    /*
    00 01 02 03 04
    10 11 12 13 14
    20 21 22 23 24
    30 31 32 33 34
    40 41 42 43 44
    50 51 52 53 54

    32 ->
    Up left 11 [r-2,c-1], Up right 13[r-2,c+1],
    Right up 24[r-1,c+2], Right down 44[r+1,c+2],
    Left up 20[r-1,c-2], Left down 40[r+1,c-2],
    Down Left 51[r+2,c-1], Down right 53[r+2,c+1]

     */
    private static void knightTours(int n, int row, int column, int[][] board, Set<String> result) {
        if (n > board.length * board.length) {
            result.add(Arrays.deepToString(board));
            return;
        }

        if (row < 0 || row >= board.length || column < 0 || column >= board.length) { // Invalid position
            return;
        }

        if (board[row][column] != 0) { // Visited already
            return;
        }

        board[row][column] = n;

        // Up left
        knightTours(n + 1, row - 2, column - 1, board, result);

        // Up right
        knightTours(n + 1, row - 2, column + 1, board, result);

        // Right up
        knightTours(n + 1, row - 1, column + 2, board, result);

        // Right down
        knightTours(n + 1, row + 1, column + 2, board, result);

        // Left up
        knightTours(n + 1, row - 1, column - 2, board, result);

        // Left down
        knightTours(n + 1, row + 1, column - 2, board, result);

        // Down Left
        knightTours(n + 1, row + 2, column - 1, board, result);

        // Down right
        knightTours(n + 1, row + 2, column + 1, board, result);

        board[row][column] = 0; // Backtrack and try different path
    }
}

