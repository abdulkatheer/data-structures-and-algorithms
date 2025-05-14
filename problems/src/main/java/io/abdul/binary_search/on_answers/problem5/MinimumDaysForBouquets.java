package io.abdul.binary_search.on_answers.problem5;

public class MinimumDaysForBouquets {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.roseGarden(8, new int[]{7, 7, 7, 7, 13, 11, 12, 7}, 3, 2));
        System.out.println(solution.roseGarden(5, new int[]{1, 10, 3, 10, 2}, 2, 3));
        System.out.println(solution.roseGarden(5, new int[]{1, 10, 3, 10, 2}, 1, 3));
    }
}

class Solution {
    public int roseGarden(int n, int[] nums, int k, int m) {
        int maxDays = Integer.MIN_VALUE;
        int minDays = Integer.MAX_VALUE;
        for (int num : nums) {
            maxDays = Math.max(num, maxDays);
            minDays = Math.min(num, minDays);
        }

        int low = minDays, high = maxDays;
        int result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;

            if (hasConsecutiveRoses(m, k, nums, mid)) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    private boolean hasConsecutiveRoses(int boquets, int roses, int[] nums, int days) {
        int count = 0;
        for (int num : nums) {
            if (num <= days) {
                count++;
            } else {
                count = 0; // reset
            }
            if (count == roses) { // a set formed
                boquets--;
                count = 0; // reset
            }
        }

        return boquets <= 0;
    }
}
