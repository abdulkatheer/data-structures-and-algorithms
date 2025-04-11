package io.abdul.recursion.practice.problem26;

import java.util.HashMap;

public class Fibonacci {
    public static void main(String[] args) {
        System.out.println(fibonacci(10));
        System.out.println(fibonacci(11));
        System.out.println(fibonacci(12));

        System.out.println(fibonacciV2(10));
        System.out.println(fibonacciV2(11));
        System.out.println(fibonacciV2(12));
    }

    /*
    T - O(2^n)
    S - O(n)
    f(n) = f(n-1) + f(n-2)
     */
    private static int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /*
    Memoization
     */
    private static int fibonacciV2(int n) {
        HashMap<Integer, Integer> mem = new HashMap<>();
        mem.put(0, 0);
        mem.put(1, 1);
        return fibonacciMemoized(n, mem);
    }

    private static int fibonacciMemoized(int n, HashMap<Integer, Integer> mem) {
        Integer f = mem.get(n);
        if (f != null) {
            return f;
        }

        int fibPrev = fibonacciMemoized(n - 1, mem);
        int fibPrevPrev = fibonacciMemoized(n - 2, mem);
        mem.put(n - 1, fibPrev);
        mem.put(n - 2, fibPrevPrev);
        return fibPrev + fibPrevPrev;
    }
}
