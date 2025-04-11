package io.abdul.maths.basic_maths.problem11;

public class GCD {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
//        Solution4 solution = new Solution4();
        Solution5 solution = new Solution5();
        System.out.println(solution.GCD(6, 4));
        System.out.println(solution.GCD(8, 9));
        System.out.println(solution.GCD(1, 2));
        System.out.println(solution.GCD(1, 1));
        System.out.println(solution.GCD(20, 40));

    }
}

class Solution {
    public int GCD(int n1, int n2) {
        int smaller = Math.min(n1, n2);

        int gcd = 1;
        for (int i = 1; i <= smaller; i++) {
            if (n1 % i == 0 && n2 % i == 0) {
                if (i > gcd) {
                    gcd = i;
                }
            }
        }

        return gcd;
    }
}

class Solution2 {
    public int GCD(int n1, int n2) {
        int smaller = Math.min(n1, n2);

        int gcd = 1;
        for (int i = 2; i <= smaller; i++) {
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }

        return gcd;
    }
}

class Solution3 {
    public int GCD(int n1, int n2) {
        int smaller = Math.min(n1, n2);

        int gcd = 1;
        for (int i = smaller; i > 1; i--) {
            if (n1 % i == 0 && n2 % i == 0) { // Stop at first hit, bcz going further will give only smaller divisor than current
                gcd = i;
                break;
            }
        }

        return gcd;
    }
}

// Euclidean function: GCD(n1, n2) = GCD(n1-n2, n2) where n1 > n2
class Solution4 {
    public int GCD(int n1, int n2) {
        while (n1 > 0 && n2 > 0) {
            if (n1 >= n2) {
                n1 = n1 - n2;
            } else {
                n2 = n2 - n1;
            }
        }

        return n1 == 0 ? n2 : n1;
    }
}

// Euclidean function optimized: GCD(n1, n2) = GCD(n1-n2, n2) where n1 > n2
/*
100 15
We'll keep on subtracting 15 from 100 unless it reach 10.
Meaning 6 times 15 is 90.
So technically, we subtract the divisible number and keeping the remaninder.
By doing modulo, we reduce 6 iterations to 1.
 */
class Solution5 {
    public int GCD(int n1, int n2) {
        while (n1 > 0 && n2 > 0) {
            if (n1 >= n2) {
                n1 = n1 % n2;
            } else {
                n2 = n2 % n1;
            }
        }

        return n1 == 0 ? n2 : n1;
    }
}
