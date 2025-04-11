package io.abdul.array.greedy_or_optimization.problem3;

import java.util.Arrays;

// https://www.geeksforgeeks.org/problems/shortest-job-first/1
public class ShortestJobFirst {
    public static void main(String[] args) {
        System.out.println(Solution.solve(new int[]{4, 3, 7, 1, 2}));
        System.out.println(Solution.solve(new int[]{1, 2, 3, 4}));
    }
}

class Solution {
    static int solve(int[] bt) {
        /*
        1 2 3 4 7
        0 1 3 6 10 = 20/5 = 4

        1 2 3 4
        0 1 3 6 = 10/4 = 2
         */
        Arrays.sort(bt); // Time - O(n logn)
        int perJobWaitTime = 0;
        int totalWaitTime = 0;
        for (int i = 1; i < bt.length; i++) { // Time - O(n)
            perJobWaitTime += bt[i - 1]; // waits for all previous jobs, wait time of 1 => 0, 2 => 0+1, 3 => 0+1+2
            totalWaitTime += perJobWaitTime;
        }
        return totalWaitTime / bt.length;
    }
}
