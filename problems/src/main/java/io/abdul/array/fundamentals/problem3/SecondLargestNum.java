package io.abdul.array.fundamentals.problem3;

public class SecondLargestNum {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.secondLargestElement(new int[]{1}));
        System.out.println(solution.secondLargestElement(new int[]{1, 1}));
        System.out.println(solution.secondLargestElement(new int[]{1, 2}));
        System.out.println(solution.secondLargestElement(new int[]{10, 2, 5}));
        System.out.println(solution.secondLargestElement(new int[]{10, 10, 9, 9}));

        System.out.println(solution.secondLargestElement(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE + 1}));
    }
}

class Solution {
    public int secondLargestElement(int[] nums) {
        int largest = nums[0];
        int secondLargest = Integer.MIN_VALUE;

        boolean found = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > largest) {
                found = true;
                secondLargest = largest;
                largest = nums[i];
            } else if (nums[i] < largest && nums[i] > secondLargest) {
                found = true;
                secondLargest = nums[i];
            }
        }

        return found ? secondLargest : -1;
    }
}