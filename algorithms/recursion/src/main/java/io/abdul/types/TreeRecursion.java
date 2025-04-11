package io.abdul.types;

public class TreeRecursion {
    public static void main(String[] args) {
        fun(3);
    }

    /*
    T - O(2^n)
    S - O(n)
    Recurrence relation => T(n) = T(n-1) + T(n-1) + O(1) = 2T(n-1) + O(1) -- (1)
    T(n) = 2 (2T(n-1) + O(1) - 1) + O(1) = 4T(n-2) + 2O(1) -- (2)
    T(n) = 4 (2T(n-1) + O(1) - 2 + 2O(1) = 8T(n-3) + 3O(1) -- (3)
    T(n) = 2^kT(n-k) + kO(1)
    Let n-k=0, n=k
    T(n) = 2^n T(0) + O(n)
    T(n) = 2^n + O(n)
    T(n) = O(2^n)
     */
    private static void fun(int n) {
        if (n <= 0) {
            return;
        }
        System.out.print(n + " ");
        fun(n - 1);
        fun(n - 1);
    }
}
