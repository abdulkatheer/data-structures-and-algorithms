package io.abdul.array.greedy_or_optimization.problem1;

import java.util.Arrays;

// https://leetcode.com/problems/assign-cookies/
public class AssignCookies {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findContentChildren(new int[]{1, 2, 3}, new int[]{1, 2}));
    }
}

/*
Two pointer + Greedy method
Time - O(g + s)
Space - O(1)
 */
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        /*
        Greedy method
        At every step, we'll make sure we're assigning cookies optimally to maximize count
        So sort both arrays, and have two pointers
         */
        Arrays.sort(g);
        Arrays.sort(s);

        int left = 0;
        int right = 0;
        int count = 0;

        while (left < s.length && right < g.length) {
            // Try to find the smallest possible cookies you can assign to the least greedy person
            if (s[left] >= g[right]) {
                count++;
                left++;
                right++;
            } else {
                left++;
            }
        }

        return count;
    }
}
