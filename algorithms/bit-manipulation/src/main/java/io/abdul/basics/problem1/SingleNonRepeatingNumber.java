package io.abdul.basics.problem1;

public class SingleNonRepeatingNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findNonRepeating(new int[]{1, 2, 3, 8, 4, 5, 4, 3, 2, 5, 1}));
    }
}

/*
Using self-inverse and identity property
x ^ x = 0;
x ^ 0 = x
 */
class Solution {
    public int findNonRepeating(int[] nums) {
        int num = 0;
        for (int n : nums) {
            num = n ^ num;
        }

        return num;
    }
}
