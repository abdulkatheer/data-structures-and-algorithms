package io.abdul.patterns.problem3;

// https://takeuforward.org/pattern/pattern-3-right-angled-number-pyramid/
public class RightAngledNumberPyramid {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}
