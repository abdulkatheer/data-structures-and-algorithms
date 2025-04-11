package io.abdul.patterns.problem18;

// https://takeuforward.org/pattern/pattern-18-alpha-triangle-pattern/
public class AlphaTrianglePattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = n - i; j <= n; j++) {
                System.out.print((char) (j + 65 - 1) + " ");
            }
            System.out.println();
        }
    }
}
