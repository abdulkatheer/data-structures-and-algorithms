package io.abdul.basics.problem5;

public class BinaryToDecimal {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println("10 => " + solution.binaryToDecimal("1010"));
        System.out.println("13 => " + solution.binaryToDecimal("1101"));
        System.out.println("274293 => " + solution.binaryToDecimal("1000010111101110101"));
        System.out.println("176688613 => " + solution.binaryToDecimal("1010100010000000110111100101"));
    }
}

class Solution {
    public int binaryToDecimal(String binary) {
        int result = 0;

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                result = (int) (result + Math.pow(2, binary.length() - i - 1));
            }
        }

        return result;
    }
}

class Solution2 {
    public int binaryToDecimal(String binary) {
        int result = 0;
        int powOfTwo = 1;

        for (int i = binary.length()-1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                result = result + powOfTwo;
            }
            powOfTwo *= 2;
        }

        return result;
    }
}

