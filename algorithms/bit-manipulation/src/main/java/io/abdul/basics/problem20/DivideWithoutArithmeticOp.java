package io.abdul.basics.problem20;

// https://takeuforward.org/plus/dsa/bit-manipulation/problems/divide-two-numbers-without-multiplication-and-division
public class DivideWithoutArithmeticOp {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        System.out.println(solution.divide(36, 5));
        System.out.println(solution.divide(Integer.MIN_VALUE, -1));
        System.out.println(solution.divide(Integer.MAX_VALUE, 1));
        System.out.println(solution.divide(Integer.MIN_VALUE, 1));
        System.out.println(solution.divide(Integer.MAX_VALUE, -1));
    }
}

/*
Brute - Minus divisor from dividend and count
T - O(n)
S - O(1)
 */
class Solution {
    public int divide(int dividend, int divisor) {
        return -1; // TODO implement
    }
}

/*
Optimal - Divide by powers of 2, that can be done using << and >>

Ex: 36 and 5
Max 2^ that can divide 36 is 2^2
5 x 2^2 = 20 (balance 16)
5 x 2^1 = 10 (balance 6)
5 x 2^0 = 5 (balance 1)
----------
Total fives = 4 + 2 + 1 = 7

NOTE: Do all operations in long to avoid loss of conversion
 */
class Solution2 {
    public int divide(int dividend, int divisor) {
        boolean positive = true;
        if (dividend < 0 && divisor >= 0) {
            positive = false;
        } else if (dividend >= 0 && divisor < 0) {
            positive = false;
        }
        long n = dividend;
        long d = divisor;
        n = Math.abs(n);
        d = Math.abs(d);
        long result = 0;

        // Find the highest 2^ than can divide dividend
        int count = 0;
        while (n >= d << count + 1) {
            count++;
        }

        while (count >= 0 && n > 0) {
            long twoPowerCount = 1L << count;
            long value = d << count;
            if (n >= value) {
                result += twoPowerCount;
                n -= value;
            }
            count--;
        }

        // Handle overflows
        if (result == (1L << 31)) {
            if (positive) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        return positive ? (int) result : -(int) result;
    }
}