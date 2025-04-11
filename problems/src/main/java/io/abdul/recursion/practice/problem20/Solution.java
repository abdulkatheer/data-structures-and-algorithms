package io.abdul.recursion.practice.problem20;

// https://leetcode.com/problems/count-good-numbers/

/**
 * Possible digits -> 0 to 9
 * Even -> 0, 2, 4, 6, 8
 * Prime -> 2, 3, 5, 7
 * n - 1
 * <p>
 * n + 1 / 2 = 2/2 = 1 even
 * n - 1 even = 0 odd
 * 5^1 even * 4^0 odd (0) = 5
 * <p>
 * n - 5
 * 5 + 1 / 2 = 3 even
 * 5 - 3 even = 2 odd
 * <p>
 * 5^3even * 4^2 odd = 2000
 * <p>
 * n - 4
 * 2 even
 * 2 odd
 * 5^2 * 4^2 = 400
 * <p>
 * Modular Exponentiation - using binary approach
 * <p>
 * Read Modular Arithmetic rules!
 */
// #tag_recursion
class Solution {
    private static final long MOD = 1_000_000_007L;

    public int countGoodNumbers(long n) {
        long even = (n + 1) / 2;
        long odd = n / 2;
        long e = pow(5, even) % MOD;
        long o = pow(4, odd) % MOD;
        long r = (e * o) % MOD;
        return (int) r;
    }

    private static long pow(long x, long n) {
        if (n == 0) {
            return 1;
        }
        long r = pow(x, n / 2);
        if (n % 2 == 0) {
            return (r * r) % MOD;
        } else {
            return (x * r * r) % MOD;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countGoodNumbers(Long.MAX_VALUE));
    }
}