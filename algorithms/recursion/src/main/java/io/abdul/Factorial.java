package io.abdul;

/**
 * 5! = 1 x 2 x 3 x 4 x 5 = 120 </p>
 * n! = n x n-1 x n-2 ... till n = 1 </p>
 * <p>
 * Recursive function: </p>
 * f(n) = n * f(n-1) where n >= 1 </p>
 * <p>
 * Time taken: </p>
 * T(n) = 1, where n = 0 </p>
 * T(n) = T(n-1) + 1, where n > 0 </p>
 * <p>
 * <p>
 * T(n) = T(n-1) + 1 </p>
 * T(n) = T(n-2) + 2 </p>
 * T(n) = T(n-3) + 3 </p>
 * Let constant be k </p>
 * T(n) = T(n-k) + k </p>
 * Let k be n </p>
 * T(n) = T(n-n) + k </p>
 * T(n) = 1 + k </p>
 * T(n) = O(n) - Linear time
 */
public class Factorial {
    private static int factorial(int n) {
        if (n <= 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
}
