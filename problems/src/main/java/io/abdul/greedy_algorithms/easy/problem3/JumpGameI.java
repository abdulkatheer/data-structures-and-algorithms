package io.abdul.greedy_algorithms.easy.problem3;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JumpGameI {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();

        // Test Case 1: Possible to reach the last index
        int[] nums1 = {2, 3, 1, 1, 4};
        assertTrue(solution.canJump(nums1), "Test Case 1 Failed");

        // Test Case 2: Not possible to reach the last index
        int[] nums2 = {3, 2, 1, 0, 4};
        assertFalse(solution.canJump(nums2), "Test Case 2 Failed");

        // Test Case 3: Possible to reach the last index with a large jump
        int[] nums3 = {5, 3, 2, 1, 0};
        assertTrue(solution.canJump(nums3), "Test Case 3 Failed");

        // Test Case 4: Single element array
        int[] nums4 = {0};
        assertTrue(solution.canJump(nums4), "Test Case 4 Failed");

        // Test Case 5: All elements are zero except the first
        int[] nums5 = {1, 0, 0, 0};
        assertFalse(solution.canJump(nums5), "Test Case 5 Failed");

        // Test Case 6: All elements are zero
        int[] nums6 = {0, 0, 0, 0};
        assertFalse(solution.canJump(nums6), "Test Case 6 Failed");

        // Test Case 7: Large input with all elements as maximum jump
        int[] nums7 = new int[10000];
        Arrays.fill(nums7, 10000);
        assertTrue(solution.canJump(nums7), "Test Case 7 Failed");

        // Test Case 8: Large input with a zero blocking the path
        int[] nums8 = new int[10000];
        for (int i = 0; i < nums8.length - 1; i++) {
            nums8[i] = 1;
        }
        nums8[nums8.length - 2] = 0;
        assertFalse(solution.canJump(nums8), "Test Case 8 Failed");

    }
}

/*
Brute - Explore all combinations
 */
class Solution {
    public boolean canJump(int[] nums) {
        return false;
    }
}

/*
Optimal - Greedy
T - O(n)
S - O(1)

2 3 1 0 2 0 4
0 -> 2
1 -> 4
2 -> 4
3 -> 4
4 -> 6
5 -> 6
6 -> 10 | Stop

2 3 1 1 4
0 -> 2
1 -> 4 | Stop
 */
class Solution2 {
    public boolean canJump(int[] nums) {
        int n = nums.length - 1;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) { // Can't reach i from any of the previous positions
                return false;
            }
            int dist = i + nums[i];
            max = Math.max(dist, max);
            if (max >= n) {
                return true;
            }
        }
        return false;
    }
}
