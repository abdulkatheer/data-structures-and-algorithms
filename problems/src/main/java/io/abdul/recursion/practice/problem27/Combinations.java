package io.abdul.recursion.practice.problem27;

public class Combinations {
    public static void main(String[] args) {
        System.out.println(nCr(5, 0));
        System.out.println(nCr(5, 1));
        System.out.println(nCr(5, 2));

        System.out.println(nCrV2(5, 0));
        System.out.println(nCrV2(5, 1));
        System.out.println(nCrV2(5, 2));
    }

    /*
    nCr = n!/ r! * (n-r)!
    T - O(n)
    S - O(1)
     */
    private static int nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /*
    Pascal's Triangle
    nCr = n-1Cr-1 + n-1Cr
     */
    private static int nCrV2(int n, int r) {
        if (r == 0 || n == r) {
            return 1;
        }
        return nCrV2(n - 1, r - 1) + nCrV2(n - 1, r);
    }
}
