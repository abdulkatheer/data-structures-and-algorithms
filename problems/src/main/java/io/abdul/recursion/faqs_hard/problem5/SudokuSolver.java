package io.abdul.recursion.faqs_hard.problem5;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/recursion/faqs-hard/sudoko-solver
public class SudokuSolver {
    public static void main(String[] args) {
        Solution solution = new Solution();
        char[][] sudoku = {{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        solution.solveSudoku(sudoku);
        System.out.println(Arrays.deepToString(sudoku));
    }
}

/*
Find a vacant position and try all 9 possible numbers.
So many possibilities!!!
 */
class Solution {
    public void solveSudoku(char[][] board) {
        solveSudoku(board, 0, 0);
    }

    private boolean solveSudoku(char[][] board, int r, int c) {

        // Find next vacant position from given r and c
        int vacantR = -1;
        int vacantC = -1;
        while (r < board.length) {
            while (c < board.length) {
                if (board[r][c] == '.') {
                    vacantR = r;
                    vacantC = c;
                    break;
                }
                c++;
            }
            if (vacantR != -1) {
                break;
            }
            c = 0;
            r++;
        }

        if (vacantR == -1) {
            return true; // successfully filled all
        }

        for (int i = 1; i <= 9; i++) {
            char num = (char) (i + '0');
            if (canPlaceNumber(board, vacantR, vacantC, num)) {
                board[vacantR][vacantC] = num;
                boolean solved = solveSudoku(board, vacantR, vacantC);
                if (solved) {
                    return true;
                }
                board[vacantR][vacantC] = '.';
            }
        }

        return false;
    }

    /*
    00 01 02 03 04 05
    10 11 12 13 14 15
    20 21 22 23 24 25
    30 31 32 33 34 35
    40 41 42 43 44 45
    50 51 52 53 54 55

    14 -> r 0,1,2 c 3,4,5
    52 -> r 3,4,5 c 0,1,2
    20 -> r 0,1,2 c 0,1,2
    13 -> r 0,1,2 c 3,4,5

    1 - 0
    5 - 3
    2 - 0
    4 - 3
    2 - 0
    0 - 0
    3 - 3
     */
    private boolean canPlaceNumber(char[][] board, int r, int c, char number) {
        // In row and column
        for (int i = 0; i < 9; i++) {
            if (board[r][i] == number) {
                return false;
            }
            if (board[i][c] == number) {
                return false;
            }
        }

        // In 3x3 matrix
        int mR = (r / 3) * 3;
        int mC = (c / 3) * 3;
        for (int i = mR; i < mR + 3; i++) {
            for (int j = mC; j < mC + 3; j++) {
                if (board[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }


}
