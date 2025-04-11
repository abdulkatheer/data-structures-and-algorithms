package io.abdul.recursion.practice.problem29;

import java.util.Arrays;

// https://www.techiedelight.com/magnet-puzzle/
public class MagnetPuzzle {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(Solution.solveMagnetPuzzle(new char[][]{{'L', 'R'}, {'L', 'R'}},
                new int[]{1, -1}, new int[]{-1, -1}, new int[]{-1, -1}, new int[]{1, -1})));
        System.out.println(Arrays.deepToString(Solution.solveMagnetPuzzle(new char[][]{
                {'L', 'R', 'L', 'R', 'T', 'T'},
                {'L', 'R', 'L', 'R', 'B', 'B'},
                {'T', 'T', 'T', 'T', 'L', 'R'},
                {'B', 'B', 'B', 'B', 'T', 'T'},
                {'L', 'R', 'L', 'R', 'B', 'B'}
        }, new int[]{1, -1, -1, 2, 1, -1}, new int[]{2, -1, -1, 2, -1, 3}, new int[]{2, 3, -1, -1, -1}, new int[]{-1, -1, -1, 1, -1})));
    }
}
/*

We are given a set of bipolar magnets, each domino-shaped. The objective is to place magnets on an `M × N` board, which satisfies a set of conditions where both M and N are not odd.

For instance, the following problem has the solution on its right:

https://techiedelight.com/practice/images/Magnet-Puzzle.png

Each `2 × 1` or `1 × 2` grid in the board can contain a magnet or empty. The blank entry will be indicated by X’s, and the magnet will be represented by `+` and `-` (For the positive and negative end, respectively). The digits along the board’s left and top sides represent the count of `+` squares in corresponding rows or columns. Similarly, those along the right and bottom show the total number of `-` signs in particular rows or columns. Rows and columns for which no number is mentioned can have any number of `+` or `-` signs. The puzzle solution must also satisfy the constraint that no two adjacent squares can have the same sign. But diagonally joined squares can have the same sign.

Example 1:

The top[], bottom[], left[], right[] arrays indicates the count of `+` or `-` along the top (+), bottom (-), left (+), and right (-) edges, respectively. The value of -1 indicate any number of `+` or `-` signs.

top[] = [1, -1, -1, 2, 1, -1]
bottom[] = [2, -1, -1, 2, -1, 3]
left[] = [2, 3, -1, -1, -1]
right[] = [-1, -1, -1, 1, -1]

The rules[][] matrix can contain any T, B, L, or R character. T indicates its top end for a vertical slot in the board, and B indicates the bottom end. L indicates the left end, and R indicates the right end for a horizontal slot in the board.

rules[][] =
[
	[L, R, L, R, T, T],
	[L, R, L, R, B, B],
	[T, T, T, T, L, R],
	[B, B, B, B, T, T],
	[L, R, L, R, B, B]
]

Output:
[
	[+, -, +, -, X, -],
	[-, +, -, +, X, +],
	[X, X, +, -, +, -],
	[X, X, -, +, X, +],
	[-, +, X, X, X, -]
]


Example 2:

top[] = [2, -1, -1]
bottom[] = [-1, -1, 2]
left[] = [-1, -1, 2, -1]
right[] = [0, -1, -1, -1]

rules[][] =
[
	[T, T, T],
	[B, B, B],
	[T, L, R],
	[B, L, R]
]

Output:
[
	[+, X, +],
	[-, X, -],
	[+, -, +],
	[-, +, -]
]

The solution should return null if the solution does not exist.

*/

/*
 1 (+)
00 01 1 (-)

10 12

LL RR
LL RR

00,01 + - (Yes), - +, x x (3 possibilities)
10,11 + -, - +, x x

Option (1)
+ -
- + (Yes)

+ -
x x (Yes)

- +
+ - (Yes)

- +
x x (Yes)

x x
+ - (No)

x x
- + (No)



 */
class Solution {

    public static char[][] solveMagnetPuzzle(char[][] rules, int[] top, int[] bottom,
                                             int[] left, int[] right) {
        char[][] puzzle = new char[left.length][top.length];

        for (char[] chars : puzzle) {
            Arrays.fill(chars, 'x');
        }

        solveMagnetPuzzle(rules, top, bottom, left, right,
                new int[top.length], new int[bottom.length], new int[left.length], new int[right.length],
                puzzle, 0, 0);

        return puzzle;
    }

