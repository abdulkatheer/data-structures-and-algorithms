package io.abdul.maths.basic_maths.problem6;

public class Factorial {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.factorial(0));
        System.out.println(solution.factorial(1));
        System.out.println(solution.factorial(2));
        System.out.println(solution.factorial(3));
        System.out.println(solution.factorial(4));
        System.out.println(solution.factorial(5));
        System.out.println(solution.factorial(6));
    }
}

class Solution {
    public int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial = factorial * i;
        }

        return factorial;
    }
}