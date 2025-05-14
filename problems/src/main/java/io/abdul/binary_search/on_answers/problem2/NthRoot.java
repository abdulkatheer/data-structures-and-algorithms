package io.abdul.binary_search.on_answers.problem2;

public class NthRoot {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
//        System.out.println(solution.NthRoot(3, 27));
//        System.out.println(solution.NthRoot(4, 69));
//        System.out.println(solution.NthRoot(4, 81));
//        System.out.println(solution.NthRoot(7, 128));
        System.out.println(solution.NthRoot(9, 10077696));
    }
}

/*

T - O(log m X log n)
S - O(1)
 */
class Solution {
    public int NthRoot(int N, int M) {
        int low = 1, high = M;

        while (low <= high) {
            int mid = (low + high) / 2;
            int pow = power(mid, N);

            if (pow == M) {
                return mid;
            } else if (pow > M) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    /*
    n^9 = n ( n^2 (n^2 (n^2) ) )
     */
    private int power(int a, int b) {
        if (b == 0) {
            return 1;
        }

        if (b == 1) {
            return a;
        }

        int pow = 1;

        while (b > 1) {
            if (b % 2 == 0) {
                pow = pow * a * a;
                b /= 2;
            } else {
                pow = a * pow; // n^7 = n x n^3 x n^3
                b = b - 1;
            }
        }

        return pow;
    }
}

class Solution2 {
    public int NthRoot(int N, int M) {
        int low = 1, high = M;

        while (low <= high) {
            int mid = (low + high) / 2;
            int result = checkPower(mid, N, M);

            if (result == 0) {
                return mid;
            } else if (result > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    /*
    n^9 = n ( n^2 (n^2 (n^2) ) )
     */
    private int checkPower(int a, int b, int M) {
        if (b == 0) {
            return 1;
        }

        long base = a;
        long pow = 1;

        while (b > 0) {
            if ((b & 1) == 1) { // Odd
                pow *= base;
                b--;
            } else {
                base *= base;
                b >>= 1;
            }
            if (pow > M || base > M) {
                return 1;
            }
        }

        return pow == M ? 0 : -1;
    }
}

