package io.abdul.maths.basic_maths.problem1;

public class CountDigits {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.countDigit(6768183));
        System.out.println(solution.countDigit(0));
        System.out.println(solution.countDigit(1));
        System.out.println(solution.countDigit(10));
    }
}

class Solution {
    public int countDigit(int n) {
        if (n == 0) {
            return 1;
        }
        int count = 0;
        while (n != 0) {
            n = n / 10;
            count++;
        }
        return count;
    }
}