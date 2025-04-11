package io.abdul.maths.basic_maths.problem5;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/return-the-largest-digit-in-a-number
public class LargestDigit {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.largestDigit(0));
        System.out.println(solution.largestDigit(1));
        System.out.println(solution.largestDigit(11111111));
        System.out.println(solution.largestDigit(321));
        System.out.println(solution.largestDigit(198910));
    }
}

class Solution {
    public int largestDigit(int n) {
        if (n == 0) {
            return 0;
        }

        int largest = -1;
        while (n > 0) {
            int currentDigit = n % 10;
            if (currentDigit > largest) {
                largest = currentDigit;
            }
            n = n / 10;
        }

        return largest;
    }
}
