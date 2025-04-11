package io.abdul.array.practice.problem5;

// #tag_array
public class MaximumSubArraySum {
    // O(n^2)
    private static int maxSubArrayV1(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }

        int maxSoFar = Integer.MIN_VALUE;
        int currentMax = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) { // finding sum and max for every sub-array combination
                currentMax += nums[j];
                if (currentMax > maxSoFar) {
                    maxSoFar = currentMax;
                }
            }
            currentMax = 0; // resetting for next iteration
        }
        return maxSoFar;
        /*
        -2 | -2
        -2 + 1 | -1
        -2 + 1 + -3 | -1
        -2 + 1 + -3 + 4 | 0
        -2 + 1 + -3 + 4 - 1 | 0
        -2 + 1 + -3 + 4 - 1 + 2 | 1
        -2 + 1 + -3 + 4 - 1 + 2 + 1 | 2
        -2 + 1 + -3 + 4 - 1 + 2 + 1 - 5 | 2
        -2 + 1 + -3 + 4 - 1 + 2 + 1 - 5 + 4 | 2

        1 | 2
        1 - 3 | 2
        1 - 3 + 4 | 2
        1 - 3 + 4 - 1 | 2
        1 - 3 + 4 - 1 + 2 | 3
        1 - 3 + 4 - 1 + 2 + 1 | 4
        1 - 3 + 4 - 1 + 2 + 1 - 5 | 4
        1 - 3 + 4 - 1 + 2 + 1 - 5 + 4 | 4

        -3 | 4
        -3 + 4 | 4
        -3 + 4 - 1 | 4
        -3 + 4 - 1 + 2 | 4
        -3 + 4 - 1 + 2 + 1 | 4
        -3 + 4 - 1 + 2 + 1 - 5 | 4
        -3 + 4 - 1 + 2 + 1 - 5 + 4 | 4

        4 | 4
        4 - 1 | 4
        4 - 1 + 2 | 5
        4 - 1 + 2 + 1 | 6
        4 - 1 + 2 + 1 - 5 | 6
        4 - 1 + 2 + 1 - 5 + 4 | 6

        -1 | 6
        -1 + 2 | 6
        -1 + 2 + 1 | 6
        -1 + 2 + 1 - 5 | 6
        -1 + 2 + 1 - 5 + 4 | 6

        ....
         */
    }

    // O(n)
    private static int maxSubArrayV2(int[] nums) {
        int maxSoFar = Integer.MIN_VALUE;
        int maxAtCurrentIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            maxAtCurrentIndex = maxAtCurrentIndex + nums[i];
            if (maxAtCurrentIndex > maxSoFar) {
                maxSoFar = maxAtCurrentIndex;
            }
            if (maxAtCurrentIndex < 0) { // if adding him is liability, leave him and everyone else.
                maxAtCurrentIndex = 0;
            }
        }
        return maxSoFar;
    }

    public static void main(String[] args) {
        System.out.println("V1");
        System.out.println(maxSubArrayV1(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArrayV1(new int[]{1}));
        System.out.println(maxSubArrayV1(new int[]{5, 4, -1, 7, 8}));

        System.out.println("V2");
        System.out.println(maxSubArrayV2(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArrayV2(new int[]{1}));
        System.out.println(maxSubArrayV2(new int[]{5, 4, -1, 7, 8}));
    }
}
