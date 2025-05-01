package io.abdul.basics.problem24;

public class MultiplicationWithoutMultOperator {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        System.out.println(solution.multiply(1, 2));
        System.out.println(solution.multiply(1, -2));
        System.out.println(solution.multiply(1, Integer.MIN_VALUE));
        System.out.println(solution.multiply(-1, Integer.MIN_VALUE));
        System.out.println(solution.multiply(1, Integer.MAX_VALUE));
        System.out.println(solution.multiply(-1, Integer.MAX_VALUE));
    }
}

/*
Brute - Add a or b number of times
T - O(min(a,b)
S - O(1)
 */
class Solution {
    public int multiply(int a, int b) {
        if (a == 0 || b == 0) { // Handle negative values
            return 0;
        }

        long a_abs = a;
        long b_abs = b;

        long x;
        long y;
        if (a_abs < b_abs) { // To reduce number of iterations
            x = Math.abs(a_abs);
            y = Math.abs(b_abs);
        } else {
            y = Math.abs(a_abs);
            x = Math.abs(b_abs);
        }

        boolean sign = a < 0 ^ b < 0;

        long result = 0;
        for (int i = 0; i < y; i++) {
            result += x;
        }

        long intMax = 1L << 31;
        if (result >= intMax) { // Overflow
            if (sign) {
                result = intMax;
            } else {
                result = (intMax) - 1;
            }
        }

        return sign ? (int) -result : (int) result;
    }
}

/*
Optimal - Add in a in powers of 2 of b
T - O(log min(a,b))
S - O(1)

10 & 5
1 0 1 0
0 1 0 1

10 << 1 = 10
10 << 2 = 40
          50
 */
class Solution2 {
    public int multiply(int a, int b) {
        long a_abs = Math.abs((long) a);
        long b_abs = Math.abs((long) b);
        long x, y;
        if (b_abs < a_abs) {
            x = a_abs;
            y = b_abs;
        } else {
            y = a_abs;
            x = b_abs;
        }
        boolean sign = a < 0 ^ b < 0;

        long result = 0;
        while (y > 0) { // iterations reduced to logn or 31 max
            if ((y & 1) != 0) {
                result += x;
            }
            x = x << 1; // x = x * 2, maintaining x << y at each bit position. We'll add that to result if that bit is set in y;
            y = y >> 1; // drop the rightmost bit
        }

        long intMax = 1L << 31;
        if (result >= intMax) { // Overflow
            if (sign) {
                result = intMax;
            } else {
                result = (intMax) - 1;
            }
        }

        return sign ? (int) -result : (int) result;
    }
}