package io.abdul.maths.basic_maths.problem9;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/check-for-prime-number
public class CheckPrime {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isPrime(1));
        System.out.println(solution.isPrime(2));
        System.out.println(solution.isPrime(3));
        System.out.println(solution.isPrime(4));
        System.out.println(solution.isPrime(5));
        System.out.println(solution.isPrime(2_147_483_629));
    }
}

class Solution {
    public boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }

        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
