package io.abdul.maths.basic_maths.problem10;

public class PrimeCount {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.primeUptoN(1));
        System.out.println(solution.primeUptoN(2));
        System.out.println(solution.primeUptoN(3));
        System.out.println(solution.primeUptoN(4));
        System.out.println(solution.primeUptoN(5));
        System.out.println(solution.primeUptoN(6));
        System.out.println(solution.primeUptoN(Integer.MAX_VALUE));
    }
}

class Solution {
    public int primeUptoN(int n) {
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isPrime(int n) {
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
