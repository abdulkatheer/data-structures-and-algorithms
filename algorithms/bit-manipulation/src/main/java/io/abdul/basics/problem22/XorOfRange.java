package io.abdul.basics.problem22;

public class XorOfRange {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.findRangeXOR(3, 5));
        System.out.println(solution.findRangeXOR(1, 3));
        System.out.println(solution.findRangeXOR(4, 10));
    }
}

/*
T - O(n)
S - O(1)
 */
class Solution {
    public int findRangeXOR(int l, int r) {
        int xor = 0;
        for (int i = l; i <= r; i++) {
            xor ^= i;
        }

        return xor;
    }
}

/*
Optimal - based on result pattern
T - O(1)
S - O(1)

1,5,9.. -> 1
2,6,10,.. -> n+1
3,7,11,.. -> 0
4,8,12,... -> n

4,6

xor of 1,2,3,4,5 -> 1
xor of 1,2 -> 3
xor 1 ^ 3 -> 2
 */
class Solution2 {
    public int findRangeXOR(int l, int r) {
        return xor(r) ^ xor(l - 1);
    }

    private int xor(int num) {
        int mod = num % 4;
        if (mod == 0) {
            return num;
        } else if (mod == 1) {
            return 1;
        } else if (mod == 2) {
            return num + 1;
        } else {
            return 0;
        }
    }
}