package io.abdul.sliding_window.constant_window.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example 1
        assertEquals(15, solution.maxScore(new int[]{1, 2, 3, 4, 5, 6}, 3));

        // Example 2
        assertEquals(12, solution.maxScore(new int[]{5, 4, 1, 8, 7, 1, 3}, 3));

        // Example 3
        assertEquals(29, solution.maxScore(new int[]{9, 10, 1, 2, 3, 5}, 5));

        // All cards taken
        assertEquals(21, solution.maxScore(new int[]{1, 2, 3, 4, 5, 6}, 6));

        // k = 1, take max of first or last
        assertEquals(6, solution.maxScore(new int[]{6, 1, 2, 3, 4, 5}, 1));

        // Only one card
        assertEquals(7, solution.maxScore(new int[]{7}, 1));

        // All same values
        assertEquals(9, solution.maxScore(new int[]{3, 3, 3, 3, 3}, 3));

        // Take from both ends
        assertEquals(10, solution.maxScore(new int[]{2, 2, 2, 2, 2, 6}, 3));
    }
}

class Solution {
    public int maxScore(int[] cardScore, int k) {
        int leftSum = 0, rightSum = 0;

        for (int i = 0; i < k; i++) {
            leftSum += cardScore[i];
        }

        if (cardScore.length == k) {
            return leftSum;
        }

        int sum = leftSum;
        for (int i = k - 1, j = cardScore.length - 1; i >= 0; i--, j--) {
            leftSum -= cardScore[i];
            rightSum += cardScore[j];
            sum = Math.max(sum, leftSum + rightSum);
        }

        return sum;
    }
}