    private static boolean solveMagnetPuzzle(char[][] rules,
                                             int[] top, int[] bottom, int[] left, int[] right,
                                             int[] cTop, int[] cBottom, int[] cLeft, int[] cRight,
                                             char[][] puzzle, int row, int column) {


        if (row >= left.length) { // When meeting a dead-end, a sub-set is met and checking if a sub-set is valid
            return rulesMet(top, left, right, bottom, cTop, cBottom, cLeft, cRight);
        }

        int nextRow = row, nextColumn = column + 1;
        if (nextColumn >= top.length) {
            nextRow = nextRow + 1;
            nextColumn = 0;
        }
        if (rules[row][column] == 'L') {
            if (canPlaceLeftRight(puzzle, row, column, '+', '-')) {
                puzzle[row][column] = '+';
                puzzle[row][column + 1] = '-';
                cLeft[row]++;
                cTop[column]++;
                cRight[row]++;
                cBottom[column + 1]++;
                if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                    return true;
                }
                puzzle[row][column] = 'x';
                puzzle[row][column + 1] = 'x';
                cLeft[row]--;
                cTop[column]--;
                cRight[row]--;
                cBottom[column + 1]--;

            }

            if (canPlaceLeftRight(puzzle, row, column, '-', '+')) {
                puzzle[row][column] = '-';
                puzzle[row][column + 1] = '+';
                cLeft[row]++;
                cTop[column + 1]++;
                cRight[row]++;
                cBottom[column]++;
                if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                    return true;
                }
                puzzle[row][column] = 'x';
                puzzle[row][column + 1] = 'x';
                cLeft[row]--;
                cTop[column + 1]--;
                cRight[row]--;
                cBottom[column]--;
            }

            if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                return true;
            }
        } else if (rules[row][column] == 'T') {
            if (canPlaceTopBottom(puzzle, row, column, '+', '-')) {
                puzzle[row][column] = '+';
                puzzle[row + 1][column] = '-';
                cLeft[row]++;
                cTop[column]++;
                cRight[row + 1]++;
                cBottom[column]++;
                if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                    return true;
                }
                puzzle[row][column] = 'x';
                puzzle[row + 1][column] = 'x';
                cLeft[row]--;
                cTop[column]--;
                cRight[row + 1]--;
                cBottom[column]--;
            }

            if (canPlaceTopBottom(puzzle, row, column, '-', '+')) {
                puzzle[row][column] = '-';
                puzzle[row + 1][column] = '+';
                cLeft[row + 1]++;
                cTop[column]++;
                cRight[row]++;
                cBottom[column]++;
                if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                    return true;
                }
                puzzle[row][column] = 'x';
                puzzle[row + 1][column] = 'x';
                cLeft[row + 1]--;
                cTop[column]--;
                cRight[row]--;
                cBottom[column]--;
            }

            if (solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn)) {
                return true;
            }
        } else {
            return solveMagnetPuzzle(rules, top, bottom, left, right, cTop, cBottom, cLeft, cRight, puzzle, nextRow, nextColumn);
        }
        return false;
    }

    private static boolean rulesMet(int[] top, int[] left, int[] right, int[] bottom,
                                    int[] cTop, int[] cBottom, int[] cLeft, int[] cRight) {
        for (int i = 0; i < top.length; i++) {
            if (top[i] != -1 && top[i] != cTop[i]) {
                return false;
            }
            if (bottom[i] != -1 && bottom[i] != cBottom[i]) {
                return false;
            }
        }
        for (int i = 0; i < left.length; i++) {
            if (left[i] != -1 && left[i] != cLeft[i]) {
                return false;
            }
            if (right[i] != -1 & right[i] != cRight[i]) {
                return false;
            }
        }
        return true;
    }

    // 00 01
    private static boolean canPlaceLeftRight(char[][] puzzle, int srcRow, int srcColumn, char firstPole, char secondPole) {
        // Top
        if (srcRow - 1 >= 0 && puzzle[srcRow - 1][srcColumn] == firstPole) {
            return false;
        }

        // Left
        if (srcColumn - 1 >= 0 && puzzle[srcRow][srcColumn - 1] == firstPole) {
            return false;
        }

        // Bottom
        if (srcRow + 1 < puzzle.length && puzzle[srcRow + 1][srcColumn] == firstPole) {
            return false;
        }

        srcColumn++; // Next cell for other sign

        // Top
        if (srcRow - 1 >= 0 && puzzle[srcRow - 1][srcColumn] == secondPole) {
            return false;
        }

        // Right
        if (srcColumn + 1 < puzzle[0].length && puzzle[srcRow][srcColumn + 1] == secondPole) {
            return false;
        }

        // Bottom
        if (srcRow + 1 < puzzle.length && puzzle[srcRow + 1][srcColumn] == secondPole) {
            return false;
        }

        return true;
    }

    private static boolean canPlaceTopBottom(char[][] puzzle, int srcRow, int srcColumn, char firstPole, char secondPole) {
        // Top
        if (srcRow - 1 >= 0 && puzzle[srcRow - 1][srcColumn] == firstPole) {
            return false;
        }

        // Left
        if (srcColumn - 1 >= 0 && puzzle[srcRow][srcColumn - 1] == firstPole) {
            return false;
        }

        // Right
        if (srcColumn + 1 < puzzle[0].length && puzzle[srcRow][srcColumn + 1] == firstPole) {
            return false;
        }

        srcRow++; // Next cell for other sign

        // Bottom
        if (srcRow + 1 < puzzle.length && puzzle[srcRow + 1][srcColumn] == secondPole) {
            return false;
        }

        // Left
        if (srcColumn - 1 >= 0 && puzzle[srcRow][srcColumn - 1] == secondPole) {
            return false;
        }

        // Right
        if (srcColumn + 1 < puzzle[0].length && puzzle[srcRow][srcColumn + 1] == firstPole) {
            return false;
        }

        return true;
    }
}
