package io.abdul.patterns.problem16;

// https://takeuforward.org/pattern/pattern-16-alpha-ramp-pattern/
public class AlphaRampPattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 65; i < 65 + n; i++) {
            for (int j = 65; j <= i; j++) {
                System.out.print((char) i);
            }
            System.out.println();
        }
    }
}
