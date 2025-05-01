package io.abdul.basics.problem4;

public class DecimalToBinary {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("10 : " + Integer.toBinaryString(10) + " ; " + solution.decimalToBinary(10));
        System.out.println("13 : " + Integer.toBinaryString(13) + " ; " + solution.decimalToBinary(13));
        System.out.println("274293 : " + Integer.toBinaryString(274293) + " ; " + solution.decimalToBinary(274293));
        System.out.println("176688613 : " + Integer.toBinaryString(176688613) + " ; " + solution.decimalToBinary(176688613));
        System.out.println(Integer.MAX_VALUE + " : " + Integer.toBinaryString(Integer.MAX_VALUE) + " ; " + solution.decimalToBinary(Integer.MAX_VALUE));
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));
    }
}

class Solution {
    public String decimalToBinary(int num) {
        StringBuilder res = new StringBuilder();
        while (num != 0) {
            res.append(num % 2 == 0 ? "0" : "1");
            num /= 2;
        }
        return res.reverse().toString();
    }
}
