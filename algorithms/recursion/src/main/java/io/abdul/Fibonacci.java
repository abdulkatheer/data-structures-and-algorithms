package io.abdul;

import java.util.HashMap;
import java.util.Map;

/**
 * 0 1 1 2 3 5 8 13 ...
 * <p>
 * Recursive function: <p>
 * f(n) = n, where n=0, n=1 <p>
 * f(n) = f(n-1) + f(n-2), where n > 1 <p>
 * <p>
 * Time taken:
 * T(n) = 1, where n = 0 or n = 1 <p>
 * T(n) = T(n-1) + T(n-2), where n > 1 <p>
 * T(n-1) = T(n-2) + T(n-3)
 * T(n-2) = T(n-3) + T(n-4)
 * -- yet to be solved
 * <p>
 * O(2^n) Exponential time
 */
public class Fibonacci {
    private static int fibonacci(int n) { // O(2^n)
        if (n == 0 || n == 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    private static int fibonacciV2(int n) { // O(2^n)
        return fibonacciWithMemoization(n, new HashMap<>());
    }

    private static int fibonacciWithMemoization(int n, Map<Integer, Integer> calculated) { // O(2^n)
        if (n == 0 || n == 1) {
            return n;
        }
        if (calculated.containsKey(n)) {
            return calculated.get(n);
        }
        calculated.put(n, fibonacciWithMemoization(n - 1, calculated) + fibonacciWithMemoization(n - 2, calculated));
        return calculated.get(n);
    }

    private static int fibonacciItr(int n) { // O(n)
        int[] fibonacci = new int[n + 1];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci[n];
    }

    public static void main(String[] args) {
//        System.out.println(fibonacci(500));
        System.out.println(fibonacciItr(500));
        System.out.println(fibonacciV2(500));
    }
}
