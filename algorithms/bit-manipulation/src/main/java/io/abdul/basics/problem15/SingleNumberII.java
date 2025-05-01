package io.abdul.basics.problem15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SingleNumberII {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        System.out.println(solution.singleNumber(new int[]{2, 2, 2, 3}));
        System.out.println(solution.singleNumber(new int[]{1, 0, 3, 0, 1, 1, 3, 3, 10, 0}));
        System.out.println(solution.singleNumber(new int[]{5, 0, 1, 10, 1, 1, 5, 5, 10, 10}));
        System.out.println(solution.singleNumber(new int[]{-2, -2, -2, -3}));
        System.out.println(solution.singleNumber(new int[]{-1, 0, -3, 0, -1, -1, -3, -3, -10, 0}));
        System.out.println(solution.singleNumber(new int[]{-5, 0, -1, -10, -1, -1, -5, -5, -10, -10}));
    }
}

/*
Brute - HashMap and frequency count
T - O(n)
S - O(n)
 */
class Solution {
    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> countByNum = new HashMap<>();

        for (int num : nums) {
            Integer count = countByNum.getOrDefault(num, 0);
            countByNum.put(num, count + 1);
        }

        for (Map.Entry<Integer, Integer> countEntry : countByNum.entrySet()) {
            if (countEntry.getValue() == 1) {
                return countEntry.getKey();
            }
        }

        return -1;
    }
}

/*
Better - Sort and find
T - O(n logn)
S - O(1)
 */
class Solution2 {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);

        boolean found = false;
        int num = -1;
        for (int i = 2; i < nums.length; i += 3) {
            if (nums[i - 1] != nums[i - 2]) {
                num = nums[i - 2];
                found = true;
                break;
            }
        }

        if (!found) {
            num = nums[nums.length - 1];
        }

        return num;
    }
}

/*
Better - Count bits
T - O(32*n) or O(n)
S - O(1)

Same nums will have bits at same position.
The extra number will add one to its bits count, so those bit counts will not be multiple of 3

2 3 2 2

0 0 1 0
0 0 1 1
0 0 1 0
0 0 1 0
-------
0 0 4 1
-------
0 0 1 1 -> unique number
 */
class Solution3 {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int bitCount = 0;
            for (int num : nums) {
                if ((num >> i & 1) == 1) { // i-th bit set
                    bitCount++;
                }
            }

            if (bitCount % 3 != 0) { // bit is part of the result
                result |= 1 << i; // set i-th bit in result
            }
        }
        return result;
    }
}

/*
Optimal - 2-bit Finite State Machine (for 3 nums)
T - O(n)
S - O(1)

A bit has 4 states states -> 00 (Not appeared), 10 (Appeared once), 01 (Appeared twice), 00 (Appeared thrice or reset)
To represent these two bits, we need two numbers.
once, twice

Ex: 1 1 1 2
| Number | Binary | Once | Twice | Description|
 Start              0000    0000
 1          0001    0001    0000    bit 0 appeared once
 1          0001    0000    0001    bit 0 appeared twice
 1          0001    0000    0000    bit 0 appeared thrice
 2          0010    0010    0000    bit 1 appeared once

1
bit 0 first appearance (set)
once = (0 ^ 0001) & (~0) = 1 & (111111...1) = 1
twice = (0 ^ 0001) & (~1) = 1 & (0) = 0

bit 0 second appearance (flip)
once = (0001 ^ 0001) & (~0) = 0 & 111...11 = 0
twice = (0 ^ 0001) & (~0) = 0001 & 111...11 = 1

bit 0 third appearance (reset)
once = (0 ^ 0001) & (~1) = (0001) & (0000) = 0
twice = (1 ^ 0001) & (~0) = 0000 & 0000 = 0
 */
class Solution4 {
    public int singleNumber(int[] nums) {
        int once = 0;
        int twice = 0;
        for (int num : nums) {
            once = (once ^ num) & (~twice); // Add to once if not exists in twice
            twice = (twice ^ num) & (~once); // Add to twice if not exists in once.
        }

        return once; // bits appeared once
    }
}