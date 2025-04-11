package io.abdul.maths.basic_maths.problem4;

public class CheckPalindrome {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isPalindrome(0));
        System.out.println(solution.isPalindrome(1));
        System.out.println(solution.isPalindrome(11));
        System.out.println(solution.isPalindrome(10));
        System.out.println(solution.isPalindrome(121));
        System.out.println(solution.isPalindrome(1000001));
    }
}

class Solution {
    public boolean isPalindrome(int n) {
        int reversed = reverseNumber(n);
        return reversed == n;
    }

    private int reverseNumber(int n) {
        if (n == 0) {
            return 0;
        }
        int reverse = 0;
        while (n > 0) {
            int currentDigit = n % 10;
            reverse = reverse * 10 + currentDigit;
            n = n / 10;
        }

        return reverse;
    }
}
