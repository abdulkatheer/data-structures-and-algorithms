package io.abdul.array.greedy_or_optimization.problem2;

// https://leetcode.com/problems/lemonade-change/
public class LemonadeChange {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lemonadeChange(new int[]{5, 5, 5, 10, 20}));
        System.out.println(solution.lemonadeChange(new int[]{5, 5, 10, 10, 20}));
    }
}

/*
Greedy method - Every iteration we check if we've found the solution, otherwise stopping the program
Time - O(n)
Space - O(1)
 */
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int fives = 0, tens = 0, twenties = 0;
        boolean feasible = true;
        for (int bill : bills) {
            if (bill == 5) {
                fives++;
            } else if (bill == 10) {
                if (fives > 0) {
                    fives--;
                } else {
                    feasible = false;
                    break;
                }
                tens++;
            } else if (bill == 20) {
                if (tens > 0) {
                    if (fives > 0) {
                        tens--;
                        fives--;
                    } else { // if even a single five is not available, else will any be false
                        feasible = false;
                        break;
                    }
                } else {
                    if (fives > 2) {
                        fives = fives - 3;
                    } else {
                        feasible = false;
                        break;
                    }
                }
                twenties++;
            }
        }
        return feasible;
    }
}