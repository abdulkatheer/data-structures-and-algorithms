package io.abdul.patterns.problem12;

// https://takeuforward.org/pattern/pattern-12-number-crown-pattern/
public class NumberCrownPattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 1; i <= n; i++) {
            // Numbers 1, 12, 123 ..
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
            }
            // Spaces for first half
            for (int k = 1; k <= n - i; k++) {
                System.out.print(" ");
            }
            // Spaces for second half
            for (int k = 1; k <= n - i; k++) {
                System.out.print(" ");
            }
            // Numbers 1, 12, 123 ..
            for (int j = i; j >= 1; j--) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}
