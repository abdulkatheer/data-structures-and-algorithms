package io.abdul.recursion.practice.problem4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathWithDistances {
    public static void main(String[] args) {
        List<int[]> path = Solution.findPath(new int[][]
                {
                        {7, 1, 3, 5, 3, 6, 1, 1, 7, 5},
                        {2, 3, 6, 1, 1, 6, 6, 6, 1, 2},
                        {6, 1, 7, 2, 1, 4, 7, 6, 6, 2},
                        {6, 6, 7, 1, 3, 3, 5, 1, 3, 4},
                        {5, 5, 6, 1, 5, 4, 6, 1, 7, 4},
                        {3, 5, 5, 2, 7, 5, 3, 4, 3, 6},
                        {4, 1, 4, 3, 6, 4, 5, 3, 2, 6},
                        {4, 4, 1, 7, 4, 3, 3, 1, 4, 2},
                        {4, 4, 5, 1, 5, 2, 3, 5, 3, 5},
                        {3, 6, 3, 5, 2, 2, 6, 4, 2, 1}
                }
        );
        print(path);
    }

    private static void print(List<int[]> path) {
        for (int[] dir : path) {
            System.out.print(Arrays.toString(dir) + "; ");
        }
    }
}

class Solution {

    public static List<int[]> findPath(int[][] mat) {
        List<int[]> path = new ArrayList<>();
        findMath(mat, 0, 0, path);
        return path;
    }

    private static boolean findMath(int[][] mat, int row, int column, List<int[]> path) {
        if (row < 0 || row >= mat.length || column < 0 || column >= mat[0].length) { // Invalid path
            return false;
        }

        if (mat[row][column] == -1) { // Already visited
            return false;
        }

        path.add(new int[]{row, column});

        if (row == mat.length - 1 && column == mat[0].length - 1) { // Reached destination
            return true;
        }

        int temp = mat[row][column];
        mat[row][column] = -1;

        // Left
        if (findMath(mat, row, column - temp, path)) {
            return true;
        }

        // Up
        if (findMath(mat, row - temp, column, path)) {
            return true;
        }

        // Right
        if (findMath(mat, row, column + temp, path)) {
            return true;
        }

        // Down
        if (findMath(mat, row + temp, column, path)) {
            return true;
        }

        mat[row][column] = temp; // Backtrack and try different path
        path.remove(path.size() - 1);
        return false;
    }
}
