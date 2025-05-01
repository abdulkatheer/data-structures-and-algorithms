package io.abdul.basics.problem11;

/*
1 - 2^0 - true
2 - 2^1 - true
3 - false
4 -2^2 - true
 */
public class CheckPowerOfTwo {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isPowerOfTwo(1));
        System.out.println(solution.isPowerOfTwo(2));
        System.out.println(solution.isPowerOfTwo(4));
        System.out.println(solution.isPowerOfTwo(8));
        System.out.println(solution.isPowerOfTwo(3));
        System.out.println(solution.isPowerOfTwo(5));
        System.out.println(solution.isPowerOfTwo(6));
        System.out.println(solution.isPowerOfTwo(7));
    }
}

/*
T - O(1)
S - O(1)

1 - 0001
2 - 0010
4 - 0100
8 - 1000

2^x will have only one bit set. We need to find if only one of bits is set or not

0 1 0 0
(-1)
0 0 1 1
0 0 0 0

13 - 1 1 0 1
     1 1 0 0
     ^
     0 0 0 1 != 0

23 - 1 0 1 1 1
     1 0 1 1 0
     ^
     1 0 1 1 0

43 - 1 0 1 0 1 1
     1 0 1 0 1 0
     ^
     1 0 1 0 1 0

10 - 1 0 1 0
     1 0 0 1
     &
     1 0 0 0
 */
class Solution {
    public boolean isPowerOfTwo(int num) {
        return (num & (num - 1)) == 0;
    }
}
