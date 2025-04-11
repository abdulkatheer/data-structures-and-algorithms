package io.abdul.patterns.problem2;

// https://takeuforward.org/pattern/pattern-2-right-angled-triangle-pattern/
public class RightAngledTriangle {
    public static void main(String[] args) {
        pattern(6);
    }

    // Arithmetic Progression: 1 + 2 + 3 + 4 + 5 + 6 + .... + n
    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
