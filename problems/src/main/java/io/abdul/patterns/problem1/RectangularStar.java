package io.abdul.patterns.problem1;

// https://takeuforward.org/pattern/pattern-1-rectangular-star-pattern/
public class RectangularStar {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
