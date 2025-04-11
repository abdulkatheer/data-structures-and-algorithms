package io.abdul.types;

public class NestedRecursion {
    public static void main(String[] args) {
        System.out.println(fun(30));
    }

    /*
    T(n)=T(T(n+11)) for n <= 100
    T(n)=O(1) for n > 100
     */
    private static int fun(int n) {
        if (n > 100) {
            return n - 10;
        }
        return fun(fun(n + 11));
    }
}
