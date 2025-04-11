package io.abdul.patterns.problem5;

// https://takeuforward.org/pattern/pattern-5-inverted-right-pyramid/
public class InvertedRightPyramid {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            int x = n - i;
            for (int j = 0; j < x; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
