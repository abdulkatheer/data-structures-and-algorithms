package io.abdul.greedy_algorithms.leetcode.p_630;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseScheduleIII {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: General case with multiple courses
        int[][] courses1 = {{100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}};
        assertEquals(3, solution.scheduleCourse(courses1), "Test Case 1 Failed");

        // Test Case 2: Single course that can be taken
        int[][] courses2 = {{1, 2}};
        assertEquals(1, solution.scheduleCourse(courses2), "Test Case 2 Failed");

        // Test Case 3: No course can be taken
        int[][] courses3 = {{3, 2}, {4, 3}};
        assertEquals(0, solution.scheduleCourse(courses3), "Test Case 3 Failed");

        // Test Case 4: All courses can be taken
        int[][] courses4 = {{1, 2}, {2, 3}, {1, 4}};
        assertEquals(3, solution.scheduleCourse(courses4), "Test Case 4 Failed");

        // Test Case 5: Some courses overlap and cannot be taken
        int[][] courses5 = {{5, 5}, {4, 6}, {2, 6}};
        assertEquals(2, solution.scheduleCourse(courses5), "Test Case 5 Failed");

        // Test Case 6: Large input with non-overlapping courses
        int[][] courses6 = new int[1000][2];
        for (int i = 0; i < 1000; i++) {
            courses6[i][0] = 1;
            courses6[i][1] = i + 1;
        }
        assertEquals(1000, solution.scheduleCourse(courses6), "Test Case 6 Failed");

        // Test Case 7: Large input with overlapping courses
        int[][] courses7 = new int[1000][2];
        for (int i = 0; i < 1000; i++) {
            courses7[i][0] = i + 1;
            courses7[i][1] = 1000;
        }
        assertEquals(44, solution.scheduleCourse(courses7), "Test Case 7 Failed");

        // Test Case 8: Course duration exceeds its last day
        int[][] courses8 = {{10, 5}, {5, 5}};
        assertEquals(1, solution.scheduleCourse(courses8), "Test Case 8 Failed");

        assertEquals(6, solution.scheduleCourse(new int[][]{{2, 6}, {2, 10}, {2, 100}, {2, 20}, {2, 4}, {2, 6}}), "Test Case 8 Failed");

    }
}

/*
Why don't we solve meeting room problem like this?
Bcz meeting time is fixed, from and to is fixed. Here we've flexibility with start and end time.
Ex1: {2, 6}, {2, 10}, {2, 100}, {2, 20}, {2, 4}, {2, 6}
We can only pick one meeting, 2,4
Ex2: {1, 2}, {2, 3}, {1, 4}
We can pick 2. 1,2 and 2,3
 */
class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, Comparator.comparingInt(o -> o[1])); // sort by deadline
        // We need to consider courses which completes earlier

        PriorityQueue<Integer> courseDurations = new PriorityQueue<>(Comparator.reverseOrder()); // Selected courses' duration desc order
        int timeElapsed = 0;
        for (int[] c : courses) {
            int deadline = c[1];
            int duration = c[0];
            if (timeElapsed + duration <= deadline) { // course deadline is fine
                courseDurations.add(duration);
                timeElapsed += duration;
            } else {
                if (!courseDurations.isEmpty() && courseDurations.peek() > duration) { // the longest course duration is greater than current course duration
                    // we can trade-in the longest course with a shorter course
                    timeElapsed -= courseDurations.poll();
                    courseDurations.add(duration);
                    timeElapsed += duration;
                } else { // the current course duration is longer than or equals to the longest course duration.
                    // no value in replacing the same duration
                    // bring harm if we replace with a longer duration
                    // So reject the course
                    continue;
                    // Dummy block for information
                }
            }
        }

        return courseDurations.size();
    }
}