package io.abdul.patterns.problem10;

// https://takeuforward.org/pattern/pattern-10-half-diamond-star-pattern/
public class HalfDiamondStarPattern {
    public static void main(String[] args) {
        pattern(6);
        patternV2(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            // n-1 spaces
            for (int j = 0; j < n - 1; j++) {
                System.out.print(" ");
            }

            // 1,2,3,4,..n stars
            for (int k = 0; k <= i; k++) {
                System.out.print("*");
            }

            System.out.println();
        }

        for (int i = n - 1; i > 0; i--) {
            // n-1 spaces
            for (int j = 0; j < n - 1; j++) {
                System.out.print(" ");
            }

            // n-1, n-2 ... 3,2,1
            for (int k = 0; k < i; k++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    /*
    Total rows = 2*n - 1
    Up to n, print in increasing order. Based on i
    After n, print in decreasing order. Based on i and n. 2*n - i
     */
    private static void patternV2(int n) {
        for (int i = 1; i <= 2 * n - 1; i++) {
            // n-1 spaces

            int stars;
            if (i <= n) {
                stars = i;
            } else {
                stars = 2 * n - i;
            }

            for (int k = 1; k <= stars; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
