package io.abdul.patterns.problem15;

// https://takeuforward.org/pattern/pattern-15-reverse-letter-triangle-pattern/
public class ReverseLetterTrianglePattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 65; j <= 65 + n - i; j++) {
                System.out.print((char) j);
            }
            System.out.println();
        }
    }
}
