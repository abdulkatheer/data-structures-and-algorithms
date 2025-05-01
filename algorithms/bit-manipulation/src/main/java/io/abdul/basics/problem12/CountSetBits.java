package io.abdul.basics.problem12;

/*
1 0 1 0 0 1 1 0 0 1 -> 5
 */
public class CountSetBits {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.countSetBits(0b1010011001));
    }
}

/*
1 0 1 0 0 1 1 0 0 1
Trick to know if a last bit is set -> If number is odd, last bit will always be set. So modulo will help us here.
How to trip bits from left one by one to check each bit?
Divide by 2 every time.
1 0 1 0 0 1 1 0 0 1 / 2
1 0 1 0 0 1 1 0 0 / 2
1 0 1 0 0 1 1 0 / 2
1 0 1 0 0 1 1 / 2
1 0 1 0 0 1 / 2
1 0 1 0 0 / 2
1 0 1 0 / 2
1 0 1 / 2
1 0 / 2
1 / 2
0 -> Stop
 */
class Solution {
    public int countSetBits(int num) {
        int count = 0;
        while (num != 0) {
            if (num % 2 != 0) {
                count++;
            }
            num = num / 2;
        }
        return count;
    }
}

/*
2 Computations can be done better
%2 -> num & 1 = 1 (odd), 0 (even)
num/2 -> num >> 1 = num / 2^k = num / 2^1
 */
class Solution2 {
    public int countSetBits(int num) {
        int count = 0;
        while (num != 0) {
            count += num & 1;
            num = num >> 1;
        }
        return count;
    }
}

/*
Optimal
T - O(log n)
S - O(1)

Unset last bit until number becomes zero.
1 0 1 1 1 0 0 1 0 (n)
1 0 1 1 1 0 0 0 1 (n-1)
1 0 1 1 1 0 0 0 0 (&) -> count=1
1 0 1 1 0 1 1 1 1 (-1)
1 0 1 1 0 0 0 0 0 (&) -> count=2
1 0 1 0 1 1 1 1 1 (-1)
1 0 1 0 0 0 0 0 0 (&) -> count=3
1 0 0 1 1 1 1 1 1 (-1)
1 0 0 0 0 0 0 0 0 (&) -> count=4
0 1 1 1 1 1 1 1 1 (-1)
0 0 0 0 0 0 0 0 0 (&) -> count=5
 */
class Solution3 {
    public int countSetBits(int num) {
        int count = 0;
        while (num != 0) {
            num = num & (num - 1);
            count++;
        }
        return count;
    }
}