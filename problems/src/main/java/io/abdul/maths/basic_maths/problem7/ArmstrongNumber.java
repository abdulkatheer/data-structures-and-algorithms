package io.abdul.maths.basic_maths.problem7;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/check-if-the-number-if-armstrong
public class ArmstrongNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isArmstrong(0));
        System.out.println(solution.isArmstrong(1));
        System.out.println(solution.isArmstrong(12));
        System.out.println(solution.isArmstrong(153));
    }
}

class Solution {
    public boolean isArmstrong(int n) {
        if (n == 0) {
            return true;
        }

        // Count digits
        int numOfDigits = 0;
        int x = n;
        while (x > 0) {
            x = x / 10;
            numOfDigits++;
        }

        // Find sum
        int sum = 0;
        int y = n;
        while (y > 0) {
            int currentDigit = y % 10;
            sum += (int) Math.pow(currentDigit, numOfDigits);
            y = y / 10;
        }

        return sum == n;
    }
}
