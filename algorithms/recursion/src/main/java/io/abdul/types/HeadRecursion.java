package io.abdul.types;

public class HeadRecursion {
    public static void main(String[] args) {
        fun(10);
    }

    /*
    T - O(n)
    S - O(n)
    Recurrence Relation => T(n) = T(n-1) + O(1) -- (1)
    T(n) = T(n-1-1) + O(1) + O(1) = T(n-2) + 2O(1) -- (2)
    T(n) = T(n-1-2) + O(1)+ 2O(1) = T(n-3) + 3O(1) -- (3)
    T(n) = T(n-k) + kO(1) -- (4)
    Let n-k=0
    T(n) = T(0) + nO(1)
    T(n) = O(1) + O(n)
    T(n) = O(n)
     */
    private static void fun(int n) {
        if (n <= 0) {
            return;
        }
        System.out.print(n + " ");
        fun(n - 1);
    }
}
