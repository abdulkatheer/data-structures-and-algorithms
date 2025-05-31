package io.abdul.greedy_algorithms.scheduling_and_interval.problem4;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonOverlappingIntervals {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: General case with overlapping intervals
        int[][] intervals1 = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        assertEquals(1, solution.MaximumNonOverlappingIntervals(intervals1), "Test Case 1 Failed");

        // Test Case 2: Multiple overlapping intervals
        int[][] intervals2 = {{1, 3}, {1, 4}, {3, 5}, {3, 4}, {4, 5}};
        assertEquals(2, solution.MaximumNonOverlappingIntervals(intervals2), "Test Case 2 Failed");

        // Test Case 3: Highly overlapping intervals
        int[][] intervals3 = {{1, 10}, {1, 4}, {3, 8}, {3, 4}, {4, 5}};
        assertEquals(3, solution.MaximumNonOverlappingIntervals(intervals3), "Test Case 3 Failed");

        // Test Case 4: Non-overlapping intervals
        int[][] intervals4 = {{1, 2}, {3, 4}, {5, 6}};
        assertEquals(0, solution.MaximumNonOverlappingIntervals(intervals4), "Test Case 4 Failed");

        // Test Case 5: Single interval
        int[][] intervals5 = {{1, 2}};
        assertEquals(0, solution.MaximumNonOverlappingIntervals(intervals5), "Test Case 5 Failed");

        // Test Case 6: All intervals overlap
        int[][] intervals6 = {{1, 5}, {2, 6}, {3, 7}, {4, 8}};
        assertEquals(3, solution.MaximumNonOverlappingIntervals(intervals6), "Test Case 6 Failed");

        // Test Case 7: Large input with no overlaps
        int[][] intervals7 = new int[1000][2];
        for (int i = 0; i < 1000; i++) {
            intervals7[i][0] = i * 2;
            intervals7[i][1] = i * 2 + 1;
        }
        assertEquals(0, solution.MaximumNonOverlappingIntervals(intervals7), "Test Case 7 Failed");

        // Test Case 8: Large input with all intervals overlapping
        int[][] intervals8 = new int[1000][2];
        for (int i = 0; i < 1000; i++) {
            intervals8[i][0] = 0;
            intervals8[i][1] = 1;
        }
        assertEquals(999, solution.MaximumNonOverlappingIntervals(intervals8), "Test Case 8 Failed");

    }
}

/*
T - O(n logn) - n logn + n; n logn for sorting; n to find meeting
S - O(1)

Same as meeting room allocation, just need to pick which meeting is not possible or has to be removed
[1, 10] , [1, 4] , [3, 8] , [3, 4] , [4, 5]
[3, 4] , [1, 4] , [4, 5] , [3, 8] , [1, 10]

[3, 4]
[4, 5]

[1, 4]
[4, 5]
 */
class Solution {
    public int MaximumNonOverlappingIntervals(int[][] Intervals) {
        Arrays.sort(Intervals, Comparator.comparingInt(o -> o[1]));

        int n = Intervals.length;

        int lastInterval = 0; // First interval which ends the earliest will happen always
        int toBeRemoved = 0;
        for (int i = 1; i < n; i++) {
            if (Intervals[i][0] >= Intervals[lastInterval][1]) { // start time is >= lastInterval's end time
                lastInterval = i;
            } else {
                toBeRemoved++;
            }
        }

        return toBeRemoved;
    }
}