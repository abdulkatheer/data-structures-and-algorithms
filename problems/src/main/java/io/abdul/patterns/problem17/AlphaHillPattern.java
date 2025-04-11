package io.abdul.patterns.problem17;

// https://takeuforward.org/pattern/pattern-17-alpha-hill-pattern/
public class AlphaHillPattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            int spaces = n - i - 1;
            for (int j = 0; j < spaces; j++) {
                System.out.print(" ");
            }
            int alphas = 2 * i + 1;
            int ordered = alphas / 2 + 1;
            for (int j = 0; j < ordered; j++) {
                System.out.print((char) (65 + j));
            }
            int reversed = alphas - ordered - 1;
            for (int j = reversed; j >= 0; j--) {
                System.out.print((char) (65 + j));
            }
            System.out.println();
        }
    }
}
