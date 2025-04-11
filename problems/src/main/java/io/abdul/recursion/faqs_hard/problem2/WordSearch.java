package io.abdul.recursion.faqs_hard.problem2;

public class WordSearch {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'E'}, {'A', 'D', 'E', 'E'}}, "ABCCED"));
        System.out.println(solution.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED"));
        System.out.println(solution.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "SEE"));
        System.out.println(solution.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCB"));
    }
}

class Solution {
    /*
    T - O(m*n * 4^k) - k is the length of the word, we're traversing in 4 different directions for m*n chars
    S - O(m * n)
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (exist(board, word, i, j, 0, visited)) {
                        return true; // Stop when found, otherwise find next starting letter and repeat search
                    }
                }
            }
        }

        return false; // No match found
    }

    /*
    00 01 02 03
     A  B  C  E
    10 11 12 13
     S  F  C  E
    20 21 22 23
     A  D  E  E

     Word = ABCCED

     Go Left, Up, Right, Down
     */
    private boolean exist(char[][] board, String word, int i, int j, int position, boolean[][] visited) {
        if (position >= word.length()) {
            return true; // Match found
        }

        if (i < 0 || i >= board.length) {
            return false; // Can't move in this direction
        }

        if (j < 0 || j >= board[0].length) {
            return false; // Can't move in this direction
        }

        if (visited[i][j]) {
            return false; // Already visited, can't go this way
        }

        if (board[i][j] != word.charAt(position)) {
            return false; // Char not matched, can't go this direction
        } else { // Char at position matches, let's see further
            visited[i][j] = true;
            boolean exist;

            // Left
            exist = exist(board, word, i, j - 1, position + 1, visited);

            if (exist) {
                return true;
            }

            // Up
            exist = exist(board, word, i - 1, j, position + 1, visited);

            if (exist) {
                return true;
            }

            // Right
            exist = exist(board, word, i, j + 1, position + 1, visited);

            if (exist) {
                return true;
            }

            // Down
            exist = exist(board, word, i + 1, j, position + 1, visited);

            if (exist) {
                return true;
            }

            visited[i][j] = false; // Could not find full match, let's go back
            return false;
        }

    }
}

class Solution2 {
    /*
    T - O(m*n * 4^k) - k is the length of the word, we're traversing in 4 different directions for m*n chars
    S - O(1)
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (exist(board, word, i, j, 0)) {
                        return true; // Stop when found, otherwise find next starting letter and repeat search
                    }
                }
            }
        }

        return false; // No match found
    }

    /*
    00 01 02 03
     A  B  C  E
    10 11 12 13
     S  F  C  E
    20 21 22 23
     A  D  E  E

     Word = ABCCED

     Go Left, Up, Right, Down
     */
    private boolean exist(char[][] board, String word, int i, int j, int position) {
        if (position >= word.length()) {
            return true; // Match found
        }

        if (i < 0 || i >= board.length) {
            return false; // Can't move in this direction
        }

        if (j < 0 || j >= board[0].length) {
            return false; // Can't move in this direction
        }

        if (board[i][j] == '$') {
            return false; // Already visited, can't go this way
        }

        if (board[i][j] != word.charAt(position)) {
            return false; // Char not matched, can't go this direction
        } else { // Char at position matches, let's see further
            char currentChar = board[i][j];
            board[i][j] = '$';
            boolean exist;

            // Left
            exist = exist(board, word, i, j - 1, position + 1);

            if (exist) {
                return true;
            }

            // Up
            exist = exist(board, word, i - 1, j, position + 1);

            if (exist) {
                return true;
            }

            // Right
            exist = exist(board, word, i, j + 1, position + 1);

            if (exist) {
                return true;
            }

            // Down
            exist = exist(board, word, i + 1, j, position + 1);

            if (exist) {
                return true;
            }

            board[i][j] = currentChar; // Could not find full match, let's go back
            return false;
        }

    }
}
