package io.abdul.recursion.faqs_hard.problem3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NQueen {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        printMatrix(solution.solveNQueens(4));
        printMatrix(solution.solveNQueens(5));
    }

    private static void printMatrix(List<List<String>> matrix) {
        for (List<String> row : matrix) {
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}

/*
00 01 02 03

10 11 12 13

20 21 22 23

30 31 32 33


00
Vertical, 0 to j-1
Horizontal, 0 to i-1
Diagonal, r-1 c-1 until  r or c < 0 and r+1 c+1 until r or c >= n
 */
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }
        solveNQueens(n, board, 0, result);
        return result;
    }

    private void solveNQueens(int queens, char[][] board, int row, List<List<String>> result) {
        if (queens == 0) {
            List<String> solution = new ArrayList<>();
            for (char[] chars : board) {
                solution.add(new String(chars));
            }
            result.add(solution);
            return;
        }

        boolean atLeastOneOptionAvailable = false;
        for (int column = 0; column < board.length; column++) {
            boolean canPlaceQueen = canPlaceQueen(board, row, column);
            atLeastOneOptionAvailable = atLeastOneOptionAvailable || canPlaceQueen;
            if (canPlaceQueen) {
                board[row][column] = 'Q';
                solveNQueens(queens - 1, board, row + 1, result);
                board[row][column] = '.';
            }
        }
    }

    // Can just check in the upward rows, downwards is not necessary as there'll be no queens there
    // Horizontal is not needed as anyway one queen can be placed in a row
    private boolean canPlaceQueen(char[][] board, int row, int column) {
        // Vertical upwards
        for (int r = row - 1; r >= 0; r--) {
            if (board[r][column] == 'Q') {
                return false;
            }
        }

        // Upward main diagonal (r--, c--)
        for (int r = row - 1, c = column - 1; r >= 0 && c >= 0; r--, c--) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }
        // Upward anti diagonal (r--, c++)
        for (int r = row - 1, c = column + 1; r >= 0 && c < board.length; r--, c++) {
            if (board[r][c] == 'Q') {
                return false;
            }
        }

        return true;
    }
}

/*
Above solution has more compute as it spend 3n times to check if a queen can be placed, What if we can do it in constant time?

Idea behind this is some truths and 2d matrix number tricks
1) Only one queen can be placed in a column. So we can maintain an array of size n to keep track of Queens placed in a column or not.
2) Only one queen can be placed in the main diagonal of an element. row + column of main diagonal positions will always be the same.
So we need to maintain 2n - 1 sized array and keep queens state for the main diagonal in row+column terms.
3) Only one queen can be placed in the anti-diagonal of an element. All anti-diagonal positions will have value equal to (n-1) + (column - row).
So we need to maintain 2n - 1 sized array and keep queens state for the anti-diagonal in (n-1) + (column - row) terms.

 */
class Solution2 {
    public List<List<String>> solveNQueens(int n) {
        boolean[] columnState = new boolean[n];
        boolean[] mainDiagonalState = new boolean[2 * n - 1];
        boolean[] antiDiagonalState = new boolean[2 * n - 1];
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];

        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        solveNQueens(n, board, 0, columnState, mainDiagonalState, antiDiagonalState, result);
        return result;
    }

    private void solveNQueens(int queens, char[][] board, int row, boolean[] columnState, boolean[] mainDiagonalState,
                              boolean[] antiDiagonalState, List<List<String>> result) {
        if (queens == 0) {
            List<String> solution = new ArrayList<>();
            for (char[] r : board) {
                solution.add(new String(r));
            }
            result.add(solution);
            return;
        }

        if (row >= board.length) {
            return;
        }

        for (int column = 0; column < board.length; column++) {
            if (!columnState[column] && !mainDiagonalState[row + column] && !antiDiagonalState[board.length - 1 + column - row]) {
                board[row][column] = 'Q';
                columnState[column] = true;
                mainDiagonalState[row + column] = true;
                antiDiagonalState[board.length - 1 + column - row] = true;
                solveNQueens(queens - 1, board, row + 1, columnState, mainDiagonalState, antiDiagonalState, result);
                board[row][column] = '.';
                columnState[column] = false;
                mainDiagonalState[row + column] = false;
                antiDiagonalState[board.length - 1 + column - row] = false;
            }
        }
    }
}
