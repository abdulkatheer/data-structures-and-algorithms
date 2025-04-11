package io.abdul.patterns.problem20;

// https://takeuforward.org/pattern/pattern-20-symmetric-butterfly-pattern/
public class SymmetricButterflyPattern {
    public static void main(String[] args) {
        pattern(6);
    }

    private static void pattern(int n) {
        printNormal(n);
        printInverted(n);
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
        for (int i = 1; i < n; i++) {
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
