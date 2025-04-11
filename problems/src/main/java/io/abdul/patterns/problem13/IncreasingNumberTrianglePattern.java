package io.abdul.patterns.problem13;

// https://takeuforward.org/pattern/pattern-13-increasing-number-triangle-pattern/
public class IncreasingNumberTrianglePattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        int count = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(count + " ");
                count++;
            }
            System.out.println();
        }
    }
}
