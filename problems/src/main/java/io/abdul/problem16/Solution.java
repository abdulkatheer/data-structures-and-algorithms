package io.abdul.problem16;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/count-collisions-of-monkeys-on-a-polygon/
// Tags: Math, Recursion

/**
 * Math - Fast Exponentiation with Modulo
 * https://en.wikipedia.org/wiki/Modular_exponentiation
 */
class Solution {
    static int mod = 1_000_000_007;
//    public int monkeyMove(int n) {
//        HashMap<String, Long> container = new HashMap<>();
//        container.put("result", 0L);
//        combinations(n, 0, n, container);
//        long result = container.get("result");
//        result = (long) (result % (Math.pow(10, 9) + 7));
//        return (int) result;
//    }
//
//    private static void combinations(int n, int c, int total, Map<String, Long> container) { // O(2^n)
//        if (n == 0) {
//            if (Math.abs(c) != total) {
//                container.put("result", container.get("result") + 1);
//            }
//            return;
//        }
//        combinations(n - 1, c + 1, total, container);
//        combinations(n - 1, c - 1, total, container);
//    }

    public int monkeyMove(int n) {
        return (int) (mod + pow(2, n) - 2) % mod;
    }

    public static long pow(long x, long n) {
        if (n == 0) {
            return 1;
        }
        long r = pow(x, n / 2);
        if (n % 2 == 0) {
            return (r * r) % mod;
        } else {
            return (x * r * r) % mod;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.monkeyMove(500000003));
    }
}