package io.abdul.basics.problem6;

public class CheckIthBit {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.isBitSet(13, 2));
        System.out.println(solution.isBitSet(13, 1));
    }
}

/*
Brute - Convert to binary string and check
T - O(n)
S - O(n)
 */
class Solution {
    public boolean isBitSet(int num, int i) {
        String binaryString = Integer.toBinaryString(num);
        return binaryString.charAt(binaryString.length() - i - 1) == '1';
    }
}

/*
Optimal - << and & operator
T - O(1)
S - O(1)

13 and 2
13 binary form - 1 1 0 1
1 << 2         - 0 1 0 0
&              - 0 1 0 0 != 0

13 and 1
1 << 1         - 0 0 1 0
&              - 0 0 0 0 == 0
 */
class Solution2 {
    public boolean isBitSet(int num, int i) {
        return (num & (1 << i)) > 0;
    }
}

/*
Optimal - >> and & operator
T - O(1)
S - O(1)

13 and 2
13 binary form - 1 1 0 1
Move the required bit to the rightmost position
13 >> 2        - 0 0 1 1
1              - 0 0 0 1
&              - 0 0 0 1 == 1
 */
class Solution3 {
    public boolean isBitSet(int num, int i) {
        return (num >> i & 1) != 0;
    }
}