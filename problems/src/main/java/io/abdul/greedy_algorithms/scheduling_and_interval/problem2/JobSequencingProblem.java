package io.abdul.greedy_algorithms.scheduling_and_interval.problem2;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/*
Pick highest deadline jobs and take highest profit one from it - We can do only that - Not optimal
Pick lowest deadline jobs and take highest profit. Once its done, we can look for next lowest deadline jobs and highest profit from it - Not optimal
Pick highest value with lowest deadline
 */
public class JobSequencingProblem {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: General case with mixed deadlines and profits
        int[][] jobs1 = {{1, 4, 20}, {2, 1, 10}, {3, 1, 40}, {4, 1, 30}};
        assertArrayEquals(new int[]{2, 60}, solution.JobScheduling(jobs1), "Test Case 1 Failed");

        // Test Case 2: Jobs with overlapping deadlines
        int[][] jobs2 = {{1, 2, 100}, {2, 1, 19}, {3, 2, 27}, {4, 1, 25}, {5, 1, 15}};
        assertArrayEquals(new int[]{2, 127}, solution.JobScheduling(jobs2), "Test Case 2 Failed");

        // Test Case 3: All jobs can be scheduled
        int[][] jobs3 = {{1, 1, 100}, {2, 2, 200}, {3, 3, 300}, {4, 4, 400}};
        assertArrayEquals(new int[]{4, 1000}, solution.JobScheduling(jobs3), "Test Case 3 Failed");

        // Test Case 4: Single job
        int[][] jobs4 = {{1, 1, 50}};
        assertArrayEquals(new int[]{1, 50}, solution.JobScheduling(jobs4), "Test Case 4 Failed");

        // Test Case 5: Jobs with the same deadline
        int[][] jobs5 = {{1, 2, 50}, {2, 2, 60}, {3, 2, 70}};
        assertArrayEquals(new int[]{2, 130}, solution.JobScheduling(jobs5), "Test Case 5 Failed");

        // Test Case 6: Jobs with zero profit
        int[][] jobs6 = {{1, 1, 0}, {2, 2, 0}, {3, 3, 0}};
        assertArrayEquals(new int[]{3, 0}, solution.JobScheduling(jobs6), "Test Case 6 Failed");

        // Test Case 7: Large input with identical deadlines and profits
        int[][] jobs7 = new int[1000][3];
        for (int i = 0; i < 1000; i++) {
            jobs7[i][0] = i + 1;
            jobs7[i][1] = 1;
            jobs7[i][2] = 10;
        }
        assertArrayEquals(new int[]{1, 10}, solution.JobScheduling(jobs7), "Test Case 7 Failed");

        // Test Case 8: Large input with increasing deadlines and profits
        int[][] jobs8 = new int[1000][3];
        for (int i = 0; i < 1000; i++) {
            jobs8[i][0] = i + 1;
            jobs8[i][1] = i + 1;
            jobs8[i][2] = i + 1;
        }
        assertArrayEquals(new int[]{1000, 500500}, solution.JobScheduling(jobs8), "Test Case 8 Failed");

    }
}

/*
Optimal - Greedy
T - O(n^2) - n logn + n^2; n logn to sort; n^2 to assign
S - O(1)

Doing task with earliest deadline and high value is beneficial
Deadline is between 1 and N. Bounded.
Sort by value of job.
Take it if it can be done, otherwise move on.
 */
class Solution {
    public int[] JobScheduling(int[][] Jobs) {
        int n = Jobs.length;
        Arrays.sort(Jobs, (o1, o2) -> Integer.compare(o2[2], o1[2]));

        int jobs = 0;
        int profit = 0;
        int[] jobSchedule = new int[n + 1]; // for days 1 to N, 0 is unused
        for (int[] job : Jobs) { // Iterate from highest value job and try to assign a best slot (deadline to 1)
            // Task can be best done on the last day and worst done on 1st day
            for (int j = job[1]; j >= 1; j--) {
                if (jobSchedule[j] == 0) {
                    jobSchedule[j] = job[0]; // Storing Job ID
                    jobs++;
                    profit += job[2];
                    break;
                }
            }
        }

        return new int[]{jobs, profit};
    }
}
