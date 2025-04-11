package io.abdul;

import java.util.HashMap;
import java.util.Stack;

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
    // Step 1: Solve it with recursion (Top-down)
    /*
    Time - O(2^n)
    Space - O(2^n)
     */
    private static int fibonacciStep1(int n) { // O(2^n)
        if (n == 0 || n == 1) {
            return n;
        }
        return fibonacciStep1(n - 1) + fibonacciStep1(n - 2);
    }

    // Step 2: Convert to iterative solution (Top-down)
    private static int fibonacciStep2(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        // Mimicking the call-stack
        // To find n, replaces it with n-1 and n-2
        // Again to find n-1, replaces it with n-1 and n-2, goes on until we get 0
        // Again to find n-2, replaces it with n-1 and n-2, goes on until we get 0
        Stack<int[]> computeStack = new Stack<>();
        computeStack.push(new int[]{n, 0}); // n, sum

        int result = 0;
        while (!computeStack.empty()) {
            int[] toCompute = computeStack.pop();
            int num = toCompute[0];
            if (num == 0 || num == 1) { // Solvable sub-problems
                result += num;
            } else { // Unsolvable sub-problems, so add to stack
                computeStack.push(new int[]{num - 1, 0});
                computeStack.push(new int[]{num - 2, 0});
            }
        }
        return result;
    }

    // Step 3: Iterative solution with Memoization (Top-down)
    // Top to bottom approach, we started to find value of n and then boiled down to known solutions 0 and 1
    /*
    n=5
    itr=1 -> 3 4 5
    itr=2 -> 1 2 3 4 5
    itr=3 -> 2 3 4 5 | 1=1
    itr=4 -> 0 2 3 4 5 | 1=1
    itr=5 -> 2 3 4 5 | 1=1, 0=0
    itr=6 -> 3 4 5 | 1=1, 0=0, 2=1
    itr=7 -> 4 5 | 1=1, 0=0, 2=1, 3=2
    itr=8 -> 5 | 1=1, 0=0, 2=1, 3=2, 4=3
    itr=9 ->  | 1=1, 0=0, 2=1, 3=2, 4=3, 5=5
     */
    private static int fibonacciStep3(int n) {
        if (n == 0 || n == 1) {
            return n;
        }

        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> memo = new HashMap<>();

        memo.put(0, 0);
        memo.put(1, 1);

        stack.push(n);

        while (!stack.isEmpty()) {
            int num = stack.peek();

            if (memo.containsKey(num)) {
                stack.pop();
            } else if (memo.containsKey(num - 1) && memo.containsKey(num - 2)) {
                // Both dependencies are computed, now we can compute fib(num)
                memo.put(num, memo.get(num - 1) + memo.get(num - 2));
                stack.pop();
            } else {
                // Push dependencies onto the stack
                if (!memo.containsKey(num - 1)) {
                    stack.push(num - 1);
                }
                if (!memo.containsKey(num - 2)) {
                    stack.push(num - 2);
                }
            }
        }

        return memo.get(n);
    }

    // Step 4: Iterative + Tabulation (Bottom-up)
    // Bottom to top approach -> Building solution from the known solution 0 and 1
    private static int fibonacciStep4(int n) {
        int[] fibonacci = new int[n + 1];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci[n];
    }

    // Step 5: Iterative + Tabulation (Space optimized) (Bottom-up)
    // We only need last and last to last element for any calculation and not the entire result set
    private static int fibonacciStep5(int n) {
        int[] fibonacci = new int[2];
        fibonacci[0] = 0;
        fibonacci[1] = 1;

        for (int i = 2; i <= n; i++) {
            int currentRes = fibonacci[0] + fibonacci[1];
            fibonacci[0] = fibonacci[1];
            fibonacci[1] = currentRes;
        }
        return fibonacci[1];
    }

    public static void main(String[] args) {
        System.out.println(fibonacciStep1(10));
        System.out.println(fibonacciStep2(10));
        System.out.println(fibonacciStep3(10));
        System.out.println(fibonacciStep4(10));
        System.out.println(fibonacciStep5(10));
    }
}
