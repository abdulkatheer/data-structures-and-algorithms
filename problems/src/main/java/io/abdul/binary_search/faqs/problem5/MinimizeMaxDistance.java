package io.abdul.binary_search.faqs.problem5;

import java.util.PriorityQueue;

public class MinimizeMaxDistance {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.minimiseMaxDistance(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10));
        System.out.println(solution.minimiseMaxDistance(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 1));
        System.out.println(solution.minimiseMaxDistance(new int[]{3, 6, 12, 19, 33, 44, 67, 72, 89, 95}, 2));
    }
}

/*
Brute - Place gas station between max distance every time
T - O(k * n)
S - O(n)
 */
class Solution {
    public double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length;
        int[] gasStationsPlaced = new int[n - 1];

        for (int i = 1; i <= k; i++) {
            double max = -1;
            int maxPos = -1;

            // find max with gas stations already placed
            for (int j = 0; j < n - 1; j++) {
                int actualDist = arr[j + 1] - arr[j];
                int placed = gasStationsPlaced[j];
                double dist = (double) actualDist / (placed + 1);
                if (dist > max) {
                    max = dist;
                    maxPos = j;
                }
            }

            // Place 1 gas station at the max now
            gasStationsPlaced[maxPos]++;
        }

        // find max
        double max = -1;
        for (int i = 0; i < n - 1; i++) {
            int actualDist = arr[i + 1] - arr[i];
            int placed = gasStationsPlaced[i];
            double dist = (double) actualDist / (placed + 1);
            if (dist > max) {
                max = dist;
            }
        }

        return max;
    }
}

/*
Better - Heap (max heap) to store distances
T - O(k logn) - placing k, O(n logn) Init max-heap
S - O(n)
 */
class Solution2 {
    public double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length;

        PriorityQueue<Distance> distances = new
                PriorityQueue<>((o1, o2) -> Double.compare(o2.distance, o1.distance));
        for (int i = 0; i < n - 1; i++) {
            distances.add(new Distance(arr[i + 1] - arr[i], i, 1));
        }

        for (int i = 0; i < k; i++) {
            Distance max = distances.poll();// take the current max
            int newPlaced = max.placed + 1;
            double newDistance = (double) (arr[max.index + 1] - arr[max.index]) / newPlaced;

            max.distance = newDistance;
            max.placed = newPlaced;
            distances.add(max); // O(log n) time
        }

        return distances.peek().distance; // max after placing all
    }

    private static class Distance {
        private double distance;
        private int index;
        private int placed;

        public Distance(double distance, int index, int placed) {
            this.distance = distance;
            this.index = index;
            this.placed = placed;
        }
    }
}

/*
Optimal - Binary Search (on answers type)
T - O()
S - O(1)

We know that the maximum possible distance can be, when no additional gas stations added, max of current will the max.
So from 0.000000 is min-max low and max(arr) is min-max high
min-max low can be greater than this like min-distance/(k+1)

search range is 0.000000 and max.000000, only 6 decimals
if max is 1
0.000000 to 1.000000
mid = 0.500000 -> 0.7500000 -> 0.875000 -> 0.937500 -> 0.968750 -> 0.984375 -> 0.9921875 | Stop
mid = 0.500000 -> 0.2500000 -> 0.125000 -> 0.062500 -> 0.031250 -> 0.015625 -> 0.0078125 | Stop

How to check if mid is less than? It's difficult. That's why we check high-low > 0.000001
 */
class Solution3 {
    public double minimiseMaxDistance(int[] arr, int k) {
        double low = 0.0;
        double high = -1;
        for (int i = 0; i < arr.length - 1; i++) {
            high = Math.max(arr[i + 1] - arr[i], high);
        }

        double maxFractionAllowed = 1e-6; // 0.000001
        // can check > 0 as well, but we'll end up searching a very larger search space and takes more time
        /*
        Why low <= high won't work?
        Bcz it's difficult to set low and high by adding/subtracting a fraction of 1 to mid. So we set mid itself
        In this case, at some point, (low+high)/2 remains the same even after updating mid.
         */
        double answer = 0;
        while (high - low > maxFractionAllowed) {
            double mid = (low + high) / 2;

            int placed = placeGasStations(arr, mid);
            if (placed <= k) { // Enough stations, decrease distance to find any better available
                answer = mid;
                high = mid;
            } else { // Need more stations, increase distance
                low = mid;
            }
        }

        /*
         Loop will stop only when the difference between high and low is too low (<1 * 10^-6)
         That can happen when high moves left or low moves right
         It may happen right after finding answer and high moves left or anytime after that
         For ex, when answer is 0.5
         high=0.5 and low=0.0
         low = 0.25, increasing distance to match k
         low = 0.375 -> 0.4375 -> 0.46875 -> 0.484375 -> 0.4921875 -> 0.49609375 -> 0.49609375 -> 0.4990234375 -> 0.49951171875 -> 0.499755859375
         0.4998779296875 -> 0.49993896484375 -> 0.499969482421875 -> 0.4999847412109375 -> 0.4999847412109375 -> 0.49999237060546875
         0.4999961853027344 -> 0.4999980926513672 (diff 0000019073486328 not greater than 0.000001) | Stop
        */
        return answer;
    }

    /*
    Counts how many gas stations required to achieve this maxDistance condition
     */
    private int placeGasStations(int[] arr, double maxDistanceAllowed) {
        int gasStations = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            double actualDistance = arr[i + 1] - arr[i];
            int gasStationsRequired = (int) (actualDistance / maxDistanceAllowed);

            /*
            actualDistance = 1.0, maxDistanceAllowed = 0.5, gasStationsRequired = 2.0, but we actually need to place 1
            actualDistance = 1.0, maxDistanceAllowed = 0.4, gasStationsRequired = 2.5, but we actually need to place 2
            So when exact match, need to take int minus 1 and when overflows, just take the int
             */
            if (actualDistance == gasStationsRequired * maxDistanceAllowed) { // Not rounded
                gasStationsRequired--;
            }

            gasStations += gasStationsRequired;
        }
        return gasStations;
    }
}