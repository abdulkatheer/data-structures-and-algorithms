package io.abdul.patterns.problem11;

// https://takeuforward.org/pattern/pattern-11-binary-number-triangle-pattern/
public class BinaryNumberTrianglePattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 1; i <= n; i++) {
            boolean current = i % 2 != 0;
            for (int j = 1; j <= i; j++) {
                System.out.print(current ? "1 " : "0 ");
                current = !current;
            }
            System.out.println();
        }
    }
}
