package io.abdul.recursion.basic_recursion.problem6;

public class PrimeCheck {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.checkPrime(1));
        System.out.println(solution.checkPrime(2));
        System.out.println(solution.checkPrime(3));
        System.out.println(solution.checkPrime(4));
        System.out.println(solution.checkPrime(5));
        System.out.println(solution.checkPrime(6));
        System.out.println(solution.checkPrime(7));
        System.out.println(solution.checkPrime(8));
        System.out.println(solution.checkPrime(9));
        System.out.println(solution.checkPrime(10));
        System.out.println(solution.checkPrime(41));
    }
}

class Solution {
    public boolean checkPrime(int num) {
        return checkPrime(num, 2, (int) Math.sqrt(num));
    }

    public boolean checkPrime(int num, int divisor, int sqrt) {
        if (num < 2) {
            return false;
        }
        if (divisor > sqrt) { // Reached till sqrt, so divisions will repeat after that. It's a prime then!
            return true;
        }
        if (num % divisor == 0) { // Divided, so not a prime!
            return false;
        }
        return checkPrime(num, divisor + 1, sqrt);
    }
}
