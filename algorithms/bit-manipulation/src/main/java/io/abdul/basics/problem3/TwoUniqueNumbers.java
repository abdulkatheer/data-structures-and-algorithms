package io.abdul.basics.problem3;

import java.util.Arrays;

public class TwoUniqueNumbers {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoUniqueNumbers(new int[]{1, 2, 1, 14, 1, 3, 3, 1})));
    }
}

/*
Using Self-inverse, Commutative & Associative properties

As two numbers are unique, at least one bit will change between each other.
Means one of them will be 1 and other one's bit will be zero.
XOR of those two will give 1 in that bit;
 */
class Solution {
    public int[] twoUniqueNumbers(int[] nums) {
        int a_xor_b = 0;
        for (int num : nums) {
            a_xor_b = a_xor_b ^ num; // every other element will cancel each other
        }
        System.out.println("a^b=" + Integer.toBinaryString(a_xor_b));
        System.out.println("(-) a^b=" + Integer.toBinaryString(-a_xor_b));

        int[] result = new int[2];
        // let's say a is 0010 and b is 1110. 3rd and 4th bit are different.
        // a ^ b = 1100
        // (-) a^b = 0011 + 1 = 1111111111111111111111111111_0100
        // a^b & (-)a^b = 0100 => differing bit. 3rd bit is 0 for one and 1 for other
        // Now let's group numbers into two, Group A with 0 at that bit and Group B with 1 at that bit
        int diffBit = a_xor_b & (-a_xor_b); // The rightmost set bit
        System.out.println("a^b & (-)a^b=" + Integer.toBinaryString(-a_xor_b));

        for (int num : nums) {
            if ((num & diffBit) == 0) { // 1110 & 0100 = 0
                System.out.println("Group A num=" + num + "; binary=" + Integer.toBinaryString(num));
                result[0] = result[0] ^ num; // 0 ^ num1 ^ num2 ^ uniqueNum1 ^ num2 ^ num1
            } else {
                System.out.println("Group B num=" + num + "; binary=" + Integer.toBinaryString(num));
                result[1] = result[1] ^ num; // 0 ^ num3 ^ num4 ^ uniqueNum2 ^ num4 ^ num3
            }
        }
        return result;
    }
}
