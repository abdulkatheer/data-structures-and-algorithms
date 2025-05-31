package io.abdul.greedy_algorithms.easy.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LemonadeChange {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: All customers receive correct change
        int[] bills1 = {5, 5, 10, 5, 20};
        assertTrue(solution.lemonadeChange(bills1), "Test Case 1 Failed");

        // Test Case 2: Unable to provide correct change
        int[] bills2 = {5, 5, 10, 10, 20};
        assertFalse(solution.lemonadeChange(bills2), "Test Case 2 Failed");

        // Test Case 3: Exact change is possible
        int[] bills3 = {5, 5, 10, 20};
        assertTrue(solution.lemonadeChange(bills3), "Test Case 3 Failed");

        // Test Case 4: Only $5 bills
        int[] bills4 = {5, 5, 5, 5};
        assertTrue(solution.lemonadeChange(bills4), "Test Case 4 Failed");

        // Test Case 5: Only $20 bills (impossible to give change)
        int[] bills5 = {20, 20, 20};
        assertFalse(solution.lemonadeChange(bills5), "Test Case 5 Failed");

        // Test Case 6: No customers
        int[] bills6 = {};
        assertTrue(solution.lemonadeChange(bills6), "Test Case 6 Failed");

        // Test Case 7: Large input with valid change
        int[] bills7 = new int[100000];
        Arrays.fill(bills7, 5);
        assertTrue(solution.lemonadeChange(bills7), "Test Case 7 Failed");

        // Test Case 8: Large input with invalid change
        int[] bills8 = {5, 10, 20, 20, 20};
        assertFalse(solution.lemonadeChange(bills8), "Test Case 8 Failed");

    }
}

/*
Optimal
T - O(n)
S - O(1)
 */
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int fives = 0, tens = 0, twenties = 0;
        for (int bill : bills) {
            if (bill == 5) {
                fives++;
            } else if (bill == 10) {
                if (fives >= 1) {
                    fives--;
                    tens++;
                } else {
                    return false;
                }
            } else {
                if (tens >= 1 && fives >= 1) {
                    tens--;
                    fives--;
                    twenties++;
                } else if (fives >= 3) {
                    fives = fives - 3;
                    twenties++;
                } else {
                    return false;
                }
            }
        }

        return true;
    }
}
