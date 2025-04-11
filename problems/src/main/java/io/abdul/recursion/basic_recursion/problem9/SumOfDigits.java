package io.abdul.recursion.basic_recursion.problem9;

public class SumOfDigits {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.addDigits(12345));
    }
}

/*
Ex 1:
12345 0
1234 5
123 9
12 12
1 14
0 15, 0 and > 9
15 0
1 5
0 6, 0 and not > 9, exit

Ex 2:
12345678 0
1234567 8
123456 15
12345 21
1234 26
123 30
12 33
1 35
0 36, 0 and > 9
36, 0
3 6
0 9, 0 and not > 9, exit
 */
class Solution {
    public int addDigits(int num) {
        if (num == 0) {
            return 0;
        }
        int lastDigit = num % 10;
        int sum = lastDigit + addDigits(num / 10); // Add num
        if (sum < 10) {
            return sum;
        }
        return addDigits(sum); // Add sum again
    }
}