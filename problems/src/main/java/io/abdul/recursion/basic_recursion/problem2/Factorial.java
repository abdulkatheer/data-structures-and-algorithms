package io.abdul.recursion.basic_recursion.problem2;

public class Factorial {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.factorial(0));
        System.out.println(solution.factorial(1));
        System.out.println(solution.factorial(2));
        System.out.println(solution.factorial(3));
    }
}

class Solution {
    public long factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}