package io.abdul.maths.basic_maths.problem8;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/check-for-perfect-number
public class PerfectNumber {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.isPerfect(1));
        System.out.println(solution.isPerfect(11));
        System.out.println(solution.isPerfect(12));
        System.out.println(solution.isPerfect(123456));
        System.out.println(solution.isPerfect(6));
        System.out.println(solution.isPerfect(28));
    }
}

class Solution {
    // T - O(n/2)
    public boolean isPerfect(int n) {
        int halfOfN = n / 2;
        int sum = 0;
        for (int i = 1; i <= halfOfN; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return sum == n;
    }
}

class Solution2 {
    /*
    n=36
    1  36
    2  18
    3  12
    4   9
    6   6
    We need 1, 2, 18, 3, 12, 4, 9, 6
    Start with 1 to ignore 36
    Add condition to ignore duplicate 6
     */
    // T - O(sqrt(n))
    public boolean isPerfect(int n) {
        if (n == 1) {
            return false;
        }
        int sqrt = (int) Math.sqrt(n); // Costlier, instead do i*i <= n; Square is opposite of square root and easier to compute
        int sum = 1;
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                sum += i;
                if (i != n / i) { // For ex, n=36, i=6. We should not add 6 twice
                    sum += n / i;
                }
            }
        }
        return sum == n;
    }
}
