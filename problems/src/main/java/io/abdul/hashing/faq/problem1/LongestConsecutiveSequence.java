package io.abdul.hashing.faq.problem1;

import java.util.Arrays;
import java.util.HashSet;

public class LongestConsecutiveSequence {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        System.out.println(solution.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        System.out.println(solution.longestConsecutive(new int[]{1, 9, 3, 10, 4, 20, 2}));
        System.out.println(solution.longestConsecutive(new int[]{17, 12, -8, -11, 14, -19, 9, -4, -11, -12, -8, 5, 15, 14, 9, -19, 12, 5, 0, 18, 13, -1, 3, 19, 16, -13, -11, 9}));
    }
}

/*
Better - Sort and count!
T - O(n logn) for sorting
S - O(1)
 */
class Solution {
    public int longestConsecutive(int[] nums) {
        Arrays.sort(nums);

        int maxCount = Integer.MIN_VALUE;
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                count++;
            } else if (nums[i] == nums[i - 1]) {
                continue; // Ignore repetitive
            } else {
                if (count > maxCount) {
                    maxCount = count;
                }
                count = 1;
            }
        }

        return Math.max(count, maxCount);
    }
}

/*
Optimal - HashSet for quick lookup of sequences for an element and also skipping duplicates
T - O(n) - to store into HashSet.
S - O(n) for the HashSet

Sequence lookup will not be O(n^2) bcz iteration will stop as soon as we don't find next element. In worst case, where no sequence exists, we iterate for 2n times
 */

class Solution2 {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> numSet = new HashSet<>();

        for (int num : nums) {
            numSet.add(num);
        }

        int maxCount = Integer.MIN_VALUE;

        for (Integer n : numSet) {
            int count = 1;
            int nextElement = n + 1;
            while (numSet.contains(nextElement)) {
                count++;
                nextElement++;
            }
            if (count > maxCount) {
                maxCount = count;
            }
        }

        return maxCount;
    }
}