package io.abdul.patterns.problem22;

// https://takeuforward.org/pattern/pattern-22-the-number-pattern/
public class NumberPattern {
    public static void main(String[] args) {
        pattern(4);
    }

    private static void pattern(int n) {
        int x = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) { // Decrement
                System.out.print(x + " ");
                x--;
            }
            for (int k = i; k < n; k++) { // Repeat
                System.out.print(x + " ");
            }

            for (int l = i + 1; l < n; l++) { // Repeat
                System.out.print(x + " ");
            }
            for (int m = 0; m < i; m++) { // Increment
                x++;
                System.out.print(x + " ");
            }

            x = n; // reset
            System.out.println();
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                System.out.print(x + " ");
                x--;
            }
            for (int k = n - i - 1; k < n; k++) {
                System.out.print(x + " ");
            }

            for (int l = n - i; l < n; l++) {
                System.out.print(x + " ");
            }
            for (int m = 0; m < n - i - 1; m++) {
                x++;
                System.out.print(x + " ");
            }

            x = n; // reset
            System.out.println();
        }
    }
}
