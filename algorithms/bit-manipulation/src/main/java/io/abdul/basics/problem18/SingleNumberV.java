package io.abdul.basics.problem18;

/*
5 repeating numbers and 1 single number
 */
public class SingleNumberV {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.singleNumber(new int[]{1, 1, 1, 1, 1, 3}));
        System.out.println(solution.singleNumber(new int[]{2, 2, 2, 2, 2, 6}));
        System.out.println(solution.singleNumber(new int[]{2, 1, 2, 2, 1, 2, 1, 5, 2, 1, 1}));
        System.out.println(solution.singleNumber(new int[]{-1, -2, 10, -1, -1, -1, -2, -2, -2, -1, -2}));
    }
}

/*
Optimal - 4-bit Finite State Machine
T - O(n)
S - O(1)
 */
class Solution {
    public int singleNumber(int[] nums) {
        int bitOne = 0, bitTwo = 0, bitThree = 0, bitFour = 0;

        for (int num : nums) {
            bitOne = (bitOne ^ num) & ~bitTwo & ~bitThree & ~bitFour; // Add to bitOne if not in bitTwo & bitThree
            bitTwo = (bitTwo ^ num) & ~bitOne & ~bitThree & ~bitFour; // Add to bitTwo if not in bitOne & bitThree
            bitThree = (bitThree ^ num) & ~bitOne & ~bitTwo & ~bitFour; // Add to bitThree if not in bitOne & bitTwo
            bitFour = (bitFour ^ num) & ~bitOne & ~bitTwo & ~bitThree;
            System.out.printf("once=%s bitTwo=%s bitThree=%s bitFour=%s%n", Integer.toBinaryString(bitOne),
                    Integer.toBinaryString(bitThree), Integer.toBinaryString(bitThree),
                    Integer.toBinaryString(bitFour));

        }

        return bitOne;
    }
}