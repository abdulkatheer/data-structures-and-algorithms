package io.abdul.patterns.problem19;

// https://takeuforward.org/pattern/pattern-19-symmetric-void-pattern/
public class SymmetricVoidPattern {
    public static void main(String[] args) {
        pattern(2);
    }

    /*
    i=0
    a=0 to a=4 star
    b=5 Nil
    c=0 to c=4 space
    c=5Nil
     */
    private static void pattern(int n) {
        printInverted(n);
        printNormal(n);
    }

    private static void printNormal(int n) {
        for (int i = 0; i < n; i++) {
            for (int a = 0; a < i + 1; a++) {
                System.out.print("*");
            }
            for (int b = i + 1; b < n; b++) {
                System.out.print(" ");
            }
            for (int a = 0; a < n - i - 1; a++) {
                System.out.print(" ");
            }
            for (int b = n - i - 1; b < n; b++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    private static void printInverted(int n) {
        for (int i = 0; i < n; i++) {
            for (int a = 0; a < n - i; a++) {
                System.out.print("*");
            }
            for (int b = n - i; b < n; b++) {
                System.out.print(" ");
            }
            for (int a = 0; a < i; a++) {
                System.out.print(" ");
            }
            for (int b = i; b < n; b++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
