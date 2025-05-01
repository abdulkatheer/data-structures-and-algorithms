package io.abdul.basics.problem9;

public class ToggleBit {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println("13 => " + Integer.toBinaryString(13) + " ; " + Integer.toBinaryString(solution.toggleBit(13, 2)));
        System.out.println("13 => " + Integer.toBinaryString(13) + " ; " + Integer.toBinaryString(solution.toggleBit(13, 1)));
    }
}

/*
Brute - Convert to binary, toggle and rebuild
 */
class Solution {
    public int toggleBit(int num, int i) {
        return -1;
    }
}

/*
Optimal - Bit manipulation

13, 2
Binary form - 1 1 0 1
Expected    - 1 0 0 1

1 ^ 1 = 0
0 ^ 1 = 0

1 1 0 1
^
0 1 0 0
--
1 0 0 1

13, 1

1 1 0 1
^
0 0 1 0
--
1 1 1 1
 */
class Solution2 {
    public int toggleBit(int num, int i) {
        return num ^ (1 << i);
    }
}