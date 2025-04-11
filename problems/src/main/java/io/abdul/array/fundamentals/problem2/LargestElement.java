package io.abdul.array.fundamentals.problem2;

public class LargestElement {
}

class Solution {
    public int largestElement(int[] nums) {
        int largest = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > largest) {
                largest = nums[i];
            }
        }

        return largest;
    }
}
