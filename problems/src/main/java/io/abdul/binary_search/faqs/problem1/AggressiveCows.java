package io.abdul.binary_search.faqs.problem1;

import java.util.Arrays;

public class AggressiveCows {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.aggressiveCows(new int[]{0, 3, 4, 7, 10, 9}, 4));
        System.out.println(solution.aggressiveCows(new int[]{4, 2, 1, 3, 6}, 2));
        System.out.println(solution.aggressiveCows(new int[]{10, 1, 2, 7, 5}, 3));
    }
}

/*
Brute - Linear Search on distances
T - O(n log n) + O(n * maximum_of_minimum) -> We iterate all elements to place cows for maximum_of_minimum times
S - O(1)
 */
class Solution {
    public int aggressiveCows(int[] nums, int k) {
        Arrays.sort(nums); // To keep adjacent stalls together.
        // Intuition for sorting: Distance between more than two stalls will always be higher than distance between two adjacent stalls.

        int minDistance = 1;
        int maxDistance = nums[nums.length - 1] - nums[0];// max distance is between first and last stall
        int maxMin = minDistance;
        for (int i = minDistance; i <= maxDistance; i++) {
            int cowsLeft = k - 1; // For 5 cows, we can only check for 4 distances c1 -- c2 -- c3 -- c4 -- c5
            // Intuition: One cow has to be placed at 0th position, bcz the maximum distance is between 0th and nth position only.
            // To increase distance, 2nd cow has to be moved.
            int left = 0, right = 1;
            while (right < nums.length) {
                if (nums[right] - nums[left] >= i) {
                    cowsLeft--;
                    left = right;
                }
                right++;
                if (cowsLeft == 0) {
                    break;
                }
            }
            if (cowsLeft > 0) { // Can't try this and anymore future min distances
                break;
            }
            maxMin = i;
        }

        return maxMin;
    }
}

/*
Optimal - Binary Search on distances between stalls
T - O(n log n) + O(n log maximum_of_minimum)
S - O(1)
 */
class Solution2 {
    public int aggressiveCows(int[] nums, int k) {
        Arrays.sort(nums); // Now stall at smallest distance located first and longest distance located last

        int minDistance = 1; // least distance between stalls
        int maxDistance = nums[nums.length - 1] - nums[0];

        int low = minDistance, high = maxDistance, maxMinDistance = minDistance;
        while (low <= high) {
            int mid = (low + high) / 2;

            if (canPlaceCows(nums, k, mid)) { // look for better mid
                maxMinDistance = mid;
                low = mid + 1;
            } else { // Look for smaller mid
                high = mid - 1;
            }
        }
        return maxMinDistance;
    }

    private boolean canPlaceCows(int[] distances, int cows, int minDistance) {
        int left = 0, right = 1;
        cows--; // best position for first cow will be 0
        while (right < distances.length) {
            if (distances[right] - distances[left] >= minDistance) {
                cows--;
                left = right; // reset
            }
            right++;
            if (cows == 0) {
                break;
            }
        }
        return cows == 0;
    }
}
