package io.abdul.basics.problem13;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/bit-manipulation/problems/minimum-bit-flips-to-convert-number
public class MinimumBitFlips {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.minBitsFlip(10, 7));
        System.out.println(solution.minBitsFlip(3, 4));
        System.out.println(solution.minBitsFlip(1, 7));
        System.out.println(solution.minBitsFlip(Integer.MAX_VALUE, 0));
        System.out.println(solution.minBitsFlip(0, Integer.MAX_VALUE));
    }
}

/*
Brute - convert to Binary and count
T - O(1) - 32 bits to be checked
T - O(n) - converted bit string
 */
class Solution {
    public int minBitsFlip(int start, int goal) {
        int count = 0;
        String startBits = Integer.toBinaryString(start);
        String goalBits = Integer.toBinaryString(goal);

        char[] startZeros = new char[32 - startBits.length()];
        Arrays.fill(startZeros, '0');

        char[] goalZeros = new char[32 - goalBits.length()];
        Arrays.fill(goalZeros, '0');

        startBits = new String(startZeros) + startBits; // 32 bits
        goalBits = new String(goalZeros) + goalBits; // 32 bits

        for (int i = 0; i < 32; i++) {
            if (startBits.charAt(i) != goalBits.charAt(i)) {
                count++;
            }
        }

        return count;
    }
}

/*
Optimal - xor and & operator
T - O(1)
S - O(1)

1 0 0 1 1 0 1
      1 0 1 0 ^
1 0 0 0 1 1 1
Now count bits -> 4

      1 1 1 0
1 0 0 0 0 0 1 ^
1 0 0 1 1 1 1 -> 5

1 0 1 0
  1 1 1
1 1 0 1 -> 3
 */
class Solution2 {
    public int minBitsFlip(int start, int goal) {
        int diff = start ^ goal;
        int count = 0;
        while (diff != 0) {
            diff = diff & (diff - 1);
            count++;
        }
        return count;
    }
}
