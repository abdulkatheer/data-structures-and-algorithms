package io.abdul.recursion.practice.problem24;

public class Power {
    public static void main(String[] args) {
        System.out.println(power(2, 5));
    }

    /*
    T - O(n)
    S - O(n)
     */
    private static int power(int base, int power) {
        if (power == 0) {
            return 1;
        }
        return base * power(base, power - 1);
    }

    /*
    2^10 = (2*2)^5
    2^15 = 2 * (2*2)^7

    T - O(log n)
    S - O(log n)
     */
    private static int powerV2(int base, int power) {
        if (power == 0) {
            return 1;
        }
        if (power % 2 == 0) {
            return power(base * base, power / 2);
        } else {
            return base * power(base * base, power / 2);
        }
    }
}
