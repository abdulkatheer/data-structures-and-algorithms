package io.abdul.maths.basic_maths.problem2;

public class CountOddDigits {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countOddDigit(5));
        System.out.println(solution.countOddDigit(1234567890));
        System.out.println(solution.countOddDigit(0));
        System.out.println(solution.countOddDigit(2));
        System.out.println(solution.countOddDigit(222222222));
    }
}

class Solution {
    public int countOddDigit(int n) {
        if (n == 0) {
            return 0;
        }

        int count = 0;
        while (n > 0) {
            if (n % 2 != 0) {
                count++;
            }
            n = n / 10;
        }
        return count;
    }
}