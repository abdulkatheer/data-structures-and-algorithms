package io.abdul.hashing.faq.problem5;

import java.util.HashMap;

// https://takeuforward.org/plus/dsa/hashing/faqs/count-subarrays-with-given-xor-k
public class CountSubarraysWithXorK {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.subarraysWithXorK(new int[]{4, 2, 2, 6, 4}, 6));
        System.out.println(solution.subarraysWithXorK(new int[]{5, 6, 7, 8, 9}, 5));
        System.out.println(solution.subarraysWithXorK(new int[]{5, 2, 9}, 7));
    }
}

/*
Brute - All possible subarrays
T - O(n^3)
S - O(1)

 */
class Solution {
    public int subarraysWithXorK(int[] nums, int k) {
        int count = 0;
        for (int a = 0; a < nums.length; a++) {
            for (int b = nums.length - 1; b >= a; b--) {
                int xor = 0;
                for (int c = a; c <= b; c++) {
                    xor ^= nums[c];
                }
                if (xor == k) {
                    count++;
                }
            }
        }

        return count;
    }
}

/*
Better - Calculate xor once and avoid recomputations
T - O(n^2)
S - O(1)
 */
class Solution2 {
    public int subarraysWithXorK(int[] nums, int k) {
        int count = 0;
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        for (int a = 0; a < nums.length; a++) {
            int xorAta = xor;
            for (int b = nums.length - 1; b >= a; b--) {
                if (xorAta == k) {
                    count++;
                }
                xorAta ^= nums[b]; // Removing num[b] from current position's xor
            }
            xor ^= nums[a]; // Removing num[a] from overall xor
        }

        return count;
    }
}

/*
Optimal - Prefix xor, similar to prefix sum
T - O(n)
S - O(n)
 */
class Solution3 {
    public int subarraysWithXorK(int[] nums, int k) {
        HashMap<Integer, Integer> xorAndCount = new HashMap<>();
        int xorSoFar = 0;
        int count = 0;
        for (int num : nums) {
            xorSoFar ^= num;
            if (xorSoFar == k) { // Entire subarray to the left forms k
                count++;
            }

            int balance = xorSoFar ^ k;
            if (xorAndCount.containsKey(balance)) { // Partial subarray matches
                Integer numOfXors = xorAndCount.get(balance);
                count += numOfXors;
            }
            Integer c = xorAndCount.getOrDefault(xorSoFar, 0);
            xorAndCount.put(xorSoFar, c + 1);
        }

        return count;
    }
}