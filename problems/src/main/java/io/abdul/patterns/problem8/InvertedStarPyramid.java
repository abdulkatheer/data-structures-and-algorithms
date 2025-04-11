package io.abdul.patterns.problem8;

// https://takeuforward.org/pattern/pattern-8-inverted-star-pyramid/
public class InvertedStarPyramid {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
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
}
