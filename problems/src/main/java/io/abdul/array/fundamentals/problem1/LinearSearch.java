package io.abdul.array.fundamentals.problem1;

// https://takeuforward.org/plus/dsa/arrays/fundamentals/linear-search
public class LinearSearch {
}

class Solution {
    public int linearSearch(int nums[], int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
