package io.abdul.types;

public class IndirectRecursion {
    public static void main(String[] args) {
        funA(20);
    }

    /*
    T = O(log n)
    S = O(log n)
    Recurrence relation:
    T_a(n) = T_b(n-1) + O(1)
    T_b(n) = T_a(n/2) + O(1)
    T_a(n) = T_a((n−1)/2) + O(1) + O(1) = T_a((n−1)/2) + 2O(1)
    (n−1)/2 ~ n/2, for larger n. So to simplify things, we take it n/2
    2O(1) as constant as it's not tied to n in any ways.

    T(n) = T(n/2) + O(1) -- (1)
    T(n) = T(n/2 * 1/2) + O(1) + O(1) = T(n/4) + 2O(n) -- (2)
    T(n) = T(n/8) + 3O(1) -- (3)
    T(n) = T(n/2^k) + kO(1) -- (4)
    Let n/2^k = 0, so k = log_2 n
    T(n) = T(0) + log_2 n O(1)
    T(n) = O(1) + O(log_2 n)
    T(n) = O(log n)
     */
    private static void funA(int n) {
        if (n <= 0) {
            return;
        }
        System.out.print(n + " ");
        funB(n - 1);
    }

    private static void funB(int n) {
        if (n <= 1) {
            return;
        }
        System.out.print(n + " ");
        funA(n / 2);
    }
}
