package io.abdul.greedy_algorithms.scheduling_and_interval.problem5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InsertInterval {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: General case with overlapping intervals
        int[][] intervals1 = {{1, 3}, {6, 9}};
        int[] newInterval1 = {2, 5};
        int[][] expected1 = {{1, 5}, {6, 9}};
        assertArrayEquals(expected1, solution.insertNewInterval(intervals1, newInterval1), "Test Case 1 Failed");

        // Test Case 2: Overlapping multiple intervals
        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}};
        int[] newInterval2 = {4, 8};
        int[][] expected2 = {{1, 2}, {3, 10}};
        assertArrayEquals(expected2, solution.insertNewInterval(intervals2, newInterval2), "Test Case 2 Failed");

        // Test Case 3: New interval overlaps all intervals
        int[][] intervals3 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}};
        int[] newInterval3 = {1, 8};
        int[][] expected3 = {{1, 10}};
        assertArrayEquals(expected3, solution.insertNewInterval(intervals3, newInterval3), "Test Case 3 Failed");

        // Test Case 4: New interval does not overlap and is added at the end
        int[][] intervals4 = {{1, 2}, {3, 5}};
        int[] newInterval4 = {6, 7};
        int[][] expected4 = {{1, 2}, {3, 5}, {6, 7}};
        assertArrayEquals(expected4, solution.insertNewInterval(intervals4, newInterval4), "Test Case 4 Failed");

        // Test Case 5: New interval does not overlap and is added at the beginning
        int[][] intervals5 = {{3, 5}, {6, 7}};
        int[] newInterval5 = {1, 2};
        int[][] expected5 = {{1, 2}, {3, 5}, {6, 7}};
        assertArrayEquals(expected5, solution.insertNewInterval(intervals5, newInterval5), "Test Case 5 Failed");

        // Test Case 6: Empty intervals array
        int[][] intervals6 = {};
        int[] newInterval6 = {1, 5};
        int[][] expected6 = {{1, 5}};
        assertArrayEquals(expected6, solution.insertNewInterval(intervals6, newInterval6), "Test Case 6 Failed");

        // Test Case 7: New interval completely inside an existing interval
        int[][] intervals7 = {{1, 10}};
        int[] newInterval7 = {2, 5};
        int[][] expected7 = {{1, 10}};
        assertArrayEquals(expected7, solution.insertNewInterval(intervals7, newInterval7), "Test Case 7 Failed");

        // Test Case 8: Large input with no overlaps
        int[][] intervals8 = new int[1000][2];
        for (int i = 0; i < 1000; i++) {
            intervals8[i][0] = i * 2;
            intervals8[i][1] = i * 2 + 1;
        }
        int[] newInterval8 = {2001, 2002};
        int[][] expected8 = Arrays.copyOf(intervals8, intervals8.length + 1);
        expected8[1000] = newInterval8;
        assertArrayEquals(expected8, solution.insertNewInterval(intervals8, newInterval8), "Test Case 8 Failed");

    }
}

class Solution {
    public int[][] insertNewInterval(int[][] Intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int n = Intervals.length;

        // Intervals before newInterval
        int i = 0;
        while (i < n && Intervals[i][1] < newInterval[0]) { // end time of existing intervals less than start time of new interval
            result.add(Intervals[i]);
            i++;
        }

        // Intervals overlapping with newInterval; Update newInterval with merged values
        while (i < n && Intervals[i][0] <= newInterval[1]) { // Start time of existing intervals less than or equals end time of newInterval
            newInterval[0] = Math.min(Intervals[i][0], newInterval[0]);
            newInterval[1] = Math.max(Intervals[i][1], newInterval[1]);
            i++;
        }
        result.add(newInterval); // adding merged newInterval

        // Intervals after newInterval
        while (i < n) { // start time of existing intervals greater than end time of new interval
            result.add(Intervals[i]);
            i++;
        }

        return result.toArray(new int[][]{});
    }
}
