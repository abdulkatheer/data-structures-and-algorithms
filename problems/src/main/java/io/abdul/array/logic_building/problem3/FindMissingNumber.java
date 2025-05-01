package io.abdul.array.logic_building.problem3;

public class FindMissingNumber {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.missingNumber(new int[]{0, 2, 3, 1, 4}));
        System.out.println(solution.missingNumber(new int[]{0, 1, 2, 4, 5, 6}));
        System.out.println(solution.missingNumber(new int[]{1, 3, 6, 4, 2, 5}));
    }
}

class Solution {
    public int missingNumber(int[] nums) {
        // AP
        int expectedSum = (nums.length * (nums.length + 1)) / 2;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        return expectedSum - sum;
    }
}
