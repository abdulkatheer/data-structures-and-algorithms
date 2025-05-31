package io.abdul.greedy_algorithms.scheduling_and_interval.problem6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.abdul.util.SortTwoArrays.heapSort;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumPlatforms {
    public static void main(String[] args) {
//        Solution1 solution = new Solution1();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: General case with overlapping trains
        int[] arrival1 = {900, 940, 950, 1100, 1500, 1800};
        int[] departure1 = {910, 1200, 1120, 1130, 1900, 2000};
        assertEquals(3, solution.findPlatform(arrival1, departure1), "Test Case 1 Failed");

        // Test Case 2: All trains can use the same platform
        int[] arrival2 = {900, 1100, 1235};
        int[] departure2 = {1000, 1200, 1240};
        assertEquals(1, solution.findPlatform(arrival2, departure2), "Test Case 2 Failed");

        // Test Case 3: Some trains overlap
        int[] arrival3 = {900, 1000, 1200};
        int[] departure3 = {1000, 1200, 1240};
        assertEquals(2, solution.findPlatform(arrival3, departure3), "Test Case 3 Failed");

        // Test Case 4: Single train
        int[] arrival4 = {900};
        int[] departure4 = {1000};
        assertEquals(1, solution.findPlatform(arrival4, departure4), "Test Case 4 Failed");

        // Test Case 5: All trains overlap
        int[] arrival5 = {900, 905, 910};
        int[] departure5 = {920, 925, 930};
        assertEquals(3, solution.findPlatform(arrival5, departure5), "Test Case 5 Failed");

        // Test Case 6: No trains
        int[] arrival6 = {};
        int[] departure6 = {};
        assertEquals(0, solution.findPlatform(arrival6, departure6), "Test Case 6 Failed");

        // Test Case 7: Large input with no overlaps
        int[] arrival7 = new int[1000];
        int[] departure7 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arrival7[i] = i * 10;
            departure7[i] = i * 10 + 5;
        }
        assertEquals(1, solution.findPlatform(arrival7, departure7), "Test Case 7 Failed");

        // Test Case 8: Large input with all trains overlapping
        int[] arrival8 = new int[1000];
        int[] departure8 = new int[1000];
        for (int i = 0; i < 1000; i++) {
            arrival8[i] = 900;
            departure8[i] = 1000;
        }
        assertEquals(1000, solution.findPlatform(arrival8, departure8), "Test Case 8 Failed");

    }
}

/*
Arrival = [0900, 0940, 0950, 1100, 1500, 1800] , Departure = [0910, 1200, 1120, 1130, 1900, 2000]
1500 2000 1130 -> 3

0900 0910
0940 1200
0950 1120
1100 1130
1500 1900
1800 2000
 */

/*
Brute
T - O(n logn) + O(n * p) - n is number of trains; p is number of platforms; p may equals n when all trains clash
S - O(p)
 */
class Solution {
    public int findPlatform(int[] Arrival, int[] Departure) {
        int n = Arrival.length;
        heapSort(Departure, Arrival); // Sort by Arrivals

        List<Integer> platforms = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            boolean found = false;
            for (int j = 0; j < platforms.size(); j++) {
                if (Arrival[i] > platforms.get(j)) {
                    found = true;
                    platforms.set(j, Departure[i]);
                    break;
                }
            }
            if (!found) { // Need new platform
                platforms.add(Departure[i]);
            }
        }

        return platforms.size();
    }
}

/*
Better
T - O(n^2)
S - O(1)

Find intersections
arr, dept
arr >= arr[j] && arr < dept[j] -> j between arr and dept (inclusive)

Intersection - By the time a train reaches a station, how many trains are on the platform ( parked / just reached / set to release )
 */
class Solution2 {
    public int findPlatform(int[] Arrival, int[] Departure) {
        int n = Arrival.length;

        int max = 0;
        for (int i = 0; i < n; i++) {
            int arr = Arrival[i];
            int intersections = 1; // When two intersects, we need three platforms
            for (int j = 0; j < n; j++) {
                if (i == j) { // same train
                    continue;
                }
                if (Arrival[j] < arr && Departure[j] > arr) { // j arrives before i and leaves after i
                    intersections++;
                }
            }
            max = Math.max(intersections, max);
        }

        return max;
    }
}

/*
Optimal
T - O(n logn) - 2 (n logn) + n - n logn to sort Arrival; n logn to sort Departure; n+n to track arrival and departure
S - O(1)

Track the sequence of events by time. We'll know at max how many are in the platform.
We don't need to know which train arrives and departs, we just need how many platforms occupied at any given point in time.

Arrival = [0900, 0940, 0950, 1100, 1500, 1800] , Departure = [0910, 1200, 1120, 1130, 1900, 2000]
Arrival = [0900, 0940, 0950, 1100, 1500, 1800] , Departure = [0910, 1120, 1130, 1200, 1900, 2000]
0900 + 1
0910 - 0
0940 + 1
0950 + 2
1100 + 3
1120 - 2
1130 - 1
1200 - 0
1500 + 1
1800 + 2
1900 - 1
2000 - 0
 */
class Solution3 {
    public int findPlatform(int[] Arrival, int[] Departure) {
        int n = Arrival.length;
        Arrays.sort(Arrival);
        Arrays.sort(Departure);
        int max = 0;
        int occupied = 0;
        int left = 0, right = 0;
        while (left < n && right < n) {
            if (Arrival[left] <= Departure[right]) {
                occupied++;
                max = Math.max(max, occupied);
                left++;
            } else {
                occupied--;
                right++;
            }
        }

        // By now, left would've reached n. Right may have some left, but we don't need that
        // Best case, nothing overlaps - left at n and right at n-1
        // Worst case, everything overlaps - left at n, right at 1

        return max;
    }
}