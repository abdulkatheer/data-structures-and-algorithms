package io.abdul.basics.problem7;

public class SetIthBit {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.setBit(17, 2));
    }
}

/*
Brute - Convert to Binary, set and build int back
 */
class Solution {
    public int setBit(int num, int i) {
        return -1;
    }
}

/*
Optimal - Bit manipulation
T - O(1)
S - O(1)

17 & 2
Binary form - 1 0 0 0 1
1 << 2      - 0 0 1 0 0
|           - 1 0 1 0 1

 */
class Solution2 {
    public int setBit(int num, int i) {
        return num | 1 << i;
    }
}