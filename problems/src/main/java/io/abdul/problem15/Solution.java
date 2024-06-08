package io.abdul.problem15;

import java.math.BigDecimal;

// https://leetcode.com/problems/powx-n/
class Solution {
    public double myPow(double x, int n) {
        double pow = powV2(x, Math.abs(n));
        return n < 0 ? 1 / pow : pow;
    }

    // O(n)
    private static double pow(double x, double power) {
        if (power == 1) {
            return x;
        }
        if (power == 0) {
            return 1;
        }
        return x * pow(x, power - 1);
    }

    // O(llog n)

    /**
     * In order to improve efficiency we will opt for Binary Exponentiation using which we can calculate xn using O log2(N) multiplications.
     * <p>
     * Basic Idea is to divide the work using binary representation of exponents
     * i.e. is to keep multiplying pow with x, if the bit is odd, and multiplying x with itself until we get 0
     * We will use very 1st example of 1st Approach i.e.
     * x = 7, n = 11 and pow = 1
     * Here, we have to calculate 711
     * Binary of n i.e. (11)10 is (1011)2
     * 1    0    1    1
     * 2^3  2^2  2^1  2^0   <-- Corresponding place values of each bit
     * <p>
     * OR we can also write this as
     * 1 0 1 1
     * 8 4 2 1 <-- Corresponding place values of each bit
     * <p>
     * Now, 7^8 × 7^2 × 7^1 == 7^11 as 7^(8 + 2 + 1) == 7^11
     * NOTE: We have not considered 74 in this case as the 4th place bit is OFF
     * <p>
     * So, 7^8 × 7^2 × 7^1 == 5764801 × 49 × 7 == 1977326743 <-- Desired Output
     * Now, applying logic keeping this concept in mind
     * <p>
     * --
     * <p>
     * we know that
     * <p>
     * if p is even we can write  N p  = N p/2  * N  p/2  = (N p/2) 2 and
     * <p>
     * if p is odd we can wrte N p = N * (N (p-1)/2  * N (p-1)/2) = N * (N (p-1)/2) 2
     * <p>
     * for example : 24 = 22 * 22
     * <p>
     * also, 25 = 2 * (22 * 22)
     * <p>
     * <p>
     * From this definaion we can derive this recurrance relation as
     * <p>
     * if p is even
     * <p>
     * result = ( func(N, p/2) ) 2
     * <p>
     * else
     * <p>
     * result = N * ( func(N, (p-1)/2) ) 2
     *
     * @return
     */
    public static double powV2(double x, int n) {
        if (n == 0) {
            return 1;
        }
        double r = powV2(x, n / 2);
        if (n % 2 == 0) {
            return r * r;
        } else {
            return x * r * r;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.myPow(2.00000, 10));
//        System.out.println(s.myPow(2.00000, -2));
//        System.out.println(s.myPow(100.00000, -9));
        System.out.println(BigDecimal.valueOf(s.myPow(2.00000, 10)));
        System.out.println(BigDecimal.valueOf(s.myPow(2.10000, 3)));
        System.out.println(BigDecimal.valueOf(s.myPow(2.00000, -2)));
        // 1.977326743E9
    }
}