package io.abdul.basics.problem10;

/*
13 - 1 1 0 1 -> 1 1 0 0
10 - 1 0 1 0 -> 1 0 0 0
 */
public class RemoveLastSetBit {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("13 => " + Integer.toBinaryString(13) + " ; " + Integer.toBinaryString(solution.removeLastSetBit(13)));
        System.out.println("10 => " + Integer.toBinaryString(10) + " ; " + Integer.toBinaryString(solution.removeLastSetBit(10)));
    }
}

/*
16 -> 1 0 0 0 0
15 -> 0 1 1 1 1

40 -> 1 0 1 0 0 0
39 -> 1 0 0 1 1 1

84 -> 1 0 1 0 1 0 0
83 -> 1 0 1 0 0 1 1

So when we minus one, the rightmost set bit is unset and bits right to it are set

13 - 1 1 0 1
&
12 - 1 1 0 0
--
     1 1 0 0

10 - 1 0 1 0
&
9  - 1 0 0 1
--
   - 1 0 0 0
 */
class Solution {
    public int removeLastSetBit(int num) {
        return num & num - 1;
    }
}
