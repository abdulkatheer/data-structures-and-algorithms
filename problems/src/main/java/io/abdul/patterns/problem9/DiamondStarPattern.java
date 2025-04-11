package io.abdul.patterns.problem9;

// https://takeuforward.org/pattern/pattern-9-diamond-star-pattern/
public class DiamondStarPattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        // Combining Star Pyramid and Inverted Star Pyramid
        printStarPyramid(n);
        printInvertedStarPyramid(n);
    }

    private static void printInvertedStarPyramid(int n) {
        for (int i = n - 1; i >= 0; i--) {
            int numOfSpaces = n - i - 1;
            int numOfStars = 2 * i + 1;
            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }
            for (int k = 0; k < numOfStars; k++) {
                System.out.print("*");
            }
            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void printStarPyramid(int n) {
        for (int i = 0; i < n; i++) {
            int numOfSpaces = n - i - 1;
            int numOfStars = 2 * i + 1;

            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }

            for (int k = 0; k < numOfStars; k++) {
                System.out.print("*");
            }

            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
