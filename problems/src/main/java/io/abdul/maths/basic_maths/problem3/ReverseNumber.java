package io.abdul.maths.basic_maths.problem3;

public class ReverseNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.reverseNumber(0));
        System.out.println(solution.reverseNumber(1));
        System.out.println(solution.reverseNumber(11));
        System.out.println(solution.reverseNumber(12));
        System.out.println(solution.reverseNumber(123));
        System.out.println(solution.reverseNumber(1234567890));
    }
}

class Solution {
    public int reverseNumber(int n) {
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
