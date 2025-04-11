package io.abdul.array.subarray_problems;

public class Subarray1D {
    public static void main(String[] args) {
        int[] nums = new int[5];

        int numOfSubarrays = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                numOfSubarrays++;
            }
        }

        System.out.println(numOfSubarrays);
    }
}
