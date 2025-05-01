package io.abdul.basics.problem8;

public class UnsetIthBit {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println("13 => " + Integer.toBinaryString(13) + " ; " + Integer.toBinaryString(solution.unset(13, 2)));
    }
}

/*
Brute - Convert to binary, unset and rebuild
T - O(n)
S - O(n)
 */
class Solution {
    public int unset(int num, int i) {
        return -1;
    }
}

/*
Optimal - Bit manipulation
T - O(1)
S - O(1)


13, 2
Binary form - 1 1 0 1
1 -> 0
0 -> 0
& operation might fit this case
              1 1 0 1
              1 0 1 1 -> How to form this number

1 Binary form - 0 (sign) 0... 30 zero's ... 0 0 0 1
1 << 2        - 0 (sign) 0... 28 zero's ... 0 1 0 0
~             - 1 (sign) 1... 28 one's  ... 1 0 1 1

-----
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 1
&
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1 1
----
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 1

 */
class Solution2 {
    public int unset(int num, int i) {
        return num & ~(1 << i);
    }
}