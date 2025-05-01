package io.abdul.basics.problem19;

/*
6 repeating and 1 unique number
 */
public class SingleNumberVI {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.singleNumber(new int[]{1, 1, 1, 1, 1, 1, 3}));
        System.out.println(solution.singleNumber(new int[]{2, 2, 2, 2, 2, 2, 6}));
        System.out.println(solution.singleNumber(new int[]{2, 1, 2, 2, 2, 1, 1, 2, 1, 5, 2, 1, 1}));
        System.out.println(solution.singleNumber(new int[]{-1, -2, 10, -1, -2, -1, -1, -2, -2, -2, -1, -1, -2}));
    }
}

/*
Optimal - 5-bit Finite State Machine
T - O(n)
S - O(1)
 */
class Solution {
    public int singleNumber(int[] nums) {
        int bitOne = 0, bitTwo = 0, bitThree = 0, bitFour = 0, bitFive = 0;

        for (int num : nums) {
            bitOne = (bitOne ^ num) & ~bitTwo & ~bitThree & ~bitFour & ~bitFive;
            bitTwo = (bitTwo ^ num) & ~bitOne & ~bitThree & ~bitFour & ~bitFive;
            bitThree = (bitThree ^ num) & ~bitOne & ~bitTwo & ~bitFour & ~bitFive;
            bitFour = (bitFour ^ num) & ~bitOne & ~bitTwo & ~bitThree & ~bitFive;
            bitFive = (bitFive ^ num) & ~bitOne & ~bitTwo & ~bitThree & ~bitFour;

            System.out.printf("once=%s bitTwo=%s bitThree=%s bitFour=%s bitFive=%s%n", Integer.toBinaryString(bitOne),
                    Integer.toBinaryString(bitThree), Integer.toBinaryString(bitThree),
                    Integer.toBinaryString(bitFour), Integer.toBinaryString(bitFive));
        }

        return bitOne;
    }
}
