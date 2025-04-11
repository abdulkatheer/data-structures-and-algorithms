package io.abdul.recursion.implementation_problems.problem1;

public class PowerOfN {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.myPow(2.0000, 10));
        System.out.println(solution.myPow(2.5000, 2));
        System.out.println(solution.myPow(2.0000, -2));
    }
}

class Solution {
    public double myPow(double x, int n) {
        return n < 0 ? 1 / calculatePower(x, -n) : calculatePower(x, n);
    }

    private double calculatePower(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            return calculatePower(x * x, n / 2);
        } else {
            return x * calculatePower(x * x, n / 2);
        }
    }
}
