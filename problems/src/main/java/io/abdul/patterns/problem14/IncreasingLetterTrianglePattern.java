package io.abdul.patterns.problem14;

// https://takeuforward.org/pattern/pattern-14-increasing-letter-triangle-pattern/
public class IncreasingLetterTrianglePattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 65; j < 65 + i; j++) {
                System.out.print((char) j);
            }
            System.out.println();
        }
    }
}
