package io.abdul.patterns.problem6;

// https://takeuforward.org/pattern/pattern-6-inverted-numbered-right-pyramid/
public class InvertedNumberedRightPyramid {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        for (int i = 0; i < n; i++) {
            int x = n - i;
            for (int j = 1; j <= x; j++) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}
