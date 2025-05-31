package io.abdul.greedy_algorithms.hard.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Candy {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

//        // Test Case 1: General case with varying ratings
//        int[] ratings1 = {1, 0, 5};
//        assertEquals(5, solution.candy(ratings1), "Test Case 1 Failed");
//
//        // Test Case 2: Ratings with a plateau
//        int[] ratings2 = {1, 2, 2};
//        assertEquals(4, solution.candy(ratings2), "Test Case 2 Failed");
//
//        // Test Case 3: Increasing and decreasing ratings
//        int[] ratings3 = {1, 2, 1, 4, 5};
//        assertEquals(9, solution.candy(ratings3), "Test Case 3 Failed");
//
//        // Test Case 4: Single child
//        int[] ratings4 = {3};
//        assertEquals(1, solution.candy(ratings4), "Test Case 4 Failed");
//
//        // Test Case 5: All children have the same rating
//        int[] ratings5 = {4, 4, 4, 4};
//        assertEquals(4, solution.candy(ratings5), "Test Case 5 Failed");
//
//        // Test Case 6: Strictly increasing ratings
//        int[] ratings6 = {1, 2, 3, 4, 5};
//        assertEquals(15, solution.candy(ratings6), "Test Case 6 Failed");
//
//        // Test Case 7: Strictly decreasing ratings
//        int[] ratings7 = {5, 4, 3, 2, 1};
//        assertEquals(15, solution.candy(ratings7), "Test Case 7 Failed");
//
//        // Test Case 8: Large input with alternating ratings
//        int[] ratings8 = new int[1000];
//        for (int i = 0; i < 1000; i++) {
//            ratings8[i] = (i % 2 == 0) ? 1 : 2;
//        }
//        assertEquals(1500, solution.candy(ratings8), "Test Case 8 Failed");
//
//        // Test Case 9: Large input with constant ratings
//        int[] ratings9 = new int[1000];
//        for (int i = 0; i < 1000; i++) {
//            ratings9[i] = 5;
//        }
//        assertEquals(1000, solution.candy(ratings9), "Test Case 9 Failed");

        assertEquals(19, solution.candy(new int[]{10, 9, 8, 1, 6, 7, 11}), "Test Case 7 Failed");

    }
}

/*
Brute
T - O(n) - 3n; n - candies for left; n - candies for right; n - find max
S - O(n) - 2n; n - left; n - right;
 */
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        int total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.max(left[i], right[i]);
        }

        return total;
    }
}

/*
Better
T - O(n) - 3n
S - O(n) - n
Same approach as above, but space is optimized
 */
class Solution2 {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int right = 1;
        int total = Math.max(right, left[n - 1]); // right most
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right = right + 1;
            } else {
                right = 1;
            }
            total += Math.max(left[i], right);
        }

        return total;
    }
}

/*
Optimal
T - O(n) - n
S - O(1)

Find the slops (both increasing and decreasing) and assign values from 1
For increasing trend, start from 1
For decreasing trend, actually we need to assign backwards. but we don't know where to start.
So start from 1 and replace the last peak with next value.
For same values, keep 1 always.
------>|<-----------|-|<-----|--->|--|
0 2 4 7 6 5 4 3 2 1 1 1 2 3 4 2 1 1 1
1 2 3 4 1 2 3 4 5 6 1 1 2 3 4 1 2 1 1
1 2 3 7 6 5 4 3 2 1 1 1 2 3 4 2 1 1 1
 */
class Solution3 {
    public int candy(int[] ratings) {
        int total = 1; // Assign 1 to first kid

        int i = 1;
        int n = ratings.length;
        while (i < n) {
            while (i < n && ratings[i] == ratings[i - 1]) {
                total += 1;
                i++;
            }

            int upward = 1;
            // start adding from 2 to x and end with x
            while (i < n && ratings[i] > ratings[i - 1]) { // Upward slope
                upward++;
                total += upward;
                i++;
            }

            int downward = 1;
            // start adding from 1 to x and end with x+1
            while (i < n && ratings[i] < ratings[i - 1]) {
                total += downward;
                downward++;
                i++;
            }

            if (upward < downward) { // make downward as the peak
                total -= upward;
                total += downward;
            }
        }

        return total;
    }
}

class Solution4 {
    // To calculate the number of candies
    public int candy(int[] ratings) {
        // Size of the ratings array
        int n = ratings.length;

        // Initialize index variable
        int i = 1;

        /*Initialize the total number of candies,
        starting with one candy for the first child*/
        int sum = 1;

        // Loop the ratings array
        while (i < n) {

            /*Check if the current child's rating
            is equal to the previous one*/
            if (ratings[i] == ratings[i - 1]) {

               /* If so, give the current child one
                more candy than the previous one*/
                sum = sum + 1;

                /* Move to the next child*/
                i++;

                /*Skip the rest of the loop and
                move to the next iteration*/
                continue;
            }

           /* Initialize the candy count
            for increasing rating trend*/
            int peak = 1;

            // Loop through increasing ratings trend
            while (i < n && ratings[i] > ratings[i - 1]) {

                /*Increment candy count
                for increasing trend*/
                peak += 1;

                /*Update the total
                number of candies*/
                sum += peak;

                // Move to next
                i++;
            }

            /*Initialize the candy count
            for decreasing rating trend*/
            int down = 1;

            // Loop through decreasing ratings trend
            while (i < n && ratings[i] < ratings[i - 1]) {

                /*Update the total number of
                candies for decreasing trend*/
                sum += down;

                // Move to next
                i++;

                /*Increment the candy
                count for decreasing trend*/
                down++;
            }

            /*Check if the candy count for
            decreasing trend exceeds the peak*/
            if (down > peak) {
                /*Adjust the total number of
                candies to satisfy the condition*/
                sum += (down - peak);
            }
        }

        // Return total candies
        return sum;
    }
}
