package io.abdul.array.sliding_window.problem1;

public class MaximumAverageSubarray {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4));
    }
}

// O(n)
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        double sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        double maxAvg = sum == 0 ? 0 : sum / k;

        int j = k;
        while (j < nums.length) {
            sum += nums[j];
            sum -= nums[j - k];
            double avg = sum == 0 ? 0 : sum / k;
            if (avg > maxAvg) {
                maxAvg = avg;
            }
            j++;
        }
        return maxAvg;
    }
}
