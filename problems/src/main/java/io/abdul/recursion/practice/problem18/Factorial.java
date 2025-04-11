package io.abdul.recursion.practice.problem18;

public class Factorial {
    public static void main(String[] args) {
        System.out.println(factorial(10));
        System.out.println(factorialItr(10));

        System.out.println(factorial(30));
        System.out.println(factorialItr(30));
    }

    /*
    T - O(n)
    S - O(n)
    Recurrence relation: T(n) = T(n-1) + O(1) -- (1)
    T(n) = T(n-2) + 2O(1) -- (2)
    T(n) = T(n-3) + 3O(1) -- (3)
    T(n) = T(n-k) + kO(1) -- (4)
    Let n-k = 1, k = n-1
    T(n) = T(1) + (n-1)O(1)
    T(n) = O(n)
     */
    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private static int factorialItr(int n) {
        if (n == 0) {
            return 1;
        }
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
